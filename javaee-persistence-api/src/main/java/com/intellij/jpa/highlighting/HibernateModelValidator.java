package com.intellij.jpa.highlighting;

import com.intellij.persistence.model.validators.ModelValidator;
import com.intellij.persistence.util.PersistenceModelBrowser;
import consulo.language.editor.inspection.ProblemsHolder;
import com.intellij.persistence.model.*;
import com.intellij.java.language.psi.PsiClass;
import com.intellij.java.language.psi.PsiType;

public class HibernateModelValidator implements ModelValidator {
    private final PersistenceModelBrowser myBrowser;

    public HibernateModelValidator(PersistenceModelBrowser browser) {
        myBrowser = browser;
    }

    @Override
    public boolean checkEmbeddable(PsiClass aClass, PersistentEmbeddable embeddable, boolean isReportingErrors, ProblemsHolder holder) {
        return true;
    }

    @Override
    public boolean checkEntity(PsiClass aClass, PersistentEntity entity, boolean isReportingErrors, ProblemsHolder holder) {
        return true;
    }

    @Override
    public boolean checkListener(PsiClass aClass, PersistenceListener listener, boolean isReportingErrors, ProblemsHolder holder) {
        return true;
    }

    @Override
    public boolean checkSuperclass(PsiClass aClass, PersistentSuperclass superclass, boolean isReportingErrors, ProblemsHolder holder) {
        return true;
    }

    @Override
    public String getRelationshipAttributeTypeProblem(PsiType type, RelationshipType relationshipType, PsiClass targetEntityClass, String attributeTypeName) {
        return null;
    }

    @Override
    public String getAttributeTypeProblem(PsiType type, boolean isContainer, boolean isEmbedded, boolean isLob, String attributeTypeName) {
        return null;
    }
}
