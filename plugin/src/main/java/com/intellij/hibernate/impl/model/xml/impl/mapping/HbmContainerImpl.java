/*
 * Copyright (c) 2000-2007 JetBrains s.r.o. All Rights Reserved.
 */

package com.intellij.hibernate.impl.model.xml.impl.mapping;

import com.intellij.hibernate.model.xml.mapping.HbmCollectionAttributeBase;
import com.intellij.hibernate.model.xml.mapping.HbmContainer;
import com.intellij.hibernate.impl.model.xml.impl.HibernateBaseImpl;
import consulo.util.lang.ref.Ref;
import consulo.xml.dom.DomElement;
import consulo.xml.dom.DomElementVisitor;
import consulo.xml.dom.DomUtil;

/**
 * @author Gregory.Shrago
 */
public abstract class HbmContainerImpl extends HibernateBaseImpl implements HbmContainer {
  public HbmCollectionAttributeBase getContainedAttribute() {
    final Ref<HbmCollectionAttributeBase> result = new Ref<HbmCollectionAttributeBase>();
    DomUtil.acceptAvailableChildren(this, new DomElementVisitor() {
      public void visitDomElement(final DomElement element) {
        if (result.get() == null && element instanceof HbmCollectionAttributeBase) {
          result.set((HbmCollectionAttributeBase)element);
        }
      }
    });
    return result.get();
  }
}
