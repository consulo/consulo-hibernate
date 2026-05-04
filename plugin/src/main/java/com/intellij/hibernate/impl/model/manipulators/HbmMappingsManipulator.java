/*
 * Copyright (c) 2000-2007 JetBrains s.r.o. All Rights Reserved.
 */

package com.intellij.hibernate.impl.model.manipulators;

import com.intellij.hibernate.localize.HibernateLocalize;
import com.intellij.hibernate.model.xml.mapping.*;
import com.intellij.hibernate.view.HibernateIcons;
import com.intellij.jpa.model.manipulators.MappingsManipulatorBase;
import com.intellij.persistence.facet.PersistenceHelper;
import com.intellij.persistence.model.PersistencePackage;
import com.intellij.persistence.model.PersistentEmbeddable;
import com.intellij.persistence.model.PersistentEntity;
import com.intellij.persistence.model.PersistentSuperclass;
import com.intellij.persistence.model.manipulators.PersistenceAction;
import com.intellij.persistence.model.manipulators.PersistenceMappingsManipulator;
import com.intellij.persistence.model.manipulators.PersistenceUnitManipulator;
import com.intellij.java.language.psi.PsiClass;
import consulo.language.psi.PsiDirectory;
import consulo.language.util.IncorrectOperationException;
import com.intellij.jam.reflect.JamClassMeta;

import java.util.Arrays;
import java.util.List;

/**
 * @author Gregory.Shrago
 */
public class HbmMappingsManipulator extends MappingsManipulatorBase<HbmHibernateMapping> implements PersistenceMappingsManipulator<HbmHibernateMapping>{
  public HbmMappingsManipulator(final HbmHibernateMapping target) {
    super(target);
  }

  public List<PersistenceAction> getCreateActions() {
    return Arrays.<PersistenceAction>asList(
      new MyObjectAction<HbmClass>(this, HibernateLocalize.actionNameCreateHibernateClass().get(),
                               HibernateIcons.CLASS_ICON, HbmClass.class, null, HibernateLocalize.typeHibernateClass().get()),
      new MyObjectAction<HbmSubclass>(this, HibernateLocalize.actionNameCreateHibernateSubclass().get(),
                               HibernateIcons.CLASS_ICON, HbmSubclass.class, null, HibernateLocalize.typeHibernateSubclass().get()),
      new MyObjectAction<HbmJoinedSubclass>(this, HibernateLocalize.actionNameCreateHibernateJoinedSubclass().get(),
                               HibernateIcons.CLASS_ICON, HbmJoinedSubclass.class, null, HibernateLocalize.typeHibernateJoinedSubclass().get()),
      new MyObjectAction<HbmUnionSubclass>(this, HibernateLocalize.actionNameCreateHibernateUnionSubclass().get(),
                               HibernateIcons.CLASS_ICON, HbmUnionSubclass.class, null, HibernateLocalize.typeHibernateUnionSubclass().get())
      //new MyObjectAction<HbmComponent>(this, HibernateLocalize.actionNameCreateHibernateComponent().get(),
      //                         HibernateIcons.CLASS_ICON, HbmComponent.class, null, HibernateLocalize.typeHibernateComponent().get())
      //new MyObjectAction<HbmJoin>(this, HibernateLocalize.actionNameCreateHibernateJoin().get(),
      //                         HibernateIcons.CLASS_ICON, HbmJoin.class, null, HibernateLocalize.typeHibernateJoin().get())
    );
  }


  protected <T> T getOrCreateModelObject(final PersistencePackage unit, final Class<T> aClass, final JamClassMeta<?> meta, final PsiClass psiClass) {
    final HbmPersistentObjectBase object;
    if (aClass == HbmClass.class) {
      object = getManipulatorTarget().addClass();
    }
    else if (aClass == HbmSubclass.class) {
      object = getManipulatorTarget().addSubclass();
    }
    else if (aClass == HbmJoinedSubclass.class) {
      object = getManipulatorTarget().addJoinedSubclass();
    }
    else if (aClass == HbmUnionSubclass.class) {
      object = getManipulatorTarget().addUnionSubclass();
    }
    else {
      throw new AssertionError("invalid class");
    }
    object.getClazz().setValue(psiClass);
    if (unit != null) {
      final PersistenceUnitManipulator manipulator = PersistenceHelper.getHelper().getManipulatorsRegistry().getManipulator(unit, PersistenceUnitManipulator.class);
      if (manipulator != null) {
        manipulator.ensureMappingIncluded(getManipulatorTarget());
      }
    }
    return (T)object;
  }

  public PersistentEntity addEntity(final PsiDirectory directory, final PersistencePackage unit, final String shortClassName, final String entityName)
    throws IncorrectOperationException {
    return addPersistentObject(directory, unit, HbmClass.class, null, shortClassName);
  }

  public PersistentSuperclass addSuperclass(final PsiDirectory directory, final PersistencePackage unit, final String shortClassName)
    throws IncorrectOperationException {
    final HbmClass hbmClass = addPersistentObject(directory, unit, HbmClass.class, null, shortClassName);
    hbmClass.getAbstract().setValue(Boolean.TRUE);
    return hbmClass;
  }

  public PersistentEmbeddable addEmbeddable(final PsiDirectory directory, final PersistencePackage unit, final String shortClassName)
    throws IncorrectOperationException {
    throw new UnsupportedOperationException("HibernateMappings.addEmbeddable is not supported");
  }
}
