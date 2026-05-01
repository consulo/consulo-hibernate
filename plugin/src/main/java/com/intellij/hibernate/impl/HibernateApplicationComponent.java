/*
 * Copyright (c) 2000-2006 JetBrains s.r.o. All Rights Reserved.
 */

package com.intellij.hibernate.impl;

import com.intellij.hibernate.facet.HibernateFacet;
import com.intellij.hibernate.model.enums.HibernateAttributeType;
import com.intellij.hibernate.model.manipulators.*;
import com.intellij.hibernate.impl.model.manipulators.HbmMappingsManipulator;
import com.intellij.hibernate.impl.model.manipulators.HibernateAttributeManipulator;
import com.intellij.hibernate.impl.model.manipulators.HibernateEmbeddableManipulator;
import com.intellij.hibernate.impl.model.manipulators.HibernateFacetManipulator;
import com.intellij.hibernate.impl.model.manipulators.HibernateObjectManipulator;
import com.intellij.hibernate.impl.model.manipulators.HibernateRelationshipAttributeManipulator;
import com.intellij.hibernate.model.xml.config.HibernateConfiguration;
import com.intellij.hibernate.model.xml.config.SessionFactory;
import com.intellij.hibernate.impl.model.xml.impl.mapping.HbmEmbeddableImpl;
import com.intellij.hibernate.model.xml.mapping.*;
import com.intellij.hibernate.view.HibernateIcons;
import com.intellij.jam.view.JamDeleteHandler;
import com.intellij.jam.model.common.CommonModelManager;
import com.intellij.jam.model.common.CommonModelElement;
import com.intellij.jpa.util.JpaUtil;
import com.intellij.persistence.facet.PersistenceHelper;
import com.intellij.persistence.model.manipulators.ManipulatorsRegistry;
import consulo.annotation.component.ComponentScope;
import consulo.annotation.component.ServiceAPI;
import consulo.annotation.component.ServiceImpl;
import consulo.language.psi.PsiElement;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Singleton;

import java.util.Collection;

/**
 * @author Gregory.Shrago
 */
@Singleton
@ServiceAPI(ComponentScope.APPLICATION)
@ServiceImpl
public class HibernateApplicationComponent {

  @PostConstruct
  public void initComponent() {
    // Note: MetaDataRegistrar.getInstance() was removed in Consulo 3.
    // MetaData registration, icon registration, and type name registration
    // are no longer done via static registration methods.
    // ElementPresentationManager.registerIcon/registerIconProvider were removed.
    // TypeNameManager was removed entirely.
    // These registrations would need to be reimplemented using the new Consulo 3 extension point system.

    CommonModelManager.getInstance().registerDeleteHandler(new JamDeleteHandler() {
      public void addPsiElements(final CommonModelElement element, final Collection<PsiElement> result) {
        if (element instanceof HbmAttributeBase) {
          result.addAll(JpaUtil.getAttributePsiMembers((HbmAttributeBase)element));
        }
      }
    });

    final ManipulatorsRegistry manipulatorsRegistry = PersistenceHelper.getHelper().getManipulatorsRegistry();
    manipulatorsRegistry.registerManipulator(HibernateFacet.class, HibernateFacetManipulator.class);
    manipulatorsRegistry.registerManipulator(SessionFactory.class, SessionFactoryManipulator.class);
    manipulatorsRegistry.registerManipulator(HbmHibernateMapping.class, HbmMappingsManipulator.class);
    manipulatorsRegistry.registerManipulator(HbmPersistentObjectBase.class, HibernateObjectManipulator.class);
    manipulatorsRegistry.registerManipulator(HbmEmbeddableImpl.class, HibernateEmbeddableManipulator.class);
    manipulatorsRegistry.registerManipulator(HbmRelationAttributeBase.class, HibernateRelationshipAttributeManipulator.class);
    manipulatorsRegistry.registerManipulator(HbmAttributeBase.class, HibernateAttributeManipulator.class);

    HibernateAttributeType.initialize();
  }

}
