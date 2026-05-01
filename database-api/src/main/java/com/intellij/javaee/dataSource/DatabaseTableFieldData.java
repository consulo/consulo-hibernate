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
import consulo.util.collection.ContainerUtil;
import consulo.util.lang.function.Condition;
import consulo.util.xml.serializer.XmlSerializer;
import consulo.util.xml.serializer.annotation.Transient;
import org.jdom.Element;
import org.jetbrains.annotations.NonNls;

import java.sql.Types;
import java.util.List;

public class DatabaseTableFieldData implements DatabaseColumnInfo {
  private DatabaseTableData myTable;

  public String NAME;
  public String SQL_TYPE;
  public int JDBC_TYPE;
  public String TYPE;
  public boolean PRIMARY;
  public int LENGTH;
  public int PRECISION;
  public boolean NULLABLE;
  private boolean INSERTABLE = true;
  private boolean UPDATABLE = true;

  public DatabaseTableFieldData(final String colName, final String sqlType, final int jdbcType, final String colType, final boolean primary, final int length, final int precision,
                                final boolean nullable, final DatabaseTableData databaseTableData) {
    NAME = colName;
    SQL_TYPE = sqlType;
    TYPE = colType;
    JDBC_TYPE = jdbcType;
    PRIMARY = primary;
    LENGTH = length;
    PRECISION = precision;
    NULLABLE = nullable;
    myTable = databaseTableData;
  }
  
  public DatabaseTableFieldData(final String colName, final int jdbcType, final String colType, final boolean primary, final int length, final int precision,
                                final boolean nullable, final DatabaseTableData databaseTableData) {
    this(colName, null, Types.OTHER, colType, primary, length, precision, nullable, databaseTableData);
  }

  @Deprecated
  public DatabaseTableFieldData(final String colName, final String colType, final boolean primary, final int length, final int precision,
                                final boolean nullable, final DatabaseTableData databaseTableData) {
    this(colName, Types.OTHER, colType, primary, length, precision, nullable,  databaseTableData);
  }

  public void readExternal(Element element) {
    XmlSerializer.deserializeInto(this, element);
  }

  public void writeExternal(Element element) {
    XmlSerializer.serializeInto(this, element);
  }

  public DatabaseTableFieldData(DatabaseTableData table){
    this(null, null, false, 0, 0, true, table);
  }

  public String getName(){
    return NAME;
  }

  public DatabaseTableData getTable(){
    return myTable;
  }

  public String getSqlType(){
    return SQL_TYPE;
  }

  public String getType(){
    return TYPE;
  }

  @Transient
  public void setType(String javaType) {
    TYPE = javaType;
  }

  public int getJdbcType() {
    return JDBC_TYPE;
  }

  public boolean isPrimary(){
    return PRIMARY;
  }

  public boolean isForeign() {
    return ContainerUtil.find(myTable.getRelationships(), new Condition<DatabaseTableRelationData>() {
      public boolean value(final DatabaseTableRelationData object) {
        return object.getSourceField() == DatabaseTableFieldData.this;
      }
    }) != null;
  }

  public List<DatabaseTableRelationData> getRelationships() {
    return ContainerUtil.findAll(myTable.getRelationships(), new Condition<DatabaseTableRelationData>() {
      public boolean value(final DatabaseTableRelationData object) {
        return object.getSourceField() == DatabaseTableFieldData.this;
      }
    });
  }

  public boolean isNullable() {
    return NULLABLE;
  }

  public int getLength() {
    return LENGTH;
  }

  public int getPrecision() {
    return PRECISION;
  }

  public boolean isInsertable() {
    return INSERTABLE;
  }

  public boolean isUpdatable() {
    return UPDATABLE;
  }

  @Transient
  void setInsertable(final boolean insertable) {
    INSERTABLE = insertable;
  }

  @Transient
  void setUpdatable(final boolean updatable) {
    UPDATABLE = updatable;
  }

  @NonNls
  public String toString() {
    return "Field::" + NAME;
  }

}
