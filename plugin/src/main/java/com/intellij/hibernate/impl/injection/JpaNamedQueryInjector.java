package com.intellij.hibernate.impl.injection;

import com.intellij.java.language.psi.PsiAnnotation;
import com.intellij.java.language.psi.PsiLiteralExpression;
import consulo.annotation.component.ExtensionImpl;
import consulo.language.inject.MultiHostInjector;
import consulo.language.inject.MultiHostRegistrar;
import consulo.language.psi.PsiElement;
import jakarta.annotation.Nonnull;

/**
 * Injects JPQL into the {@code query} of {@code @jakarta.persistence.NamedQuery},
 * and plain SQL into the {@code query} of {@code @jakarta.persistence.NamedNativeQuery}.
 */
@ExtensionImpl
public class JpaNamedQueryInjector implements MultiHostInjector {

    private static final String NAMED_QUERY_FQN = "jakarta.persistence.NamedQuery";
    private static final String NAMED_NATIVE_QUERY_FQN = "jakarta.persistence.NamedNativeQuery";
    private static final String QUERY_ATTRIBUTE = "query";

    private static final String JPQL_VERSION_ID = "JPQL";

    @Nonnull
    @Override
    public Class<? extends PsiElement> getElementClass() {
        return PsiLiteralExpression.class;
    }

    @Override
    public void injectLanguages(@Nonnull MultiHostRegistrar registrar, @Nonnull PsiElement context) {
        if (!(context instanceof PsiLiteralExpression literal)) {
            return;
        }

        PsiAnnotation jpqlAnnotation = InjectionUtil.matchAnnotationAttribute(literal, NAMED_QUERY_FQN, QUERY_ATTRIBUTE);
        if (jpqlAnnotation != null) {
            InjectionUtil.injectInto(registrar, literal, InjectionUtil.preferredQlVersion(literal, JPQL_VERSION_ID));
            return;
        }

        PsiAnnotation nativeAnnotation = InjectionUtil.matchAnnotationAttribute(literal, NAMED_NATIVE_QUERY_FQN, QUERY_ATTRIBUTE);
        if (nativeAnnotation != null) {
            InjectionUtil.injectInto(registrar, literal, null);
        }
    }
}
