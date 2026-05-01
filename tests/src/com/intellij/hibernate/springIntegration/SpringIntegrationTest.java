package com.intellij.hibernate.springIntegration;

import consulo.module.extension.ModuleExtensionHelper;
import com.intellij.hibernate.HibernateInspectionToolProvider;
import com.intellij.hibernate.facet.HibernateFacet;
import com.intellij.hibernate.facet.HibernateFacetType;
import com.intellij.j2ee.J2EETestCase;
import com.intellij.javaee.dataSource.DataSource;
import com.intellij.jpa.JpaInspectionToolProvider;
import consulo.application.PathManager;
import consulo.application.Result;
import consulo.language.editor.WriteCommandAction;
import consulo.util.lang.Pair;
import consulo.virtualFileSystem.VirtualFile;
import com.intellij.persistence.model.PersistenceMappings;
import com.intellij.persistence.model.PersistencePackage;
import com.intellij.persistence.roles.PersistenceRoleHolder;
import com.intellij.persistence.util.PersistenceCommonUtil;
import consulo.xml.language.psi.XmlFile;
import com.intellij.spring.facet.SpringFacet;
import com.intellij.spring.facet.SpringFacetConfiguration;
import com.intellij.spring.facet.SpringFacetType;
import com.intellij.spring.facet.SpringFileSet;
import com.intellij.spring.model.highlighting.SpringModelInspection;
import com.intellij.testFramework.PsiTestUtil;
import com.intellij.testFramework.builders.JavaModuleFixtureBuilder;
import com.intellij.testFramework.fixtures.JavaCodeInsightFixtureTestCase;
import consulo.xml.dom.GenericValue;
import consulo.language.editor.inspection.InspectionToolProvider;

import java.io.File;
import java.util.List;
import java.util.Set;

/**
 * @author Gregory.Shrago
 */
public class SpringIntegrationTest extends JavaCodeInsightFixtureTestCase {

  private SpringFacet mySpringFacet;
  private HibernateFacet myFacet;

  @Override
  protected String getBasePath() {
    return "/svnPlugins/hibernate/tests/testData/springIntegration";
  }

  protected void setUp() throws Exception {
    super.setUp();
    J2EETestCase.addJavaeeLibraryToRoots(myModule);
    PsiTestUtil.addLibrary(myModule, "Hibernate", PathManager.getHomePath() + "/lib/", "hibernate3.jar");
    PsiTestUtil.addLibrary(myModule, "Spring", PathManager.getHomePath() + "/lib/", "spring.jar");

    final Pair<HibernateFacet, SpringFacet> pair = setupFacets();
    myFacet = pair.first;
    mySpringFacet = pair.second;
    PersistenceRoleHolder.getInstance(getProject());
    myFixture.enableInspections(new JpaInspectionToolProvider(), new HibernateInspectionToolProvider(), new InspectionToolProvider() {
      public Class[] getInspectionClasses() {
        return new Class[]{SpringModelInspection.class};
      }
    });
  }

  @Override
  protected void tuneFixture(final JavaModuleFixtureBuilder moduleBuilder) {
    moduleBuilder.setMockJdkLevel(JavaModuleFixtureBuilder.MockJdkLevel.jdk15);
  }

  public void test1() throws Throwable {
    doTest();
  }

  private void doTest() throws Throwable {
    final VirtualFile beansXml = myFixture.copyFileToProject(getTestName(true) + "/beans.xml", "beans.xml");
    final VirtualFile mappingXml = myFixture.copyFileToProject(getTestName(true) + "/pkg/mapping.hbm.xml", "pkg/mapping.hbm.xml");
    final VirtualFile mapping2Xml = myFixture.copyFileToProject(getTestName(true) + "/pkg/mapping2.hbm.xml", "pkg/mapping2.hbm.xml");
    final VirtualFile classesJava = myFixture.copyFileToProject(getTestName(true) + "/pkg/classes.java", "pkg/classes.java");
    myFixture.allowTreeAccessForFile(classesJava);
    final DataSource dataSource = J2EETestCase.loadDataSourceFromFile(myModule, new File(
      PathManager.getHomePath() + getBasePath() + "/" + getTestName(true) + "/datasource.xml"));

    final SpringFacetConfiguration configuration = mySpringFacet.getConfiguration();
    final Set<SpringFileSet> list = configuration.getFileSets();
    final SpringFileSet fileSet = new SpringFileSet("", "default", configuration);
    list.add(fileSet);
    fileSet.addFile(beansXml);
    configuration.setModified();

    final XmlFile beansPsiFile = (XmlFile)getPsiManager().findFile(beansXml);
    assertNotNull(beansPsiFile);

    final List<PersistencePackage> units = myFacet.getPersistenceUnits();
    assertEquals(1, units.size());

    myFacet.setDataSourceId(myFacet.getPersistenceUnits().get(0), dataSource.getUniqueId());

    final PersistencePackage unit = units.get(0);
    assertEquals(beansPsiFile, unit.getContainingFile());
    final List<? extends GenericValue<PersistenceMappings>> mappings = myFacet.getPersistenceUnits().get(0).getModelHelper().getMappingFiles(PersistenceMappings.class);
    assertEquals(2, mappings.size());

    final PersistenceMappings mapping1 = mappings.get(0).getValue();
    assertNotNull(mapping1);
    assertNotNull(PersistenceCommonUtil.queryPersistentObjects(mapping1).findFirst());

    final PersistenceMappings mapping2 = mappings.get(1).getValue();
    assertNotNull(mapping2);
    assertNotNull(PersistenceCommonUtil.queryPersistentObjects(mapping2).findFirst());

    myFixture.testHighlightingAllFiles(true, false, false, beansXml, mappingXml, classesJava);
  }

  protected Pair<HibernateFacet, SpringFacet> setupFacets() {
    return new WriteCommandAction<Pair<HibernateFacet, SpringFacet>>(getProject()) {
      protected void run(final Result<Pair<HibernateFacet, SpringFacet>> result) throws Throwable {
        // TODO: FacetManager.addFacet() removed; Spring facet creation needs its own migration
        final SpringFacet springFacet = null; // TODO: SpringFacetType.INSTANCE equivalent for Consulo 3
        HibernateFacetType type = HibernateFacetType.INSTANCE;
        final HibernateFacet facet = type.createFacet(myModule, type.getDefaultFacetName(), type.createDefaultConfiguration());
        //facet.getConfiguration().getDescriptorsConfiguration().addConfigFile(HibernateDescriptorsConstants.HIBERNATE_CONFIGURATION_META_DATA, cfgXml.getUrl());

        result.setResult(Pair.create(facet, springFacet));
      }
    }.execute().getResultObject();
  }

}
