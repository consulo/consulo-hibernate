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

import jakarta.annotation.Nonnull;

import consulo.module.Module;
import consulo.util.lang.Pair;
import consulo.virtualFileSystem.VirtualFile;
import com.intellij.persistence.facet.PersistenceFacetBase;
import com.intellij.persistence.facet.PersistenceFacetConfiguration;
import com.intellij.persistence.model.PersistenceMappings;
import com.intellij.persistence.model.PersistencePackage;
import com.intellij.persistence.model.PersistentObject;
import com.intellij.persistence.model.RelationshipType;
import com.intellij.java.language.psi.PsiClass;
import consulo.language.psi.PsiDirectory;
import com.intellij.java.language.psi.PsiType;
import java.util.function.Function;

import jakarta.annotation.Nullable;

/**
 * @author Gregory.Shrago
 */
public interface UserResponse {
  @Nullable
  String getPersistenceUnitName(@Nonnull final PersistenceFacetBase<?, ? extends PersistencePackage> facet);

  @Nonnull
  VirtualFile[] getPersistenceMappingFiles(@Nonnull final PersistenceFacetBase<?, ?> facet, final String templateName, final Class<? extends PersistenceMappings> mappingsClass);

  @Nullable
  Pair<PsiDirectory, String> getClassName(@Nonnull final Module module, final String title, final String helpID);

  void askUserToSetupListener(final PersistenceAction action, final PersistenceFacetBase<PersistenceFacetConfiguration, PersistencePackage> facet,
                              final PsiClass aClass);

  @Nullable
  AttributeInfo getAttributeName(final PersistentObject object, final RelationshipType relationshipType, final Function<PsiType, String> typeValidator,
                                 final String title, final String defaultName);


  class AttributeInfo {
    public final String name;
    public final PsiType type;
    public final boolean fieldAccess;

    public final String targetAttribute;
    public final boolean inverse;

    public AttributeInfo(final String name, final PsiType type, final boolean fieldAccess) {
      this.name = name;
      this.type = type;
      this.fieldAccess = fieldAccess;
      targetAttribute = null;
      inverse = false;
    }

    public AttributeInfo(final String name, final PsiType type,
                         final boolean fieldAccess,
                         final String targetAttribute, final boolean inverse) {
      this.name = name;
      this.type = type;
      this.fieldAccess = fieldAccess;
      this.targetAttribute = targetAttribute;
      this.inverse = inverse;
    }
  }
}
