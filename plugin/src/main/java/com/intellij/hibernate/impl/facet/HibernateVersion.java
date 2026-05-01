package com.intellij.hibernate.impl.facet;

/**
 * @author Gregory.Shrago
 */
public enum HibernateVersion {

  Hibernate_3_2_0("3.2", "hibernate-main-3.0.java");

  private final String myName;
  private final String myMainTemplateName;

  private HibernateVersion(String name, String mainTemplateName) {
    myName = name;
    myMainTemplateName = mainTemplateName;
  }

  public String getName() {
    return myName;
  }

  public String toString() {
    return myName;
  }

  public String getMainTemplateName() {
    return myMainTemplateName;
  }
}
