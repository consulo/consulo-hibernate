package com.intellij.javaee.model.common;

import com.intellij.jam.model.common.CommonModelElement;
import consulo.language.psi.PsiElement;
import consulo.language.psi.PsiFile;
import consulo.language.psi.PsiManager;
import consulo.module.Module;
import consulo.xml.language.psi.XmlTag;

/**
 * Stub for com.intellij.javaee.model.common.CommonModelElementWrapper.
 * A base class that wraps a CommonModelElement and delegates all calls to it.
 */
public abstract class CommonModelElementWrapper implements CommonModelElement {

  public abstract CommonModelElement getDelegate();

  @Override
  public boolean isValid() {
    CommonModelElement delegate = getDelegate();
    return delegate != null && delegate.isValid();
  }

  @Override
  public XmlTag getXmlTag() {
    CommonModelElement delegate = getDelegate();
    return delegate != null ? delegate.getXmlTag() : null;
  }

  @Override
  public PsiManager getPsiManager() {
    CommonModelElement delegate = getDelegate();
    return delegate != null ? delegate.getPsiManager() : null;
  }

  @Override
  public Module getModule() {
    CommonModelElement delegate = getDelegate();
    return delegate != null ? delegate.getModule() : null;
  }

  @Override
  public PsiElement getIdentifyingPsiElement() {
    CommonModelElement delegate = getDelegate();
    return delegate != null ? delegate.getIdentifyingPsiElement() : null;
  }

  @Override
  public PsiFile getContainingFile() {
    CommonModelElement delegate = getDelegate();
    return delegate != null ? delegate.getContainingFile() : null;
  }
}
