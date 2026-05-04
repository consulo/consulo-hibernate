package com.intellij.hibernate.model;

import com.intellij.hibernate.localize.HibernateLocalize;
import com.intellij.java.impl.util.descriptors.ConfigFileVersion;
import com.intellij.java.impl.util.descriptors.ConfigFileMetaData;

/**
 * @author Gregory.Shrago
 */
public interface HibernateDescriptorsConstants {     
  String HIBERNATE_CONFIGURATION_VERSION = "3.0";

  String HIBERNATE_MAPPING_VERSION = "3.0";

  ConfigFileVersion[] HIBERNATE_CONFIGURATION_VERSIONS = {
    new ConfigFileVersion(HIBERNATE_CONFIGURATION_VERSION, HibernateConstants.TEMPLATE_HIBERNATE_CONFIGURATION_3_0)
  };

  ConfigFileMetaData HIBERNATE_CONFIGURATION_META_DATA =
    new ConfigFileMetaData(HibernateLocalize.deploymentDescriptorTitleConfigurationXml().get(), "hibernate.cfg.xml", "",
                           HIBERNATE_CONFIGURATION_VERSIONS, null, false, false, false);



  ConfigFileVersion[] HIBERNATE_MAPPING_VERSIONS = {
    new ConfigFileVersion(HIBERNATE_MAPPING_VERSION, HibernateConstants.TEMPLATE_HIBERNATE_MAPPING_3_0)
  };

  ConfigFileMetaData HIBERNATE_MAPPING_META_DATA =
    new ConfigFileMetaData(HibernateLocalize.deploymentDescriptorTitleMappingXml().get(), "mapping.hbm.xml", "",
                           HIBERNATE_MAPPING_VERSIONS, null, true, false, true);
}
