/*
 * Copyright (c) 2000-2007 JetBrains s.r.o. All Rights Reserved.
 */

package com.intellij.hibernate.impl.engine;

import com.intellij.hibernate.localize.HibernateLocalize;
import com.intellij.hibernate.model.HibernateDescriptorsConstants;
import com.intellij.hibernate.model.HibernatePropertiesConstants;
import com.intellij.hibernate.model.xml.config.HibernateConfiguration;
import com.intellij.hibernate.model.xml.config.SessionFactory;
import com.intellij.hibernate.util.HibernateUtil;
import com.intellij.jam.JamElement;
import com.intellij.java.language.psi.JavaPsiFacade;
import com.intellij.java.language.psi.PsiClass;
import com.intellij.java.language.psi.PsiJavaPackage;
import com.intellij.javaee.dataSource.DataSource;
import com.intellij.javaee.dataSource.DataSourceManager;
import com.intellij.javaee.model.common.persistence.mapping.Entity;
import com.intellij.javaee.module.view.dataSource.LocalDataSource;
import com.intellij.javaee.module.view.dataSource.classpath.DataSourceClasspathElement;
import com.intellij.jpa.model.annotations.mapping.JamEntityMappings;
import com.intellij.persistence.database.psi.DbDataSourceElement;
import com.intellij.persistence.model.PersistenceMappings;
import com.intellij.persistence.model.PersistencePackage;
import com.intellij.persistence.run.AbstractQueryLanguageConsole;
import com.intellij.persistence.run.ConsoleContextProvider;
import com.intellij.persistence.run.ConsoleOutputProcessAdapter;
import com.intellij.persistence.util.PersistenceUtil;
import consulo.application.ApplicationManager;
import consulo.application.util.function.Computable;
import consulo.compiler.CompilerPaths;
import consulo.dataContext.DataContext;
import consulo.dataContext.DataManager;
import consulo.disposer.Disposable;
import consulo.execution.ExecutionManager;
import consulo.execution.executor.DefaultRunExecutor;
import consulo.execution.runner.ExecutionEnvironment;
import consulo.execution.runner.ProgramRunner;
import consulo.execution.runner.RunnerRegistry;
import consulo.execution.ui.RunContentDescriptor;
import consulo.fileTemplate.FileTemplate;
import consulo.fileTemplate.FileTemplateManager;
import consulo.language.codeStyle.CodeStyleManager;
import consulo.language.psi.PsiFile;
import consulo.language.psi.PsiFileFactory;
import consulo.language.psi.scope.GlobalSearchScope;
import consulo.language.util.IncorrectOperationException;
import consulo.module.Module;
import consulo.module.content.ProjectFileIndex;
import consulo.module.content.ProjectRootManager;
import consulo.process.BaseProcessHandler;
import consulo.process.ExecutionException;
import consulo.process.ProcessHandler;
import consulo.process.event.ProcessEvent;
import consulo.process.event.ProcessListener;
import consulo.util.collection.ContainerUtil;
import consulo.util.concurrent.ActionCallback;
import consulo.util.io.ClassPathUtil;
import consulo.util.io.FileUtil;
import consulo.util.lang.Pair;
import consulo.util.lang.StringUtil;
import consulo.util.lang.ref.Ref;
import consulo.virtualFileSystem.VirtualFile;
import consulo.virtualFileSystem.fileType.FileTypeRegistry;
import consulo.virtualFileSystem.util.VirtualFileUtil;
import consulo.xml.language.psi.XmlFile;
import consulo.xml.dom.DomFileElement;
import consulo.xml.dom.DomManager;
import consulo.xml.dom.GenericValue;
import consulo.xml.dom.ModelMergerUtil;
import gnu.trove.THashSet;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import org.jdom.Element;
import org.jetbrains.annotations.NonNls;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;

/**
 * @author Gregory.Shrago
 */
