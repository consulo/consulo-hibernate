/*
 * Copyright (c) 2000-2007 JetBrains s.r.o. All Rights Reserved.
 */

package com.intellij.hibernate.model.enums;

import com.intellij.hibernate.localize.HibernateLocalize;
import static com.intellij.jpa.model.annotations.mapping.AttributeBaseImpl.*;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import com.intellij.hibernate.model.common.mapping.CollectionOfElements;
import com.intellij.hibernate.model.xml.mapping.*;
import com.intellij.jam.reflect.JamMemberMeta;
import com.intellij.javaee.model.common.persistence.mapping.AttributeType;
import com.intellij.jpa.model.annotations.mapping.JamAttributeBase;
import com.intellij.persistence.model.PersistentAttribute;
import com.intellij.persistence.model.RelationshipType;
import com.intellij.persistence.util.JavaContainerType;
import com.intellij.java.language.psi.PsiClassType;
import consulo.language.psi.PsiManager;
import com.intellij.java.language.psi.PsiMember;
import com.intellij.java.language.psi.PsiType;
import consulo.language.psi.scope.GlobalSearchScope;
import consulo.language.util.IncorrectOperationException;
import consulo.xml.dom.ModelMergerUtil;
import consulo.xml.dom.DomUtil;

/**
 * @author Gregory.Shrago
 */
public class HibernateAttributeType extends AttributeType{

  // Hibernate-specific JamMemberMeta constants (initialized by CollectionOfElementsImpl in plugin module)
  public static JamMemberMeta<PsiMember, ? extends JamAttributeBase> COLLECTION_OF_ELEMENTS_ATTR_META = null;
  public static JamMemberMeta<PsiMember, ? extends JamAttributeBase> COLLECTION_OF_EMBEDDED_ELEMENTS_ATTR_META = null;

  public static final HibernateAttributeType ID = new HibernateAttributeType(HbmId.class, HibernateLocalize.typeHibernateAttrId().get(), ID_ATTR_META);
  public static final HibernateAttributeType COMPOSITE_ID = new HibernateAttributeType(HbmCompositeId.class, HibernateLocalize.typeHibernateAttrCompositeId().get(), EMBEDDED_ID_ATTR_META);
  public static final HibernateAttributeType VERSION = new HibernateAttributeType(HbmVersion.class, HibernateLocalize.typeHibernateAttrVersion().get(), VERSION_ATTR_META);
  public static final HibernateAttributeType TIMESTAMP = new HibernateAttributeType(HbmTimestamp.class, HibernateLocalize.typeHibernateAttrTimestamp().get(), BASIC_ATTR_META);
  public static final HibernateAttributeType PROPERTY = new HibernateAttributeType(HbmProperty.class, HibernateLocalize.typeHibernateAttrProperty().get(), BASIC_ATTR_META) {
    protected boolean accepts(final PersistentAttribute attribute) {
      return super.accepts(attribute) || ModelMergerUtil.getImplementation(attribute, HbmKeyProperty.class) != null;
    }
  };

  public static final HibernateAttributeType ONE_TO_ONE =  new HibernateAttributeType(HbmOneToOne.class, HibernateLocalize.typeHibernateAttrOneToOne().get(), ONE_TO_ONE_ATTR_META);
  public static final HibernateAttributeType ONE_TO_MANY = new HibernateAttributeType(HbmOneToMany.class, HibernateLocalize.typeHibernateAttrOneToMany().get(), ONE_TO_MANY_ATTR_META);
  public static final HibernateAttributeType MANY_TO_ONE = new HibernateAttributeType(HbmManyToOne.class, HibernateLocalize.typeHibernateAttrManyToOne().get(), MANY_TO_ONE_ATTR_META) {
    protected boolean accepts(final PersistentAttribute attribute) {
      return super.accepts(attribute) || ModelMergerUtil.getImplementation(attribute, HbmKeyManyToOne.class) != null;
    }
  };
  public static final HibernateAttributeType MANY_TO_MANY = new HibernateAttributeType(HbmManyToMany.class, HibernateLocalize.typeHibernateAttrManyToMany().get(), MANY_TO_MANY_ATTR_META);

  public static final HibernateAttributeType ELEMENTS  = new HibernateAttributeType(HbmElement.class, HibernateLocalize.typeHibernateAttrElements().get(), BASIC_COLLECTION_ATTR_META, COLLECTION_OF_ELEMENTS_ATTR_META);
  public static final HibernateAttributeType COMPOSITE_ELEMENTS = new HibernateAttributeType(HbmCompositeElement.class, HibernateLocalize.typeHibernateAttrCompositeElements().get(), EMBEDDED_COLLECTION_ATTR_META, COLLECTION_OF_EMBEDDED_ELEMENTS_ATTR_META);
  public static final HibernateAttributeType COLLECTION_OF_ELEMENTS = new HibernateAttributeType(CollectionOfElements.class, HibernateLocalize.typeHibernateAttrCollectionOfElements().get(), COLLECTION_OF_ELEMENTS_ATTR_META, COLLECTION_OF_EMBEDDED_ELEMENTS_ATTR_META);

