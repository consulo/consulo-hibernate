/*
 * Copyright 2000-2007 JetBrains s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.intellij.javaee.dataSource;

import com.intellij.persistence.DatabaseMessages;
import com.intellij.persistence.database.DataSourceInfo;
import com.intellij.persistence.database.DbUtil;
import consulo.application.progress.EmptyProgressIndicator;
import consulo.application.progress.ProgressIndicator;
import consulo.application.progress.ProgressManager;
import consulo.component.ProcessCanceledException;
import consulo.project.Project;
import consulo.util.collection.ArrayUtil;
import consulo.util.collection.ContainerUtil;
import consulo.util.lang.Comparing;
import consulo.util.lang.ExceptionUtil;
import consulo.util.lang.Pair;
import consulo.util.lang.StringUtil;
import consulo.util.lang.ref.Ref;
import consulo.util.xml.serializer.XmlSerializer;
import consulo.util.xml.serializer.annotation.Transient;
import gnu.trove.THashSet;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import org.jdom.Element;
import org.jetbrains.annotations.NonNls;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import static com.intellij.javaee.dataSource.SQLUtil.getResultSetStringSafe;

/**
 * @author cdr
 */

public abstract class DataSource implements DataSourceInfo {
  @NonNls private static final String RS_TABLE_NAME = "TABLE_NAME";
  @NonNls private static final String RS_TABLE_SCHEMA = "TABLE_SCHEM";
  @NonNls private static final String RS_TABLE_CATALOG = "TABLE_CAT";
  @NonNls private static final String RS_TABLE_TYPE = "TABLE_TYPE";
  @NonNls private static final String TABLE_ELEMENT = "TABLE_DATA";

  public String UUID;
  public String NAME;
  public String DATABASE_PRODUCT_NAME;
  public String DATABASE_PRODUCT_VERSION;

  private boolean myGlobal;
  protected volatile Pair<String[], DatabaseTableData[]> myState = Pair.create(ArrayUtil.EMPTY_STRING_ARRAY, new DatabaseTableData[0]);

  protected DataSource() {
    UUID = java.util.UUID.randomUUID().toString();
  }

  public boolean isGlobal() {
    return myGlobal;
  }

  @Transient
  public void setGlobal(final boolean global) {
    myGlobal = global;
  }

  public String getUniqueId() {
    return UUID;
  }

  public void readExternal(@Nullable final Project project, final Element element) {
    final ArrayList<DatabaseTableData> tables = new ArrayList<DatabaseTableData>();
    final TreeSet<String> schemas = new TreeSet<String>();
    XmlSerializer.deserializeInto(this, element);
    if (StringUtil.isEmpty(UUID)) {
      UUID = java.util.UUID.randomUUID().toString();
    }
    for (final Element tableElement : (List<Element>)element.getChildren(TABLE_ELEMENT)) {
      DatabaseTableData databaseTableData = new DatabaseTableData(null, this);
      databaseTableData.readExternal(tableElement);
      tables.add(databaseTableData);
    }
    calculateSchemas(tables, schemas);
    for (DatabaseTableData table : tables) {
      table.resolveReferences(tables);
    }
    myState = Pair.create(ArrayUtil.toStringArray(schemas), tables.toArray(new DatabaseTableData[tables.size()]));
  }

  public void writeExternal(Element element) {
    XmlSerializer.serializeInto(this, element);
    for (DatabaseTableData databaseTableData : myState.second) {
      Element tableElement = new Element(TABLE_ELEMENT);
      element.addContent(tableElement);
      databaseTableData.writeExternal(tableElement);
    }
  }

  public String getName() {
    return NAME;
  }

  public String getDatabaseProductName() {
    return DATABASE_PRODUCT_NAME;
  }

  public String getDatabaseProductVersion() {
    return DATABASE_PRODUCT_VERSION;
  }

  @Nullable
  protected abstract Connection getConnection(ServerInstance serverInstance) throws Exception;
  @Nullable
  protected abstract String getSchemaName();

  @Nullable
  protected String getTablePattern() {
    return null;
  }

  public abstract void init();

  public List<DatabaseTableData> getTables() {
    return Arrays.asList(myState.second);
  }

