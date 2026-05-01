package com.intellij.hibernate.model.converters;

import jakarta.annotation.Nonnull;

import consulo.language.psi.EmptyResolveMessageProvider;
import consulo.localize.LocalizeValue;
import com.intellij.hibernate.HibernateManager;
import com.intellij.hibernate.HibernateMessages;
import consulo.xml.dom.Converter;
import consulo.xml.dom.WrappingConverter;
import consulo.xml.dom.GenericDomValue;

import jakarta.annotation.Nullable;

/**
 * @author Gregory.Shrago
 */
public class ParamValueConverter extends WrappingConverter implements EmptyResolveMessageProvider {
  @Nullable
  public Converter<?> getConverter(@Nonnull final GenericDomValue domElement) {
    return HibernateManager.getInstance(domElement.getManager().getProject()).getConvertersRegistry().getValueConverter(domElement);
  }

  @Nonnull
  public LocalizeValue buildUnresolvedMessage(String ref) {
    return LocalizeValue.localizeTODO(HibernateMessages.message("cannot.resolve.parameter.value.0"));
  }

}
