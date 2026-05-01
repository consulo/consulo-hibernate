package com.intellij.hibernate.model.enums;

import consulo.xml.dom.NamedEnum;
import org.jetbrains.annotations.NonNls;

public enum PropertyGeneratedType implements NamedEnum {
  NEVER("never"), INSERT("insert"), ALWAYS("always");

  private final String myValue;

  private PropertyGeneratedType(final @NonNls String value) {
    myValue = value;
  }

  public String getValue() {
    return myValue;
  }

}
