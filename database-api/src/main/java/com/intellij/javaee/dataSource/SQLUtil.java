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

import consulo.util.lang.StringUtil;
import com.intellij.persistence.database.DatabaseColumnInfo;
import org.jetbrains.annotations.NonNls;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: nick
 * Date: 09.01.2003
 * Time: 16:17:35
 * To change this template use Options | File Templates.
 */
public class SQLUtil {


  private SQLUtil() {
  }

  /**
   * Has a side effect - strings are converted into lower case.
   * @param rs
   * @param columnName
   * @return
   * @throws SQLException
   */
  public static <T> List<T> resultSetToList(ResultSet rs, String columnName) throws SQLException {
    List<T> retVal = new ArrayList<T>();
    while (rs.next()) {
      Object o = rs.getObject(columnName);
      if (o instanceof String) {
        o = ((String) o).toLowerCase();
      }
      retVal.add((T)o);
    }
    return retVal;
  }

  @Nullable
  public static String getResultSetStringSafe(final ResultSet resultSet, final String columnName) throws SQLException {
    try {
      return resultSet.getString(columnName);
    }
    catch (SQLException e) {
      // do nothing
    }
    return null;
  }

  /**
   *
   * @param jdbcType
   * @return
   */
  public static boolean hasScaleAndPrecision(int jdbcType) {
    switch(jdbcType) {
      case Types.DECIMAL:
      case Types.NUMERIC:
      case Types.FLOAT:
      case Types.REAL:
      case Types.DOUBLE:
        return true;
      default: return false;
    }
  }

  public static boolean hasLength(int jdbcType) {
    switch(jdbcType) {
      case Types.CHAR:
      case Types.VARCHAR:
      case Types.LONGVARCHAR:
        return true;
      default:return false;
    }
  }

  public static String getJdbcTypeName(final DatabaseColumnInfo tableFieldData) {
    final String sqlType = tableFieldData.getSqlType();
    if (StringUtil.isNotEmpty(sqlType) && sqlType.indexOf('(') > -1) return sqlType;
    final int jdbcType = tableFieldData.getJdbcType();
    final String typeName = StringUtil.isNotEmpty(sqlType)? sqlType : getJdbcTypeName(jdbcType);
    if (hasLength(jdbcType) && tableFieldData.getLength()>0) {
      return typeName+"("+tableFieldData.getLength()+")";
    }
    else if (hasScaleAndPrecision(jdbcType) && tableFieldData.getLength() > 0 && tableFieldData.getPrecision() >= 0) {
      return typeName + "(" + tableFieldData.getLength() + ", " + tableFieldData.getPrecision() +")";
    }
    return typeName;
  }

  /**
   * Returns JDBC type name string, i.e. "LONGVARCHAR" for Types.LONGVARCHAR.
   * @param jdbcType one of java.sql.Types.* constants
   * @return type name
   */
  @Nonnull
  public static String getJdbcTypeName(final int jdbcType) {
    @NonNls final String result;
    switch (jdbcType) {
      case Types.BIT:
        result = "BIT";
        break;
      case Types.TINYINT:
        result = "TINYINT";
        break;
      case Types.SMALLINT:
        result = "SMALLINT";
        break;
      case Types.INTEGER:
        result = "INTEGER";
        break;
      case Types.BIGINT:
        result = "BIGINT";
        break;
      case Types.FLOAT:
        result = "FLOAT";
        break;
      case Types.REAL:
        result = "REAL";
        break;
      case Types.DOUBLE:
        result = "DOUBLE";
        break;
      case Types.NUMERIC:
        result = "NUMERIC";
        break;
      case Types.DECIMAL:
        result = "DECIMAL";
        break;
      case Types.CHAR:
        result = "CHAR";
        break;
      case Types.VARCHAR:
        result = "VARCHAR";
        break;
      case Types.LONGVARCHAR:
        result = "LONGVARCHAR";
        break;
      case Types.DATE:
        result = "DATE";
        break;
      case Types.TIME:
        result = "TIME";
        break;
      case Types.TIMESTAMP:
        result = "TIMESTAMP";
        break;
      case Types.BINARY:
        result = "BINARY";
        break;
      case Types.VARBINARY:
        result = "VARBINARY";
        break;
      case Types.LONGVARBINARY:
        result = "LONGVARBINARY";
        break;
      case Types.NULL:
        result = "NULL";
        break;
      case Types.OTHER:
        result = "OTHER";
        break;
      case Types.JAVA_OBJECT:
        result = "JAVA_OBJECT";
        break;
      case Types.DISTINCT:
        result = "DISTINCT";
        break;
      case Types.STRUCT:
        result = "STRUCT";
        break;
      case Types.ARRAY:
        result = "ARRAY";
        break;
      case Types.BLOB:
        result = "BLOB";
        break;
      case Types.CLOB:
        result = "CLOB";
        break;
      case Types.REF:
        result = "REF";
        break;
      case Types.DATALINK:
        result = "DATALINK";
        break;
      case Types.BOOLEAN:
        result = "BOOLEAN";
        break;
      default:
        result = "UNKNOWN";
    }
    return result;
  }

  public static int guessJdbcTypeByName(final String name) {
    if (StringUtil.isEmpty(name)) return 0;
    @NonNls final String fixed = name.toUpperCase();
    if (fixed.contains("BINARY")) return Types.VARBINARY;
    else if (fixed.contains("BOOL")) return Types.BOOLEAN;
    else if (fixed.contains("DATE")) return Types.DATE;
    else if (fixed.contains("TIMESTAMP")) return Types.TIMESTAMP;
    else if (fixed.contains("TIME")) return Types.TIME;
    else if (fixed.contains("REAL")) return Types.REAL;
    else if (fixed.contains("FLOAT")) return Types.FLOAT;
    else if (fixed.contains("DOUBLE")) return Types.DOUBLE;
    else if (fixed.contains("INTERVAL")) return Types.DATE;
    else if (fixed.equals("CHAR") && !fixed.contains("VAR")) return Types.CHAR;
    else if (fixed.contains("INT")) return Types.INTEGER;
    else if (fixed.contains("DECIMAL")) return Types.DECIMAL;
    else if (fixed.contains("NUMERIC")) return Types.NUMERIC;
    else if (fixed.contains("CHAR") || fixed.contains("TEXT")) return Types.VARCHAR;
    else if (fixed.contains("BLOB")) return Types.LONGVARBINARY;
    else if (fixed.contains("CLOB")) return Types.LONGVARCHAR;
    else if (fixed.contains("REFERENCE")) return Types.REF;
    return 0;
  }
}
