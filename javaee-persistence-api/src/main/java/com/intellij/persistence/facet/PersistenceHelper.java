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

package com.intellij.persistence.facet;

import java.util.Collection;

import jakarta.annotation.Nonnull;

import consulo.annotation.component.ComponentScope;
import consulo.annotation.component.ServiceAPI;
import consulo.application.Application;
import consulo.configurable.UnnamedConfigurable;
import consulo.project.Project;
import com.intellij.persistence.model.PersistencePackage;
import com.intellij.persistence.model.manipulators.ManipulatorsRegistry;
import com.intellij.persistence.util.PersistenceModelBrowser;
import consulo.language.psi.PsiElement;

/**
 * @author Gregory.Shrago
 */
@ServiceAPI(ComponentScope.APPLICATION)
public abstract class PersistenceHelper {

  public static PersistenceHelper getHelper() {
    return Application.get().getInstance(PersistenceHelper.class);
  }

  @Nonnull
  public abstract <C extends PersistenceFacetConfiguration, Unit extends PersistencePackage, MyFacet extends PersistenceFacetBase<C, Unit>> UnnamedConfigurable createUnitToDataSourceMappingComponent(final MyFacet facet, final boolean standaloneMode);

  @Nonnull
  public abstract ManipulatorsRegistry getManipulatorsRegistry();

  @Nonnull
  public abstract PersistenceModelBrowser createModelBrowser();

  public abstract PersistenceModelBrowser getSharedModelBrowser();

  public abstract void runCompositeWriteCommandAction(final Project project, final String actionName, final Collection<PsiElement> affectedElements, final Runnable... actions);
}
