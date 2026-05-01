package com.intellij.persistence.util;

import com.intellij.persistence.database.DatabaseColumnInfo;
import com.intellij.java.language.psi.CommonClassNames;
import com.intellij.java.language.psi.util.PsiTypesUtil;
import org.jetbrains.annotations.NonNls;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.sql.Types;

/**
 * @author Gregory.Shrago
 */
public class JdbcTypesUtil {
  @NonNls public static final String CHAR_ARR = "char[]";
  @NonNls public static final String BYTE_ARR = "byte[]";
  public static final String BOOLEAN = "java.lang.Boolean";
  public static final String BYTE = "java.lang.Byte";
  public static final String SHORT = "java.lang.Short";
  public static final String INTEGER = "java.lang.Integer";
  public static final String LONG = "java.lang.Long";
  public static final String BIGINT = "java.math.BigInteger";
  public static final String BIGDECIMAL = "java.math.BigDecimal";
  public static final String FLOAT = "java.lang.Float";
  public static final String DOUBLE = "java.lang.Double";
  public static final String STRING = "java.lang.String";
  public static final String BLOB = "java.sql.Blob";
  public static final String SQL_DATE = "java.sql.Date";
  public static final String UTIL_DATE = "java.util.Date";
  public static final String SQL_TIME = "java.sql.Time";
  public static final String SQL_TIMESTAMP = "java.sql.Timestamp";
  public static final String CLOB = "java.sql.Clob";
  public static final String SQL_ARRAY = "java.sql.Array";
  public static final String SQL_REF = "java.sql.Ref";
  public static final String OBJECT = "java.lang.Object";
  public static final String SQL_STRUCT = "java.sql.Struct";
  public static final String SERIALIZABLE = "java.io.Serializable";
  public static final String MAP = "java.util.Map";

  private JdbcTypesUtil() {
  }

