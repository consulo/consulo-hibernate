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

package com.intellij.persistence.model.helpers;

import java.util.List;
import java.util.Properties;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import com.intellij.persistence.model.PersistenceListener;
import com.intellij.persistence.model.PersistenceMappings;
import com.intellij.java.language.psi.PsiClass;
import consulo.language.psi.PsiFile;
import com.intellij.java.language.psi.PsiJavaPackage;
import consulo.xml.dom.GenericValue;

/**
 * @author Gregory.Shrago
*/
public interface PersistenceUnitModelHelper extends PersistenceModelHelper {

  @Nonnull
  <V extends PersistenceMappings> List<? extends GenericValue<V>> getMappingFiles(final Class<V> mappingsClass);

  @Nonnull
  List<? extends PersistenceListener> getPersistentListeners();

  @Nonnull
  List<? extends GenericValue<PsiFile>> getJarFiles();

  @Nonnull
  List<? extends GenericValue<PsiClass>> getClasses();

  @Nonnull
  List<? extends GenericValue<PsiJavaPackage>> getPackages();

  @Nullable
  GenericValue<String> getDataSourceName();

  @Nonnull
  Properties getPersistenceUnitProperties();

}
