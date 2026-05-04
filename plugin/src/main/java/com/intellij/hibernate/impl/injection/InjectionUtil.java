package com.intellij.hibernate.impl.injection;

import com.intellij.java.language.psi.PsiAnnotation;
import com.intellij.java.language.psi.PsiAnnotationMemberValue;
import com.intellij.java.language.psi.PsiLiteralExpression;
import com.intellij.java.language.psi.PsiNameValuePair;
import consulo.document.util.TextRange;
import consulo.language.inject.MultiHostRegistrar;
import consulo.language.psi.ElementManipulators;
import consulo.language.psi.PsiElement;
import consulo.language.psi.PsiLanguageInjectionHost;
import consulo.language.version.LanguageVersion;
import consulo.sql.language.SqlLanguage;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

/**
 * Shared helpers for {@link consulo.language.inject.MultiHostInjector} implementations
 * that inject SQL / JPQL / HQL into Java annotation literals.
 */
final class InjectionUtil {

    private InjectionUtil() {
    }

    /**
     * @return the enclosing annotation if {@code literal} sits in {@code attributeName}
     * of an annotation whose qualified name equals {@code annotationFqn}; {@code null} otherwise.
     */
    @Nullable
    static PsiAnnotation matchAnnotationAttribute(@Nonnull PsiLiteralExpression literal,
                                                  @Nonnull String annotationFqn,
                                                  @Nonnull String attributeName) {
        if (!(literal.getValue() instanceof String stringValue) || stringValue.isEmpty()) {
            return null;
        }
        if (!(literal instanceof PsiLanguageInjectionHost host) || !host.isValidHost()) {
            return null;
        }

        PsiElement parent = literal.getParent();
        String actualName;
        PsiAnnotation annotation;

        if (parent instanceof PsiNameValuePair pair) {
            actualName = pair.getName() == null ? "value" : pair.getName();
            PsiElement attrList = pair.getParent();
            if (attrList == null || !(attrList.getParent() instanceof PsiAnnotation a)) {
                return null;
            }
            annotation = a;
        }
        else if (parent != null && parent.getParent() instanceof PsiAnnotation a) {
            actualName = "value";
            annotation = a;
        }
        else {
            return null;
        }

        if (!attributeName.equals(actualName)) {
            return null;
        }
        return annotationFqn.equals(annotation.getQualifiedName()) ? annotation : null;
    }

    /**
     * Inject the given SQL {@link LanguageVersion} (or the default {@link SqlLanguage} when {@code version}
     * is {@code null}) over the value-text range of {@code literal}.
     */
    static void injectInto(@Nonnull MultiHostRegistrar registrar,
                           @Nonnull PsiLiteralExpression literal,
                           @Nullable LanguageVersion version) {
        TextRange range = ElementManipulators.getValueTextRange(literal);
        if (range.isEmpty()) {
            return;
        }
        PsiLanguageInjectionHost host = (PsiLanguageInjectionHost) literal;

        if (version != null) {
            registrar.startInjecting(version)
                    .addPlace(null, null, host, range)
                    .doneInjecting();
        }
        else {
            registrar.startInjecting(SqlLanguage.INSTANCE)
                    .addPlace(null, null, host, range)
                    .doneInjecting();
        }
    }

    static boolean isAttributeTrue(@Nonnull PsiAnnotation annotation, @Nonnull String attributeName) {
        PsiAnnotationMemberValue value = annotation.findAttributeValue(attributeName);
        return value != null && "true".equals(value.getText());
    }

    @Nullable
    static LanguageVersion findSqlVersionById(@Nonnull String id) {
        for (LanguageVersion version : SqlLanguage.INSTANCE.getVersions()) {
            if (id.equals(version.getId())) {
                return version;
            }
        }
        return null;
    }
}