  /**
   * Returns possible java types for a column of the given JDBC type.
   * @param jdbcType column JDBC type
   * @param preferUnboxed
   * @param length
   * @param precision @return possible java type names
   * @return java type names as string array
   */
  @Nonnull
  public static String[] getJavaTypeVariants(final int jdbcType, final boolean preferUnboxed, final int length, final int precision) {
    final String[] result;
    switch (jdbcType) {
      case Types.BOOLEAN:
      case Types.BIT:
        result = new String[] {BOOLEAN, BYTE, SHORT, INTEGER, LONG, BIGINT, //or
          FLOAT, DOUBLE, BIGDECIMAL, STRING};
        break;
      case Types.TINYINT:
        result = new String[]{BYTE, SHORT, INTEGER, LONG, BIGINT,  //or
          BOOLEAN, FLOAT, DOUBLE, BIGDECIMAL, STRING};
        break;
      case Types.SMALLINT:
        result = new String[]{SHORT, INTEGER, LONG, BIGINT,  //or
          BOOLEAN, BYTE, FLOAT, DOUBLE, BIGDECIMAL, STRING};
        break;
      case Types.INTEGER:
        result = new String[]{INTEGER, LONG, BIGINT,  //or
          BOOLEAN, BYTE, SHORT, FLOAT, DOUBLE, BIGDECIMAL, STRING};
        break;
      case Types.BIGINT:
        result = new String[]{LONG, BIGINT,  //or
          BOOLEAN, BYTE, SHORT, INTEGER, FLOAT, DOUBLE, BIGDECIMAL, STRING};
        break;
      case Types.REAL:
        result = new String[]{FLOAT, DOUBLE, BIGDECIMAL,  //or
          BOOLEAN, BYTE, SHORT, INTEGER, LONG, BIGINT, STRING};
        break;
      case Types.FLOAT:
      case Types.DOUBLE:
        result = new String[]{DOUBLE, FLOAT, BIGDECIMAL, //or
          BOOLEAN, BYTE, SHORT, INTEGER, LONG, BIGINT, STRING};
        break;
      case Types.NUMERIC:
      case Types.DECIMAL:
        final int bytesRequired = length > 0 ? (int)(length / Math.log(2) / 8 + 0.5) : 0;
        if (bytesRequired > 0) {
          if (precision == 0) {
            if (bytesRequired <= 4) {
              result = new String[]{INTEGER,  //or
                      BOOLEAN, BYTE, SHORT, LONG, BIGINT, FLOAT, DOUBLE, BIGDECIMAL, STRING};
            }
            else if (bytesRequired <= 8) {
              result = new String[]{LONG,  //or
                      BOOLEAN, BYTE, SHORT, INTEGER, BIGINT, FLOAT, DOUBLE, BIGDECIMAL, STRING};
            }
            else {
              result = new String[]{BIGINT,  //or
                      BOOLEAN, BYTE, SHORT, INTEGER, LONG, FLOAT, DOUBLE, BIGDECIMAL, STRING};
            }
          }
          else {
            result = new String[]{BIGDECIMAL,  //or
                    BOOLEAN, BYTE, SHORT, INTEGER, LONG, BIGINT, FLOAT, DOUBLE, STRING};
          }
        }
        else if (precision == 0) {
          result = new String[]{BIGINT,  //or
                  BOOLEAN, BYTE, SHORT, INTEGER, LONG, FLOAT, DOUBLE, BIGDECIMAL, STRING};
        }
        else {
          result = new String[]{BIGDECIMAL,  //or
                  BOOLEAN, BYTE, SHORT, INTEGER, LONG, BIGINT, FLOAT, DOUBLE, STRING};
        }
        break;
      case Types.CHAR:
      case Types.VARCHAR:
      case Types.LONGVARCHAR:
        if (length == 1) {
          result = new String[]{STRING, BOOLEAN, BYTE, SHORT, INTEGER,
                  CHAR_ARR, BYTE_ARR};
        }
        else {
          result = new String[]{STRING, CHAR_ARR, BYTE_ARR, // or
                  SQL_DATE, SQL_TIME, SQL_TIMESTAMP, UTIL_DATE};
        }
        break;
      case Types.DATE:
        result = new String[] {SQL_DATE, UTIL_DATE, STRING};
        break;
      case Types.TIME:
        result = new String[]{SQL_TIME, STRING};
        break;
      case Types.TIMESTAMP:
        result = new String[]{SQL_TIMESTAMP, STRING};
        break;
      case Types.BINARY:
      case Types.VARBINARY:
      case Types.LONGVARBINARY:
        result = new String[]{BYTE_ARR, STRING};
        break;
      case Types.DATALINK:
      case Types.NULL:
      case Types.OTHER:
      case Types.DISTINCT:
      case Types.JAVA_OBJECT:
        result = new String[]{OBJECT, SERIALIZABLE};
        break;
      case Types.STRUCT:
        result = new String[]{SQL_STRUCT, MAP, OBJECT};
        break;
      case Types.ARRAY:
        result = new String[] {SQL_ARRAY, OBJECT};
        break;
      case Types.BLOB:
        result = new String[] {BLOB, BYTE_ARR};
        break;
      case Types.CLOB:
        result = new String[]{CLOB, CHAR_ARR};
        break;
      case Types.REF:
        result = new String[] {SQL_REF, OBJECT, SERIALIZABLE};
        break;
      default:
        result = new String[]{OBJECT, SERIALIZABLE};
    }
    return preferUnboxed? unboxJavaTypes(result) : result;
  }

  /**
   * Unboxes java types if possible in the given array
   * @param types array of java types
   * @return types
   */
  public static String[] unboxJavaTypes(final String[] types) {
    for (int i = 0; i < types.length; i++) {
      types[i] = PsiTypesUtil.unboxIfPossible(types[i]);
    }
    return types;
  }

  /**
   * Returns the unboxed type name or parameter.
   * @param type boxed java type name
   * @return unboxed type name if available; same value otherwise
   * * @deprecated use {@link com.intellij.psi.util.PsiTypesUtil#unboxIfPossible(String)}
   */
  @Nullable
  public static String unboxIfPossible(final String type) {
    return PsiTypesUtil.unboxIfPossible(type);
  }

  /**
   * Returns the boxed type name or parameter.
   * @param type primitive java type name
   * @return boxed type name if available; same value otherwise
   * @deprecated use {@link com.intellij.psi.util.PsiTypesUtil#boxIfPossible(String)}
   */
  @Nullable
  public static String boxIfPossible(final String type) {
    return PsiTypesUtil.boxIfPossible(type);
  }

  @Nullable
  public static String getJavaType(final DatabaseColumnInfo fieldData, final boolean preferPrimitiveTypes) {
    final int jdbcType = fieldData.getJdbcType();
    final String[] javaType = getJavaTypeVariants(jdbcType, preferPrimitiveTypes, fieldData.getLength(), fieldData.getPrecision());
    return javaType.length > 0? javaType[0] : CommonClassNames.JAVA_LANG_OBJECT;
  }
}
