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

import consulo.util.lang.function.Condition;
import com.intellij.persistence.facet.PersistenceFacetBase;
import com.intellij.persistence.facet.PersistenceFacetConfiguration;
import com.intellij.persistence.model.*;
import com.intellij.persistence.roles.PersistenceClassRole;
import com.intellij.persistence.roles.PersistenceClassRoleEnum;
import com.intellij.java.language.psi.PsiClass;
import consulo.language.psi.PsiElement;
import com.intellij.java.language.psi.PsiMember;
import consulo.application.util.query.Query;
import jakarta.annotation.Nonnull;

import java.util.List;

/**
 * @author Gregory.Shrago
 */
public interface PersistenceModelBrowser {
  @Nonnull
  PersistenceModelBrowser setRoleFilter(final Condition<PersistenceClassRole> filter);

  boolean acceptsRole(final PersistenceClassRole role);

  @Nonnull
  Query<PersistentObject> queryPerstistentObjectHierarchy(final PersistentObject obj);

  @Nonnull
  Query<PersistentAttribute> queryAttributes(final PersistentObject obj);

  @Nonnull
  Query<PersistentObject> queryTargetPersistentObjects(final PersistentRelationshipAttribute attribute);

  @Nonnull
  Query<PersistentObject> queryTargetPersistentObjects(final PersistentEmbeddedAttribute attribute);

  @Nonnull
  Query<PersistenceListener> queryPersistenceListeners(final PsiClass psiClass);

  @Nonnull
  Query<PersistentObject> queryPersistentObjects(final PsiClass psiClass);

  @Nonnull
  Query<PersistentObject> queryPersistentObjects(final PsiClass psiClass, final PersistenceClassRoleEnum type);

  @Nonnull
  Query<PersistentRelationshipAttribute> queryTheOtherSideAttributes(final PersistentRelationshipAttribute relationAttribute, boolean inverseOnly);

  @Nonnull
  Query<PersistentRelationshipAttribute> queryTheOtherSideAttributes(final PersistentRelationshipAttribute relationAttribute, boolean inverseOnly, final PersistentObject targetEntity);

  @Nonnull
  Query<PersistenceFacetBase<PersistenceFacetConfiguration, PersistencePackage>> queryPersistenceFacets(final PsiElement element);

  @Nonnull
  List<PersistentAttribute> getPersistenceAttributes(final PsiMember psiMember);

  @Nonnull
  List<PersistentAttribute> getPersistenceAttributes(final PersistentObject object, final PsiMember psiMember);

  @Nonnull
  List<PersistenceFacetBase<PersistenceFacetConfiguration, PersistencePackage>> getPersistenceFacets(final PersistencePackage unit);

  @Nonnull
  List<PersistencePackage> getPersistenceUnits(final PersistenceMappings mappings);

}
