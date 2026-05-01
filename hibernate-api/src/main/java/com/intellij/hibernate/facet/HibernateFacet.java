package com.intellij.hibernate.facet;

import com.intellij.hibernate.model.HibernateDescriptorsConstants;
import com.intellij.hibernate.model.HibernatePropertiesConstants;
import com.intellij.hibernate.model.xml.config.HibernateConfiguration;
import com.intellij.hibernate.model.xml.config.SessionFactory;
import com.intellij.hibernate.model.xml.mapping.HbmHibernateMapping;
import com.intellij.hibernate.util.HibernateUtil;
import com.intellij.jam.model.util.JamCommonUtil;
import com.intellij.java.impl.util.descriptors.*;
import com.intellij.java.language.module.EffectiveLanguageLevelUtil;
import com.intellij.java.language.psi.util.PropertyMemberType;
import com.intellij.javaee.JavaeePersistenceDescriptorsConstants;
import com.intellij.javaee.JavaeeUtil;
import com.intellij.javaee.model.common.persistence.mapping.EntityMappings;
import com.intellij.jpa.JpaInspectionToolProvider;
import com.intellij.jpa.facet.JpaFacetImpl;
import com.intellij.jpa.highlighting.HibernateModelValidator;
import com.intellij.jpa.model.annotations.mapping.EntityMappingsImpl;
import com.intellij.jpa.model.common.MergedPersistenceMappings;
import com.intellij.persistence.facet.PersistenceFacetBase;
import com.intellij.persistence.facet.PersistencePackageDefaults;
import com.intellij.persistence.model.PersistenceMappings;
import com.intellij.persistence.model.PersistencePackage;
import com.intellij.persistence.model.validators.ModelValidator;
import com.intellij.persistence.util.PersistenceCommonUtil;
import com.intellij.persistence.util.PersistenceModelBrowser;
import com.intellij.java.language.LanguageLevel;
import com.intellij.ql.psi.impl.QLLanguage;
import consulo.application.ApplicationManager;
import consulo.application.util.CachedValue;
import consulo.application.util.CachedValueProvider;
import consulo.application.util.CachedValuesManager;
import consulo.disposer.Disposer;
import consulo.language.Language;
import consulo.language.psi.PsiManager;
import consulo.language.psi.PsiModificationTracker;
import consulo.module.Module;
import consulo.module.content.ModuleRootManager;
import consulo.util.collection.ContainerUtil;
import consulo.util.dataholder.Key;
import consulo.virtualFileSystem.VirtualFile;
import consulo.xml.dom.DomManager;
import consulo.xml.dom.GenericValueUtil;
import gnu.trove.THashSet;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.util.*;
import java.util.function.Function;

/**
 * @author Gregory.Shrago
 */
public class HibernateFacet extends PersistenceFacetBase<HibernateFacetConfiguration, PersistencePackage> {

  public static final String ID = "hibernate";

  private final ConfigFileContainer myDescriptorsContainer;
  private EntityMappingsImpl myAnnotationsPersistenceRoot;

  private final JpaFacetImpl.UnitDataSourceMap<PersistencePackage> myUnitDataSourceMap;
  private final CachedValue<List<PersistencePackage>> myUnitsValue;

  @Nullable
  public static HibernateFacet getInstance(@Nonnull final Module module) {
    // TODO: migrate to ModuleExtension lookup
    return null;
  }

  public HibernateFacet(@Nonnull final Module module,
                        final String name, @Nonnull final HibernateFacetConfiguration configuration) {
    super(module, name, configuration);
    myUnitDataSourceMap = new JpaFacetImpl.UnitDataSourceMap<PersistencePackage>(configuration.getUnitToDataSourceMap(), this);
    myDescriptorsContainer = ConfigFileFactory.getInstance().createConfigFileContainer(getModule().getProject(), configuration.getDescriptorsConfiguration().getMetaDataProvider(), configuration.getDescriptorsConfiguration());
    myDescriptorsContainer.addListener(new ConfigFileAdapter() {
      protected void configChanged(final ConfigFile configFile) {
      }
    });
    Disposer.register(this, myDescriptorsContainer);
    myUnitsValue = CachedValuesManager.getManager(module.getProject()).createCachedValue(new CachedValueProvider<List<PersistencePackage>>() {
      public Result<List<PersistencePackage>> compute() {
        return getPersistenceUnitsInner();
      }
    }, false);
  }

