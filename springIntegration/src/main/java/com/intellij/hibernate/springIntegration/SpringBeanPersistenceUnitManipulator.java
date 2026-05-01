/*
 * Copyright (c) 2000-2007 JetBrains s.r.o. All Rights Reserved.
 */

package com.intellij.hibernate.springIntegration;

import com.intellij.hibernate.model.HibernatePropertiesConstants;
import com.intellij.hibernate.model.manipulators.SessionFactoryManipulator;
import com.intellij.hibernate.util.HibernateUtil;
import com.intellij.persistence.database.DatabaseConnectionInfo;
import com.intellij.persistence.model.PersistenceMappings;
import com.intellij.persistence.model.manipulators.AbstractPersistenceManipulator;
import com.intellij.persistence.model.manipulators.PersistenceAction;
import com.intellij.persistence.model.manipulators.PersistenceUnitManipulator;
import com.intellij.java.language.psi.*;
import consulo.language.psi.PsiManager;
import com.intellij.spring.impl.ide.model.SpringUtils;
import com.intellij.spring.impl.ide.model.converters.ResourceResolverUtils;
import com.intellij.spring.impl.ide.model.xml.beans.*;
import consulo.xml.dom.DomElement;
import consulo.xml.dom.GenericDomValue;
import consulo.xml.dom.GenericValue;
import consulo.xml.dom.GenericValueUtil;
import consulo.util.lang.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Gregory.Shrago
 */
public class SpringBeanPersistenceUnitManipulator extends AbstractPersistenceManipulator<SpringBeanPersistenceUnit> implements
                                                                                                                    PersistenceUnitManipulator<SpringBeanPersistenceUnit> {
  public SpringBeanPersistenceUnitManipulator(final SpringBeanPersistenceUnit target) {
    super(target);
  }

  public List<PersistenceAction> getCreateActions() {
    return SessionFactoryManipulator.getCreateActionsDefault(this, new ArrayList<PersistenceAction>());
  }

  public void ensureMappingIncluded(final PersistenceMappings mappings) {
    if (mappings instanceof DomElement && !GenericValueUtil.containsValue(getManipulatorTarget().getModelHelper().getMappingFiles(PersistenceMappings.class), mappings)) {
      final SpringBean bean = getManipulatorTarget().getBean();
      SpringPropertyDefinition propertyDef = SpringUtils.findPropertyByName(bean, SpringBeanPersistenceUnit.MAPPING_LOCATIONS, false);
      propertyDef = propertyDef != null? propertyDef : SpringUtils.findPropertyByName(bean, SpringBeanPersistenceUnit.CACHEABLE_MAPPING_LOCATIONS, false);
      propertyDef = propertyDef != null? propertyDef : SpringUtils.findPropertyByName(bean, SpringBeanPersistenceUnit.MAPPING_RESOURCES, false);
      final String fileReferenceString = ResourceResolverUtils.getResourceFileReferenceString(mappings.getContainingFile());
      IntegrationUtil.setBeanProperty(bean, propertyDef, SpringBeanPersistenceUnit.MAPPING_LOCATIONS, fileReferenceString, true);
    }
  }

  public void ensureClassIncluded(final PsiClass psiClass) {
    if (!getManipulatorTarget().supportsAnnotations()) return;
    if (!GenericValueUtil.containsValue(getManipulatorTarget().getModelHelper().getClasses(), psiClass)) {
      final String packageName = ((PsiJavaFile)psiClass.getContainingFile()).getPackageName();
      final PsiManager manager = PsiManager.getInstance(psiClass.getProject());
      final PsiJavaPackage psiPackage = JavaPsiFacade.getInstance(manager.getProject()).findPackage(packageName);
      for (GenericValue<PsiJavaPackage> pkgValue : getManipulatorTarget().getModelHelper().getPackages()) {
        if (manager.areElementsEquivalent(psiPackage, pkgValue.getValue())) {
          return;
        }
      }
      final SpringBean bean = getManipulatorTarget().getBean();
      IntegrationUtil.setBeanProperty(bean, SpringBeanPersistenceUnit.ANNOTATED_CLASSES, null, psiClass.getQualifiedName(), true);
    }
  }

  @SuppressWarnings("unchecked")
  public void setConnectionProperties(final DatabaseConnectionInfo info) {
    final SpringBeanPersistenceUnit unit = getManipulatorTarget();
    final SpringBean bean = unit.getBean();
    SpringPropertyDefinition propertyDef = SpringUtils.findPropertyByName(bean, SpringBeanPersistenceUnit.HIBERNATE_PROPERTIES, false);
    if (!(propertyDef instanceof SpringProperty)) {
      if (propertyDef != null) IntegrationUtil.asDomElement(propertyDef).undefine();
      final SpringProperty property = bean.addProperty();
      property.getName().setStringValue(SpringBeanPersistenceUnit.HIBERNATE_PROPERTIES);
      propertyDef = property;
    }
    final SpringProperty hibernateProperties = (SpringProperty)propertyDef;

    IntegrationUtil.setBeanProperty(hibernateProperties.getProps(), HibernateUtil.getFullPropertyName(HibernatePropertiesConstants.URL), HibernatePropertiesConstants.URL, info.getUrl());
    IntegrationUtil.setBeanProperty(hibernateProperties.getProps(), HibernateUtil.getFullPropertyName(HibernatePropertiesConstants.DRIVER), HibernatePropertiesConstants.DRIVER, info.getDriverClass());
    IntegrationUtil.setBeanProperty(hibernateProperties.getProps(), HibernateUtil.getFullPropertyName(HibernatePropertiesConstants.USER), HibernatePropertiesConstants.USER, info.getUsername());
    IntegrationUtil.setBeanProperty(hibernateProperties.getProps(), HibernateUtil.getFullPropertyName(HibernatePropertiesConstants.PASS), HibernatePropertiesConstants.PASS, info.getPassword());

    Prop dialect = IntegrationUtil.findProp(hibernateProperties.getProps(), HibernateUtil.getFullPropertyName(HibernatePropertiesConstants.DIALECT), HibernatePropertiesConstants.DIALECT);
    if (dialect == null) {
      dialect = hibernateProperties.getProps().addProp();
      final String dialectValue = HibernateUtil.getDefaultDialectValue(IntegrationUtil.asDomElement(dialect).getXmlElement(), info.getUrl());
      if (StringUtil.isNotEmpty(dialectValue)) {
        dialect.getKey().setStringValue(HibernateUtil.getFullPropertyName(HibernatePropertiesConstants.DIALECT));
        ((GenericDomValue<Object>) IntegrationUtil.asDomElement(dialect)).setStringValue(dialectValue);
      }
      else {
        IntegrationUtil.asDomElement(dialect).undefine();
      }
    }
  }
}
