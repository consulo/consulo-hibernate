package com.intellij.hibernate.impl.injection;

import com.intellij.java.language.psi.PsiAnnotation;
import com.intellij.java.language.psi.PsiLiteralExpression;
import consulo.annotation.component.ExtensionImpl;
import consulo.language.inject.MultiHostInjector;
import consulo.language.inject.MultiHostRegistrar;
import consulo.language.psi.PsiElement;
import jakarta.annotation.Nonnull;

/**
 * Injects HQL into the {@code query} of {@code @org.hibernate.annotations.NamedQuery},
 * and plain SQL into the {@code query} of {@code @org.hibernate.annotations.NamedNativeQuery}.
 * <p>
 * These are Hibernate's vendor extensions of the JPA {@code @NamedQuery} / {@code @NamedNativeQuery},
 * adding cache, flush mode, timeout, etc. The query string itself is the same dialect Hibernate
 * parses at runtime — HQL.
 */
@ExtensionImpl
public class HibernateNamedQueryInjector implements MultiHostInjector {

    private static final String HIBERNATE_NAMED_QUERY_FQN = "org.hibernate.annotations.NamedQuery";
    private static final String HIBERNATE_NAMED_NATIVE_QUERY_FQN = "org.hibernate.annotations.NamedNativeQuery";
    private static final String QUERY_ATTRIBUTE = "query";

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

        PsiAnnotation hqlAnnotation = InjectionUtil.matchAnnotationAttribute(literal, HIBERNATE_NAMED_QUERY_FQN, QUERY_ATTRIBUTE);
        if (hqlAnnotation != null) {
            InjectionUtil.injectInto(registrar, literal, InjectionUtil.findSqlVersionById(HQL_VERSION_ID));
            return;
        }

        PsiAnnotation nativeAnnotation = InjectionUtil.matchAnnotationAttribute(literal, HIBERNATE_NAMED_NATIVE_QUERY_FQN, QUERY_ATTRIBUTE);
        if (nativeAnnotation != null) {
            InjectionUtil.injectInto(registrar, literal, null);
        }
    }
}
