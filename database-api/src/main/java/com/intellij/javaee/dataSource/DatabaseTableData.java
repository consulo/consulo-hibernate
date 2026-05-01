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

import consulo.util.lang.Comparing;
import consulo.util.lang.function.Condition;
import com.intellij.persistence.database.DatabaseColumnInfo;
import com.intellij.persistence.database.DatabaseReferenceConstraintInfo;
import com.intellij.persistence.database.DatabaseTableLongInfo;
import com.intellij.persistence.database.TableType;
import consulo.util.collection.ContainerUtil;
import consulo.util.xml.serializer.XmlSerializer;
import consulo.util.xml.serializer.annotation.Transient;
import org.jdom.Element;
import org.jetbrains.annotations.NonNls;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DatabaseTableData implements DatabaseTableLongInfo {

    
  @NonNls public static final String TYPE_TABLE = "TABLE";
  @NonNls public static final String TYPE_VIEW = "VIEW";
  @NonNls public static final String TYPE_SEQUENCE = "SEQUENCE";
  public static final String[] TABLE_TYPES = new String[] { TYPE_TABLE, TYPE_VIEW, TYPE_SEQUENCE };

  @NonNls private static final String RS_COLUMN_NAME = "COLUMN_NAME";
  @NonNls private static final String RS_DATA_TYPE = "DATA_TYPE";

  @NonNls private static final String RS_PK_TABLE_CAT = "PKTABLE_CAT";
  @NonNls private static final String RS_PK_TABLE_SCHEM = "PKTABLE_SCHEM";
  @NonNls private static final String RS_PK_TABLE_NAME = "PKTABLE_NAME";
  @NonNls private static final String RS_PK_COLUMN_NAME = "PKCOLUMN_NAME";
  @NonNls private static final String RS_FK_COLUMN_NAME = "FKCOLUMN_NAME";
  @NonNls private static final String RS_FK_NAME = "FK_NAME";
  @NonNls private static final String RS_KEY_SEQ = "KEY_SEQ";

  @NonNls private static final String FIELD_ELEMENT_NAME = "FIELD_ELEMENT";
  @NonNls private static final String RELATIONSHIP_ELEMENT_NAME = "RELATIONSHIP_ELEMENT";
  @NonNls private static final String RS_TYPE_NAME = "TYPE_NAME";
  @NonNls private static final String RS_COLUMN_SIZE = "COLUMN_SIZE";
  @NonNls private static final String RS_DECIMAL_DIGITS = "DECIMAL_DIGITS";
  @NonNls private static final String RS_NULLABLE = "NULLABLE";
  @NonNls private static final String RS_PRIVILEGE = "PRIVILEGE";
  @NonNls private static final String RS_UPDATE_RULE = "UPDATE_RULE";
  @NonNls private static final String RS_DELETE_RULE = "DELETE_RULE";

  public String NAME;
  public String SCHEMA;
  public String CATALOG;
  public String TYPE;
  private DataSource myDataSource;

  private final List<DatabaseTableFieldData> myFields = new ArrayList<DatabaseTableFieldData>();
  private final List<DatabaseTableRelationData> myRelationships = new ArrayList<DatabaseTableRelationData>();

  public DatabaseTableData(String name, DataSource dataSource) {
    NAME = name;
    myDataSource = dataSource;
  }

  public DatabaseTableData(String name, String schema, String catalog, final String type, DataSource dataSource) {
    NAME = name;
    SCHEMA = schema;
    CATALOG = catalog;
    TYPE = type;
    myDataSource = dataSource;
  }

  public void readExternal(Element element) {
    XmlSerializer.deserializeInto(this, element);
    for (final Element fieldElement : (List<Element>)element.getChildren(FIELD_ELEMENT_NAME)) {
      DatabaseTableFieldData newFieldData = new DatabaseTableFieldData(this);
      newFieldData.readExternal(fieldElement);
      myFields.add(newFieldData);
    }
    for (final Element refElement : (List<Element>)element.getChildren(RELATIONSHIP_ELEMENT_NAME)) {
      DatabaseTableRelationData newRelationData = new DatabaseTableRelationData(this);
      newRelationData.readExternal(refElement);
      myRelationships.add(newRelationData);
    }
  }

  public void resolveReferences(final Collection<DatabaseTableData> tables) {
    for (DatabaseTableRelationData relationship : myRelationships) {
      relationship.resolveReferences(tables);
    }
  }

  public void writeExternal(Element element) {
    XmlSerializer.serializeInto(this, element);
    for (DatabaseTableFieldData databaseTableFieldData : myFields) {
      Element e = new Element(FIELD_ELEMENT_NAME);
      element.addContent(e);
      databaseTableFieldData.writeExternal(e);
    }
    for (DatabaseTableRelationData relationData : myRelationships) {
      Element e = new Element(RELATIONSHIP_ELEMENT_NAME);
      element.addContent(e);
      relationData.writeExternal(e);
    }
  }

  public String getName() {
    return NAME;
  }

  public String getSchema() {
    return SCHEMA;
  }

  public String getCatalog() {
    return CATALOG;
  }

  public String getType() {
    return TYPE;
  }

  @Transient
  public void setType(final String type) {
    TYPE = type;
  }

  public DataSource getDataSource() {
    return myDataSource;
  }

  @Transient
  void setDataSource(DataSource newDataSource) {
    myDataSource = newDataSource;
  }

  public List<DatabaseTableFieldData> getFields() {
    return myFields;
  }

  public List<DatabaseTableRelationData> getRelationships() {
    return myRelationships;
  }

  public List<DatabaseTableRelationData> getRelationships(final DatabaseTableFieldData fieldData) {
    return ContainerUtil.findAll(myRelationships, new Condition<DatabaseTableRelationData>() {
      public boolean value(final DatabaseTableRelationData object) {
        return object.getSourceField() == fieldData;
      }
    });
  }

  @Nullable
  public DatabaseTableFieldData findColumnByName(final String name) {
    return ContainerUtil.find(myFields, new Condition<DatabaseTableFieldData>() {
      public boolean value(final DatabaseTableFieldData object) {
        return Comparing.strEqual(name, object.getName(), false);
      }
    });
  }

  @NonNls
  public String toString() {
    return "Table::" + NAME;
  }

  public void refreshMetaData(DatabaseMetaData metaData, final String tableName, String schemaNamePattern) throws SQLException {
    SQLException exception = null;
    myFields.clear();
    myRelationships.clear();

    final List<String> primaryKeys = new ArrayList<String>();
    ResultSet primaryKeysRS = null;
    try {
      primaryKeysRS = metaData.getPrimaryKeys(getCatalog(), getSchema(), getName());
      while (primaryKeysRS.next()) {
        final String columnName = (String)primaryKeysRS.getObject(RS_COLUMN_NAME);
        primaryKeys.add(columnName.toLowerCase());
      }
    }
    catch(SQLException ex) {
      if (exception == null) exception = ex;
    }
    finally {
      if (primaryKeysRS != null) {
        primaryKeysRS.close();
      }
    }

    final ResultSet columnResultSet = metaData.getColumns(getCatalog(), getSchema(), getName(), null);
    while (columnResultSet.next()) {
      final String columnName = columnResultSet.getString(RS_COLUMN_NAME);
      final String sqlTypeName = columnResultSet.getString(RS_TYPE_NAME);
      final int jdbcType = columnResultSet.getInt(RS_DATA_TYPE);
      final int length = columnResultSet.getInt(RS_COLUMN_SIZE);
      final int precision = columnResultSet.getInt(RS_DECIMAL_DIGITS);
      final boolean nullable = columnResultSet.getInt(RS_NULLABLE) != DatabaseMetaData.attributeNoNulls;

      final boolean primary = primaryKeys.contains(columnName.toLowerCase());
      final DatabaseTableFieldData tableFieldData = new DatabaseTableFieldData(columnName, sqlTypeName, jdbcType, null, primary, length, precision, nullable, this);
      myFields.add(tableFieldData);
    }
    columnResultSet.close();

    ResultSet foreignKeysRS = null;
    try {
      foreignKeysRS = metaData.getImportedKeys(getCatalog(), getSchema(), getName());
      int counter = 0;
      while (foreignKeysRS.next()) {
        final short keySeq = foreignKeysRS.getShort(RS_KEY_SEQ);
        if (keySeq == 0) counter ++;
        final String fkName = foreignKeysRS.getString(RS_FK_NAME);
        final String foreignKeyName = fkName == null? Integer.toString(counter) : fkName;
        final String sourceColumn = foreignKeysRS.getString(RS_FK_COLUMN_NAME);
        final String targetColumn = foreignKeysRS.getString(RS_PK_COLUMN_NAME);
        final String targetSchema = foreignKeysRS.getString(RS_PK_TABLE_SCHEM);
        final String targetCatalog = foreignKeysRS.getString(RS_PK_TABLE_CAT);
        final String targetTableName = foreignKeysRS.getString(RS_PK_TABLE_NAME);
        final boolean cascadeUpdate = foreignKeysRS.getShort(RS_UPDATE_RULE) == DatabaseMetaData.importedKeyCascade;
        final boolean cascadeRemove = foreignKeysRS.getShort(RS_DELETE_RULE) == DatabaseMetaData.importedKeyCascade;

        final DatabaseTableRelationData relationData = new DatabaseTableRelationData(this, foreignKeyName, targetTableName, targetSchema, targetCatalog,
                                                                                     sourceColumn, targetColumn, cascadeRemove, cascadeUpdate);
        myRelationships.add(relationData);
      }
    }
    catch (SQLException ex) {
      if (exception == null) exception = ex;
    }
    finally {
      if (foreignKeysRS != null) foreignKeysRS.close();
    }

    //for (DatabaseTableFieldData tableFieldData : myFields) {
    //  @NonNls final List<String> privileges =
    //    SQLUtil.resultSetToList(metaData.getColumnPrivileges(null, schemaPattern, tableName, tableFieldData.getName()), RS_PRIVILEGE);
    //  tableFieldData.setInsertable(privileges.contains("INSERT"));
    //  tableFieldData.setUpdatable(privileges.contains("UPDATE"));
    //}

    if (exception != null) throw new SQLWrapperException(exception, false);
  }

  @Nonnull
  public TableType getTableType() {
    final TableType s = TableType.findByName(getType());
    return s == null ? TableType.TABLE : s;
  }

  @Nonnull
  public DatabaseColumnInfo[] getColumns() {
    return myFields.toArray(new DatabaseColumnInfo[myFields.size()]);
  }

  @Nonnull
  public DatabaseReferenceConstraintInfo[] getReferenceConstraints() {
    return myRelationships.toArray(new DatabaseReferenceConstraintInfo[myRelationships.size()]);
  }
}
