/*
 * Copyright (c) 2000-2007 JetBrains s.r.o. All Rights Reserved.
 */

package com.intellij.hibernate.impl.model.xml.impl.mapping;

import com.intellij.hibernate.model.xml.mapping.HbmSqlQuery;
import com.intellij.hibernate.impl.model.xml.impl.HibernateBaseImpl;
import consulo.xml.dom.GenericValue;

/**
 * @author Gregory.Shrago
 */
public abstract class HbmSqlQueryImpl extends HibernateBaseImpl implements HbmSqlQuery {

  public GenericValue<String> getQuery() {
    return this;
  }
}