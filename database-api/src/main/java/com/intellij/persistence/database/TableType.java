package com.intellij.persistence.database;

import jakarta.annotation.Nullable;

import com.intellij.persistence.DatabaseMessages;

/**
 * @author Gregory.Shrago
 */
public enum TableType {

  TABLE(DatabaseMessages.message("table.type.table")),
  VIEW(DatabaseMessages.message("table.type.view")),
  SEQUENCE(DatabaseMessages.message("table.type.sequence")),
  SYSTEM(DatabaseMessages.message("table.type.system")),
  LOCAL_TEMPORARY(DatabaseMessages.message("table.type.local.temporary")),
  GLOBAL_TEMPORARY(DatabaseMessages.message("table.type.global.temporary")),
  ALIAS(DatabaseMessages.message("table.type.alias"));

  final String myTitle;

  private TableType(final String title) {
    myTitle = title;
  }

  public String getTitle() {
    return myTitle;
  }

  @Nullable
  public static TableType findByName(final String name) {
    for (TableType type : values()) {
      if (type.name().equals(name)) return type;
    }
    return null;
  }
}
