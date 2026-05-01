package com.intellij.javaee;

/**
 * Stub for ResourceRegistrar - registers standard XML resources (DTDs, schemas).
 */
public interface ResourceRegistrar {

    void addStdResource(String url, String resourcePath, Class<?> loaderClass);

    void addStdResource(String url, String version, String resourcePath, Class<?> loaderClass);
}
