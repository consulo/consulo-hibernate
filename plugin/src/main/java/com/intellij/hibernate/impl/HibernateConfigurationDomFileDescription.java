/*
 * Copyright (c) 2000-2007 JetBrains s.r.o. All Rights Reserved.
 */
package com.intellij.hibernate.impl;

import java.util.Collections;
import java.util.Set;

import jakarta.annotation.Nonnull;

import com.intellij.hibernate.facet.HibernateFacet;
import com.intellij.hibernate.impl.highlighting.HibernateDomAnnotator;
import com.intellij.hibernate.model.HibernateConstants;
import com.intellij.hibernate.model.xml.config.HibernateConfiguration;
import com.intellij.hibernate.util.HibernateUtil;
import consulo.module.Module;
import consulo.language.util.ModuleUtilCore;
import consulo.xml.language.psi.XmlFile;
import consulo.xml.dom.DomFileDescription;
import consulo.xml.dom.editor.DomElementsAnnotator;
import consulo.annotation.component.ExtensionImpl;

/**
 * @author peter
*/
@ExtensionImpl
public class HibernateConfigurationDomFileDescription extends DomFileDescription<HibernateConfiguration> {
  public HibernateConfigurationDomFileDescription() {
    super(HibernateConfiguration.class, HibernateConstants.CFG_XML_ROOT_TAG);
  }

  protected void initializeFileDescription() {
    // Note: registerImplementation() was removed in Consulo 3's DomFileDescription.
    // DOM implementation classes are now resolved via @Implementation annotations or other mechanisms.
  }

  public DomElementsAnnotator createAnnotator() {
    return new HibernateDomAnnotator();
  }

  public boolean isMyFile(@Nonnull XmlFile file, final Module module) {
    return HibernateUtil.isHibernateConfig(file, module);
  }

  @Nonnull
    public Set<? extends Object> getDependencyItems(final XmlFile file) {
    final Module module = ModuleUtilCore.findModuleForPsiElement(file);
    return Collections.singleton(module == null ? null : HibernateFacet.getInstance(module));
  }

  public boolean isAutomaticHighlightingEnabled() {
    return false;
  }
}
