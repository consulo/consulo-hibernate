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

package com.intellij.persistence.roles;

import consulo.module.Module;
import com.intellij.persistence.facet.PersistenceFacetBase;
import com.intellij.persistence.facet.PersistenceFacetConfiguration;
import com.intellij.persistence.model.PersistencePackage;
import com.intellij.persistence.model.PersistentObject;
import com.intellij.persistence.model.PersistenceListener;

import consulo.ui.image.Image;

/**
 * @author Gregory.Shrago
 */
public interface PersistenceClassRole {

  PersistenceClassRole[] EMPTY_ARRAY = new PersistenceClassRole[0];

  PersistenceClassRoleEnum getType();

  String getTitle();

  Image getIcon();

  Module getModule();

  PersistenceFacetBase<PersistenceFacetConfiguration, PersistencePackage> getFacet();

  PersistencePackage getPersistenceUnit();

  PersistentObject getPersistentObject();

  PersistenceListener getEntityListener();
}
