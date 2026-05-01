package com.intellij.persistence.database;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

/**
 * @author Gregory.Shrago
 */
public interface DataSourceInfo {

  String getName();

  String getUniqueId();

  @Nullable
  String getDatabaseProductName();

  @Nullable
  String getDatabaseProductVersion();

  @Nonnull
  DatabaseTableLongInfo[] getMyTables();


}
