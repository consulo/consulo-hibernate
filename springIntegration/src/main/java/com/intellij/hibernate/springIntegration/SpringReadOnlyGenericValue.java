package com.intellij.hibernate.springIntegration;

import consulo.xml.dom.GenericValue;

/**
 * Simple read-only GenericValue implementation for spring integration.
 * Replaces the removed consulo.xml.dom.ReadOnlyGenericValue.
 */
final class SpringReadOnlyGenericValue<T> implements GenericValue<T> {
    private final T myValue;

    private SpringReadOnlyGenericValue(T value) {
        myValue = value;
    }

    static <T> GenericValue<T> getInstance(T value) {
        return new SpringReadOnlyGenericValue<>(value);
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
