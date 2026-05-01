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

import consulo.util.dataholder.Key;
import com.intellij.persistence.model.PersistencePackage;
import com.intellij.persistence.model.PersistentObject;

/**
 * @author Gregory.Shrago
 */
public final class PersistenceDataKeys {
  private PersistenceDataKeys() {
  }
  public static final Key<PersistenceFacetBase<PersistenceFacetConfiguration, PersistencePackage>> PERSISTENCE_FACET_CONTEXT = Key.create("PERSISTENCE_FACET_CONTEXT");
  public static final Key<PersistenceFacetBase<PersistenceFacetConfiguration, PersistencePackage>> PERSISTENCE_FACET = Key.create("PERSISTENCE_FACET");
  public static final Key<String> DEFAULT_NAME = Key.create("PERSISTENCE_DEFAULT_NAME");

  public static final Key<PersistencePackage> PERSISTENCE_UNIT = Key.create("PERSISTENCE_UNIT");
  public static final Key<PersistencePackage> PERSISTENCE_UNIT_CONTEXT = Key.create("PERSISTENCE_UNIT_CONTEXT");

  public static final Key<PersistentObject> PERSISTENT_OBJECT = Key.create("PERSISTENT_OBJECT");
  public static final Key<PersistentObject> PERSISTENT_OBJECT_CONTEXT = Key.create("PERSISTENT_OBJECT");

}
