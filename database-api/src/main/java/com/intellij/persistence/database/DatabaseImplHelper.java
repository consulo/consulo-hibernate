package com.intellij.persistence.database;

import consulo.component.extension.ExtensionPointName;
import consulo.module.Module;
import consulo.project.Project;
import consulo.ui.ex.awt.LabeledComponent;
import consulo.ui.ex.awt.TextFieldWithBrowseButton;
import consulo.virtualFileSystem.util.PathsList;

/**
 * @author Gregory.Shrago
 */
public interface DatabaseImplHelper {
  ExtensionPointName<DatabaseImplHelper> EP_NAME = ExtensionPointName.create(DatabaseImplHelper.class);

  void setupPsiClassField(final Project project, LabeledComponent<TextFieldWithBrowseButton> field);

  void collectModuleClasspath(Module module, PathsList classPath);
}
