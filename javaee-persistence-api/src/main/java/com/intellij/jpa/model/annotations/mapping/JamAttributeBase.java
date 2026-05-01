package com.intellij.jpa.model.annotations.mapping;

import com.intellij.jam.JamElement;
import com.intellij.persistence.model.PersistentAttribute;
import com.intellij.persistence.model.PersistentObject;
import com.intellij.persistence.model.helpers.PersistentAttributeModelHelper;
import com.intellij.java.language.psi.PsiMember;
import com.intellij.java.language.psi.PsiType;
import consulo.language.psi.PsiElement;
import consulo.language.psi.PsiFile;
import consulo.language.psi.PsiManager;
import consulo.module.Module;
import consulo.xml.dom.GenericValue;
import consulo.xml.language.psi.XmlTag;
import jakarta.annotation.Nullable;

public abstract class JamAttributeBase implements JamElement, PersistentAttribute {

    @Override
    public GenericValue<String> getName() {
        return null;
    }

    @Nullable
    @Override
    public PsiMember getPsiMember() {
        return null;
    }

    @Nullable
    @Override
    public PersistentObject getPersistentObject() {
        return null;
    }

    @Nullable
    @Override
    public PsiType getPsiType() {
        return null;
    }

    @Override
    public PersistentAttributeModelHelper getAttributeModelHelper() {
        return null;
    }

    @Override
    public boolean isValid() {
        return false;
    }

    @Nullable
    @Override
    public XmlTag getXmlTag() {
        return null;
    }

    @Override
    public PsiManager getPsiManager() {
        return null;
    }

    @Nullable
    @Override
    public Module getModule() {
        return null;
    }

    @Override
    public PsiElement getIdentifyingPsiElement() {
        return null;
    }

    @Override
    public PsiFile getContainingFile() {
        return null;
    }
}
