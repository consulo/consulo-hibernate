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

import com.intellij.java.impl.util.descriptors.ConfigFile;
import com.intellij.java.impl.util.descriptors.ConfigFileContainer;
import com.intellij.java.impl.util.descriptors.ConfigFileMetaData;
import com.intellij.persistence.model.PersistenceMappings;
import com.intellij.persistence.model.PersistencePackage;
import com.intellij.persistence.model.validators.ModelValidator;
import consulo.disposer.Disposable;
import consulo.language.Language;
import consulo.module.Module;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.util.List;
import java.util.Map;

/**
 * @author Gregory.Shrago
 */
@Deprecated
public abstract class PersistenceFacetBase<C extends PersistenceFacetConfiguration, Unit extends PersistencePackage> implements Disposable {

  private Module myModule;
  private String myName;
  private C myConfiguration;

  protected PersistenceFacetBase() {
  }

  protected PersistenceFacetBase(@Nonnull Module module, @Nonnull String name, @Nonnull C configuration) {
    myModule = module;
    myName = name;
    myConfiguration = configuration;
  }

  @Nonnull
  public Module getModule() {
    return myModule;
  }

  public String getName() {
    return myName;
  }

  @Nonnull
  public C getConfiguration() {
    return myConfiguration;
  }

  @Override
  public void dispose() {
  }

  public abstract ConfigFile[] getDescriptors();

  public abstract ConfigFileContainer getDescriptorsContainer();

  @Nonnull
  public abstract List<Unit> getPersistenceUnits();

  @Nullable
  public abstract PersistenceMappings getAnnotationEntityMappings();

  @Nonnull
  public abstract PersistenceMappings getEntityMappings(@Nonnull final Unit unit);

  @Nonnull
  public abstract List<? extends PersistenceMappings> getDefaultEntityMappings(@Nonnull final Unit unit);

  @Nonnull
  public abstract Class<? extends PersistencePackage> getPersistenceUnitClass();

  @Nonnull
  public abstract Map<ConfigFileMetaData,Class<? extends PersistenceMappings>> getSupportedDomMappingFormats();

  public abstract String getDataSourceId(@Nonnull final Unit unit);

  public abstract void setDataSourceId(@Nonnull final Unit unit, final String dataSourceId);

  @Nullable
  public abstract Language getQlLanguage();

  @Nonnull
  public abstract ModelValidator getModelValidator(@Nullable final Unit unit);

  @Nonnull
  public abstract Class[] getInspectionToolClasses();

  @Nonnull
  public abstract PersistencePackageDefaults getPersistenceUnitDefaults(@Nonnull final Unit unit);

}
