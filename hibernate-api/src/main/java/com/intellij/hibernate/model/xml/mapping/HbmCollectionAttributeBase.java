package com.intellij.hibernate.model.xml.mapping;

import consulo.xml.dom.*;
import com.intellij.hibernate.model.converters.AttributeMemberConverter;
import com.intellij.hibernate.model.converters.CascadeTypeListConverter;
import com.intellij.hibernate.model.enums.CascadeType;
import com.intellij.java.language.psi.PsiMember;

import java.util.List;

/**
 * @author Gregory.Shrago
 */
public interface HbmCollectionAttributeBase extends HbmAttributeBase{
  @Required
  @Attribute("name")
  @Convert(AttributeMemberConverter.class)
  GenericAttributeValue<PsiMember> getTargetMember();

  @Convert(CascadeTypeListConverter.class)
  GenericAttributeValue<List<CascadeType>> getCascade();

}