  public static final HibernateAttributeType COMPONENT = new HibernateAttributeType(HbmComponent.class, HibernateLocalize.typeHibernateAttrComponent().get(), EMBEDDED_ATTR_META) {
    protected boolean accepts(final PersistentAttribute attribute) {
      return super.accepts(attribute) || ModelMergerUtil.getImplementation(attribute, HbmNestedCompositeElement.class) != null;
    }
  };

  public static final HibernateAttributeType ANY = new HibernateAttributeType(HbmAny.class, HibernateLocalize.typeHibernateAttrAny().get());
  public static final HibernateAttributeType DYNAMIC_COMPONENT = new HibernateAttributeType(HbmDynamicComponent.class, HibernateLocalize.typeHibernateAttrDynamicComponent().get());

  public HibernateAttributeType(final Class attributeClass, final String typeName, final JamMemberMeta<PsiMember, ? extends JamAttributeBase>... jamMeta) {
    super(attributeClass, typeName, jamMeta);
  }

  public boolean isIdAttribute() {
    return this == ID || this == COMPOSITE_ID;
  }

  public boolean isEmbedded() {
    return this == COMPOSITE_ID || this == COMPONENT || this == COMPOSITE_ELEMENTS;
  }

  public boolean isContainer() {
    return this == COLLECTION_OF_ELEMENTS ||
           this == COMPOSITE_ELEMENTS ||
           this == ELEMENTS ||
           this == MANY_TO_MANY ||
           this == ONE_TO_MANY;
  }

  public boolean isBasic() {
    return this == PROPERTY;
  }

  @Nullable
  public RelationshipType getRelationshipType() {
    if (this == MANY_TO_MANY) {
      return RelationshipType.MANY_TO_MANY;
    }
    else if (this == MANY_TO_ONE) {
      return RelationshipType.MANY_TO_ONE;
    }
    else if (this == ONE_TO_MANY) {
      return RelationshipType.ONE_TO_MANY;
    }
    else if (this == ONE_TO_ONE) {
      return RelationshipType.ONE_TO_ONE;
    }
    else {
      return null;
    }
  }

  public static void initialize() {
    // nothing
  }

  @Nonnull
  public PsiType getDefaultPsiType(final PersistentAttribute attribute) {
    if (!isContainer()) return super.getDefaultPsiType(attribute);
    final HbmAttributeBase domAttribute = ModelMergerUtil.getImplementation(attribute, HbmAttributeBase.class);
    if (domAttribute == null) return super.getDefaultPsiType(attribute);
    final HbmContainer container = domAttribute.getParentOfType(HbmContainer.class, false);
    if (container == null) return super.getDefaultPsiType(attribute);
    final PsiType psiType = getDefaultElementPsiType(attribute);
    final JavaContainerType containerType;
    PsiType mapKeyType = null;
    if (container instanceof HbmArray) {
      containerType = JavaContainerType.ARRAY;
    }
    else if (container instanceof HbmBag) {
      containerType = DomUtil.hasXml(((HbmBag)container).getOrderBy()) ? JavaContainerType.LIST : JavaContainerType.COLLECTION;
    }
    else if (container instanceof HbmIdbag) {
      containerType = DomUtil.hasXml(((HbmIdbag)container).getOrderBy()) ? JavaContainerType.LIST : JavaContainerType.COLLECTION;
    }
    else if (container instanceof HbmList) {
      containerType = JavaContainerType.LIST;
    }
    else if (container instanceof HbmMap) {
      containerType = JavaContainerType.MAP;
      final PsiManager psiManager = attribute.getPsiManager();
      mapKeyType = PsiType.getJavaLangObject(psiManager, GlobalSearchScope.allScope(psiManager.getProject()));
    }
    else if (container instanceof HbmPrimitiveArray) {
      containerType = JavaContainerType.ARRAY;
    }
    else if (container instanceof HbmSet) {
      containerType = JavaContainerType.SET;
    }
    else {
      containerType = JavaContainerType.COLLECTION;
    }
    return containerType.createCollectionType(attribute.getIdentifyingPsiElement(), psiType, mapKeyType);
  }

  @Nonnull
  protected PsiType getDefaultElementPsiType(final PersistentAttribute attribute) {
    if (this == DYNAMIC_COMPONENT) {
      try {
        final PsiManager psiManager = attribute.getPsiManager();
        final PsiClassType stringClass = PsiType.getJavaLangString(psiManager, GlobalSearchScope.allScope(psiManager.getProject()));
        final PsiClassType objectClass = PsiType.getJavaLangObject(psiManager, GlobalSearchScope.allScope(psiManager.getProject()));
        return JavaContainerType.MAP.createCollectionType(attribute.getIdentifyingPsiElement(), objectClass, stringClass);
      }
      catch (IncorrectOperationException e) {
        throw new AssertionError(e);
      }
    }
    return super.getDefaultElementPsiType(attribute);
  }
}