public class HibernateEngine implements Disposable {
  @NonNls static final String MAIN_CLASS = "com.intellij.hibernate.console.HibernateConsoleMain";

  // TODO: DefaultProgramRunner.CONTENT_TO_REUSE was removed in Consulo 3 - define locally for compatibility
  @SuppressWarnings("unchecked")
  private static final consulo.util.dataholder.Key<RunContentDescriptor> CONTENT_TO_REUSE = consulo.util.dataholder.Key.create("DefaultProgramRunner.CONTENT_TO_REUSE");

  private static final Object WAITING = new Object();

  private final HibernateConsoleContextProvider.HibernateRunContext myRunContext;
  private AbstractQueryLanguageConsole.OutputHandler myOutputHandler;
  private volatile ProcessHandler myProcessHandler;
  private RunContentDescriptor myDescriptor;
  private File myTemporaryHibernateConfig;
  private final Ref<Object> myResponse = Ref.create(null);
  private ProcessListener myProcessListener;

  private enum State {INIT, CONFIG, FACTORY}
  private State myState = null;

  public static boolean isAvailable(final Module module ) {
    final PsiClass psiClass = JavaPsiFacade.getInstance(module.getProject())
      .findClass("org.hibernate.engine.SessionFactoryImplementor", GlobalSearchScope.moduleWithDependenciesAndLibrariesScope(module));
    final PsiFile containingFile = psiClass == null? null : psiClass.getContainingFile();
    return containingFile != null &&
           ProjectRootManager.getInstance(module.getProject()).getFileIndex().isInLibraryClasses(containingFile.getVirtualFile());
  }

  public HibernateEngine(final HibernateConsoleContextProvider.HibernateRunContext runContext) {
    myRunContext = runContext;
    myProcessListener = createProcessListener(new ActionCallback());
  }

  public boolean isWaitingForResponse() {
    synchronized (myResponse) {
      return WAITING.equals(myResponse.get());
    }
  }

  public boolean isInitialized() {
    final ProcessHandler processHandler = myProcessHandler;
    return processHandler != null && !processHandler.isProcessTerminating() && !processHandler.isProcessTerminated();
  }

  public void setOutputHandler(final AbstractQueryLanguageConsole.OutputHandler outputHandler) {
    myOutputHandler = outputHandler;
  }

  public AbstractQueryLanguageConsole.OutputHandler getOutputHandler() {
    return myOutputHandler;
  }

  public ProcessHandler getProcessHandler() {
    return myProcessHandler;
  }

  public String[] getClassPath() {
    final ArrayList<String> result = new ArrayList<String>();
    ContainerUtil.addIfNotNull(result, ClassPathUtil.getJarPathForClass(StringUtil.class));
    ContainerUtil.addIfNotNull(result, ClassPathUtil.getJarPathForClass(Nonnull.class));

    final PersistencePackage unit = myRunContext.getPersistenceUnit();
    if (unit != null) {
      final Collection<DbDataSourceElement> dataSources =
        PersistenceUtil.getDataSources(myRunContext.getModule().getProject(), Collections.singletonList(unit));
      if (dataSources.size() == 1) {
        final Object delegate = dataSources.iterator().next().getDelegate();
        if (delegate instanceof LocalDataSource) {
          for (DataSourceClasspathElement pathElement : ((LocalDataSource)delegate).getClasspathElements()) {
            for (String url : pathElement.getClassesRootUrls()) {
              ContainerUtil.addIfNotNull(result, VirtualFileUtil.urlToPath(url));
            }
          }
        }
      }
    }
    return result.toArray(new String[result.size()]);
  }

  public ProcessListener getProcessListener() {
    return myProcessListener;
  }

  public static String getJarPath() {
    return AbstractQueryLanguageConsole.getConsoleJarPath(HibernateEngine.class, MAIN_CLASS);
  }

