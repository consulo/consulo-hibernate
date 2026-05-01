/*
 * Copyright (c) 2000-2006 JetBrains s.r.o. All Rights Reserved.
 */

package com.intellij.hibernate.impl;

import com.intellij.hibernate.model.xml.mapping.*;
import com.intellij.jpa.ORMHelperRegistry;
import consulo.annotation.component.ComponentScope;
import consulo.annotation.component.ServiceAPI;
import consulo.annotation.component.ServiceImpl;
import consulo.project.Project;
import consulo.util.lang.StringUtil;
import java.util.function.Function;
import consulo.xml.dom.DomElement;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

/**
 * @author Gregory.Shrago
 */
@Singleton
@ServiceAPI(ComponentScope.PROJECT)
@ServiceImpl
public class HibernateProjectComponent {

  @Inject
  public HibernateProjectComponent(final Project project,
                                   final ORMHelperRegistry ormRegistry) {
    ormRegistry.registerTableInfoProvider(new Function<DomElement, Object>() {
      public Object apply(final DomElement domElement) {
        final DomElement elementParent = domElement.getParent();
        final DomElement parent = elementParent instanceof HbmColumn ? elementParent.getParent() : elementParent;
        if (parent instanceof HbmKey && parent.getParent() instanceof HbmContainer) {
          final HbmContainer container = (HbmContainer)parent.getParent();
          final HbmCollectionAttributeBase attribute = container.getContainedAttribute();
          if (attribute instanceof HbmRelationAttributeBase && StringUtil.isEmpty(container.getTableName().getValue())) {
            return ((HbmRelationAttributeBase)attribute).getTargetEntityClass().getValue();
          }
        }
        return null;
      }
    });
  }

}
