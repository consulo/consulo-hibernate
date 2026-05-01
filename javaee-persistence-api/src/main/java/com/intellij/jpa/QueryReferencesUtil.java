package com.intellij.jpa;

import consulo.language.psi.PsiElement;
import jakarta.annotation.Nullable;

import java.util.Set;

/**
 * Stub for QueryReferencesUtil - utility for query reference resolution.
 */
public class QueryReferencesUtil {

    @Nullable
    public static PsiElement skipChainedMethodCalls(PsiElement expression, Set<String> methodNames) {
        // TODO: implement chained method call skipping
        return expression;
    }
}
