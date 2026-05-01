package com.intellij.persistence.database;

import gnu.trove.THashSet;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import consulo.language.Language;
import consulo.language.util.LanguageUtil;
import consulo.virtualFileSystem.fileType.FileType;
import consulo.language.file.FileTypeManager;
import consulo.language.file.LanguageFileType;
import consulo.language.plain.PlainTextLanguage;
import consulo.project.Project;
import consulo.util.lang.Comparing;
import consulo.util.lang.function.Condition;
import consulo.util.lang.StringUtil;
import com.intellij.persistence.database.psi.DbDataSourceElement;
import com.intellij.persistence.database.psi.DbElement;
import com.intellij.persistence.database.psi.DbPsiFacade;
import consulo.util.collection.ContainerUtil;

/**
 * @author Gregory.Shrago
 */
public class DbUtil {
  private DbUtil() {
  }

  public static List<DatabaseReferenceConstraintInfo> getRefenceConstraints(final DatabaseColumnInfo column) {
    return ContainerUtil.findAll(column.getTable().getReferenceConstraints(), new Condition<DatabaseReferenceConstraintInfo>() {
      public boolean value(final DatabaseReferenceConstraintInfo object) {
        return object.getSourceColumn() == column;
      }
    });
  }

  public static String getTableQualifiedName(final DatabaseTableInfo tableData) {
    final String schema = tableData.getSchema();
    final String name = tableData.getName();
    return StringUtil.isEmpty(schema) ? name : schema + "." + name;
  }

  @Nonnull
  public static String getQualifiedName(final DbElement element) {
    final StringBuffer sb = new StringBuffer();
    DbElement cur = element;
    while (cur != null) {
      final String name = cur.getName();
      if (StringUtil.isNotEmpty(name)) {
        if (sb.length() > 0) {
          sb.insert(0, '.');
        }
        sb.insert(0, name);
      }
      cur = cur.getDbParent();
      if (cur instanceof DbDataSourceElement) break;
    }
    return sb.toString();
  }

  @Nullable
  public static DatabaseColumnInfo findColumn(final DatabaseTableLongInfo tableInfo, final String name) {
    return tableInfo == null? null : ContainerUtil.find(tableInfo.getColumns(), new Condition<DatabaseColumnInfo>() {
      public boolean value(final DatabaseColumnInfo object) {
        return Comparing.strEqual(name, object.getName(), false);
      }
    });
  }

  @Nullable
  public static DatabaseTableInfo findTable(final DataSourceInfo dataSourceInfo, final DatabaseTableInfo templateInfo) {
    return dataSourceInfo == null || templateInfo == null? null :
           findTable(dataSourceInfo, templateInfo.getName(), templateInfo.getSchema(), templateInfo.getCatalog());
  }

  @Nullable
  public static DatabaseTableInfo findTable(final DataSourceInfo dataSourceInfo, final String table, final String schema, final String catalog) {
    return dataSourceInfo == null? null : findTable(table, schema, catalog, Arrays.asList(dataSourceInfo.getMyTables()));
  }

  @Nullable
  public static <T extends DatabaseTableInfo> T findTable(final String table, final String schema, final String catalog, final Collection<T> tables) {
    return table == null? null : ContainerUtil.find(tables, new Condition<DatabaseTableInfo>() {
      public boolean value(final DatabaseTableInfo object) {
        return Comparing.strEqual(table, object.getName(), false) &&
               (schema == null || Comparing.strEqual(schema, object.getSchema(), false)) &&
               (catalog == null || Comparing.strEqual(catalog, object.getCatalog(), false));
      }
    });
  }

  public static Set<String> getExistingDataSourceNames(final Project project) {
    final Set<String> result = new THashSet<String>();
    for (final DbDataSourceElement dataSource : DbPsiFacade.getInstance(project).getDataSources()) {
      result.add(dataSource.getName());
    }
    return result;
  }

  public static Language guessSqlDialect(final DatabaseConnectionInfo connectionInfo) {
    final Language sqlLanguage = getSqlLanguage();
    if (sqlLanguage == null) return PlainTextLanguage.INSTANCE;
    final Language[] dialects = LanguageUtil.getLanguageDialects(sqlLanguage);
    final String driverClassName = connectionInfo.getDriverClass();
    if (StringUtil.isEmpty(driverClassName)) return sqlLanguage;
    for (Language dialect : dialects) {
      if (StringUtil.containsIgnoreCase(driverClassName, dialect.getID())) return dialect;
    }
    return sqlLanguage;
  }

  @Nullable
  public static Language getSqlLanguage() {
    final FileType sqlFileType = FileTypeManager.getInstance().getFileTypeByExtension("sql");
    return sqlFileType instanceof LanguageFileType ? ((LanguageFileType)sqlFileType).getLanguage() : null;
  }
}
