package com.intellij.javaee.model.xml.converters;

import com.intellij.java.language.psi.PsiJavaPackage;
import consulo.xml.dom.ConvertContext;
import consulo.xml.dom.ResolvingConverter;

import java.util.Collection;
import java.util.Collections;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

/**
 * Stub for PackageNameConverter - converts between string package names and PsiJavaPackage.
 */
public class PackageNameConverter extends ResolvingConverter<PsiJavaPackage> {

    @Nonnull
    @Override
    public Collection<? extends PsiJavaPackage> getVariants(ConvertContext context) {
        return Collections.emptyList();
    }

    @Nullable
    @Override
    public PsiJavaPackage fromString(@Nullable String s, ConvertContext context) {
        // TODO: implement package resolution
        return null;
    }

    @Nullable
    @Override
    public String toString(@Nullable PsiJavaPackage psiJavaPackage, ConvertContext context) {
        return psiJavaPackage != null ? psiJavaPackage.getQualifiedName() : null;
    }
}
