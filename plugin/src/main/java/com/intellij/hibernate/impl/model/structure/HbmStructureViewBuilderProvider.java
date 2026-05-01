/*
 * Copyright (c) 2000-2007 JetBrains s.r.o. All Rights Reserved.
 */

package com.intellij.hibernate.impl.model.structure;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import consulo.fileEditor.structureView.StructureViewBuilder;
import consulo.xml.ide.structureView.xml.XmlStructureViewBuilderProvider;
import com.intellij.persistence.model.PersistenceMappings;
import com.intellij.persistence.model.PersistenceQuery;
import com.intellij.persistence.model.PersistentAttribute;
import com.intellij.persistence.model.PersistentObject;
import consulo.xml.language.psi.XmlFile;
import java.util.function.Function;
import consulo.util.lang.reflect.ReflectionUtil;
import consulo.xml.dom.DomElement;
import consulo.xml.dom.DomFileElement;
import consulo.xml.dom.DomManager;
import consulo.xml.dom.DomService;
import consulo.annotation.component.ExtensionImpl;

@ExtensionImpl
public class HbmStructureViewBuilderProvider implements XmlStructureViewBuilderProvider {
  @Nullable
  public StructureViewBuilder createStructureViewBuilder(@Nonnull XmlFile file) {
    final DomFileElement<DomElement> fileElement = DomManager.getDomManager(file.getProject()).getFileElement(file, DomElement.class);
    if (fileElement == null || !(fileElement.getRootElement() instanceof PersistenceMappings)) return null;
    return DomService.getInstance().createSimpleStructureViewBuilder(file, new Function<DomElement, DomService.StructureViewMode>() {
      @Nonnull
      public DomService.StructureViewMode apply(final DomElement domElement) {
        if (domElement instanceof PersistenceMappings ||
            domElement instanceof PersistentObject ||
            domElement instanceof PersistentAttribute ||
            domElement instanceof PersistenceQuery) return DomService.StructureViewMode.SHOW;
        return DomService.StructureViewMode.SKIP;
      }
    });

  }
}