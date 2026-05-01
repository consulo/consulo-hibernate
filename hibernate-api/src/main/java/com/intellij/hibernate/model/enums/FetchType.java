package com.intellij.hibernate.model.enums;

import org.jetbrains.annotations.NonNls;
import consulo.xml.dom.NamedEnum;

public enum FetchType implements NamedEnum {
  JOIN("join"), SELECT("select");

  private final String myValue;

  private FetchType(final @NonNls String value) {
    myValue = value;
  }

  public String getValue() {
    return myValue;
  }
}
