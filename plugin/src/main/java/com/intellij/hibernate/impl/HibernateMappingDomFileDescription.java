/*
 * Copyright (c) 2000-2007 JetBrains s.r.o. All Rights Reserved.
 */
package com.intellij.hibernate.impl;

import jakarta.annotation.Nonnull;

import com.intellij.hibernate.impl.highlighting.HibernateDomAnnotator;
import com.intellij.hibernate.model.HibernateConstants;
import com.intellij.hibernate.model.xml.mapping.*;
import com.intellij.hibernate.util.HibernateUtil;
import consulo.module.Module;
import consulo.xml.language.psi.XmlFile;
import consulo.xml.dom.DomFileDescription;
import consulo.xml.dom.editor.DomElementsAnnotator;
import consulo.annotation.component.ExtensionImpl;

/**
 * @author peter
*/
@ExtensionImpl
public class HibernateMappingDomFileDescription extends DomFileDescription<HbmHibernateMapping> {
  public HibernateMappingDomFileDescription() {
    super(HbmHibernateMapping.class, HibernateConstants.HBM_XML_ROOT_TAG);
  }

  protected void initializeFileDescription() {
    // Note: registerImplementation() was removed in Consulo 3's DomFileDescription.
    // DOM implementation classes are now resolved via @Implementation annotations or other mechanisms.
  }

  public DomElementsAnnotator createAnnotator() {
    return new HibernateDomAnnotator();
  }

  public boolean isMyFile(@Nonnull XmlFile file, final Module module) {
    return HibernateUtil.isHibernateMapping(file, module);
  }

  public boolean isAutomaticHighlightingEnabled() {
    return false;
  }
}
