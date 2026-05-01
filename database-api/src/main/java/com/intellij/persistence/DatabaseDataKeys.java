package com.intellij.persistence;

import com.intellij.persistence.database.psi.DbDataSourceElement;
import consulo.util.dataholder.Key;
import consulo.ui.ex.awt.dnd.DnDTarget;

/**
 * @author Gregory.Shrago
 */
public final class DatabaseDataKeys {
  public static final Key<DbDataSourceElement> DATA_SOURCE_KEY = Key.create("DATA_SOURCE_KEY");
  public static final Key<DnDTarget> DND_TARGET_KEY = Key.create("DND_TARGET_KEY");

  private DatabaseDataKeys() {
  }
}
