/*
 * Copyright (c) 2000-2007 JetBrains s.r.o. All Rights Reserved.
 */

package com.intellij.hibernate.model.xml.mapping;

import com.intellij.hibernate.model.converters.PropertyTypeResolvingConverter;
import com.intellij.java.language.psi.PsiType;
import consulo.xml.dom.Convert;
import consulo.xml.dom.GenericAttributeValue;

/**
 * @author Gregory.Shrago
 */
public interface HbmTypeHolderBase {
  @Convert(PropertyTypeResolvingConverter.class)
  @consulo.xml.dom.Attribute("type")
  GenericAttributeValue<PsiType> getTypeAttr();

  HbmType getType();
}