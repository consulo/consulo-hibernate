package com.intellij.hibernate.facet;

import com.intellij.persistence.model.PersistencePackage;
import consulo.annotation.component.ComponentScope;
import consulo.annotation.component.ExtensionAPI;
import consulo.component.extension.ExtensionPointName;
import jakarta.annotation.Nonnull;

import java.util.List;

/**
 * @author Gregory.Shrago
 */
@ExtensionAPI(ComponentScope.PROJECT)
public interface HibernateSessionFactoryProvider {
  ExtensionPointName<HibernateSessionFactoryProvider> EP_NAME = ExtensionPointName.create(HibernateSessionFactoryProvider.class);

  @Nonnull
  List<PersistencePackage> getSessionFactories(final HibernateFacet facet);
}
