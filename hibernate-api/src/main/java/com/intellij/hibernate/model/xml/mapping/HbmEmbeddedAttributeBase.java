package com.intellij.hibernate.model.xml.mapping;

import com.intellij.persistence.model.PersistentEmbeddedAttribute;
import com.intellij.java.language.psi.PsiClass;
import consulo.xml.dom.GenericAttributeValue;
import consulo.xml.dom.Convert;
import com.intellij.hibernate.model.converters.PersistentObjectClassResolveConverter;

/**
 * @author Gregory.Shrago
 */
public interface HbmEmbeddedAttributeBase extends HbmAttributeBase, HbmAttributeContainerBase, PersistentEmbeddedAttribute {

  @Convert(PersistentObjectClassResolveConverter.class)
  GenericAttributeValue<PsiClass> getClazz();

}