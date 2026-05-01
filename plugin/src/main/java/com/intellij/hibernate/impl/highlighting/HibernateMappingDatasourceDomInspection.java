/*
 * Copyright (c) 2000-2006 JetBrains s.r.o. All Rights Reserved.
 */

package com.intellij.hibernate.impl.highlighting;

import jakarta.annotation.Nonnull;

import com.intellij.hibernate.HibernateMessages;
import com.intellij.hibernate.model.HibernateConstants;
import consulo.localize.LocalizeValue;
import com.intellij.hibernate.model.xml.mapping.HbmHibernateMapping;
import consulo.xml.dom.DomElement;
import consulo.xml.dom.editor.BasicDomElementsInspection;
import consulo.xml.dom.editor.DomElementAnnotationHolder;
import consulo.xml.dom.editor.DomHighlightingHelper;
import com.intellij.jpa.highlighting.JpaDataSourceORMDomInspection;
import org.jetbrains.annotations.NonNls;

/**
 * @author Gregory.Shrago
 */
public class HibernateMappingDatasourceDomInspection extends BasicDomElementsInspection<HbmHibernateMapping, Void> {

  public HibernateMappingDatasourceDomInspection() {
    super(HbmHibernateMapping.class);
  }

  @Nonnull
  public LocalizeValue getGroupDisplayName() {
    return LocalizeValue.localizeTODO(HibernateConstants.HIBERNATE_INSPECTIONS_GROUP);
  }

  @Nonnull
  public LocalizeValue getDisplayName() {
    return LocalizeValue.localizeTODO(HibernateMessages.message("inspection.name.hibernate.datasource.mapping"));
  }

  @Nonnull
  @NonNls
  public String getShortName() {
    return "HibernateMappingDatasourceDomInspection";
  }

  protected void checkDomElement(final DomElement element, final DomElementAnnotationHolder holder, final DomHighlightingHelper helper, final Void state) {
    JpaDataSourceORMDomInspection.checkDataSourceRelatedValues(element, holder, helper);
  }
}