  @Nonnull
  public DatabaseTableData[] getMyTables() {
    return myState.second;
  }

  public List<String> getSchemas() {
    return Arrays.asList(myState.first);
  }

  @Deprecated
  @Nullable
  public DatabaseTableData findTableByName(final String name) {
    final List<DatabaseTableData> tables = getTables();
    for (DatabaseTableData tableData : tables) {
      if (Comparing.strEqual(name, tableData.getName(), false)) return tableData;
    }
    return null;
  }

  @Nullable
  public DatabaseTableData findTableByName(final String name, final String schema, final String catalog) {
    return DbUtil.findTable(name, schema, catalog, getTables());
  }

  @Nullable
  public <T> T performJdbcOperation(final ServerInstance serverInstance,
                                        final Ref<String> errorMessage,
                                        final JdbcOperation<T> operation) {
    final ProgressManager progressManager = ProgressManager.getInstance();

    ProgressIndicator indicator = progressManager.getProgressIndicator();
    if (indicator == null) indicator = new EmptyProgressIndicator();
    Connection connection = null;
    try {
      indicator.setIndeterminate(true);
      indicator.setText(DatabaseMessages.message("message.datasource.progress.connecting.to.database"));
      connection = getConnection(serverInstance);
      try {
        if (connection != null) {
          return operation.perform(connection, indicator);
        }
        else if (errorMessage.get() == null) {
          errorMessage.set(DatabaseMessages.message("wrong.driver.specified"));
        }
      }
      catch (ProcessCanceledException e) {
        throw e;
      }
      catch (Exception e) {
        errorMessage.set(DatabaseMessages.message("message.text.jdbc.operation.failed", throwableToString(e)));
      }
      catch (Throwable e) {
        errorMessage.set(throwableToString(e));
      }
    }
    catch (SQLException e) {
      errorMessage.set(DatabaseMessages.message("message.text.cant.establish.connection.because.of.error", throwableToString(e)));
    }
    catch (ClassNotFoundException e) {
      errorMessage.set(DatabaseMessages.message("message.driver.class.not.found.text", e.getMessage()));
    }
    catch (ProcessCanceledException e) {
      throw e;
    }
    catch (UnsatisfiedLinkError e) {
      // IDEADEV-6598
      errorMessage.set(DatabaseMessages.message("message.text.native.library.cannot.be.loaded", throwableToString(e)));
    }
    catch (Throwable e) {
      errorMessage.set(throwableToString(e));
    }
    finally {
      try {
        releaseConnection(connection);
      }
      catch (Throwable e) {
        if (errorMessage.get() == null) {
          errorMessage.set(throwableToString(e));
        }
      }
    }
    return null;
  }

  protected void releaseConnection(@Nullable final Connection connection) {
    try {
      if (connection != null) {
        connection.close();
      }
    }
    catch (SQLException e) {
      // ignore
    }
  }

