package com.intellij.persistence.database.psi;

import consulo.component.extension.ExtensionPointName;

/**
 * @author Gregory.Shrago
 */
public interface DbPsiManagerSpi extends DbPsiManager {
  ExtensionPointName<DbPsiManagerSpi> EP_NAME = ExtensionPointName.create(DbPsiManagerSpi.class);

}