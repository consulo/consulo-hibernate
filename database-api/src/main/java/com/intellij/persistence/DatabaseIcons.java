package com.intellij.persistence;

import com.intellij.persistence.icon.icon.PersistenceIconGroup;
import consulo.ui.image.Image;

/**
 * @author Gregory.Shrago
 */
public interface DatabaseIcons {
  Image DATASOURCE_ICON = PersistenceIconGroup.datasource();
  Image DATASOURCE_DISABLED_ICON = Image.empty(16);
  Image DATASOURCE_TABLE_ICON = PersistenceIconGroup.datasourcetable();
  Image DATASOURCE_VIEW_ICON = PersistenceIconGroup.datasourceview();
  Image DATASOURCE_SEQUENCE_ICON = PersistenceIconGroup.datasourcesequence();
  Image DATASOURCE_COLUMN_ICON = PersistenceIconGroup.datasourcecolumn();
  Image DATASOURCE_FK_COLUMN_ICON = PersistenceIconGroup.datasourcefkcolumn();
  Image DATASOURCE_PK_COLUMN_ICON = PersistenceIconGroup.datasourcepkcolumn();

  Image OVR_BINARY = Image.empty(16);
  Image OVR_DATE_TIME = Image.empty(16);
  Image OVR_NUMERIC = Image.empty(16);
  Image OVR_STRING = Image.empty(16);

  Image OVR_NULLABLE = Image.empty(16);
  Image OVR_NOTNULL = Image.empty(16);

  Image CONSOLE_ICON = PersistenceIconGroup.console();
  Image CONSOLE_OUTPUT_ICON = Image.empty(16);
  Image SQL_ICON = PersistenceIconGroup.sql();

  Image IMPORT_DATASOURCES = Image.empty(16);

  Image PROPERTIES_ICON = Image.empty(16);
  Image SYNCHRONIZE_ICON = Image.empty(16);
  Image SELECT_ALL_ICON = Image.empty(16);
  Image UNSELECT_ALL_ICON = Image.empty(16);
}
