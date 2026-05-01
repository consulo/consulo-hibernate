package com.intellij.jpa.model.manipulators;

import com.intellij.jam.reflect.JamClassMeta;
import com.intellij.persistence.model.PersistencePackage;
import com.intellij.persistence.model.manipulators.PersistenceAction;
import com.intellij.persistence.model.manipulators.PersistenceManipulator;
import com.intellij.persistence.model.manipulators.UserResponse;
import com.intellij.java.language.psi.PsiClass;
import consulo.language.psi.PsiDirectory;
import consulo.language.psi.PsiElement;
import consulo.language.util.IncorrectOperationException;
import consulo.ui.ex.action.AnActionEvent;
import consulo.ui.ex.action.Presentation;
import consulo.ui.image.Image;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.util.Collection;
import java.util.Map;

public abstract class MappingsManipulatorBase<T> {
    private final T myTarget;

    public MappingsManipulatorBase(T target) {
        myTarget = target;
    }

    public T getManipulatorTarget() {
        return myTarget;
    }

    protected abstract <V> V getOrCreateModelObject(PersistencePackage unit, Class<V> aClass, JamClassMeta<?> meta, PsiClass psiClass);

    protected <V> V addPersistentObject(PsiDirectory directory, PersistencePackage unit, Class<V> aClass,
                                         JamClassMeta<?> meta, String shortClassName) throws IncorrectOperationException {
        return null;
    }

    public void addAffectedElements(@Nonnull Collection<PsiElement> affectedElements) {
    }

    public static class MyObjectAction<T> implements PersistenceAction {
        public MyObjectAction(MappingsManipulatorBase<?> manipulator, String name, Image icon, Class<T> clazz, JamClassMeta<?> meta, String typeName) {
        }

        @Override
        public int getGroupId() {
            return GROUP_OBJECT;
        }

        @Override
        public Object getActionKey() {
            return this;
        }

        @Override
        public String getActionName() {
            return "";
        }

        @Nonnull
        @Override
        public Presentation getPresentation() {
            return new Presentation();
        }

        @Nullable
        @Override
        public PersistenceManipulator getActiveManipulator() {
            return null;
        }

        @Override
        public void update(AnActionEvent e) {
        }

        @Override
        public boolean preInvoke(UserResponse response) {
            return false;
        }

        @Override
        public void putTargetElement(Map<PersistenceAction, PsiElement> targetMap) {
        }

        @Override
        public void addAffectedElements(@Nonnull Collection<PsiElement> affectedElements) {
        }

        @Override
        public void invokeAction(@Nonnull Collection<PsiElement> result) throws IncorrectOperationException {
        }

        @Override
        public boolean postInvoke(PersistenceAction action, UserResponse response) {
            return false;
        }
    }
}
