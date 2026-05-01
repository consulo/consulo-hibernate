package com.intellij.hibernate.model.xml.mapping;

import com.intellij.javaee.model.xml.JavaeeDomModelElement;
import com.intellij.javaee.model.JavaeePersistenceORMResolveConverters;
import com.intellij.persistence.model.TableInfoProvider;
import consulo.xml.dom.Convert;
import consulo.xml.dom.GenericAttributeValue;

/**
 * @author Gregory.Shrago
 */
public interface HbmTableInfoProvider extends TableInfoProvider, JavaeeDomModelElement
{
  @Convert(JavaeePersistenceORMResolveConverters.TableResolver.class)
  GenericAttributeValue<String> getTableName();

  @Convert(JavaeePersistenceORMResolveConverters.CatalogResolver.class)
  GenericAttributeValue<String> getCatalog();

  @Convert(JavaeePersistenceORMResolveConverters.SchemaResolver.class)
  GenericAttributeValue<String> getSchema();
}
