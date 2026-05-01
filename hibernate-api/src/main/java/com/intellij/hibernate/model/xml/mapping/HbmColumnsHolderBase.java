/*
 * Copyright (c) 2000-2007 JetBrains s.r.o. All Rights Reserved.
 */

package com.intellij.hibernate.model.xml.mapping;

import consulo.xml.dom.Convert;
import consulo.xml.dom.GenericAttributeValue;
import com.intellij.javaee.model.JavaeePersistenceORMResolveConverters;

import java.util.List;

/**
 * @author Gregory.Shrago
 */
public interface HbmColumnsHolderBase {
  @Convert(JavaeePersistenceORMResolveConverters.ColumnResolver.class)
  GenericAttributeValue<String> getColumn();

  List<HbmColumn> getColumns();

  HbmColumn addColumn();
}
