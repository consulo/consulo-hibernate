package com.intellij.hibernate.impl.highlighting;

import com.intellij.java.language.JavaLanguage;
import com.intellij.java.language.codeInsight.AnnotationUtil;
import com.intellij.java.language.psi.PsiClass;
import com.intellij.java.language.psi.PsiIdentifier;
import com.intellij.javaee.model.common.persistence.JavaeePersistenceConstants;
import com.intellij.jpa.model.annotations.mapping.JamJpaEntity;
import com.intellij.persistence.PersistenceIcons;
import consulo.annotation.access.RequiredReadAction;
import consulo.annotation.component.ExtensionImpl;
import consulo.codeEditor.markup.GutterIconRenderer;
import consulo.language.Language;
import consulo.language.editor.Pass;
import consulo.language.editor.gutter.LineMarkerInfo;
import consulo.language.editor.gutter.LineMarkerProvider;
import consulo.language.psi.PsiElement;
import org.jspecify.annotations.Nullable;

/**
 * Provides gutter line markers for JPA @Entity and @Table annotated classes.
 */
@ExtensionImpl
public class JpaEntityLineMarkerProvider implements LineMarkerProvider {

    @RequiredReadAction
    @Nullable
    @Override
    public LineMarkerInfo<?> getLineMarkerInfo(PsiElement element) {
        if (!(element instanceof PsiIdentifier)) {
            return null;
        }

        PsiElement parent = element.getParent();
        if (!(parent instanceof PsiClass psiClass)) {
            return null;
        }

        PsiIdentifier nameIdentifier = psiClass.getNameIdentifier();
        if (nameIdentifier != element) {
            return null;
        }

        if (!AnnotationUtil.isAnnotated(psiClass, JavaeePersistenceConstants.ENTITY_ANNO, false)) {
            return null;
        }

        String tableName = resolveTableName(psiClass);

        return new LineMarkerInfo<>(
            element,
            element.getTextRange(),
            PersistenceIcons.ENTITY_ICON,
            Pass.LINE_MARKERS,
            e -> "JPA Entity" + (tableName != null ? " → " + tableName : ""),
            null,
            GutterIconRenderer.Alignment.LEFT
        );
    }

    @Nullable
    private static String resolveTableName(PsiClass psiClass) {
        String tableName = getDeclaredTableName(psiClass);
        if (tableName != null && !tableName.isEmpty()) {
            return tableName;
        }
        // Fall back to @Entity(name=...) or simple class name
        String entityName = AnnotationUtil.getDeclaredStringAttributeValue(
            AnnotationUtil.findAnnotation(psiClass, JavaeePersistenceConstants.ENTITY_ANNO), "name"
        );
        if (entityName != null && !entityName.isEmpty()) {
            return entityName;
        }
        return psiClass.getName();
    }

    @Nullable
    private static String getDeclaredTableName(PsiClass psiClass) {
        var tableAnno = AnnotationUtil.findAnnotation(psiClass, JavaeePersistenceConstants.TABLE_ANNO);
        if (tableAnno == null) {
            return null;
        }
        return AnnotationUtil.getDeclaredStringAttributeValue(tableAnno, "name");
    }

    @Override
    public Language getLanguage() {
        return JavaLanguage.INSTANCE;
    }
}
