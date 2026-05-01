package com.intellij.persistence.database.psi;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import consulo.component.util.WeighedItem;
import consulo.navigation.NavigationItem;
import consulo.language.psi.PsiNamedElement;
import consulo.language.psi.meta.PsiPresentableMetaData;

/**
 * @author Gregory.Shrago
 */
public interface DbElement extends PsiNamedElement, NavigationItem, PsiPresentableMetaData, WeighedItem {
  DbElement[] EMPTY_ARRAY = new DbElement[0];

  Object getDelegate();

  @Nonnull
  DbElementType getType();

  String getDocumentation();

  @Nullable
  DbElement getDbParent();

  DbPsiManager getDbManager();

  DbDataSourceElement getDataSource();

  @Nonnull
  DbElement[] getDbChildren();
}
