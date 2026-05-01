package com.intellij.ql.psi.impl;

import consulo.language.Language;

/**
 * Stub for QLLanguage - Query Language definitions for JPA/Hibernate.
 */
public class QLLanguage extends Language {

    public static final QLLanguage HIBERNATE_QL = new QLLanguage("HibernateQL", "HQL");
    public static final QLLanguage JPA_QL = new QLLanguage("JPAQL", "JPQL");

    protected QLLanguage(String id, String... mimeTypes) {
        super(id, mimeTypes);
    }
}
