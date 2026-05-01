package com.intellij.jpa;

import com.intellij.persistence.facet.PersistenceFacetBase;
import com.intellij.persistence.model.PersistenceMappings;
import com.intellij.persistence.util.PersistenceModelBrowser;
import consulo.util.lang.function.PairProcessor;
import jakarta.annotation.Nonnull;

import java.util.Collections;
import java.util.List;

/**
 * Stub for AbstractQlPersistenceModel - base class for QL persistence models.
 */
public abstract class AbstractQlPersistenceModel {

    protected abstract void processPersistenceMappings(PairProcessor<PersistenceMappings, PersistenceModelBrowser> processor);

    protected abstract boolean isHibernate();

    @Nonnull
    protected abstract List<? extends PersistenceFacetBase> getPersistenceFacets();
}
