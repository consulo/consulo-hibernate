package com.intellij.jpa.model.manipulators;

import com.intellij.javaee.model.common.persistence.mapping.AttributeType;
import com.intellij.persistence.model.PersistentObject;
import com.intellij.persistence.model.RelationshipType;
import com.intellij.persistence.model.manipulators.AbstractPersistenceAction;
import com.intellij.persistence.model.manipulators.AbstractPersistenceManipulator;
import com.intellij.persistence.model.manipulators.PersistenceAction;
import com.intellij.persistence.model.manipulators.PersistentObjectManipulator;
import com.intellij.java.language.psi.PsiAnnotation;
import com.intellij.java.language.psi.PsiClass;
import com.intellij.java.language.psi.PsiMember;
import com.intellij.java.language.psi.PsiType;
import com.intellij.java.language.psi.util.PropertyMemberType;
import consulo.language.psi.PsiElement;
import consulo.language.util.IncorrectOperationException;
import consulo.ui.ex.action.AnActionEvent;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.util.Collection;
import java.util.List;

/**
 * Stub for com.intellij.jpa.model.manipulators.ObjectManipulatorBase.
 * Base class for persistent object manipulators that provides common functionality
 * for creating attributes, ensuring classes exist, etc.
 */
public abstract class ObjectManipulatorBase<T extends PersistentObject> extends AbstractPersistenceManipulator<T>
    implements PersistentObjectManipulator<T> {

  private boolean myGenerateColumnProperties;

  public ObjectManipulatorBase(T target) {
    super(target);
  }

  public void setGenerateColumnProperties(boolean generateColumnProperties) {
    myGenerateColumnProperties = generateColumnProperties;
  }

  public boolean isGenerateColumnProperties() {
    return myGenerateColumnProperties;
  }

  @Nullable
  public PsiClass ensureClassExists() throws IncorrectOperationException {
    // TODO: implement class creation/lookup
    return null;
  }

  @Nullable
  public PsiClass ensureIdClassExists() throws IncorrectOperationException {
    // TODO: implement id class creation/lookup
    return null;
  }

  @Nullable
  public PsiMember ensurePropertyExists(PsiClass psiClass, String name, PsiType type,
                                         PropertyMemberType memberType, PsiAnnotation[] psiAnnotations)
      throws IncorrectOperationException {
    // TODO: implement property creation/lookup
    return null;
  }

  public abstract List<PersistenceAction> getCreateActions();

  /**
   * Base class for attribute creation actions.
   */
  public abstract static class MyAttributeAction<M extends ObjectManipulatorBase<?>> extends AbstractPersistenceAction<M> {
    protected final AttributeType myAttributeType;
    protected AttributeInfo myInfo;

    public MyAttributeAction(M manipulator, AttributeType attributeType) {
      super(manipulator, attributeType.toString(), attributeType.toString(), null);
      myAttributeType = attributeType;
    }

    @Override
    public int getGroupId() {
      return PersistenceAction.GROUP_ATTRIBUTE;
    }

    @Nullable
    protected abstract RelationshipType getRelationshipType();

    @Override
    public void update(AnActionEvent e) {
      super.update(e);
    }

    @Override
    public void addAffectedElements(@Nonnull Collection<PsiElement> affectedElements) {
      getManipulator().addAffectedElements(affectedElements);
    }

    @Nullable
    @Override
    protected PsiElement getTargetElement() {
      return null;
    }

    @Override
    public void invokeAction(@Nonnull Collection<PsiElement> result) throws IncorrectOperationException {
      getManipulator().addAffectedElements(result);
    }

    /**
     * Holds information about the attribute to be created.
     */
    public static class AttributeInfo {
      public String name;
      public PsiType type;
      public String targetAttribute;
      public boolean inverse;
    }
  }
}
