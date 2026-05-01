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

import com.intellij.persistence.model.PersistentRelationshipAttribute;
import com.intellij.persistence.database.DatabaseTableInfo;
import com.intellij.persistence.database.DatabaseColumnInfo;
import consulo.language.util.IncorrectOperationException;

import java.util.Map;

/**
 * @author Gregory.Shrago
 */
public interface PersistentRelationshipAttributeManipulator<T extends PersistentRelationshipAttribute> extends PersistentAttributeManipulator<T>{
  void setMappedByAndInverse(final String name, final boolean inverse) throws IncorrectOperationException;

  void setMapKeyColumn(final String attributeName, final DatabaseColumnInfo columnInfo, final boolean inverse) throws IncorrectOperationException;

  void setJoinTable(final boolean inverse, final DatabaseTableInfo joinTable,
                    final Map<DatabaseColumnInfo, DatabaseColumnInfo> sourceColumnsMap,
                    final Map<DatabaseColumnInfo, DatabaseColumnInfo> targetColumnsMap) throws IncorrectOperationException;

  void setJoinColumns(final boolean inverse, final Map<DatabaseColumnInfo, DatabaseColumnInfo> columnsMap) throws IncorrectOperationException;
}