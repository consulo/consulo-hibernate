package com.intellij.javaee.model.xml.persistence.mapping;

import consulo.xml.dom.NamedEnum;
import org.jetbrains.annotations.NonNls;

/**
 * JPA FetchType enum stub for persistence mapping.
 */
public enum FetchType implements NamedEnum {
  EAGER("EAGER"),
  LAZY("LAZY");

  private final String myValue;

  FetchType(@NonNls String value) {
    myValue = value;
  }

  public String getValue() {
    return myValue;
  }

  public String getDisplayName() {
    return getValue();
  }
}
