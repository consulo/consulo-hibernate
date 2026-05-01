/*
 * Copyright (c) 2000-2007 JetBrains s.r.o. All Rights Reserved.
 */

package com.intellij.hibernate.impl.model.xml.impl.mapping;

import com.intellij.hibernate.model.xml.mapping.HbmQuery;
import com.intellij.hibernate.impl.model.xml.impl.HibernateBaseImpl;
import consulo.xml.dom.GenericValue;

/**
 * @author Gregory.Shrago
 */
public abstract class HbmQueryImpl extends HibernateBaseImpl implements HbmQuery {

  public GenericValue<String> getQuery() {
    return this;
  }
}
