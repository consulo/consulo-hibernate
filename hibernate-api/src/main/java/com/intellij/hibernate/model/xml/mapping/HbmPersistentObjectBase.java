package com.intellij.hibernate.model.xml.mapping;

import com.intellij.hibernate.model.converters.PersistentObjectClassResolveConverter;
import com.intellij.persistence.model.PersistentObject;
import com.intellij.java.language.psi.PsiClass;
import consulo.xml.dom.Convert;
import consulo.xml.dom.GenericDomValue;
import consulo.xml.dom.PrimaryKey;
import jakarta.annotation.Nonnull;

import java.util.List;

/**
 * @author Gregory.Shrago
 */
public interface HbmPersistentObjectBase extends HbmAttributeContainerBase, PersistentObject {
  @PrimaryKey
  @Convert(PersistentObjectClassResolveConverter.class)
  GenericDomValue<PsiClass> getClazz();

  List<HbmAttributeBase> getAllAttributes();

  @Nonnull
  List<? extends HbmPropertyBase> getProperties();

  HbmPropertyBase addProperty();

  @Nonnull
  List<? extends HbmManyToOneBase> getManyToOnes();

  HbmManyToOneBase addManyToOne();

}
