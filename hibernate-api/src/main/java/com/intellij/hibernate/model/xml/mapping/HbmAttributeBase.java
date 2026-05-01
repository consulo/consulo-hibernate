package com.intellij.hibernate.model.xml.mapping;

import jakarta.annotation.Nullable;

import com.intellij.hibernate.model.converters.AttributeMemberConverter;
import com.intellij.hibernate.model.enums.AccessType;
import com.intellij.javaee.model.xml.JavaeeDomModelElement;
import com.intellij.persistence.model.PersistentAttribute;
import com.intellij.java.language.psi.PsiMember;
import consulo.xml.dom.Attribute;
import consulo.xml.dom.Convert;
import consulo.xml.dom.GenericAttributeValue;
import consulo.xml.dom.MutableGenericValue;
import consulo.xml.dom.NameValue;
import consulo.xml.dom.PrimaryKey;

/**
 * @author Gregory.Shrago
 */
public interface HbmAttributeBase extends JavaeeDomModelElement, PersistentAttribute {

  @PrimaryKey
  MutableGenericValue<String> getName();

  GenericAttributeValue<AccessType> getAccess();

  @Nullable
  PsiMember getPsiMember();

  @NameValue
  @Attribute("name")
  @Convert(AttributeMemberConverter.class)
  GenericAttributeValue<PsiMember> getTargetMember();

}
