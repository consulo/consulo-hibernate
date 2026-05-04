package com.intellij.hibernate.impl.facet.ui;

import com.intellij.hibernate.localize.HibernateLocalize;
import com.intellij.hibernate.facet.HibernateFacet;
import com.intellij.hibernate.facet.HibernateFacetConfiguration;
import com.intellij.hibernate.model.HibernateDescriptorsConstants;
import consulo.module.Module;
import consulo.configurable.ConfigurationException;
import consulo.configurable.UnnamedConfigurable;
import consulo.module.content.ModuleRootManager;
import consulo.virtualFileSystem.VirtualFile;
import com.intellij.persistence.facet.PersistenceHelper;
import com.intellij.java.impl.util.descriptors.ConfigFileFactory;
import com.intellij.java.impl.util.descriptors.ConfigFileInfoSet;
import com.intellij.java.impl.util.descriptors.ConfigFileMetaData;
import com.intellij.java.impl.util.descriptors.ConfigFileMetaDataProvider;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.Arrays;

/**
 * @author Gregory.Shrago
 *
 * TODO: Previously extended FacetEditorTab from consulo.ide.impl.idea.facet.ui.
 * The Facet UI framework is no longer available in Consulo 3.
 * Configuration UI should be provided through ModuleExtension configurable.
 */
public class HibernateGeneralEditorTab {
  private JPanel myMainPanel;
  private JPanel myDescriptorsPanel;
  private JPanel myDatasourceMapPanel;

  @Nullable
  private final UnnamedConfigurable myDataSourceComponent;
  @Nullable
  private final HibernateFacet myFacet;
  private ConfigFileInfoSet myFileInfoSet;
  private final HibernateFacetConfiguration myConfiguration;

  public HibernateGeneralEditorTab(final HibernateFacetConfiguration configuration, @Nonnull Module module, @Nullable HibernateFacet facet) {
    myConfiguration = configuration;
    myFacet = facet;
    myMainPanel = new JPanel(new BorderLayout());
    myDatasourceMapPanel = new JPanel(new BorderLayout());
    if (myFacet != null) {
      myDataSourceComponent = PersistenceHelper.getHelper().createUnitToDataSourceMappingComponent(myFacet, false);
      myDatasourceMapPanel.add(myDataSourceComponent.createComponent(), BorderLayout.CENTER);
    } else {
      myDataSourceComponent = null;
    }
    myFileInfoSet = myFacet == null
        ? ConfigFileFactory.getInstance().createConfigFileInfoSet(
            ConfigFileFactory.getInstance().createMetaDataProvider(
                new ConfigFileMetaData[]{HibernateDescriptorsConstants.HIBERNATE_CONFIGURATION_META_DATA}))
        : myFacet.getConfiguration().getDescriptorsConfiguration();
    myMainPanel.add(myDatasourceMapPanel, BorderLayout.CENTER);
  }

  public String getDisplayName() {
    return HibernateLocalize.tabTitleHibernateGeneralSettings().get();
  }

  public JComponent createComponent() {
    return myMainPanel;
  }

  public boolean isModified() {
    if (myDataSourceComponent != null && myDataSourceComponent.isModified()) return true;
    return false;
  }

  public void apply() throws ConfigurationException {
    if (myDataSourceComponent != null) {
      myDataSourceComponent.apply();
    }
  }

  public void reset() {
    if (myDataSourceComponent != null) {
      myDataSourceComponent.reset();
    }
  }

  public void disposeUIResources() {
    if (myDataSourceComponent != null) {
      myDataSourceComponent.disposeUIResources();
    }
  }
}
