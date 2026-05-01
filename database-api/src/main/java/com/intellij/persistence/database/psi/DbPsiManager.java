package com.intellij.persistence.database.psi;

import consulo.ui.ex.action.Presentation;
import jakarta.annotation.Nullable;

import java.util.List;

/**
 * @author Gregory.Shrago
 */
public interface DbPsiManager {
  List<DbDataSourceElement> getDataSources();

  void removeDataSource(final DbDataSourceElement element);

  void editDataSource(final DbDataSourceElement element);

  @Nullable
  DbDataSourceElement addDataSource(@Nullable final DbDataSourceElement template);

  void tuneCreateDataSourceAction(final Presentation presentation);
}
