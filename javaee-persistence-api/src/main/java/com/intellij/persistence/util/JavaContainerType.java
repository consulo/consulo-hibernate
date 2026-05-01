/*
 * Copyright 2000-2007 JetBrains s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.intellij.persistence.util;

import com.intellij.java.language.LanguageLevel;
import com.intellij.java.language.psi.CommonClassNames;
import com.intellij.java.language.psi.JavaPsiFacade;
import consulo.language.psi.PsiElement;
import com.intellij.java.language.psi.PsiType;
import com.intellij.java.language.psi.util.PsiUtil;
import consulo.language.util.IncorrectOperationException;

/**
 * @author Gregory.Shrago
 */
public enum JavaContainerType {
  MAP, SET, LIST, COLLECTION, ARRAY;

  public String getJavaBaseClassName() {
    switch (this) {
      case ARRAY: return CommonClassNames.JAVA_LANG_REFLECT_ARRAY;
      case COLLECTION: return CommonClassNames.JAVA_UTIL_COLLECTION;
      case LIST: return CommonClassNames.JAVA_UTIL_LIST;
      case MAP: return CommonClassNames.JAVA_UTIL_MAP;
      case SET: return CommonClassNames.JAVA_UTIL_SET;
      default: throw new AssertionError();
    }
  }

  public boolean isCollection() {
    return this == LIST || this == SET || this == COLLECTION;
  }

  public PsiType createCollectionType(final PsiElement context, final PsiType elementType, final PsiType mapKeyType) throws IncorrectOperationException {
    return JavaPsiFacade.getInstance(context.getProject()).getElementFactory()
      .createTypeFromText(getCollectionTypeText(PsiUtil.getLanguageLevel(context).isAtLeast(LanguageLevel.JDK_1_5), elementType.getCanonicalText(), mapKeyType == null? null : mapKeyType.getCanonicalText()), context);
  }

  public String getCollectionTypeText(final boolean useGenerics, final String elementTypeName, final String mapKeyType) {
    final String text;
    switch (this) {
      case ARRAY:
        text = elementTypeName + "[]";
        break;
      case COLLECTION:
        text = CommonClassNames.JAVA_UTIL_COLLECTION + (!useGenerics ? "" : "<" + elementTypeName + ">");
        break;
      case LIST:
        text = CommonClassNames.JAVA_UTIL_LIST + (!useGenerics ? "" : "<" + elementTypeName + ">");
        break;
      case MAP:
        text = CommonClassNames.JAVA_UTIL_MAP + (!useGenerics ? "" : "<" + (mapKeyType == null? CommonClassNames.JAVA_LANG_OBJECT : mapKeyType) + ", " + elementTypeName + ">");
        break;
      case SET:
        text = CommonClassNames.JAVA_UTIL_SET + (!useGenerics ? "" : "<" + elementTypeName + ">");
        break;
      default:
        throw new AssertionError();
    }
    return text;
  }
}
