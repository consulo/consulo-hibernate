package com.intellij.hibernate.model.enums;

import org.jetbrains.annotations.NonNls;
import consulo.xml.dom.NamedEnum;

public enum AccessType implements NamedEnum {
  FIELD("field"), PROPERTY("property");

  private final String myValue;

  private AccessType(final @NonNls String value) {
    myValue = value;
  }

  public String getValue() {
    return myValue;
  }

}
