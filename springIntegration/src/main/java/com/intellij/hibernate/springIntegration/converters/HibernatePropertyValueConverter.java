package com.intellij.hibernate.springIntegration.converters;

import com.intellij.hibernate.HibernateConvertersRegistry;
import com.intellij.hibernate.HibernateManager;
import com.intellij.hibernate.model.xml.config.Property;
import com.intellij.hibernate.springIntegration.IntegrationUtil;
import consulo.util.lang.function.Condition;
import consulo.util.lang.Pair;
import com.intellij.java.language.psi.PsiType;
import com.intellij.spring.impl.ide.model.xml.beans.Prop;
import com.intellij.spring.impl.ide.model.xml.beans.SpringBean;
import com.intellij.spring.impl.ide.model.xml.beans.SpringProperty;
import consulo.xml.dom.Converter;
import consulo.xml.dom.GenericDomValue;
import consulo.xml.dom.WrappingConverter;
import org.jetbrains.annotations.NonNls;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

public class HibernatePropertyValueConverter extends WrappingConverter implements Condition<Pair<PsiType, GenericDomValue>> {
  @NonNls private static final String HIBERNATE_PROPERTIES = "hibernateProperties";

  @Nullable
  public Converter<?> getConverter(@Nonnull final GenericDomValue domElement) {
    final Prop prop = IntegrationUtil.getParentOfType(domElement, Prop.class, false);
    if (prop != null) {
      final String value = prop.getKey().getStringValue();
      if (value != null) {
        final HibernateConvertersRegistry convertersRegistry =
          HibernateManager.getInstance(domElement.getManager().getProject()).getConvertersRegistry();
        return convertersRegistry.getConverter(Property.class, value);
      }
    }
    return null;
  }

  public boolean value(final Pair<PsiType, GenericDomValue> pair) {
    final GenericDomValue domValue = pair.getSecond();
    final SpringProperty springProperty = IntegrationUtil.getParentOfType(domValue, SpringProperty.class, false);
    if (springProperty != null && HIBERNATE_PROPERTIES.equals(springProperty.getName().getStringValue())) {
      final SpringBean springBean = IntegrationUtil.getParentOfType(domValue, SpringBean.class, false);
      if (springBean != null && IntegrationUtil.isSessionFactoryBean(springBean, false, true)) {
        return true;
      }
    }
    return false;
  }

}
