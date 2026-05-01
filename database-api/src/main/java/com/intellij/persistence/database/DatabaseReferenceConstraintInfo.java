package com.intellij.persistence.database;

import jakarta.annotation.Nullable;

/**
 * @author Gregory.Shrago
 */
public interface DatabaseReferenceConstraintInfo {
  DatabaseReferenceConstraintInfo[] EMPTY_ARRAY = new DatabaseReferenceConstraintInfo[0];

  String getName();

  DatabaseColumnInfo getSourceColumn();

  @Nullable
  DatabaseColumnInfo getTargetColumn();

  DatabaseTableLongInfo getSourceTable();

  @Nullable
  DatabaseTableLongInfo getTargetTable();
}
