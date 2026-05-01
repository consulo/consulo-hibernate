/*
 * Copyright (c) 2000-2007 JetBrains s.r.o. All Rights Reserved.
 */

package com.intellij.hibernate.springIntegration;

import com.intellij.hibernate.facet.HibernateFacet;
import consulo.module.Module;
import com.intellij.persistence.facet.PersistenceHelper;
import com.intellij.persistence.model.manipulators.*;
import consulo.xml.language.psi.XmlFile;
import com.intellij.spring.impl.ide.SpringManager;
import com.intellij.spring.impl.ide.SpringModel;
import com.intellij.spring.impl.ide.model.xml.beans.Beans;
import consulo.xml.dom.DomFileElement;
import consulo.xml.dom.DomManager;
import gnu.trove.THashSet;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Gregory.Shrago
 */
public class HibernateFacetManipulator extends AbstractPersistenceManipulator<HibernateFacet> implements PersistenceFacetManipulator<HibernateFacet> {
  public HibernateFacetManipulator(final HibernateFacet target) {
    super(target);
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  public List<PersistenceAction> getCreateActions() {
    final Module module = getManipulatorTarget().getModule();
    final THashSet<XmlFile> files = new THashSet<XmlFile>();
    final List<SpringModel> springModels = SpringManager.getInstance(module.getProject()).getAllModels(module);
    for (SpringModel springModel : springModels) {
      files.addAll(springModel.getConfigFiles());
    }
    List<PersistenceAction> result = new ArrayList<PersistenceAction>();
    final DomManager domManager = DomManager.getDomManager(module.getProject());
    final ManipulatorsRegistry manipulatorsRegistry = PersistenceHelper.getHelper().getManipulatorsRegistry();
    for (XmlFile file : files) {
      // Use raw type to bypass generic bound: Beans extends old DomElement, not new DomElement
      final DomFileElement fileElement = domManager.getFileElement(file, (Class) Beans.class);
      if (fileElement != null) {
        final Beans beans = (Beans) fileElement.getRootElement();
        final PersistenceManipulator<Beans> manipulator = manipulatorsRegistry.getManipulator(beans, PersistenceManipulator.class);
        if (manipulator != null) {
          result.addAll(manipulator.getCreateActions());
        }
      }
    }
    return result;
  }

}
