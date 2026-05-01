/*
 * Copyright (c) 2000-2007 JetBrains s.r.o. All Rights Reserved.
 */

package com.intellij.hibernate.springIntegration;

import com.intellij.hibernate.facet.HibernateFacet;
import com.intellij.javaee.util.JamCommonUtil;
import consulo.util.lang.Comparing;
import consulo.util.lang.Pair;
import consulo.util.lang.StringUtil;
import com.intellij.persistence.model.PersistencePackage;
import com.intellij.java.language.psi.PsiClass;
import consulo.xml.language.psi.XmlAttribute;
import consulo.xml.language.psi.XmlTag;
import com.intellij.spring.impl.ide.model.SpringUtils;
import com.intellij.spring.impl.ide.model.xml.beans.*;
import java.util.function.Function;
import consulo.xml.dom.DomElement;
import consulo.xml.dom.GenericAttributeValue;
import consulo.xml.dom.GenericDomValue;
import consulo.xml.dom.DomUtil;
import org.jetbrains.annotations.NonNls;
import jakarta.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Gregory.Shrago
 */
public class IntegrationUtil {

  /**
   * Calls getParentOfType using raw types to bypass the generic bound
   * {@code T extends DomElement}. Spring DOM types extend the old
   * {@code consulo.xml.util.xml.DomElement} rather than the new
   * {@code consulo.xml.dom.DomElement}, but the runtime lookup still works.
   */
  @SuppressWarnings({"unchecked", "rawtypes"})
  @Nullable
  public static <T> T getParentOfType(DomElement element, Class<T> type, boolean strict) {
    return (T) ((DomElement) element).getParentOfType((Class) type, strict);
  }

  /**
   * Casts an object (typically a Spring DOM element that extends the old
   * {@code consulo.xml.util.xml.DomElement}) to the new {@link DomElement} interface.
   * At runtime, DOM proxy objects implement both old and new interfaces.
   */
  static DomElement asDomElement(Object obj) {
    return (DomElement) obj;
  }

  @NonNls static final List<Pair<String, Function<Pair<SpringBean, HibernateFacet>, PersistencePackage>>> myAnnoProviders = new ArrayList<Pair<String, Function<Pair<SpringBean, HibernateFacet>, PersistencePackage>>>();
  @NonNls static final List<Pair<String, Function<Pair<SpringBean, HibernateFacet>, PersistencePackage>>> myProviders = new ArrayList<Pair<String, Function<Pair<SpringBean, HibernateFacet>, PersistencePackage>>>();
  static {
    final Pair<String, Function<Pair<SpringBean, HibernateFacet>, PersistencePackage>> annoProvider = Pair.<String, Function<Pair<SpringBean, HibernateFacet>, PersistencePackage>>create(
      SpringBeanPersistenceUnit.ANNO_SESSION_FACTORY, new Function<Pair<SpringBean, HibernateFacet>, PersistencePackage>() {
      public PersistencePackage apply(final Pair<SpringBean, HibernateFacet> pair) {
        return IntegrationUtil.getPersistencePackage(pair.getFirst(), pair.getSecond(), true);
      }
    });
    myAnnoProviders.add(annoProvider);
    myProviders.add(annoProvider);
    final Function<Pair<SpringBean, HibernateFacet>, PersistencePackage> basicProvider =
      new Function<Pair<SpringBean, HibernateFacet>, PersistencePackage>() {
        public PersistencePackage apply(final Pair<SpringBean, HibernateFacet> pair) {
          return IntegrationUtil.getPersistencePackage(pair.getFirst(), pair.getSecond(), false);
        }
      };
    myProviders.add(Pair.<String, Function<Pair<SpringBean, HibernateFacet>, PersistencePackage>>create(SpringBeanPersistenceUnit.SESSION_FACTORY, basicProvider));
    myProviders.add(Pair.<String, Function<Pair<SpringBean, HibernateFacet>, PersistencePackage>>create(SpringBeanPersistenceUnit.SESSION_FACTORY_1_0, basicProvider));
  }

  @Nullable
  public static <T> T getProvider(final SpringBaseBeanPointer bean, final List<Pair<String, T>> providers, final boolean allowAbstract) {
    if (!allowAbstract && bean.isAbstract()) return null;
    final PsiClass beanClass = bean.getBeanClass();
    final String beanClassName = beanClass != null? beanClass.getQualifiedName() : bean.getSpringBean() instanceof SpringBean ? ((SpringBean)bean.getSpringBean()).getClazz().getStringValue() : null;
    for (Pair<String, T> pair : providers) {
      final String name = pair.getFirst();
      if (name.equals(beanClassName)) return pair.getSecond();
      if (JamCommonUtil.isSuperClass(beanClass, name)) return pair.getSecond();
    }
    return null;
  }

