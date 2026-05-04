package com.intellij.hibernate.impl.injection;

import com.intellij.java.language.psi.PsiAnnotation;
import com.intellij.java.language.psi.PsiLiteralExpression;
import consulo.annotation.component.ExtensionImpl;
import consulo.language.inject.MultiHostInjector;
import consulo.language.inject.MultiHostRegistrar;
import consulo.language.psi.PsiElement;
import consulo.language.version.LanguageVersion;
import jakarta.annotation.Nonnull;

/**
 * Injects SQL into the {@code value} of {@code @org.springframework.data.jpa.repository.Query}.
 * <p>
 * Picks the {@code HQL} dialect by default (a strict superset of JPQL — what Hibernate's runtime
 * actually parses); falls back to plain SQL when {@code nativeQuery=true}.
 */
@ExtensionImpl
public class SpringDataQueryInjector implements MultiHostInjector {

    private static final String QUERY_ANNOTATION_FQN = "org.springframework.data.jpa.repository.Query";
    private static final String VALUE_ATTRIBUTE = "value";
    private static final String NATIVE_QUERY_ATTRIBUTE = "nativeQuery";

    private static final String HQL_VERSION_ID = "HQL";

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
        PsiAnnotation annotation = InjectionUtil.matchAnnotationAttribute(literal, QUERY_ANNOTATION_FQN, VALUE_ATTRIBUTE);
        if (annotation == null) {
            return;
        }

        LanguageVersion version = InjectionUtil.isAttributeTrue(annotation, NATIVE_QUERY_ATTRIBUTE)
                ? null
                : InjectionUtil.findSqlVersionById(HQL_VERSION_ID);
        InjectionUtil.injectInto(registrar, literal, version);
    }
}
