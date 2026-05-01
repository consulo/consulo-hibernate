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

import com.intellij.persistence.database.DatabaseColumnInfo;
import com.intellij.persistence.database.DatabaseReferenceConstraintInfo;
import com.intellij.persistence.database.DbUtil;
import consulo.util.xml.serializer.XmlSerializer;
import org.jdom.Element;
import org.jetbrains.annotations.NonNls;

import java.util.Collection;

/**
 * Created by IntelliJ IDEA.
 * User: Gregory.Shrago
 * Date: 20.03.2006
 * Time: 17:28:20
 * To change this template use File | Settings | File Templates.
 */
public class DatabaseTableRelationData implements DatabaseReferenceConstraintInfo {
  private DatabaseTableData mySourceTable;
  private DatabaseTableData myTargetTable;
  private DatabaseTableFieldData mySourceField;
  private DatabaseTableFieldData myTargetField;

  public String RELATION_NAME;
  public String TARGET_TABLE;
  public String TARGET_SCHEMA;
  public String TARGET_CATALOG;
  public String TARGET_COLUMN;
  public String SOURCE_COLUMN;
  public boolean CASCADE_REMOVE;
  public boolean CASCADE_UPDATE;

  public DatabaseTableRelationData(final DatabaseTableData sourceTable,
                                   final String relationName,
                                   final String targetTable, final String targetSchema, final String targetCatalog,
                                   final String sourceField, final String targetField,
                                   final boolean cascadeRemove, final boolean cascadeUpdate) {
    mySourceTable = sourceTable;
    RELATION_NAME = relationName;
    TARGET_TABLE = targetTable;
    TARGET_SCHEMA = targetSchema;
    TARGET_CATALOG = targetCatalog;
    SOURCE_COLUMN = sourceField;
    TARGET_COLUMN = targetField;
    CASCADE_REMOVE = cascadeRemove;
    CASCADE_UPDATE = cascadeUpdate;
  }


  public DatabaseTableRelationData(final DatabaseTableData sourceTable) {
    mySourceTable = sourceTable;
  }

  public void readExternal(Element element) {
    XmlSerializer.deserializeInto(this, element);
  }

  public void resolveReferences(final Collection<DatabaseTableData> tables) {
    myTargetTable = DbUtil.findTable(TARGET_TABLE, TARGET_SCHEMA, TARGET_CATALOG, tables);
    myTargetField = myTargetTable == null? null : myTargetTable.findColumnByName(TARGET_COLUMN);
    mySourceField = mySourceTable.findColumnByName(SOURCE_COLUMN);
  }

  public void writeExternal(Element element) {
    XmlSerializer.serializeInto(this, element);
  }

  public boolean isCascadeRemove() {
    return CASCADE_REMOVE;
  }

  public boolean isCascadeUpdate() {
    return CASCADE_UPDATE;
  }

  public DatabaseTableData getTargetTable() {
    return myTargetTable;
  }

  public String getName() {
    return getRelationName();
  }

  public DatabaseColumnInfo getSourceColumn() {
    return getSourceField();
  }

  public DatabaseColumnInfo getTargetColumn() {
    return getTargetField();
  }

  public DatabaseTableData getSourceTable() {
    return mySourceTable;
  }

  public DatabaseTableFieldData getTargetField() {
    return myTargetField;
  }


  public DatabaseTableFieldData getSourceField() {
    return mySourceField;
  }

  public String getRelationName() {
    return RELATION_NAME;
  }

  public String getTargetTableName() {
    return TARGET_TABLE;
  }

  public String getTargetSchema() {
    return TARGET_SCHEMA;
  }

  public String getTargetCatalog() {
    return TARGET_CATALOG;
  }

  @NonNls
  public String toString() {
    return "Relation::" + SOURCE_COLUMN+"->"+TARGET_TABLE+"."+TARGET_COLUMN;
  }

}
