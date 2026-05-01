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

package com.intellij.persistence.model.manipulators;

import com.intellij.persistence.database.DatabaseTableInfo;
import com.intellij.persistence.database.DatabaseColumnInfo;
import com.intellij.persistence.model.*;
import com.intellij.persistence.util.JavaContainerType;
import com.intellij.java.language.psi.PsiAnnotation;
import com.intellij.java.language.psi.PsiClass;
import com.intellij.java.language.psi.PsiMember;
import com.intellij.java.language.psi.PsiType;
import com.intellij.java.language.psi.util.PropertyMemberType;
import consulo.language.util.IncorrectOperationException;

import java.util.Collection;

/**
 * @author Gregory.Shrago
 */
public interface PersistentObjectManipulator<T extends PersistentObject> extends PersistenceManipulator<T> {

  void setGenerateColumnProperties(final boolean generateColumnProperties);

  PsiClass ensureClassExists() throws IncorrectOperationException;

  PsiClass ensureIdClassExists() throws IncorrectOperationException;

  PsiMember ensurePropertyExists(final PsiClass psiClass, final String name, final PsiType type, PropertyMemberType memberType, final PsiAnnotation[] psiAnnotations) throws IncorrectOperationException;

  void setTable(final DatabaseTableInfo tableInfo) throws IncorrectOperationException;

  PersistentEmbeddedAttribute addEmbeddedAttribute(final PersistentEmbeddable embeddable, final String attributeName, final PropertyMemberType accessMode) throws
                                                                                                                                    IncorrectOperationException;
  PersistentRelationshipAttribute addRelationshipAttribute(final PersistentEntityBase entity, final RelationshipType relationshipType,
                                                           final JavaContainerType containerType, final String attributeName, final String targetAttributeName,
                                                           final boolean inverse,
                                                           final boolean optional,
                                                           final String fetchType,
                                                           final Collection<String> cascadeVariants,
                                                           final PropertyMemberType accessMode) throws
                                                                                                               IncorrectOperationException;

  void addCascadeVariants(final Collection<String> cascadeVariants);

  void addFetchVariants(final Collection<String> fetchVariants);

  PersistentAttribute addAttribute(final String attributeName, final PsiType attributeType, final PropertyMemberType accessType,
                                   final Collection<? extends DatabaseColumnInfo> columns)
    throws IncorrectOperationException;

  PersistentAttribute addIdAttribute(final boolean compositeId, final String attributeName, final PsiType attributeType, final PropertyMemberType accessType,
                                     final Collection<? extends DatabaseColumnInfo> columns)
    throws IncorrectOperationException;

  void setIdClass(final String qualifiedName) throws IncorrectOperationException;

  void addNamedQuery(final String queryName, final String queryText) throws IncorrectOperationException;
}
