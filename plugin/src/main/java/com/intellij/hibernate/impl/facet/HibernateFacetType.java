package com.intellij.hibernate.impl.facet;

import com.intellij.hibernate.HibernateMessages;
import com.intellij.hibernate.facet.HibernateFacet;
import com.intellij.hibernate.facet.HibernateFacetConfiguration;
import com.intellij.hibernate.view.HibernateIcons;
import com.intellij.hibernate.model.HibernateDescriptorsConstants;
import com.intellij.java.impl.util.descriptors.ConfigFileFactory;
import com.intellij.java.impl.util.descriptors.ConfigFileMetaDataRegistry;
import consulo.module.Module;
import consulo.ui.image.Image;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

/**
 * @author Gregory.Shrago
 */
public class HibernateFacetType {

  public static final HibernateFacetType INSTANCE = new HibernateFacetType();

  private final String myName;

  public HibernateFacetType() {
    myName = HibernateMessages.message("hibernate.facet.type.presentable.name");
  }

  public String getDefaultFacetName() {
    return myName;
  }

  public HibernateFacetConfiguration createDefaultConfiguration() {
    final ConfigFileFactory factory = ConfigFileFactory.getInstance();
    final ConfigFileMetaDataRegistry metadataRegistry = factory.createMetaDataRegistry();
    metadataRegistry.registerMetaData(HibernateDescriptorsConstants.HIBERNATE_CONFIGURATION_META_DATA);
    return new HibernateFacetConfiguration(factory.createConfigFileInfoSet(metadataRegistry));
  }

  public HibernateFacet createFacet(@Nonnull Module module, String name, @Nonnull HibernateFacetConfiguration configuration) {
    return new HibernateFacet(module, name, configuration);
  }

  @Nullable
  public Image getIcon() {
    return HibernateIcons.HIBERNATE_ICON;
  }

  public String getHelpTopic() {
    return "IntelliJ.IDEA.Procedures.Java.EE.Development.Managing.Facets.Facet.Specific.Settings.Hibernate";
  }
}
