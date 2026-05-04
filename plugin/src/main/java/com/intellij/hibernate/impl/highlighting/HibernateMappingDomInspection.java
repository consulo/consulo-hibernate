/*
 * Copyright (c) 2000-2006 JetBrains s.r.o. All Rights Reserved.
 */

package com.intellij.hibernate.impl.highlighting;

import jakarta.annotation.Nonnull;

import com.intellij.hibernate.localize.HibernateLocalize;
import com.intellij.hibernate.model.HibernateConstants;
import consulo.localize.LocalizeValue;
import com.intellij.hibernate.model.xml.mapping.HbmHibernateMapping;
import com.intellij.javaee.model.JavaeePersistenceORMResolveConverters;
import consulo.annotation.component.ExtensionImpl;
import consulo.xml.dom.Converter;
import consulo.xml.dom.DomElement;
import consulo.xml.dom.GenericDomValue;
import consulo.xml.dom.WrappingConverter;
import consulo.xml.dom.editor.BasicDomElementsInspection;
import consulo.xml.dom.editor.DomElementAnnotationHolder;
import consulo.xml.dom.editor.DomHighlightingHelper;
import org.jetbrains.annotations.NonNls;

/**
 * @author Gregory.Shrago
 */
@ExtensionImpl
public class HibernateMappingDomInspection extends BasicDomElementsInspection<HbmHibernateMapping, Void> {

  public HibernateMappingDomInspection() {
    super(HbmHibernateMapping.class);
  }

  @Nonnull
  public LocalizeValue getGroupDisplayName() {
    return HibernateLocalize.inspectionGroupNameHibernateIssues();
  }

  @Nonnull
  public LocalizeValue getDisplayName() {
    return HibernateLocalize.inspectionNameHibernateMapping();
  }

  @Nonnull
  @NonNls
  public String getShortName() {
    return "HibernateMappingDomInspection";
  }

  protected boolean shouldCheckResolveProblems(final GenericDomValue value) {
    final Converter realConverter = WrappingConverter.getDeepestConverter(value.getConverter(), value);
    return !(realConverter instanceof JavaeePersistenceORMResolveConverters.ResolverBase);
  }

  protected void checkDomElement(final DomElement element, final DomElementAnnotationHolder holder, final DomHighlightingHelper helper, final Void state) {
    final int oldSize = holder.getSize();
    element.accept(new HibernateMappingHighlightingVisitor(holder));
    if (oldSize == holder.getSize()) {
      super.checkDomElement(element, holder, helper, state);
    }
  }
}