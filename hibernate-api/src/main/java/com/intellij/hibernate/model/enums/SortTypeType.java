package com.intellij.hibernate.model.enums;

import consulo.xml.dom.NamedEnum;
import org.jetbrains.annotations.NonNls;

public enum SortTypeType implements NamedEnum {
  UNSORTED("unsorted"), NATURAL("natural");

  private final String myValue;

  private SortTypeType(final @NonNls String value) {
    myValue = value;
  }

  public String getValue() {
    return myValue;
  }
}
