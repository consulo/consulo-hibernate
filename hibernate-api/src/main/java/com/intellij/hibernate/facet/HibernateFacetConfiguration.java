package com.intellij.hibernate.facet;

import consulo.util.xml.serializer.InvalidDataException;
import consulo.util.xml.serializer.JDOMExternalizer;
import consulo.util.xml.serializer.WriteExternalException;
import com.intellij.persistence.facet.PersistenceFacetConfiguration;
import com.intellij.java.impl.util.descriptors.ConfigFileInfoSet;
import org.jdom.Element;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Gregory.Shrago
 */
public class HibernateFacetConfiguration implements PersistenceFacetConfiguration {
  private final ConfigFileInfoSet myDescriptorsConfiguration;
  private final Map<String, String> myUnitToDataSourceMap = new HashMap<String, String>();
  private static final String VALIDATION_ENABLED = "validation-enabled";

  public HibernateFacetConfiguration(final ConfigFileInfoSet descriptorsConfiguration) {
    myDescriptorsConfiguration = descriptorsConfiguration;
  }

  // TODO: Editor tabs were previously created using Facet UI framework (FacetEditorTab, FacetEditorContext, etc.)
  // which is no longer available in Consulo 3. Configuration UI should be provided through ModuleExtension configurable.

  public void readExternal(Element element) throws InvalidDataException {
    JDOMExternalizer.readMap(element, myUnitToDataSourceMap, "datasource-map", "unit-entry");
    myDescriptorsConfiguration.readExternal(element);
  }

  public void writeExternal(Element element) throws WriteExternalException {
    JDOMExternalizer.writeMap(element, myUnitToDataSourceMap, "datasource-map", "unit-entry");
    myDescriptorsConfiguration.writeExternal(element);
  }

  public ConfigFileInfoSet getDescriptorsConfiguration() {
    return myDescriptorsConfiguration;
  }

  public Map<String, String> getUnitToDataSourceMap() {
    return myUnitToDataSourceMap;
  }
}
