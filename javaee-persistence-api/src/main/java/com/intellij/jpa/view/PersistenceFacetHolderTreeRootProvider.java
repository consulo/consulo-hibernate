package com.intellij.jpa.view;

import com.intellij.persistence.facet.PersistenceFacetBase;
import consulo.annotation.component.ComponentScope;
import consulo.annotation.component.ExtensionAPI;

/**
 * Stub for com.intellij.jpa.view.PersistenceFacetHolderTreeRootProvider.
 * Base class for tree root providers that display persistence facet holders
 * in the project view.
 *
 * TODO: This was previously implemented in the consulo.javaee plugin.
 * The original accepted a FacetType parameter, but since HibernateFacetType
 * no longer extends FacetType in Consulo 3, this needs to be adapted.
 *
 * @param <F> the persistence facet type
 */
@ExtensionAPI(ComponentScope.PROJECT)
public abstract class PersistenceFacetHolderTreeRootProvider<F extends PersistenceFacetBase<?, ?>> {

  public PersistenceFacetHolderTreeRootProvider(Object facetTypeOrNull) {
    // TODO: implement - previously accepted a FacetType instance
  }
}
