package com.intellij.javaee.module.view.dataSource;

import com.intellij.javaee.dataSource.DataSource;
import com.intellij.javaee.dataSource.ServerInstance;
import com.intellij.javaee.module.view.dataSource.classpath.DataSourceClasspathElement;
import jakarta.annotation.Nullable;

import java.sql.Connection;
import java.util.Collections;
import java.util.List;

public class LocalDataSource extends DataSource {
    public List<DataSourceClasspathElement> getClasspathElements() {
        return Collections.emptyList();
    }

    public String getDriverClass() {
        return "";
    }

    public String getUrl() {
        return "";
    }

    public String getUsername() {
        return "";
    }

    public String getPassword() {
        return "";
    }

    @Override
    public String getName() {
        return "";
    }

    @Nullable
    @Override
    protected Connection getConnection(ServerInstance serverInstance) throws Exception {
        return null;
    }

    @Nullable
    @Override
    protected String getSchemaName() {
        return null;
    }

    @Override
    public void init() {
    }

    @Override
    public String getSourceName() {
        return "";
    }
}