  private ConsoleOutputProcessAdapter createProcessListener(final ActionCallback callback) {
    return new ConsoleOutputProcessAdapter() {

      @Override
      public void processTerminated(final ProcessEvent event) {
        super.processTerminated(event);
        myState = null;
        myProcessHandler = null;
        cleanup();
      }

      @Override
      public void startNotified(final ProcessEvent event) {
        super.startNotified(event);
        myState = State.INIT;
        myProcessHandler = event.getProcessHandler();
        myProcessHandler.putUserData(BaseProcessHandler.SILENTLY_DESTROY_ON_CLOSE, Boolean.TRUE);
        myDescriptor = ExecutionManager.getInstance(myRunContext.getModule().getProject()).getContentManager().findContentDescriptor(
                DefaultRunExecutor.getRunExecutorInstance(), myProcessHandler);
        setResponse(true);
        if (isInitialized()) {
          callback.setDone();
        }
        else {
          callback.setRejected();
        }
      }

      public AbstractQueryLanguageConsole.OutputHandler getOutputHandler() {
        return myOutputHandler;
      }

      @Override
      protected int handleDefaultOutput(final Element root) {
        resetCurrentResultNumber();
        return super.handleDefaultOutput(root);
      }
    };
  }

  public ActionCallback ensureInitialized() {
    final ProgramRunner runner = RunnerRegistry.getInstance().findRunnerById(DefaultRunExecutor.EXECUTOR_ID);
    if (runner != null) {
      final ActionCallback result = new ActionCallback();
      myProcessListener = createProcessListener(result);
      try {
        setResponse(WAITING);
        final DataContext dataContext = DataContext.builder()
                                                         .parent(DataManager.getInstance().getDataContext())
                                                         .add(ConsoleContextProvider.RUN_CONTEXT, myRunContext)
                                                         .add(CONTENT_TO_REUSE, myDescriptor)
                                                         .build();
        final ExecutionEnvironment env = new ExecutionEnvironment(myRunContext.getRunConfiguration(), DefaultRunExecutor.getRunExecutorInstance(), myRunContext.getModule().getProject(), null);
        env.setDataContext(dataContext);
        env.setContentToReuse(myDescriptor);
        runner.execute(env);
      }
      catch (ExecutionException e) {
        myOutputHandler.error(null, e);
        setResponse(false);
        result.setRejected();
      }
      return result;
    }
    else {
      return new ActionCallback.Rejected();
    }
  }

  private void cleanup() {
    if (myTemporaryHibernateConfig != null) {
      myTemporaryHibernateConfig.delete();
      myTemporaryHibernateConfig = null;
    }
  }

  public void getGeneratedSql(final String hqlQuery) {
    ensureState(State.FACTORY);
    sendCommand("translateQuery " + hqlQuery);
  }

  public void executeHqlQuery(final String hqlQuery, final Map<String,String> parameters) {
    ensureState(State.FACTORY);
    sendCommand("prepareHql " + hqlQuery);
    sendCommand("executeQuery "+ AbstractQueryLanguageConsole.encodeMap(parameters));
  }

  public void executeSqlQuery(final String sqlQuery, final Map<String, String> parameters) {
    ensureState(State.FACTORY);
    sendCommand("prepareSql " + sqlQuery);
    sendCommand("executeQuery " + AbstractQueryLanguageConsole.encodeMap(parameters));
  }

  public void getGeneratedDdl() {
    ensureState(State.CONFIG);
    sendCommand("generateDDL");
  }

  private void sendCommand(@NonNls final String command) {
    try {
      final ProcessHandler processHandler = myProcessHandler;
      if (processHandler != null) {
        final OutputStream input = processHandler.getProcessInput();
        if (input != null) {
          input.write(StringUtil.escapeStringCharacters(command).getBytes());
          input.write(("\n").getBytes());
          input.flush();
        }
      }
    }
    catch (IOException e) {
      myOutputHandler.error(null, e);
    }
  }

