package com.intellij.hibernate.impl.model.xml.impl;

import consulo.xml.dom.GenericValue;

/**
 * Replacement for JAM's ReadOnlyGenericValue.
 * JAM's ReadOnlyGenericValue implements the removed consulo.xml.util.xml.GenericValue API.
 * This class implements the new consulo.xml.dom.GenericValue interface instead.
 */
public final class HibernateReadOnlyValue<T> implements GenericValue<T> {
    private final T myValue;

    private HibernateReadOnlyValue(T value) {
        myValue = value;
    }

    public static <T> GenericValue<T> getInstance(T value) {
        return new HibernateReadOnlyValue<>(value);
    }

    @Override
    public T getValue() {
        return myValue;
    }

    @Override
    public String getStringValue() {
        return myValue == null ? null : String.valueOf(myValue);
    }
}
