/*
 * Copyright (c) 2000-2007 JetBrains s.r.o. All Rights Reserved.
 */

package com.intellij.hibernate.impl.actions;

import com.intellij.hibernate.impl.engine.HibernateEngine;
import com.intellij.hibernate.impl.engine.HibernateConsole;
import consulo.dataContext.DataContext;
import consulo.language.editor.CommonDataKeys;
import consulo.ui.ex.action.*;
import consulo.project.Project;
import com.intellij.persistence.facet.PersistenceDataKeys;
import com.intellij.persistence.facet.PersistenceFacetBase;
import com.intellij.persistence.facet.PersistenceFacetConfiguration;
import com.intellij.persistence.model.PersistencePackage;

/**
 * @author Gregory.Shrago
 */
public class OpenHqlConsoleAction extends AnAction {


  public void update(final AnActionEvent e) {
    final DataContext dataContext = e.getDataContext();
    final Project project = dataContext.getData(CommonDataKeys.PROJECT);
    final PersistenceFacetBase<PersistenceFacetConfiguration,PersistencePackage> facet =
      dataContext.getData(PersistenceDataKeys.PERSISTENCE_FACET);
    final PersistencePackage unit = dataContext.getData(PersistenceDataKeys.PERSISTENCE_UNIT);

    final boolean enable = project != null && facet != null && unit != null && unit.isValid()
                           && facet.getQlLanguage() != null
                           && HibernateEngine.isAvailable(facet.getModule());
    e.getPresentation().setEnabled(enable);
    e.getPresentation().setVisible(enable || !ActionPlaces.isPopupPlace(e.getPlace()));
  }

  public void actionPerformed(AnActionEvent e) {
    final DataContext dataContext = e.getDataContext();
    final Project project = dataContext.getData(CommonDataKeys.PROJECT);
    final PersistenceFacetBase<PersistenceFacetConfiguration, PersistencePackage> facet =
      dataContext.getData(PersistenceDataKeys.PERSISTENCE_FACET);
    final PersistencePackage unit = dataContext.getData(PersistenceDataKeys.PERSISTENCE_UNIT);

    HibernateConsole.openHqlConsole(project, dataContext.getData(CommonDataKeys.EDITOR), facet, unit, "", e);
  }

}