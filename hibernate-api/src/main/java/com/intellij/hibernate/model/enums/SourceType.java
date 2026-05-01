package com.intellij.hibernate.model.enums;

import consulo.xml.dom.NamedEnum;
import org.jetbrains.annotations.NonNls;

public enum SourceType implements NamedEnum {
  VM("vm"), DB("db");

  private final String myValue;

  private SourceType(final @NonNls String value) {
    myValue = value;
  }

  public String getValue() {
    return myValue;
  }

}
