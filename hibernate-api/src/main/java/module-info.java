/**
 * @author VISTALL
 * @since 2026-04-10
 */
open module hibernate.api {
    requires transitive javaee.persistence.api;

    requires transitive consulo.sql.language.api;

    requires transitive consulo.localize.api;

    requires gnu.trove;

    exports com.intellij.hibernate;
    exports com.intellij.hibernate.facet;
    exports com.intellij.hibernate.model;
    exports com.intellij.hibernate.model.common.mapping;
    exports com.intellij.hibernate.model.converters;
    exports com.intellij.hibernate.model.enums;
    exports com.intellij.hibernate.model.manipulators;
    exports com.intellij.hibernate.model.xml;
    exports com.intellij.hibernate.model.xml.config;
    exports com.intellij.hibernate.model.xml.mapping;
    exports com.intellij.hibernate.util;
    exports com.intellij.hibernate.view;

    exports com.intellij.hibernate.icon.icon;

    exports consulo.hibernate.module.extension;
}
