package com.intellij.hibernate.impl.model.xml.impl.mapping;

import com.intellij.hibernate.model.xml.mapping.HbmGenerator;
import com.intellij.hibernate.model.xml.mapping.HbmParam;
import com.intellij.hibernate.impl.model.xml.impl.HibernateBaseImpl;
import consulo.util.lang.StringUtil;
import consulo.util.collection.ArrayUtil;
import consulo.xml.dom.GenericValue;
import com.intellij.hibernate.impl.model.xml.impl.HibernateReadOnlyValue;

/**
 * @author Gregory.Shrago
 */
public abstract class HbmGeneratorImpl extends HibernateBaseImpl implements HbmGenerator {
  public GenericValue<String> getTableName() {
    return findParameter("table", "tables", "target_tables");
  }

  public GenericValue<String> getCatalog() {
    return findParameter("catalog");
  }

  public GenericValue<String> getSchema() {
    return findParameter("schema");
  }

  private GenericValue<String> findParameter(final String... names) {
    for (HbmParam param : getParams()) {
      final String name = param.getName().getStringValue();
      if (ArrayUtil.find(names, name) > -1) {
        final String value = param.getStringValue();
        return HibernateReadOnlyValue.getInstance(getFirstInList(value));
      }
    }
    return HibernateReadOnlyValue.getInstance(null);
  }

  private static String getFirstInList(final String value) {
    if (StringUtil.isEmpty(value)) return value;
    final int idx = value.indexOf(',');
    if (idx == -1) return value;
    return value.substring(0, idx).trim();
  }

}