  public static PersistencePackage getPersistencePackage(final SpringBean bean, final HibernateFacet facet, final boolean supportsAnnotation) {
    return new SpringBeanPersistenceUnit(bean, facet.getModule(), supportsAnnotation);
  }

  public static boolean isSessionFactoryBean(@Nullable final SpringBean bean, final boolean annotationOnly, final boolean allowAbstract) {
    return bean != null && isSessionFactoryBean(DomSpringBeanPointer.createDomSpringBeanPointer(bean), annotationOnly, allowAbstract);
  }

  public static boolean isSessionFactoryBean(@Nullable final DomSpringBeanPointer bean, final boolean annotationOnly, final boolean allowAbstract) {
    return bean != null && getProvider(bean, annotationOnly? myAnnoProviders : myProviders, allowAbstract) != null;
  }

  public static SpringPropertyDefinition setBeanProperty(final SpringBean bean, final String propertyName, final String propertyAlias, final String propertyValue, final boolean isList) {
    SpringPropertyDefinition property = SpringUtils.findPropertyByName(bean, propertyName, false);
    if (property == null && propertyAlias != null) {
      property = SpringUtils.findPropertyByName(bean, propertyAlias, false);
    }
    return setBeanProperty(bean, property, propertyName, propertyValue, isList);
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  public static SpringPropertyDefinition setBeanProperty(final SpringBean bean,
                                                          SpringPropertyDefinition property,
                                                          final String propertyName,
                                                          final String propertyValue,
                                                          final boolean isList) {
    if (!(property instanceof SpringProperty)) {
      if (property != null) asDomElement(property).undefine();
      property = bean.addProperty();
      ((SpringProperty)property).getName().setStringValue(propertyName);
    }
    setPropertyValue((SpringProperty)property, propertyValue, isList);
    return property;
  }

  @Nullable
  public static Prop findProp(final Props props, final String propertyName, final String propertyAlias) {
    for (Prop prop : props.getProps()) {
      final String value = prop.getKey().getValue();
      if (Comparing.strEqual(value, propertyName) || Comparing.strEqual(value, propertyAlias)) {
        return prop;
      }
    }
    return null;
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  public static void setBeanProperty(final Props props, final String propertyName, final String propertyAlias, final String propertyValue) {
    Prop prop = findProp(props, propertyName, propertyAlias);
    if (prop == null) {
      prop = props.addProp();
      prop.getKey().setStringValue(propertyName);
    }
    ((GenericDomValue) asDomElement(prop)).setStringValue(propertyValue);
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  private static void setPropertyValue(final SpringProperty property, final String stringValue, final boolean isList) {
    final ListOrSet listOrSet = property.getList();
    if (!DomUtil.hasXml((DomElement) asDomElement(listOrSet))) {
      {
        final String valueAttrString;
        // property.getValueAttr() returns old GenericAttributeValue; cast through DomElement
        final DomElement valueAttrDom = asDomElement(property.getValueAttr());
        final XmlAttribute valueAttrElement = ((GenericAttributeValue) valueAttrDom).getXmlAttribute();
        valueAttrString = ((GenericAttributeValue<String>) valueAttrDom).getStringValue();
        if (valueAttrElement != null && StringUtil.isNotEmpty(valueAttrString)) {
          if (!isList) {
            ((GenericDomValue) valueAttrDom).setStringValue(stringValue);
            return;
          }
          ((GenericDomValue) asDomElement(listOrSet.addValue())).setStringValue(valueAttrString);
          valueAttrDom.undefine();
        }
      }
      {
        final SpringValue value = property.getValue();
        final DomElement valueDom = asDomElement(value);
        final XmlTag valueElement = valueDom.getXmlTag();
        final String valueString = ((GenericDomValue<String>) valueDom).getStringValue();
        if (valueElement != null && StringUtil.isNotEmpty(valueString)) {
          if (!isList) {
            ((GenericDomValue) valueDom).setStringValue(stringValue);
            return;
          }
          ((GenericDomValue) asDomElement(listOrSet.addValue())).setStringValue(valueString);
          valueDom.undefine();
        }
      }
    }
    ((GenericDomValue) asDomElement(listOrSet.addValue())).setStringValue(stringValue);
  }
}
