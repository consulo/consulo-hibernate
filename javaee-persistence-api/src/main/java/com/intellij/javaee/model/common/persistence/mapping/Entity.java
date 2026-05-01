package com.intellij.javaee.model.common.persistence.mapping;

import com.intellij.java.language.psi.PsiClass;
import consulo.xml.dom.GenericValue;

public interface Entity {
    GenericValue<PsiClass> getClazz();
}
