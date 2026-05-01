package com.intellij.javaee.inspection;

/**
 * Stub for InspectionToolProvider - removed in Consulo 3.
 * In Consulo 3, inspections are registered via @ExtensionImpl annotations directly.
 * This stub maintains backward compatibility for code that references it.
 */
public interface InspectionToolProvider {


    Class[] getInspectionClasses();
}
