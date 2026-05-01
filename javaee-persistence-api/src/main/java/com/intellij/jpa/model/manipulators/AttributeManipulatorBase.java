package com.intellij.jpa.model.manipulators;

import com.intellij.persistence.model.manipulators.PersistenceAction;
import com.intellij.persistence.model.manipulators.PersistenceManipulator;
import consulo.language.psi.PsiElement;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public abstract class AttributeManipulatorBase<T> implements PersistenceManipulator<T> {
    private final T myTarget;

    public AttributeManipulatorBase(T target) {
        myTarget = target;
    }

    public T getManipulatorTarget() {
        return myTarget;
    }

    public List<PersistenceAction> getCreateActions() {
        return Collections.emptyList();
    }

    public void addAffectedElements(Collection<PsiElement> affectedElements) {
    }
}
