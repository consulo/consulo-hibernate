package com.intellij.persistence.util;

import com.intellij.persistence.database.psi.DbDataSourceElement;
import com.intellij.persistence.model.PersistencePackage;
import consulo.project.Project;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class PersistenceUtil {
    public static Collection<DbDataSourceElement> getDataSources(Project project, List<PersistencePackage> units) {
        return Collections.emptyList();
    }
}
