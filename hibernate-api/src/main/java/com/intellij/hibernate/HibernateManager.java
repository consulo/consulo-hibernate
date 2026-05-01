package com.intellij.hibernate;

import consulo.annotation.component.ComponentScope;
import consulo.annotation.component.ServiceAPI;
import consulo.project.Project;

/**
 * @author Gregory.Shrago
 */
@ServiceAPI(ComponentScope.PROJECT)
public abstract class HibernateManager {

  public static HibernateManager getInstance(Project project) {
    return project.getInstance(HibernateManager.class);
  }

  public abstract HibernateConvertersRegistry getConvertersRegistry();

}
