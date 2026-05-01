package com.intellij.hibernate.impl.model.xml.impl.mapping;


import java.util.ArrayList;
import java.util.List;

import jakarta.annotation.Nonnull;

import com.intellij.hibernate.model.enums.AccessType;
import com.intellij.hibernate.model.xml.mapping.HbmAttributeBase;
import com.intellij.hibernate.model.xml.mapping.HbmAttributeVisitor;
import com.intellij.hibernate.model.xml.mapping.HbmAttributeVisitorAdapter;
import com.intellij.hibernate.model.xml.mapping.HbmHibernateMapping;
import com.intellij.hibernate.model.xml.mapping.HbmPersistentObjectBase;
import com.intellij.hibernate.impl.model.xml.impl.HibernateBaseImpl;
import com.intellij.persistence.facet.PersistencePackageDefaults;
import com.intellij.persistence.model.PersistentAttribute;
import com.intellij.persistence.model.helpers.PersistentObjectModelHelper;
import com.intellij.persistence.roles.PersistenceClassRole;
import com.intellij.persistence.util.PersistenceCommonUtil;
import com.intellij.java.language.psi.PsiClass;
import com.intellij.java.language.psi.util.PropertyMemberType;
import consulo.xml.dom.DomUtil;
import consulo.xml.dom.GenericValue;
import com.intellij.hibernate.impl.model.xml.impl.HibernateReadOnlyValue;

/**
 * @author Gregory.Shrago
 */
public abstract class HbmPersistentObjectBaseImpl extends HibernateBaseImpl implements HbmPersistentObjectBase, PersistentObjectModelHelper {


  @Nonnull
  public PersistentObjectModelHelper getObjectModelHelper() {
    return this;
  }

  @Nonnull
  public List<? extends PersistentAttribute> getAttributes() {
    return getAllAttributes();
  }

  public PropertyMemberType getDefaultAccessMode() {
    final HbmHibernateMapping mapping = getParentOfType(HbmHibernateMapping.class, false);
    return mapping != null? mapping.getDefaultAccess().getValue() == AccessType.FIELD ? PropertyMemberType.FIELD : PropertyMemberType.GETTER : PropertyMemberType.GETTER;
  }

  public boolean isAccessModeFixed() {
    return false;
  }

  public List<HbmAttributeBase> getAllAttributes() {
    final ArrayList<HbmAttributeBase> result = new ArrayList<HbmAttributeBase>();
    visitAttributes(new HbmAttributeVisitorAdapter() {

      public void visitAttributeBase(final HbmAttributeBase attributeBase) {
        result.add(attributeBase);
      }
    });
    return result;
  }

  public void visitAttributes(final HbmAttributeVisitor visitor) {
    DomUtil.acceptAvailableChildren(this, new HbmAttributeDomElementVisitor(visitor));
  }

  protected <T> GenericValue<T> getCurrentValueOrDefault(final GenericValue<T> currentValue, final DefaultsProcessor<T> processor) {
    if (currentValue != null && currentValue.getValue() != null) return currentValue;
    final HbmHibernateMapping mappings = getParentOfType(HbmHibernateMapping.class, true);
    if (mappings != null) {
      final GenericValue<T> value1 = processor.processMappings(mappings);
      if (value1 != null && value1.getValue() != null) return value1;

      // we should have clazz at the moment
      final PsiClass clazz = getClazz().getValue();
      if (clazz == null) return currentValue;
      for (PersistenceClassRole role : PersistenceCommonUtil.getPersistenceRoles(clazz)) {
        if (role.getPersistentObject() == null) continue;
        final PersistencePackageDefaults defaults = role.getFacet().getPersistenceUnitDefaults(role.getPersistenceUnit());
        final T value3 = processor.processUnitDefaults(defaults);
        if (value3 != null) return HibernateReadOnlyValue.getInstance(value3);
      }
      return currentValue;
    }
    return currentValue;
  }

  protected interface DefaultsProcessor<T> {
    GenericValue<T> processMappings(final HbmHibernateMapping mappings);
    T processUnitDefaults(final PersistencePackageDefaults defaults);
  }
}
