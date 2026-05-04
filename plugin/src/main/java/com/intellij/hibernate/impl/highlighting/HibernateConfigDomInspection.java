/*
 * Copyright (c) 2000-2006 JetBrains s.r.o. All Rights Reserved.
 */

package com.intellij.hibernate.impl.highlighting;

import jakarta.annotation.Nonnull;

import com.intellij.hibernate.localize.HibernateLocalize;
import com.intellij.hibernate.model.HibernateConstants;
import consulo.localize.LocalizeValue;
import com.intellij.hibernate.model.xml.config.HibernateConfiguration;
import consulo.annotation.component.ExtensionImpl;
import consulo.xml.dom.editor.BasicDomElementsInspection;
import consulo.xml.dom.editor.DomElementAnnotationHolder;
import consulo.xml.dom.editor.DomHighlightingHelper;
import consulo.xml.dom.DomElement;
import org.jetbrains.annotations.NonNls;

/**
 * @author Gregory.Shrago
 */
@ExtensionImpl
public class HibernateConfigDomInspection extends BasicDomElementsInspection<HibernateConfiguration, Void> {

  public HibernateConfigDomInspection() {
    super(HibernateConfiguration.class);
  }

  protected void checkDomElement(final DomElement element, final DomElementAnnotationHolder holder, final DomHighlightingHelper helper, final Void state) {
    super.checkDomElement(element, holder, helper, state);
  }
  

  @Nonnull
  public LocalizeValue getGroupDisplayName() {
    return HibernateLocalize.inspectionGroupNameHibernateIssues();
  }

  @Nonnull
  public LocalizeValue getDisplayName() {
    return HibernateLocalize.inspectionNameHibernateConfiguration();
  }

  @Nonnull
  @NonNls
  public String getShortName() {
    return "HibernateConfigDomInspection";
  }

}