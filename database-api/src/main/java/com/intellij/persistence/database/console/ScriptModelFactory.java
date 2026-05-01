package com.intellij.persistence.database.console;

import jakarta.annotation.Nullable;

import consulo.document.Document;
import consulo.component.extension.ExtensionPointName;
import consulo.document.util.TextRange;
import consulo.language.psi.PsiFile;
import java.util.function.Function;

/**
 * @author Gregory.Shrago
 */
public abstract class ScriptModelFactory {

  public static ExtensionPointName<ScriptModelFactory> EP_NAME = ExtensionPointName.create(ScriptModelFactory.class);

  @Nullable
  public abstract ScriptModel getScriptModel(final PsiFile file);

  @Nullable
  public abstract Function<Document, TextRange[]> getStatementSplitter(final PsiFile file);
}