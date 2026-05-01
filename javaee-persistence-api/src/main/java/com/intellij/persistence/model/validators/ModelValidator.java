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

package com.intellij.persistence.model.validators;

import consulo.language.editor.inspection.ProblemsHolder;
import com.intellij.persistence.model.*;
import com.intellij.java.language.psi.PsiClass;
import com.intellij.java.language.psi.PsiType;

/**
 * @author Gregory.Shrago
 */
public interface ModelValidator {
  boolean checkEmbeddable(final PsiClass aClass, final PersistentEmbeddable embeddable, final boolean isReportingErrors, final ProblemsHolder holder);
  
  boolean checkEntity(final PsiClass aClass, final PersistentEntity entity, final boolean isReportingErrors, final ProblemsHolder holder);

  boolean checkListener(final PsiClass aClass, final PersistenceListener listener, final boolean isReporingErrors, final ProblemsHolder holder);

  boolean checkSuperclass(final PsiClass aClass, final PersistentSuperclass superclass, final boolean isReportingErrors, final ProblemsHolder holder);

  String getRelationshipAttributeTypeProblem(final PsiType type, final RelationshipType relationshipType, final PsiClass targetEntityClass, final String attributeTypeName);

  String getAttributeTypeProblem(final PsiType type, final boolean isContainer, final boolean isEmbedded, final boolean isLob, final String attributeTypeName);
}