  private void ensureState(final State requiredState) {
    while (myState.compareTo(requiredState) < 0) {
      if (myState == State.INIT) {
        final Pair<File, String> pair = getHibernateConfigAndFactoryClass();
        if (pair == null) {
          throw new IllegalStateException(HibernateLocalize.hqlconsoleUnitNotFound().get());
        }
        final String cfgClass = myRunContext.getRunConfiguration() != null ? myRunContext.getRunConfiguration().USER_CFG_CLASS : null;
        sendCommand("initUserConfigurator "+ (StringUtil.isEmpty(cfgClass) ? "" : cfgClass));
        sendCommand("setConfigurationClass "+pair.second);
        sendCommand("initConfiguration "+ pair.first);
        myState = State.CONFIG;
      }
      else if (myState == State.CONFIG) {
        sendCommand("initSessionFactory");
        myState = State.FACTORY;
      }
    }
  }

  @Nullable
  private Pair<File, String> getHibernateConfigAndFactoryClass() {
    return ApplicationManager.getApplication().runReadAction(new Computable<Pair<File, String>>() {
      @Nullable
      public Pair<File, String> compute() {
        final PersistencePackage unit = myRunContext.getPersistenceUnit();
        if (unit == null) {
          return null;
        }
        else if (unit instanceof SessionFactory) {
          final PsiFile containingFile = unit.getContainingFile();
          assert containingFile != null;
          return Pair.create(VirtualFileUtil.virtualToIoFile(containingFile.getVirtualFile()), getHibernateConfigurationClass());
        }
        else {
          myTemporaryHibernateConfig = createTemporaryHibernateConfig(myRunContext.getModule(), unit);
          return Pair.create(myTemporaryHibernateConfig, getHibernateConfigurationClass());
        }
      }
    });
  }

  public void dispose() {
    cleanup();
    myDescriptor = null;
    setResponse(false);
  }

  private String getHibernateConfigurationClass() {
    final PersistenceMappings mappings = myRunContext.getFacet().getEntityMappings(myRunContext.getPersistenceUnit());
    for (PersistenceMappings persistenceMappings : ModelMergerUtil.getFilteredImplementations(mappings)) {
      if (persistenceMappings instanceof JamElement) {
        if (!persistenceMappings.getModelHelper().getPersistentEntities().isEmpty()) {
          return "org.hibernate.cfg.AnnotationConfiguration";
        }
      }
    }
    return "org.hibernate.cfg.Configuration";
  }

