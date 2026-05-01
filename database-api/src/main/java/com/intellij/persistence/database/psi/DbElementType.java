package com.intellij.persistence.database.psi;

import jakarta.annotation.Nullable;

import com.intellij.persistence.DatabaseMessages;
import org.jetbrains.annotations.Nls;

/**
 * @author Gregory.Shrago
 */
public class DbElementType {

  public static final DbElementType CATALOG = new DbElementType(DatabaseMessages.message("type.name.catalog"), null);
  public static final DbElementType SCHEMA = new DbElementType(DatabaseMessages.message("type.name.schema"), CATALOG);
  public static final DbElementType TABLE = new DbElementType(DatabaseMessages.message("type.name.table"), SCHEMA);
  public static final DbElementType PROCEDURE = new DbElementType(DatabaseMessages.message("type.name.procedure"), SCHEMA);
  public static final DbElementType COLUMN = new DbElementType(DatabaseMessages.message("type.name.column"), TABLE);
  public static final DbElementType INDEX = new DbElementType(DatabaseMessages.message("type.name.index"), TABLE);
  public static final DbElementType TRIGGER = new DbElementType(DatabaseMessages.message("type.name.trigger"), TABLE);
  public static final DbElementType CONSTRAINT = new DbElementType(DatabaseMessages.message("type.name.constraint"), TABLE);


  private final String myName;
  private final DbElementType myParentType;

  protected DbElementType(@Nls final String name, final DbElementType parentType) {
    myName = name;
    myParentType = parentType;
  }

  public boolean isSynthetic() {
    return false;
  }

  public String getName() {
    return myName;
  }

  @Nullable
  public DbElementType getParentType() {
    return myParentType;
  }

  public boolean accepts(final DbElementType type) {
    return this == type;
  }

  @Override
  public String toString() {
    return myName;
  }

}