  @Nullable
  public String refreshMetaData(final ServerInstance serverInstance, final boolean loadTables, final boolean loadTableDetails) {
    final Ref<String> errorMessage = new Ref<String>();
    final String result = performJdbcOperation(serverInstance, errorMessage,
                                    new JdbcOperation<String>() {
      public String perform(@Nonnull Connection connection, @Nonnull ProgressIndicator indicator) throws Exception {
        final DatabaseMetaData metaData = connection.getMetaData();
        DATABASE_PRODUCT_NAME = metaData.getDatabaseProductName();
        DATABASE_PRODUCT_VERSION = metaData.getDatabaseProductVersion();
        if (loadTables) {
          final StringBuilder errors = new StringBuilder();
          final TreeSet<DatabaseTableData> tables = new TreeSet<DatabaseTableData>(new Comparator<DatabaseTableData>() {
            public int compare(final DatabaseTableData o1, final DatabaseTableData o2) {
              int result;
              if (0 != (result = Comparing.compare(o1.getCatalog(), o2.getCatalog()))) return result;
              if (0 != (result = Comparing.compare(o1.getSchema(), o2.getSchema()))) return result;
              return Comparing.compare(o1.getName(), o2.getName());
            }
          });
          final TreeSet<String> schemas = new TreeSet<String>();

          indicator.setText(DatabaseMessages.message("message.datasource.progress.loading.table.list"));

          boolean tablesFound = false;
          final Pattern pattern;
          if (StringUtil.isEmpty(getTablePattern())) {
            pattern = null;
          }
          else {
            try {
              pattern = Pattern.compile(getTablePattern());
            }
            catch (PatternSyntaxException e) {
              return e.getLocalizedMessage();
            }
          }
          final String schemaPattern = getSchemaName();
          final String connectionCatalog = connection.getCatalog();
          final boolean connectionCatalogSet = StringUtil.isNotEmpty(connectionCatalog);
          final ResultSet catalogsRs = connectionCatalogSet? null : metaData.getCatalogs();
          final List<String> catalogs = connectionCatalogSet? Arrays.asList(connectionCatalog): SQLUtil.<String>resultSetToList(catalogsRs, RS_TABLE_CATALOG);
          if (catalogsRs != null) catalogsRs.close();
          if (catalogs.isEmpty()) catalogs.add(null);
          final String[] schemaPatterns = StringUtil.isEmptyOrSpaces(schemaPattern)? new String[] { null } : schemaPattern.split("[\\s,]+");
          @NonNls final THashSet<String> visitedSchemas = new THashSet<String>();
          for (String catalogSchema : schemaPatterns) {
            final String curCatalog;
            final String curSchema ;
            final int idx;
            if (StringUtil.isEmptyOrSpaces(catalogSchema)) {
              curCatalog = connectionCatalogSet ? connectionCatalog : null;
              curSchema = null;
            }
            else if ((idx = catalogSchema.indexOf('.')) > -1) {
              curCatalog = catalogSchema.substring(0, idx);
              curSchema = catalogSchema.substring(idx + 1);
            }
            else {
              curCatalog = connectionCatalogSet ? connectionCatalog : catalogSchema;
              curSchema = connectionCatalogSet ? catalogSchema : null;
            }
             if (!visitedSchemas.add((curCatalog != null ? curCatalog : "*") +"." +(curSchema != null ? curSchema : "*"))) continue;
            for (String catalog : catalogs) {
              if (curCatalog != null && !"*".equals(curCatalog) && !Comparing.strEqual(catalog, curCatalog, false)) continue;
              final String schema = "*".equals(curSchema)? null : curSchema;
              tablesFound = loadTables(DataSource.this, tables,  metaData, catalog, schema, pattern, indicator) || tablesFound;
            }
          }
          calculateSchemas(tables, schemas);
          if (loadTableDetails) {
            int errorCounter = 0;
            boolean errorsTruncated = false;
            int totalCount = 2 * tables.size();
            int count = 0;
            indicator.setIndeterminate(false);
            indicator.setText(DatabaseMessages.message("message.datasource.progress.loading.table.structure"));
            for (DatabaseTableData tableData : tables) {
              indicator.checkCanceled();
              count++;
              indicator.setText2(DatabaseMessages.message("message.datasource.progress.current.table.0", tableData.getName()));
              indicator.setFraction(count / (double)totalCount);
              try {
                refreshTableMetaData(tableData, metaData, tableData.getName(), schemaPattern);
              }
              catch (Exception e) {
                final boolean wrapper = e instanceof SQLWrapperException;
                if (!wrapper || ((SQLWrapperException)e).isFatal()) {
                  errorCounter ++;
                }
                if (errors.length() < 10240) {
                  errors.append(DatabaseMessages.message("message.text.error.refreshing.table.item",
                                                   tableData.getName(), throwableToString(wrapper ? e.getCause(): e)));
                }
                else if (!errorsTruncated) {
                  errorsTruncated = true;
                  errors.append("\n\n...");
                }
              }
            }
            if (errors.length() > 0) {
              final int total = tables.size();
              errors.insert(0, DatabaseMessages.message("message.text.error.refreshing.table.header", String.valueOf(total - errorCounter), String.valueOf(total)));
            }
            indicator.setText(DatabaseMessages.message("message.datasource.progress.resolving.table.relationships"));
            for (DatabaseTableData tableData : tables) {
              indicator.checkCanceled();
              count++;
              indicator.setText2(DatabaseMessages.message("message.datasource.progress.current.table.0", tableData.getName()));
              indicator.setFraction(count / (double)totalCount);
              tableData.resolveReferences(tables);
            }
          }
          indicator.startNonCancelableSection();
          myState = Pair.create(ArrayUtil.toStringArray(schemas), tables.toArray(new DatabaseTableData[tables.size()]));
          if (errors.length() > 0) {
            return errors.toString();
          }
          if (!tablesFound) {
            return DatabaseMessages.message("message.text.no.tables.were");
          }
        }
        return null;
      }
    });
    return errorMessage.isNull()? result : errorMessage.get();
  }

