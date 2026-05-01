package com.intellij.hibernate.impl.model.xml.impl.mapping;

import com.intellij.hibernate.model.converters.AttributeMemberConverter;
import com.intellij.hibernate.model.enums.AccessType;
import com.intellij.hibernate.model.xml.mapping.HbmCompositeId;
import com.intellij.hibernate.model.xml.mapping.HbmEmbeddedAttributeBase;
import com.intellij.hibernate.model.xml.mapping.HbmAttributeVisitor;
import com.intellij.jpa.util.JpaUtil;
import com.intellij.persistence.model.helpers.PersistentAttributeModelHelper;
import com.intellij.java.language.psi.PsiClass;
import com.intellij.java.language.psi.PsiType;
import java.util.function.Function;
import consulo.xml.dom.GenericValue;
import consulo.xml.dom.DomUtil;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.util.Collections;
import java.util.List;

/**
 * @author Gregory.Shrago
 */
public abstract class HbmEmbeddedAttributeBaseImpl extends HbmAttributeBaseImpl implements HbmEmbeddedAttributeBase, PersistentAttributeModelHelper {

  public boolean isFieldAccess() {
    final AccessType thisAccess = getAccess().getValue();
    return thisAccess == AccessType.FIELD || thisAccess == null && AttributeMemberConverter.isDefaultAccessField(this);
  }

  public boolean isIdAttribute() {
    return this instanceof HbmCompositeId;
  }

  public List<? extends GenericValue<String>> getMappedColumns() {
    return Collections.emptyList();
  }

  public boolean isLob() {
    return false;
  }

  @Nullable
  public PsiType getPsiType() {
    return JpaUtil.findType(this, x -> null);
  }

  @Nullable
  public GenericValue<PsiClass> getTargetEmbeddableClass() {
    return getClazz();
  }


  @Nonnull
  public PersistentAttributeModelHelper getAttributeModelHelper() {
    return this;
  }

  public void visitAttributes(final HbmAttributeVisitor visitor) {
    DomUtil.acceptAvailableChildren(this, new HbmAttributeDomElementVisitor(visitor));
  }

}