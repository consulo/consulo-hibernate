package com.intellij.jpa.util;

import com.intellij.java.impl.util.descriptors.ConfigFileContainer;
import com.intellij.java.impl.util.descriptors.ConfigFileMetaData;
import com.intellij.java.language.psi.PsiType;
import com.intellij.javaee.model.common.persistence.mapping.AttributeType;
import com.intellij.jpa.AbstractQlPersistenceModel;
import com.intellij.persistence.model.PersistentAttribute;
import com.intellij.persistence.roles.PersistenceClassRoleEnum;
import consulo.language.Language;
import consulo.language.psi.PsiElement;
import consulo.language.psi.PsiReference;
import consulo.module.Module;
import consulo.project.Project;
import consulo.language.editor.ui.awt.EditorTextField;
import consulo.util.lang.Pair;
import consulo.language.psi.PsiFile;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.util.Collection;
import java.util.Collections;
import java.util.function.Function;

/**
 * Stub for JpaUtil - JPA utility methods.
 */
public class JpaUtil {

    @SuppressWarnings("unchecked")
    public static <T extends PsiFile> Pair<EditorTextField, T> createQlEditor(Project project, String text,
                                                                               boolean readOnly, Language language,
                                                                               AbstractQlPersistenceModel model) {
        EditorTextField field = new EditorTextField(text, project, null);
        return (Pair) Pair.create(field, null);
    }

    public static Collection<PsiElement> getAttributePsiMembers(Object attribute) {
        return Collections.emptyList();
    }

    @Nullable
    public static <T> T findReferenceOfType(PsiReference[] references, Class<T> clazz) {
        return null;
    }

    @Nullable
    public static PsiType findType(Object element, Function<PersistentAttribute, PsiType> provider) {
        return null;
    }

    @Nullable
    public static <V> V getOrChooseElement(Module module, ConfigFileContainer container, ConfigFileMetaData metaData,
                                            Class<V> rootClass, String title, boolean createIfNeeded) {
        return null;
    }

    public static boolean isPersistentObject(@Nullable PsiType type, @Nonnull PersistenceClassRoleEnum role) {
        return false;
    }

    @Nonnull
    public static PsiType getAttributeTypeOrDefault(@Nonnull PersistentAttribute attribute) {
        final AttributeType attrType = AttributeType.getAttributeTypeOrDefault(attribute);
        return attrType.getDefaultPsiType(attribute);
    }
}
