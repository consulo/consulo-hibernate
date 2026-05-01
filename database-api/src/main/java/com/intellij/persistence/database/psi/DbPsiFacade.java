package com.intellij.persistence.database.psi;

import com.intellij.persistence.DatabaseMessages;
import com.intellij.persistence.database.DataSourceInfo;
import consulo.annotation.component.ComponentScope;
import consulo.annotation.component.ServiceAPI;
import consulo.component.util.ModificationTracker;
import consulo.disposer.Disposable;
import consulo.project.Project;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

/**
 * @author Gregory.Shrago
 */
@ServiceAPI(ComponentScope.PROJECT)
public abstract class DbPsiFacade implements ModificationTracker {
  public static final String DATABASE_TOOLWINDOW_ID = DatabaseMessages.message("title.toolwindow.database");

  public static DbPsiFacade getInstance(Project project) {
    return project.getInstance(DbPsiFacade.class);
  }

  public abstract Project getProject();

  public abstract DbPsiManager[] getDbManagers();

  @Nonnull
  public abstract DbProjectElement getProjectElement();

  @Nonnull
  public abstract DbDataSourceElement[] getDataSources();
  
  @Nullable
  public abstract DbDataSourceElement findDataSource(final String id);

  public abstract void addModificationTrackerListener(final ModificationTrackerListener<DbPsiFacade> listener, final Disposable parent);

  public abstract void removeModificationTrackerListener(final ModificationTrackerListener<DbPsiFacade> listener);

  public abstract DbDataSourceElement createDataSourceWrapperElement(final DataSourceInfo info, final DbPsiManager manager);

  public abstract void clearCaches();
}