  private File createTemporaryHibernateConfig(final Module module, final PersistencePackage unit) {
    @NonNls final String fileName = module.getName().replace(' ', '_') + "-hibernate-" + System.currentTimeMillis() + ".cfg.xml";
    final FileTemplate template = FileTemplateManager.getInstance()
        .getJ2eeTemplate(HibernateDescriptorsConstants.HIBERNATE_CONFIGURATION_META_DATA.getDefaultVersion().getTemplateName());
    final XmlFile xmlFile = (XmlFile)PsiFileFactory.getInstance(module.getProject()).createFileFromText(fileName, FileTypeRegistry.getInstance().getFileTypeByExtension("xml"), template.getText());

    final DomFileElement<HibernateConfiguration> fileElement =
        DomManager.getDomManager(module.getProject()).getFileElement(xmlFile, HibernateConfiguration.class);
    assert fileElement != null;
    final SessionFactory sf = fileElement.getRootElement().getSessionFactory();
    for (GenericValue<PsiJavaPackage> value : unit.getModelHelper().getPackages()) {
      sf.addMapping().getPackage().setStringValue(value.getStringValue());
    }
    for (GenericValue<PsiFile> value : unit.getModelHelper().getJarFiles()) {
      sf.addMapping().getJar().setStringValue(value.getStringValue());
    }
    final THashSet<String> classesNames = new THashSet<String>();
    final List<? extends GenericValue<PsiClass>> classes = unit.getModelHelper().getClasses();
    for (GenericValue<PsiClass> value : classes) {
      ContainerUtil.addIfNotNull(classesNames, value.getStringValue());
    }
    if (classes.isEmpty()) {
      final JamEntityMappings jamMappings = ModelMergerUtil.getImplementation(
              myRunContext.getFacet().getEntityMappings(myRunContext.getPersistenceUnit()), JamEntityMappings.class);
      if (jamMappings != null) {
        for (Entity entity : jamMappings.getEntities()) {
          ContainerUtil.addIfNotNull(classesNames, entity.getClazz().getStringValue());
        }
      }
    }
    for (String name : classesNames) {
      sf.addMapping().getClazz().setStringValue(name);
    }
    final ProjectFileIndex index = ProjectRootManager.getInstance(module.getProject()).getFileIndex();
    for (GenericValue<PersistenceMappings> value : unit.getModelHelper().getMappingFiles(PersistenceMappings.class)) {
      final PersistenceMappings mappings = value.getValue();
      if (mappings != null) {
        final PsiFile containingFile = mappings.getContainingFile();
        assert containingFile != null;
        final VirtualFile virtualFile = containingFile.getVirtualFile();
        final VirtualFile sourceRoot = index.getSourceRootForFile(virtualFile);
        if (sourceRoot != null) {
          sf.addMapping().getResource().setStringValue(VirtualFileUtil.getRelativePath(virtualFile, sourceRoot, '/'));
        }
        else {
          // this will not work for orm.xml due to the bug in hibernate SAX parser configuration for "file" elements
          sf.addMapping().getFile().setStringValue(VirtualFileUtil.virtualToIoFile(containingFile.getVirtualFile()).getAbsolutePath());
        }
      }
    }
    final Properties properties = unit.getModelHelper().getPersistenceUnitProperties();
    for (Object key : properties.keySet()) {
      final String name = (String)key;
      HibernateUtil.setSessionFactoryProperty(sf, name, properties.getProperty(name));
    }
    if (!properties.containsKey(HibernateUtil.getFullPropertyName(HibernatePropertiesConstants.URL))) {
      final String dataSourceId = myRunContext.getFacet().getDataSourceId(myRunContext.getPersistenceUnit());
      if (dataSourceId != null) {
        final DataSource dataSource = DataSourceManager.getInstance(myRunContext.getModule().getProject()).getDataSourceByID(dataSourceId);
        if (dataSource instanceof LocalDataSource) {
          final LocalDataSource localDataSource = (LocalDataSource)dataSource;

          HibernateUtil.setSessionFactoryProperty(sf, HibernateUtil.getFullPropertyName(HibernatePropertiesConstants.DRIVER), localDataSource.getDriverClass());
          HibernateUtil.setSessionFactoryProperty(sf, HibernateUtil.getFullPropertyName(HibernatePropertiesConstants.URL), localDataSource.getUrl());
          HibernateUtil.setSessionFactoryProperty(sf, HibernateUtil.getFullPropertyName(HibernatePropertiesConstants.USER), localDataSource.getUsername());
          HibernateUtil.setSessionFactoryProperty(sf, HibernateUtil.getFullPropertyName(HibernatePropertiesConstants.PASS), localDataSource.getPassword());
        }
      }
    }
    try {
      CodeStyleManager.getInstance(module.getProject()).reformat(xmlFile);
    }
    catch (IncorrectOperationException e) {
      // nothing
    }
    final File path = CompilerPaths.getGeneratedDataDirectory(module.getProject());
    final File file = new File(path, fileName);
    try {
      file.getParentFile().mkdirs();
      FileUtil.writeToFile(file, xmlFile.getText().getBytes());
      return file;
    }
    catch (IOException e) {
      myOutputHandler.error(null, e);
    }
    return file;
  }

  private void setResponse(final Object response) {
    synchronized (myResponse) {
      myResponse.set(response);
      myResponse.notifyAll();
    }
  }

}
