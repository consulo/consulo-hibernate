/*
 * Copyright (c) 2000-2007 JetBrains s.r.o. All Rights Reserved.
 */

package com.intellij.hibernate.impl.view;

import com.intellij.jpa.view.PersistenceFacetHolderTreeRootProvider;
import com.intellij.hibernate.facet.HibernateFacet;
import consulo.annotation.component.ExtensionImpl;

/**
 * @author nik
 *
 * TODO: PersistenceFacetHolderTreeRootProvider previously accepted a FacetType in its constructor.
 * HibernateFacetType no longer extends FacetType. This needs to be adapted when
 * PersistenceFacetHolderTreeRootProvider is updated in consulo.javaee to work without FacetType.
 */
@ExtensionImpl
public class HibernateFacetHolderTreeRootProvider extends PersistenceFacetHolderTreeRootProvider<HibernateFacet> {
  public HibernateFacetHolderTreeRootProvider() {
    // TODO: super() call needs adaptation - PersistenceFacetHolderTreeRootProvider
    // needs to be updated in consulo.javaee to accept something other than FacetType
    super(null);
  }
}
