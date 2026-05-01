package com.intellij.persistence.database.psi;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import com.intellij.persistence.database.PsiDatabaseTableLongInfo;
import com.intellij.persistence.database.TableType;

/**
 * @author Gregory.Shrago
 */
public interface DbTableElement extends DbElement, PsiDatabaseTableLongInfo {
  DbTableElement[] EMPTY_ARRAY = new DbTableElement[0];

  DbSchemaElement getDbParent();

  @Nonnull
  TableType getTableType();

  @Nonnull
  DbColumnElement[] getColumns();

  @Nullable
  DbColumnElement findColumn(final String name);
}
