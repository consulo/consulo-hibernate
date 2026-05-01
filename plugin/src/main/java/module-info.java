/**
 * @author VISTALL
 * @since 2026-02-28
 */
open module com.intellij.hibernate {
    requires hibernate.api;

    requires consulo.compiler.api;
    requires consulo.datacontext.api;
    requires consulo.file.editor.api;
    requires consulo.file.template.api;
    requires consulo.platform.api;
    requires consulo.project.ui.api;
    requires consulo.util.concurrent;
    requires consulo.util.io;

    requires gnu.trove;
}
