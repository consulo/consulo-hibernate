/**
 * @author VISTALL
 * @since 2026-04-10
 */
open module com.intellij.hibernate.spring {
    requires hibernate.api;

    requires consulo.java;
    requires com.intellij.xml;
    requires com.intellij.spring;

    requires gnu.trove;
    requires consulo.jakarta.persistence.database.api;
}
