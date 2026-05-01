package com.intellij.jpa.model.common;

import com.intellij.persistence.facet.PersistenceFacetBase;
import com.intellij.persistence.model.PersistenceMappings;
import com.intellij.persistence.model.helpers.PersistenceMappingsModelHelper;
import com.intellij.java.language.psi.PsiJavaPackage;
import consulo.language.psi.PsiElement;
import consulo.language.psi.PsiFile;
import consulo.language.psi.PsiManager;
import consulo.module.Module;
import consulo.xml.language.psi.XmlTag;
import consulo.xml.dom.GenericValue;
import jakarta.annotation.Nullable;
import java.util.List;

public class MergedPersistenceMappings implements PersistenceMappings {
    private final PersistenceFacetBase myFacet;

    public MergedPersistenceMappings(PersistenceFacetBase facet, List<PersistenceMappings> mappings) {
        myFacet = facet;
    }

    @Override
    public PersistenceMappingsModelHelper getModelHelper() {
        return null;
    }

    @Override
    public GenericValue<PsiJavaPackage> getPackage() {
        return null;
    }

    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    @Nullable
    public PsiFile getContainingFile() {
        return null;
    }

    @Override
    @Nullable
    public PsiElement getIdentifyingPsiElement() {
        return null;
    }

    @Override
    @Nullable
    public XmlTag getXmlTag() {
        return null;
    }

    @Override
    @Nullable
    public PsiManager getPsiManager() {
        return null;
    }

    @Override
    @Nullable
    public Module getModule() {
        return myFacet != null ? myFacet.getModule() : null;
    }
}