  @Nonnull
  public List<PersistencePackage> getExtensionSessionFactories() {
    return ContainerUtil.concat(HibernateSessionFactoryProvider.EP_NAME.getExtensionList(), new Function<HibernateSessionFactoryProvider, Collection<? extends PersistencePackage>>() {
      public Collection<PersistencePackage> apply(final HibernateSessionFactoryProvider provider) {
        return provider.getSessionFactories(HibernateFacet.this);
      }
    });
  }

  @Nonnull
  public List<HibernateConfiguration> getHibernateConfigurations() {
    final ArrayList<HibernateConfiguration> result = new ArrayList<HibernateConfiguration>();
    for (ConfigFile configFile : myDescriptorsContainer.getConfigFiles()) {
      if (configFile.getMetaData() == HibernateDescriptorsConstants.HIBERNATE_CONFIGURATION_META_DATA) {
        ContainerUtil.addIfNotNull(result, JamCommonUtil.getRootElement(configFile.getPsiFile(), HibernateConfiguration.class, getModule()));
      }
    }
    return result;
  }

  public List<HibernateConfiguration> getDefaultHibernateConfigurations() {
    final ArrayList<HibernateConfiguration> result = new ArrayList<HibernateConfiguration>();
    final PsiManager psiManager = PsiManager.getInstance(getModule().getProject());
    for (VirtualFile root : ModuleRootManager.getInstance(getModule()).getSourceRoots()) {
      for (VirtualFile child : root.getChildren()) {
        if (child.isDirectory()) continue;
        if (!child.getName().endsWith(".cfg.xml")) continue;
        ContainerUtil.addIfNotNull(result, JamCommonUtil.getRootElement(psiManager.findFile(child), HibernateConfiguration.class, getModule()));
      }
    }
    return result;
  }

  public ConfigFile[] getDescriptors() {
    return myDescriptorsContainer.getConfigFiles();
  }

  public ConfigFileContainer getDescriptorsContainer() {
    return myDescriptorsContainer;
  }

  @Nonnull
  public List<PersistencePackage> getPersistenceUnits() {
    return myUnitsValue.getValue();
  }

  private CachedValueProvider.Result<List<PersistencePackage>> getPersistenceUnitsInner() {
    final List<PersistencePackage> result = new ArrayList<PersistencePackage>();
    for (HibernateConfiguration configuration : getHibernateConfigurations()) {
      result.add(configuration.getSessionFactory());
    }
    result.addAll(getExtensionSessionFactories());
    return new CachedValueProvider.Result<List<PersistencePackage>>(
      result, PsiModificationTracker.MODIFICATION_COUNT);
  }

  @Nullable
  public EntityMappings getAnnotationEntityMappings() {
    refreshModel();
    return myAnnotationsPersistenceRoot;
  }

  @Nonnull
  public PersistenceMappings getEntityMappings(@Nonnull final PersistencePackage unit) {
    assert unit.isValid();
    refreshModel();
    final Set<PersistenceMappings> xmlMappings = HibernateUtil.getGenericValues(unit.getModelHelper().getMappingFiles(PersistenceMappings.class), new THashSet<PersistenceMappings>());
    final ArrayList<PersistenceMappings> allMappings = new ArrayList<PersistenceMappings>(xmlMappings);
    final Collection<String> classNames = GenericValueUtil.getClassStringCollection(unit.getModelHelper().getClasses(), new THashSet<String>());
    final Collection<String> packageNames = HibernateUtil.getStringValues(unit.getModelHelper().getPackages(), new THashSet<String>());
    final Collection<String> jarFiles = HibernateUtil.getStringValues(unit.getModelHelper().getJarFiles(), new THashSet<String>());
    if (myAnnotationsPersistenceRoot != null) {
      final PersistenceMappings entityMappings = myAnnotationsPersistenceRoot.createCustomMappings(null, classNames, jarFiles, packageNames);
      allMappings.add(0, entityMappings);
    }
    return allMappings.isEmpty()?
      DomManager.getDomManager(getModule().getProject()).createMockElement(HbmHibernateMapping.class, getModule(), true) :
      new MergedPersistenceMappings(this, allMappings);
  }

  @Nonnull
  public List<PersistenceMappings> getDefaultEntityMappings(@Nonnull final PersistencePackage unit) {
    return Collections.emptyList();
  }

