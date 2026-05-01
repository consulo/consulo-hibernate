package com.intellij.hibernate.impl.model.xml.impl.mapping;

import java.util.Collections;
import java.util.List;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import com.intellij.hibernate.model.converters.AttributeMemberConverter;
import com.intellij.hibernate.model.enums.AccessType;
import com.intellij.hibernate.model.xml.mapping.*;
import com.intellij.hibernate.util.HibernateUtil;
import com.intellij.hibernate.impl.model.xml.impl.HibernateBaseImpl;
import com.intellij.jam.model.util.JamCommonUtil;
import com.intellij.javaee.model.common.persistence.JavaeePersistenceConstants;
import com.intellij.jpa.util.JpaUtil;
import com.intellij.persistence.model.PersistentAttribute;
import com.intellij.persistence.model.PersistentObject;
import com.intellij.persistence.model.helpers.PersistentAttributeModelHelper;
import com.intellij.java.language.psi.JavaPsiFacade;
import com.intellij.java.language.psi.PsiClass;
import com.intellij.java.language.psi.PsiClassType;
import com.intellij.java.language.psi.PsiMember;
import com.intellij.java.language.psi.PsiType;
import java.util.function.Function;
import consulo.util.collection.ContainerUtil;
import consulo.xml.dom.DomElement;
import consulo.xml.dom.DomUtil;
import consulo.xml.dom.GenericValue;
import consulo.xml.dom.MutableGenericValue;

/**
 * @author Gregory.Shrago
 */
public abstract class HbmAttributeBaseImpl extends HibernateBaseImpl implements HbmAttributeBase, PersistentAttributeModelHelper {
  private static final Function<PersistentAttribute, PsiType> TYPE_PROVIDER = new Function<PersistentAttribute, PsiType>() {
    public PsiType apply(final PersistentAttribute persistentAttribute) {
      if (persistentAttribute instanceof HbmTypeHolderBase) {
        final HbmTypeHolderBase typeHolder = (HbmTypeHolderBase)persistentAttribute;
        if (DomUtil.hasXml(typeHolder.getTypeAttr())) {
          return typeHolder.getTypeAttr().getValue();
        }
        final PsiClass value = typeHolder.getType().getName().getValue();
        return value == null? null : JavaPsiFacade.getInstance(value.getProject()).getElementFactory().createType(value);
      }
      return null;
    }
  };

  @Nonnull
  public PersistentAttributeModelHelper getAttributeModelHelper() {
    return this;
  }

  public boolean isFieldAccess() {
    final AccessType thisAccess = getAccess().getValue();
    return thisAccess == AccessType.FIELD || thisAccess == null && AttributeMemberConverter.isDefaultAccessField(this);
  }

  public boolean isIdAttribute() {
    return this instanceof HbmId || this instanceof HbmKeyProperty || this instanceof HbmKeyManyToOne;
  }

  public List<? extends GenericValue<String>> getMappedColumns() {
    if (this instanceof HbmColumnsHolderBase) {
      final HbmColumnsHolderBase columnsHolder = (HbmColumnsHolderBase)this;
      if (DomUtil.hasXml(columnsHolder.getColumn())) {
        return Collections.singletonList(columnsHolder.getColumn());
      }
      return ContainerUtil.map2List(columnsHolder.getColumns(), new Function<HbmColumn, GenericValue<String>>() {
        public GenericValue<String> apply(final HbmColumn hbmColumn) {
          return hbmColumn.getName();
        }
      });
    }
    return Collections.emptyList();
  }

  public boolean isContainer() {
    return this instanceof HbmAnyToManyImpl || getParent() instanceof HbmContainer;
  }

  public boolean isLob() {
    final PsiType type = getPsiType();
    final PsiClass psiClass = type instanceof PsiClassType ? ((PsiClassType)type).resolve() : null;
    return JamCommonUtil.isSuperClass(psiClass, JavaeePersistenceConstants.JAVA_SQL_BLOB)
      || JamCommonUtil.isSuperClass(psiClass, JavaeePersistenceConstants.JAVA_SQL_CLOB);
  }


  @Nullable
  public PsiType getPsiType() {
    return JpaUtil.findType(this, TYPE_PROVIDER);
  }


  public MutableGenericValue<String> getName() {
    return new MutableGenericValue<String>() {
      public void setStringValue(final String value) {
        getTargetMember().setStringValue(value);
      }

      public void setValue(final String value) {
        setStringValue(value);
      }

      public String getStringValue() {
        return getTargetMember().getStringValue();
      }

      public String getValue() {
        return getStringValue();
      }
    };
  }

  @Nullable
  public PsiMember getPsiMember() {
    return getTargetMember().getValue();
  }

  @Nullable
  public PersistentObject getPersistentObject() {
    for (DomElement cur = getParent(); cur != null; cur = cur.getParent()) {
      if (cur instanceof HbmPersistentObjectBase) {
        return (HbmPersistentObjectBase)cur;
      }
      else if (cur instanceof HbmEmbeddedAttributeBase) {
        if (cur instanceof HbmCompositeId && !HibernateUtil.isEmbedded((HbmCompositeId)cur)) continue;
        final HbmHibernateMappingImpl mappings = cur.getParentOfType(HbmHibernateMappingImpl.class, true);
        if (mappings == null) return null;
        return mappings.getEmbeddableByAttribute((HbmEmbeddedAttributeBase)cur);
      }
    }
    return null;
  }


}
