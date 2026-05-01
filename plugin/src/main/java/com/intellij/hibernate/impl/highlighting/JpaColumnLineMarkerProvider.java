package com.intellij.hibernate.impl.highlighting;

import com.intellij.java.language.JavaLanguage;
import com.intellij.java.language.codeInsight.AnnotationUtil;
import com.intellij.java.language.psi.PsiClass;
import com.intellij.java.language.psi.PsiField;
import com.intellij.java.language.psi.PsiIdentifier;
import com.intellij.java.language.psi.PsiModifier;
import com.intellij.javaee.model.common.persistence.JavaeePersistenceConstants;
import com.intellij.persistence.DatabaseIcons;
import com.intellij.persistence.PersistenceIcons;
import consulo.annotation.access.RequiredReadAction;
import consulo.annotation.component.ExtensionImpl;
import consulo.codeEditor.markup.GutterIconRenderer;
import consulo.language.Language;
import consulo.language.editor.Pass;
import consulo.language.editor.gutter.LineMarkerInfo;
import consulo.language.editor.gutter.LineMarkerProvider;
import consulo.language.psi.PsiElement;
import consulo.ui.image.Image;
import org.jspecify.annotations.Nullable;

/**
 * Marks JPA columns: every non-static, non-transient field of an @Entity / @MappedSuperclass / @Embeddable class
 * is implicitly a column unless annotated @Transient.
 */
@ExtensionImpl
public class JpaColumnLineMarkerProvider implements LineMarkerProvider {

    @RequiredReadAction
    @Nullable
    @Override
    public LineMarkerInfo<?> getLineMarkerInfo(PsiElement element) {
        if (!(element instanceof PsiIdentifier)) {
            return null;
        }
        PsiElement parent = element.getParent();
        if (!(parent instanceof PsiField field)) {
            return null;
        }
        if (field.getNameIdentifier() != element) {
            return null;
        }
        if (field.hasModifierProperty(PsiModifier.STATIC) || field.hasModifierProperty(PsiModifier.TRANSIENT)) {
            return null;
        }
        if (AnnotationUtil.isAnnotated(field, JavaeePersistenceConstants.TRANSIENT_ANNO, false)) {
            return null;
        }

        PsiClass containingClass = field.getContainingClass();
        if (containingClass == null || !isPersistentClass(containingClass)) {
            return null;
        }

        Image icon = pickIcon(field);
        String columnName = resolveColumnName(field);

        return new LineMarkerInfo<>(
            element,
            element.getTextRange(),
            icon,
            Pass.LINE_MARKERS,
            e -> "JPA Column" + (columnName != null ? " → " + columnName : ""),
            null,
            GutterIconRenderer.Alignment.LEFT
        );
    }

    private static boolean isPersistentClass(PsiClass psiClass) {
        return AnnotationUtil.isAnnotated(psiClass, JavaeePersistenceConstants.ENTITY_ANNO, false)
            || AnnotationUtil.isAnnotated(psiClass, JavaeePersistenceConstants.MAPPED_SUPERCLASS_ANNO, false)
            || AnnotationUtil.isAnnotated(psiClass, JavaeePersistenceConstants.EMBEDDABLE_ANNO, false);
    }

    private static Image pickIcon(PsiField field) {
        if (AnnotationUtil.isAnnotated(field, JavaeePersistenceConstants.ID_ANNO, false)
            || AnnotationUtil.isAnnotated(field, JavaeePersistenceConstants.EMBEDDED_ID_ANNO, false)) {
            return DatabaseIcons.DATASOURCE_PK_COLUMN_ICON;
        }
        if (AnnotationUtil.isAnnotated(field, JavaeePersistenceConstants.MANY_TO_ONE_ANNO, false)
            || AnnotationUtil.isAnnotated(field, JavaeePersistenceConstants.ONE_TO_ONE_ANNO, false)
            || AnnotationUtil.isAnnotated(field, JavaeePersistenceConstants.JOIN_COLUMN_ANNO, false)) {
            return DatabaseIcons.DATASOURCE_FK_COLUMN_ICON;
        }
        if (AnnotationUtil.isAnnotated(field, JavaeePersistenceConstants.ONE_TO_MANY_ANNO, false)
            || AnnotationUtil.isAnnotated(field, JavaeePersistenceConstants.MANY_TO_MANY_ANNO, false)) {
            return PersistenceIcons.RELATIONSHIP_ICON;
        }
        if (AnnotationUtil.isAnnotated(field, JavaeePersistenceConstants.EMBEDDED_ANNO, false)) {
            return PersistenceIcons.OVR_EMBEDDED_ATTRIBUTE;
        }
        return DatabaseIcons.DATASOURCE_COLUMN_ICON;
    }

    @Nullable
    private static String resolveColumnName(PsiField field) {
        String name = readNameAttribute(field, JavaeePersistenceConstants.COLUMN_ANNO);
        if (name != null) {
            return name;
        }
        name = readNameAttribute(field, JavaeePersistenceConstants.JOIN_COLUMN_ANNO);
        if (name != null) {
            return name;
        }
        return field.getName();
    }

    @Nullable
    private static String readNameAttribute(PsiField field, String annoFqn) {
        var anno = AnnotationUtil.findAnnotation(field, annoFqn);
        if (anno == null) {
            return null;
        }
        String value = AnnotationUtil.getDeclaredStringAttributeValue(anno, "name");
        return (value != null && !value.isEmpty()) ? value : null;
    }

    @Override
    public Language getLanguage() {
        return JavaLanguage.INSTANCE;
    }
}
