/*
 * Copyright (c) 2000-2007 JetBrains s.r.o. All Rights Reserved.
 */

package com.intellij.hibernate.impl.model.xml.impl.mapping;

import com.intellij.hibernate.model.enums.AccessType;
import com.intellij.hibernate.model.enums.CascadeType;
import com.intellij.hibernate.model.xml.mapping.HbmCompositeElement;
import com.intellij.hibernate.model.xml.mapping.HbmContainer;
import com.intellij.java.language.psi.PsiMember;
import consulo.xml.dom.GenericAttributeValue;

import java.util.List;

/**
 * @author Gregory.Shrago
 */
public abstract class HbmCompositeElementImpl extends HbmEmbeddedAttributeBaseImpl implements HbmCompositeElement {

  public GenericAttributeValue<AccessType> getAccess() {
    final HbmContainer container = getParentOfType(HbmContainer.class, false);
    assert container != null;
    return container.getAccess();
  }

  public GenericAttributeValue<PsiMember> getTargetMember() {
    final HbmContainer container = getParentOfType(HbmContainer.class, false);
    assert container != null;
    return container.getTargetMember();
  }

  public GenericAttributeValue<List<CascadeType>> getCascade() {
    final HbmContainer container = getParentOfType(HbmContainer.class, false);
    assert container != null;
    return container.getCascade();
  }

}