package com.intellij.hibernate.model.converters;

import com.intellij.hibernate.model.enums.CascadeType;
import com.intellij.hibernate.localize.HibernateLocalize;
import consulo.language.psi.PsiElement;
import consulo.xml.dom.ConvertContext;
import consulo.xml.dom.ElementPresentationManager;
import consulo.xml.dom.GenericDomValue;
import consulo.xml.dom.NamedEnumUtil;
import consulo.xml.dom.convert.DelimitedListConverter;
import java.util.function.Function;
import jakarta.annotation.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import consulo.localize.LocalizeValue;

/**
 * @author Gregory.Shrago
 */
public class CascadeTypeListConverter extends DelimitedListConverter<CascadeType> {

  public CascadeTypeListConverter() {
    super(", ");
  }

  protected CascadeType convertString(final @Nullable String string, final ConvertContext context) {
    if (string == null) return null;
    return NamedEnumUtil.getEnumElementByValue(CascadeType.class, string);
  }

  protected String toString(final @Nullable CascadeType cascadeType) {
    return cascadeType == null ? null : cascadeType.getValue();
  }

  protected Object[] getReferenceVariants(final ConvertContext context, final GenericDomValue<List<CascadeType>> genericDomValue) {
    final List<CascadeType> variants = new ArrayList<CascadeType>(Arrays.asList(CascadeType.values()));
    filterVariants(variants, genericDomValue);
    return ElementPresentationManager.getInstance().createVariants(variants, new Function<CascadeType, String>() {
      public String apply(final CascadeType cascadeType) {
        return cascadeType.getValue();
      }
    });
  }

  protected PsiElement resolveReference(@Nullable final CascadeType cascadeType, final ConvertContext context) {
    return cascadeType == null ? null : context.getReferenceXmlElement();
  }

  protected LocalizeValue buildUnresolvedMessageInner(final String value) {
    return HibernateLocalize.cannotResolveCascadeType0(value);
  }
}