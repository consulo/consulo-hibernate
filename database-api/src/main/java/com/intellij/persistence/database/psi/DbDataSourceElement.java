package com.intellij.persistence.database.psi;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import com.intellij.persistence.database.DataSourceInfo;
import com.intellij.persistence.database.DatabaseConnectionInfo;
import consulo.util.collection.ArrayFactory;

/**
 * @author Gregory.Shrago
 */
public interface DbDataSourceElement extends DbElement, DataSourceInfo
{
	DbDataSourceElement[] EMPTY_ARRAY = new DbDataSourceElement[0];

	ArrayFactory<DbDataSourceElement> ARRAY_FACTORY = i -> i == 0 ? EMPTY_ARRAY : new DbDataSourceElement[i];

	@Nullable
	DatabaseConnectionInfo getConnectionInfo();

	@Nonnull
	DbTableElement[] getMyTables();

	@Nonnull
	DbCatalogElement[] getCatalogs();

	@Nonnull
	DbSchemaElement[] getSchemas();

	@Nullable
	DbTableElement findTable(final String table, final String schema, final String catalog);

	void clearCaches();

}
