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
package com.intellij.javaee.dataSource;

import consulo.disposer.Disposable;
import consulo.project.Project;
import consulo.component.util.ModificationTracker;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.util.List;

/**
 * author: lesya
 */
public abstract class DataSourceManager implements ModificationTracker {
  public static DataSourceManager getInstance(@Nonnull Project project) {
    return project.getComponent(DataSourceManager.class);
  }

  public abstract void addDataSourceListener(DataSourceListener listener);

  public abstract void addDataSourceListener(DataSourceListener listener, Disposable parent);

  public abstract void removeDataSourceListener(DataSourceListener listener);

  public abstract List<DataSource> getDataSources();

  @Nullable
  public abstract DataSource getDataSourceByName(String name);

  @Nullable
  public abstract DataSource getDataSourceByID(String id);

  public abstract void updateDataSource(DataSource dataSource);

  public abstract void importAndRefreshDataSources(String[] configuredNames,
                                                   DataSourceProvider dataSourceProvider,
                                                   ServerInstance serverInstance) throws Exception;
  public abstract void manageDatasources();
}
