/**
 * @author VISTALL
 * @since 2026-02-28
 */
open module javaee.persistence.api {
    requires transitive com.intellij.database.api;

    requires transitive consulo.code.editor.api;
    requires transitive consulo.configurable.api;
    requires transitive consulo.execution.api;
    requires transitive consulo.language.editor.api;
    requires transitive consulo.language.editor.ui.api;
    requires transitive consulo.logging.api;
    requires transitive consulo.module.content.api;
    requires transitive consulo.project.content.api;

    requires transitive consulo.java;
    requires transitive com.intellij.xml;
    requires transitive com.intellij.properties;

    requires gnu.trove;

    exports com.intellij.javaee;
    exports com.intellij.javaee.model;
    exports com.intellij.javaee.model.common;
    exports com.intellij.javaee.model.common.persistence;
    exports com.intellij.javaee.model.common.persistence.mapping;
    exports com.intellij.javaee.model.role;
    exports com.intellij.javaee.model.xml;
    exports com.intellij.javaee.model.xml.converters;
    exports com.intellij.javaee.model.xml.persistence.mapping;
    exports com.intellij.javaee.module.view.dataSource;
    exports com.intellij.javaee.module.view.dataSource.classpath;
    exports com.intellij.jpa;
    exports com.intellij.jpa.facet;
    exports com.intellij.jpa.highlighting;
    exports com.intellij.jpa.model.annotations.mapping;
    exports com.intellij.jpa.model.common;
    exports com.intellij.jpa.model.manipulators;
    exports com.intellij.jpa.model.xml.impl.converters;
    exports com.intellij.jpa.util;
    exports com.intellij.jpa.view;
    exports com.intellij.persistence.facet;
    exports com.intellij.persistence.model;
    exports com.intellij.persistence.model.helpers;
    exports com.intellij.persistence.model.manipulators;
    exports com.intellij.persistence.model.validators;
    exports com.intellij.persistence.roles;
    exports com.intellij.persistence.run;
    exports com.intellij.persistence.util;
    exports com.intellij.ql.psi;
    exports consulo.java.persistence.module.extension;
}
