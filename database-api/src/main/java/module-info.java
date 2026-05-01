/**
 * @author VISTALL
 * @since 2026-02-28
 */
module com.intellij.database.api {
    requires transitive consulo.annotation;
    requires transitive consulo.application.api;
    requires transitive consulo.component.api;
    requires transitive consulo.disposer.api;
    requires transitive consulo.document.api;
    requires transitive consulo.language.api;
    requires transitive consulo.module.api;
    requires transitive consulo.navigation.api;
    requires transitive consulo.process.api;
    requires transitive consulo.project.api;
    requires transitive consulo.ui.api;
    requires transitive consulo.ui.ex.api;
    requires transitive consulo.ui.ex.awt.api;
    requires transitive consulo.util.collection;
    requires transitive consulo.util.dataholder;
    requires transitive consulo.util.lang;
    requires transitive consulo.util.xml.serializer;
    requires transitive consulo.virtual.file.system.api;

    requires transitive jakarta.annotation;
    requires transitive org.jdom;
    requires transitive java.sql;

    requires gnu.trove;

    exports com.intellij.javaee.dataSource;
    exports com.intellij.persistence;
    exports com.intellij.persistence.database;
    exports com.intellij.persistence.database.console;
    exports com.intellij.persistence.database.psi;
}
