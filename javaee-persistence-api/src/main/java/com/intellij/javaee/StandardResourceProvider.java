package com.intellij.javaee;

import consulo.annotation.component.ComponentScope;
import consulo.annotation.component.ExtensionAPI;

/**
 * Stub for StandardResourceProvider - provides standard XML resources.
 */
@ExtensionAPI(ComponentScope.APPLICATION)
public interface StandardResourceProvider {

    void registerResources(ResourceRegistrar registrar);
}