  @Nonnull
  public Class<SessionFactory> getPersistenceUnitClass() {
    return SessionFactory.class;
  }

  @Nonnull
  public Map<ConfigFileMetaData, Class<? extends PersistenceMappings>> getSupportedDomMappingFormats() {
    final Map<ConfigFileMetaData, Class<? extends PersistenceMappings>> map = new LinkedHashMap<ConfigFileMetaData, Class<? extends PersistenceMappings>>();
    map.put(HibernateDescriptorsConstants.HIBERNATE_MAPPING_META_DATA, HbmHibernateMapping.class);
    map.put(JavaeePersistenceDescriptorsConstants.ORM_XML_META_DATA, com.intellij.javaee.model.xml.persistence.mapping.EntityMappings.class);
    return map;
  }

  public String getDataSourceId(@Nonnull final PersistencePackage unit) {
    return myUnitDataSourceMap.getDataSourceID(unit);
  }

  public void setDataSourceId(@Nonnull final PersistencePackage unit, final String dataSourceName) {
    myUnitDataSourceMap.setDataSourceID(unit, dataSourceName);
  }

  public Language getQlLanguage() {
    return QLLanguage.HIBERNATE_QL;
  }

  @Nonnull
  public ModelValidator getModelValidator(@Nullable final PersistencePackage unit) {
    final PersistenceModelBrowser browser = PersistenceCommonUtil.createFacetAndUnitModelBrowser(this, unit, null);
    return new HibernateModelValidator(browser);
  }

  @Nonnull
  public Class[] getInspectionToolClasses() {
    return new JpaInspectionToolProvider().getInspectionClasses();
  }

  private static final Key<CachedValue<PersistencePackageDefaults>> SESSION_FACTORY_DEFAULTS = Key.create("SESSION_FACTORY_DEFAULTS");

  @Nonnull
  public PersistencePackageDefaults getPersistenceUnitDefaults(@Nonnull final PersistencePackage unit) {
    assert unit.isValid();
    CachedValue<PersistencePackageDefaults> cachedValue = unit.getUserData(SESSION_FACTORY_DEFAULTS);
    if (cachedValue == null) {
      cachedValue = CachedValuesManager.getManager(unit.getPsiManager().getProject()).createCachedValue(new CachedValueProvider<PersistencePackageDefaults>() {
        public Result<PersistencePackageDefaults> compute() {
          final Properties properties = unit.getModelHelper().getPersistenceUnitProperties();
          final String schema = properties.getProperty(HibernateUtil.getFullPropertyName(HibernatePropertiesConstants.DEFAULT_SCHEMA));
          final String catalog = properties.getProperty(HibernateUtil.getFullPropertyName(HibernatePropertiesConstants.DEFAULT_CATALOG));
          final PersistencePackageDefaults result = new PersistencePackageDefaults() {
            @Nullable
            public String getSchema() {
              return schema;
            }

            @Nullable
            public String getCatalog() {
              return catalog;
            }

            @Nullable
            public PropertyMemberType getAccess() {
              return null;
            }
          };
          return new Result<PersistencePackageDefaults>(result, unit.getContainingFile());
        }
      }, false);
      unit.putUserData(SESSION_FACTORY_DEFAULTS, cachedValue);
    }
    return cachedValue.getValue();
  }

  private void refreshModel() {
    final boolean isJdk5 = LanguageLevel.JDK_1_5.compareTo(EffectiveLanguageLevelUtil.getEffectiveLanguageLevel(getModule())) <= 0;
    if (!getPersistenceUnits().isEmpty()) {
      if (isJdk5 && myAnnotationsPersistenceRoot == null) {
        myAnnotationsPersistenceRoot = new EntityMappingsImpl(this);
      }
    }
    else if (ApplicationManager.getApplication().isUnitTestMode()) {
      if (myAnnotationsPersistenceRoot == null) {
        myAnnotationsPersistenceRoot = new EntityMappingsImpl(this);
      }
    }
    else if (myAnnotationsPersistenceRoot != null) {
      myAnnotationsPersistenceRoot = null;
    }
  }


  public void initFacet() {
    JavaeeUtil.installDomAndJamListeners(this, myDescriptorsContainer);
  }

  public void disposeFacet() {
    if (myAnnotationsPersistenceRoot != null) {
      myAnnotationsPersistenceRoot = null;
    }
  }
}