  private boolean loadTables(final DataSource dataSource, final Collection<DatabaseTableData> tables, final DatabaseMetaData metaData,
                                    final String catalog, final String schema, final Pattern tablePattern, final ProgressIndicator indicator) throws SQLException {
    boolean tablesFound = false;
    final ResultSet rs = metaData.getTables(catalog, schema, "%", DatabaseTableData.TABLE_TYPES);
    while (rs.next()) {
      indicator.checkCanceled();
      final String tableName = getResultSetStringSafe(rs, RS_TABLE_NAME);
      final String tableSchema = getResultSetStringSafe(rs, RS_TABLE_SCHEMA);
      final String tableCatalog = getResultSetStringSafe(rs, RS_TABLE_CATALOG);
      final String tableType = getResultSetStringSafe(rs, RS_TABLE_TYPE);
      if (StringUtil.isEmpty(tableName)) continue;
      if (tablePattern != null && !tablePattern.matcher(tableName).matches()) continue;
      final DatabaseTableData table = new DatabaseTableData(tableName, tableSchema, tableCatalog, tableType, dataSource);
      if (shouldIncludeTable(table)) {
        tables.add(table);
        tablesFound = true;
      }
    }
    rs.close();
    return tablesFound;
  }

  protected boolean shouldIncludeTable(final DatabaseTableData table) {
    return true;
  }

  private static void calculateSchemas(final Collection<DatabaseTableData> tables, final Set<String> schemas) {
    for (DatabaseTableData table : tables) {
      ContainerUtil.addIfNotNull(schemas, table.getSchema());
    }
  }

  protected void refreshTableMetaData(final DatabaseTableData tableData,
                                    final DatabaseMetaData metaData,
                                    final String tableName,
                                    final String schemaNamePattern) throws SQLException {
    tableData.refreshMetaData(metaData, tableName, schemaNamePattern);
  }

  @Nullable
  public String[] getSchemaNames(final ServerInstance serverInstance, final Ref<String> errorMessage) {
    return performJdbcOperation(serverInstance, errorMessage, new JdbcOperation<String[]>() {
      @Nullable
      public String[] perform(@Nonnull Connection connection, @Nonnull ProgressIndicator indicator) throws Exception {
        indicator.setText(DatabaseMessages.message("message.datasource.progress.loading.schemas"));
        final THashSet<String> set = new THashSet<String>();
        final ResultSet schemas = connection.getMetaData().getSchemas();
        while (schemas.next()) {
          final String catalog = getResultSetStringSafe(schemas, RS_TABLE_CATALOG);
          final String schema = getResultSetStringSafe(schemas, RS_TABLE_SCHEMA);
          if (StringUtil.isNotEmpty(catalog)) {
            set.add(catalog+".*");
            if (StringUtil.isNotEmpty(schema)) {
              set.add(catalog + "."+schema);
            }
          }
          else if (StringUtil.isNotEmpty(schema)) {
            set.add("*."+schema);
          }
        }
        return ArrayUtil.toStringArray(set);
      }
    });
  }

  @Transient
  public void setName(String name) {
    NAME = name;
  }

  public abstract String getSourceName();

  @Transient
  protected void moveState(final DataSource dataSource) {
    final Pair<String[], DatabaseTableData[]> state = dataSource.myState;
    dataSource.myState = Pair.create(ArrayUtil.EMPTY_STRING_ARRAY, new DatabaseTableData[0]);
    for (DatabaseTableData table : state.second) {
      table.setDataSource(this);
    }
    myState = state;
  }

  private static String throwableToString(Throwable throwable) {
    return ExceptionUtil.getThrowableText(throwable, "com.intellij.");
  }
}
