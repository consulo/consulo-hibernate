/*
 * Copyright (c) 2000-2007 JetBrains s.r.o. All Rights Reserved.
 */

package com.intellij.hibernate.impl;

import com.intellij.hibernate.model.HibernateConstants;
import com.intellij.hibernate.model.xml.mapping.HbmQuery;
import com.intellij.hibernate.model.xml.mapping.HbmQueryParam;
import com.intellij.jpa.QueryReferencesUtil;
import static consulo.xml.dom.pattern.DomPatterns.domElement;
import static consulo.xml.dom.pattern.DomPatterns.withDom;
import consulo.language.pattern.ElementPattern;
import consulo.language.pattern.PlatformPatterns;
import com.intellij.java.language.patterns.PsiJavaPatterns;
import consulo.xml.language.psi.pattern.XmlPatterns;
import static consulo.language.pattern.StandardPatterns.*;
import com.intellij.persistence.QueryInjectionSupport;
import com.intellij.java.language.psi.*;
import consulo.language.psi.PsiElement;
import consulo.language.psi.util.PsiTreeUtil;
import consulo.xml.language.psi.XmlAttributeValue;
import consulo.xml.language.psi.XmlTag;
import consulo.xml.language.psi.XmlText;
import gnu.trove.THashSet;
import org.jetbrains.annotations.NonNls;

import java.util.Arrays;
import java.util.Set;
import consulo.annotation.component.ExtensionImpl;

/**
 * @author Gregory.Shrago
 */
@ExtensionImpl
public class HibernateQueryInjectionSupport implements QueryInjectionSupport {

  private static final ElementPattern[] QUERY_PATTERNS = new ElementPattern[] {
    PsiJavaPatterns.psiExpression().methodCallParameter(0, PsiJavaPatterns.psiMethod().withName("createQuery").definedInClass(HibernateConstants.SESSION_CLASS)),
    PlatformPatterns.psiElement(XmlText.class).withParent(XmlPatterns.xmlTag().withName("query").and(withDom(domElement(HbmQuery.class))))
  };

  private static final ElementPattern[] QUERY_NAME_PATTERNS = new ElementPattern[] {
    PsiJavaPatterns.psiExpression().methodCallParameter(0, PsiJavaPatterns.psiMethod().withName("getNamedQuery").definedInClass(HibernateConstants.SESSION_CLASS)),
  };

  @NonNls
  private static final Set<String> SET_PARAMETER_METHOD_NAMES = new THashSet<String>(Arrays.asList("setParameter", "setParameterList",
                                                                                                   "setProperties", "setString",
                                                                                                   "setCharacter", "setBoolean", "setByte",
                                                                                                   "setShort", "setInteger", "setLong",
                                                                                                   "setFloat", "setDouble", "setBinary",
                                                                                                   "setText", "setSerializable", "setLocale",
                                                                                                   "setBigDecimal", "setBigInteger",
                                                                                                   "setDate", "setTime", "setTimestamp",
                                                                                                   "setCalendar", "setCalendarDate",
                                                                                                   "setEntity"));

  private static final ElementPattern[] PARAMETER_NAME_PATTERNS = new ElementPattern[] {
    XmlPatterns.xmlAttributeValue(XmlPatterns.xmlAttribute("name").withParent(
      XmlPatterns.xmlTag().withName("query-param").withParent(XmlPatterns.xmlTag().withName("query")).and(withDom(domElement(HbmQueryParam.class))))),

    PsiJavaPatterns.psiExpression().methodCallParameter(0, PsiJavaPatterns.psiMethod().withName(string().oneOf(SET_PARAMETER_METHOD_NAMES)).definedInClass(HibernateConstants.QUERY_CLASS).
      withParameters(CommonClassNames.JAVA_LANG_STRING, "..")),
  };


  public boolean acceptsQueryInjection(PsiElement element) {
    return or(QUERY_PATTERNS).accepts(element);
  }

  public boolean acceptsQueryName(final PsiElement element) {
    return or(QUERY_NAME_PATTERNS).accepts(element);
  }

  public boolean acceptsParameterName(final PsiElement element) {
    return or(PARAMETER_NAME_PATTERNS).accepts(element);
  }

  public PsiElement getQueryReferenceOrElementByParameterReference(final PsiElement paramElement) {
    if (paramElement instanceof XmlAttributeValue) {
      final PsiElement tag = paramElement.getParent().getParent().getParent();
      if (tag instanceof XmlTag && "query".equals(((XmlTag)tag).getName())) {
        return tag;
      }
    }
    final PsiMethodCallExpression expression = PsiTreeUtil.getParentOfType(paramElement, PsiMethodCallExpression.class);
    return expression == null ? null : QueryReferencesUtil.skipChainedMethodCalls(
      expression.getMethodExpression().getQualifierExpression(), SET_PARAMETER_METHOD_NAMES);
  }

}
