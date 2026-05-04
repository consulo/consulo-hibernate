/*
 * Copyright (c) 2000-2007 JetBrains s.r.o. All Rights Reserved.
 */

package com.intellij.hibernate.impl.intentions;

import com.intellij.hibernate.localize.HibernateLocalize;
import com.intellij.hibernate.impl.engine.HibernateConsole;
import consulo.codeEditor.Editor;
import consulo.language.editor.intention.IntentionMetaData;
import consulo.localize.LocalizeValue;
import consulo.project.Project;
import com.intellij.persistence.facet.PersistenceFacetBase;
import com.intellij.persistence.facet.PersistenceFacetConfiguration;
import com.intellij.persistence.model.PersistencePackage;
import jakarta.annotation.Nonnull;
import consulo.annotation.component.ExtensionImpl;

/**
 * @author Gregory.Shrago
 */
@IntentionMetaData(ignoreId = "hibernate.OpenHqlConsoleIntentionAction", categories = "Hibernate", fileExtensions = "javay ")
@ExtensionImpl
public class OpenHqlConsoleIntentionAction extends QueryIntentionActionBase {

  @Nonnull
  @Override
  protected LocalizeValue getFamilyName() {
    return HibernateLocalize.intentionExecuteHqlQueryFamily();
  }

  protected void invokeInner(final Project project, final Editor editor, final PersistenceFacetBase<PersistenceFacetConfiguration, PersistencePackage> curFacet,
                             final PersistencePackage curUnit,
                             final String query) {
    HibernateConsole.openHqlConsole(project, editor, curFacet, curUnit, query, null);
  }

}