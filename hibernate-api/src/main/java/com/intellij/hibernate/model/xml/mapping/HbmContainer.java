/*
 * Copyright (c) 2000-2007 JetBrains s.r.o. All Rights Reserved.
 */

package com.intellij.hibernate.model.xml.mapping;

import java.util.List;

import com.intellij.hibernate.model.converters.AttributeMemberConverter;
import com.intellij.hibernate.model.converters.CascadeTypeListConverter;
import com.intellij.hibernate.model.converters.LazyTypeConverter;
import com.intellij.hibernate.model.enums.AccessType;
import com.intellij.hibernate.model.enums.CascadeType;
import com.intellij.hibernate.model.enums.LazyType;
import com.intellij.javaee.model.xml.JavaeeDomModelElement;
import com.intellij.java.language.psi.PsiMember;
import consulo.xml.dom.Attribute;
import consulo.xml.dom.Convert;
import consulo.xml.dom.GenericAttributeValue;
import consulo.xml.dom.NameValue;
import consulo.xml.dom.Required;

/**
 * @author Gregory.Shrago
 */
public interface HbmContainer extends JavaeeDomModelElement, HbmTableInfoProvider {

  HbmCollectionAttributeBase getContainedAttribute();

  HbmKey getKey();

  @Required
  @NameValue
  @Attribute("name")
  @Convert(AttributeMemberConverter.class)
  GenericAttributeValue<PsiMember> getTargetMember();

  GenericAttributeValue<AccessType> getAccess();

  // skip for array & primitive array
  @Convert(LazyTypeConverter.class)
  GenericAttributeValue<LazyType> getLazy();


  // skip for primitive array
  @Convert(CascadeTypeListConverter.class)
  GenericAttributeValue<List<CascadeType>> getCascade();

  HbmElement getElement();

  // skip for PrimitiveArray
  HbmCompositeElement getCompositeElement();
  HbmManyToMany getManyToMany();
  HbmManyToAny getManyToAny();
  HbmOneToMany getOneToMany();

  // skip for PrimitiveArray and IdBag
  GenericAttributeValue<Boolean> getInverse();
}
