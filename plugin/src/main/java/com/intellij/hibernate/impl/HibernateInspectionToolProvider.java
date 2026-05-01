package com.intellij.hibernate.impl;

import com.intellij.javaee.inspection.InspectionToolProvider;
import com.intellij.hibernate.impl.highlighting.HibernateConfigDomInspection;
import com.intellij.hibernate.impl.highlighting.HibernateMappingDatasourceDomInspection;
import com.intellij.hibernate.impl.highlighting.HibernateMappingDomInspection;
import com.intellij.hibernate.impl.highlighting.HibernateConfigDomFacetInspection;
/**
 * @author Gregory.Shrago
 */
public class HibernateInspectionToolProvider implements InspectionToolProvider {

  public Class[] getInspectionClasses() {
    return new Class[]{
      HibernateConfigDomInspection.class,
      HibernateConfigDomFacetInspection.class,
      HibernateMappingDomInspection.class,
      HibernateMappingDatasourceDomInspection.class
      };
  }
}
