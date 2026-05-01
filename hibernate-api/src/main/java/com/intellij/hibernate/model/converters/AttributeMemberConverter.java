package com.intellij.hibernate.model.converters;

import com.intellij.hibernate.model.enums.AccessType;
import com.intellij.hibernate.model.xml.mapping.*;
import com.intellij.hibernate.util.HibernateUtil;
import com.intellij.persistence.model.PersistentAttribute;
import com.intellij.persistence.model.PersistentEmbeddedAttribute;
import com.intellij.persistence.util.PersistenceCommonUtil;
import com.intellij.java.language.psi.*;
import com.intellij.java.language.psi.util.PropertyMemberType;
import com.intellij.java.language.psi.util.PropertyUtil;
import consulo.xml.dom.ConvertContext;
import consulo.xml.dom.DomElement;
import consulo.xml.dom.DomUtil;
import consulo.xml.dom.ResolvingConverter;
import com.intellij.jpa.util.JpaUtil;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * @author Gregory.Shrago
 */
public class AttributeMemberConverter extends ResolvingConverter<PsiMember> {

  protected boolean methodSuits(final PsiMethod psiMethod) {
    return PropertyUtil.isSimplePropertyGetter(psiMethod)
        && !psiMethod.hasModifierProperty(PsiModifier.FINAL)
        && !psiMethod.hasModifierProperty(PsiModifier.STATIC)
        && psiMethod.hasModifierProperty(PsiModifier.PUBLIC);
  }

  protected boolean fieldSuits(final PsiField psiField) {
    return !psiField.hasModifierProperty(PsiModifier.FINAL)
        && !psiField.hasModifierProperty(PsiModifier.TRANSIENT)
        && !psiField.hasModifierProperty(PsiModifier.STATIC);
  }

  @Nullable
  protected PsiClass getTargetClass(final ConvertContext context) {
    final DomElement parent = context.getInvocationElement().getParent();
    for (DomElement cur = parent instanceof PersistentAttribute ? parent.getParent() : parent; cur != null; cur = cur.getParent()) {
      if (cur instanceof HbmPersistentObjectBase) {
        return ((HbmPersistentObjectBase) cur).getClazz().getValue();
      } else if (cur instanceof HbmEmbeddedAttributeBase) {
        if (cur instanceof HbmCompositeId && !HibernateUtil.isEmbedded((HbmCompositeId) cur)) continue;
        return PersistenceCommonUtil.getTargetClass((PersistentEmbeddedAttribute) cur);
      }
    }
    return null;
  }

  @Nonnull
  protected PropertyMemberType[] getMemberTypes(final ConvertContext context) {
    final PersistentAttribute attributeBase = DomUtil.getParentOfType(context.getInvocationElement(), PersistentAttribute.class, false);
    final HbmContainer container = attributeBase != null ? null : DomUtil.getParentOfType(context.getInvocationElement(), HbmContainer.class, false);

    final boolean isField;
    if (attributeBase != null) {
      isField = attributeBase.getAttributeModelHelper().isFieldAccess();
    } else if (container != null) {
      final AccessType type = container.getAccess().getValue();
      isField = type == AccessType.FIELD || type == null && isDefaultAccessField(container);
    } else {
      isField = false;
    }
    return new PropertyMemberType[]{isField ? PropertyMemberType.FIELD : PropertyMemberType.GETTER};
  }

  @Nullable
  protected PsiType getPsiType(final ConvertContext context) {
    final DomElement invocationElement = context.getInvocationElement();
    final DomElement parent = invocationElement.getParent();
    final PersistentAttribute attributeBase;
    if (parent instanceof HbmAttributeBase) {
      attributeBase = (PersistentAttribute) parent;
    } else if (parent instanceof HbmContainer) {
      attributeBase = ((HbmContainer) parent).getContainedAttribute();
    } else {
      attributeBase = null;
    }
    if (attributeBase == null) return null;
    return JpaUtil.getAttributeTypeOrDefault(attributeBase);
  }

  @Override
  @Nullable
  public PsiMember fromString(@Nullable String s, ConvertContext context) {
    if (s == null) return null;
    final PsiClass targetClass = getTargetClass(context);
    if (targetClass == null) return null;

    for (PropertyMemberType memberType : getMemberTypes(context)) {
      if (memberType == PropertyMemberType.GETTER || memberType == PropertyMemberType.SETTER) {
        for (PsiMethod method : targetClass.getAllMethods()) {
          if (methodSuits(method)) {
            final String propertyName = PropertyUtil.getPropertyName(method);
            if (s.equals(propertyName)) return method;
          }
        }
      } else if (memberType == PropertyMemberType.FIELD) {
        for (PsiField field : targetClass.getAllFields()) {
          if (fieldSuits(field) && s.equals(field.getName())) {
            return field;
          }
        }
      }
    }
    return null;
  }

  @Override
  @Nullable
  public String toString(@Nullable PsiMember member, ConvertContext context) {
    if (member == null) return null;
    if (member instanceof PsiMethod) {
      return PropertyUtil.getPropertyName((PsiMethod) member);
    }
    return member.getName();
  }

  @Override
  @Nonnull
  public Collection<? extends PsiMember> getVariants(ConvertContext context) {
    final PsiClass targetClass = getTargetClass(context);
    if (targetClass == null) return Collections.emptyList();

    final List<PsiMember> result = new ArrayList<>();
    for (PropertyMemberType memberType : getMemberTypes(context)) {
      if (memberType == PropertyMemberType.GETTER || memberType == PropertyMemberType.SETTER) {
        for (PsiMethod method : targetClass.getAllMethods()) {
          if (methodSuits(method)) {
            result.add(method);
          }
        }
      } else if (memberType == PropertyMemberType.FIELD) {
        for (PsiField field : targetClass.getAllFields()) {
          if (fieldSuits(field)) {
            result.add(field);
          }
        }
      }
    }
    return result;
  }

  public static boolean isDefaultAccessField(final DomElement element) {
    final HbmHibernateMapping mapping = element.getParentOfType(HbmHibernateMapping.class, false);
    return mapping != null && mapping.getDefaultAccess().getValue() == AccessType.FIELD;
  }
}
