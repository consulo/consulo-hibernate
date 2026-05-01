package com.intellij.hibernate.impl.highlighting;

import com.intellij.hibernate.HibernateMessages;
import com.intellij.hibernate.facet.HibernateFacet;
import com.intellij.jpa.highlighting.JpaValidatorBase;
import com.intellij.persistence.facet.PersistenceFacetBase;
import com.intellij.persistence.facet.PersistenceFacetConfiguration;
import com.intellij.persistence.model.PersistencePackage;
import consulo.annotation.component.ExtensionImpl;

/**
 * @author Gregory.Shrago
 */
@ExtensionImpl
public class HibernateValidator extends JpaValidatorBase {

  public HibernateValidator() {
    super(HibernateMessages.message("persistence.validator.decription"));
  }

  protected boolean acceptsFacet(final PersistenceFacetBase<? extends PersistenceFacetConfiguration, ? extends PersistencePackage> facet) {
    return facet instanceof HibernateFacet;
  }
}
