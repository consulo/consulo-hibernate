package com.intellij.jpa.model.manipulators;

import com.intellij.persistence.model.PersistenceMappings;
import com.intellij.persistence.model.manipulators.AbstractPersistenceAction;
import com.intellij.persistence.model.manipulators.PersistenceAction;
import com.intellij.persistence.model.manipulators.PersistenceUnitManipulator;
import consulo.language.psi.PsiElement;
import consulo.language.util.IncorrectOperationException;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Stub for com.intellij.jpa.model.manipulators.JpaUnitManipulator.
 * Provides common actions for persistence unit manipulation.
 */
public class JpaUnitManipulator {

  /**
   * Returns default create actions for a persistence unit manipulator.
   */
  public static List<PersistenceAction> getCreateActionsDefault(PersistenceUnitManipulator<?> unitManipulator,
                                                                  ArrayList<PersistenceAction> result) {
    // TODO: implement default create actions (add class, add jar, etc.)
    return result;
  }

  /**
   * Action for creating new mapping files in a persistence unit.
   */
  public static class MyMappingsAction extends AbstractPersistenceAction<PersistenceUnitManipulator<?>> {

    private final String myTemplateName;
    private final Class<? extends PersistenceMappings> myMappingsClass;

    @SuppressWarnings("unchecked")
    public MyMappingsAction(PersistenceUnitManipulator<?> manipulator, String actionName, String actionTitle,
                            String templateName, Class<? extends PersistenceMappings> mappingsClass) {
      super((PersistenceUnitManipulator) manipulator, actionName, actionTitle, null);
      myTemplateName = templateName;
      myMappingsClass = mappingsClass;
    }

    @Override
    public int getGroupId() {
      return PersistenceAction.GROUP_MAPPING;
    }

    @Nullable
    @Override
    protected PsiElement getTargetElement() {
      return null;
    }

    @Override
    public void addAffectedElements(@Nonnull Collection<PsiElement> affectedElements) {
      getManipulator().addAffectedElements(affectedElements);
    }

    @Override
    public void invokeAction(@Nonnull Collection<PsiElement> result) throws IncorrectOperationException {
      // TODO: implement mappings creation
    }
  }
}
