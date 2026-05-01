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

package com.intellij.persistence;

import consulo.annotation.component.ComponentScope;
import consulo.annotation.component.ExtensionAPI;
import consulo.component.extension.ExtensionPointName;
import consulo.language.psi.PsiElement;
import consulo.project.Project;
import consulo.util.lang.Pair;
import consulo.util.lang.StringUtil;
import jakarta.annotation.Nullable;
import org.jetbrains.annotations.NonNls;

import java.util.Collection;
import java.util.Collections;

/**
 * @author Gregory.Shrago
 */
@ExtensionAPI(ComponentScope.PROJECT)
public abstract class DataSourceInfoProvider {
  public static ExtensionPointName<DataSourceInfoProvider> EP_NAME = ExtensionPointName.create(DataSourceInfoProvider.class);

  public abstract Collection<Pair<PsiElement,DataSourceInfo>> getDataSources(final Project project);

  @Nullable
  public static DataSourceInfo getInfo(@NonNls final String type,
                                       @NonNls final String name,
                                       @NonNls final Collection<String> drivers,
                                       @NonNls final Collection<String> urls,
                                       @NonNls final Collection<String> userNames,
                                       @NonNls final Collection<String> passwords) {
    if (!drivers.isEmpty() || !urls.isEmpty() || !userNames.isEmpty() || !passwords.isEmpty()) {
      return new DefaultDataSourceInfo(type, name, drivers, urls, userNames, passwords);
    }
    return null;
  }

  @Nullable
  public static DataSourceInfo getInfo(@NonNls final String type,
                                       @NonNls final String name,
                                       @NonNls final String driver,
                                       @NonNls final String url,
                                       @NonNls final String userName,
                                       @NonNls final String password) {
    if (StringUtil.isNotEmpty(driver) || StringUtil.isNotEmpty(url) || StringUtil.isNotEmpty(userName) ||
        StringUtil.isNotEmpty(password)) {
      return new DefaultDataSourceInfo(type, name, Collections.singletonList(driver), Collections.singletonList(url), Collections.singletonList(userName),
                        Collections.singletonList(password));
    }
    return null;
  }

  public interface DataSourceInfo {
    String getType();

    String getName();

    Collection<String> getDriverVariants();

    Collection<String> getUrlVariants();

    Collection<String> getUserNameVariants();

    Collection<String> getPasswordVariants();
  }

  public static class DefaultDataSourceInfo implements DataSourceInfo {
    private String myType;
    private String myName;
    private Collection<String> myDriverClassNames;
    private Collection<String> myUrls;
    private Collection<String> myUserNames;
    private Collection<String> myPasswords;

    public DefaultDataSourceInfo(final String type,
                  final String name,
                  final Collection<String> driverClassNames,
                  final Collection<String> urls,
                  final Collection<String> userNames,
                  final Collection<String> passwords) {
      myType = type;
      myName = name;
      myDriverClassNames = driverClassNames;
      myUrls = urls;
      myUserNames = userNames;
      myPasswords = passwords;
    }

    public String getType() {
      return myType;
    }

    public String getName() {
      return myName;
    }

    public Collection<String> getDriverVariants() {
      return myDriverClassNames;
    }

    public Collection<String> getUrlVariants() {
      return myUrls;
    }

    public Collection<String> getUserNameVariants() {
      return myUserNames;
    }

    public Collection<String> getPasswordVariants() {
      return myPasswords;
    }
  }
}
