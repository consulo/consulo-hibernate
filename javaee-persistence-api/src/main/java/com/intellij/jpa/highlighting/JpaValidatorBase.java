package com.intellij.jpa.highlighting;

import com.intellij.persistence.facet.PersistenceFacetBase;
import com.intellij.persistence.facet.PersistenceFacetConfiguration;
import com.intellij.persistence.model.PersistencePackage;
import consulo.annotation.component.ComponentScope;
import consulo.annotation.component.ExtensionAPI;
import jakarta.annotation.Nonnull;

@ExtensionAPI(ComponentScope.PROJECT)
public abstract class JpaValidatorBase {
    private final String myId;

    public JpaValidatorBase(@Nonnull String id) {
        myId = id;
    }

    protected abstract boolean acceptsFacet(PersistenceFacetBase<? extends PersistenceFacetConfiguration, ? extends PersistencePackage> facet);
}
