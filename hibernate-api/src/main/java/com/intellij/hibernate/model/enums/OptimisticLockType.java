package com.intellij.hibernate.model.enums;

import consulo.xml.dom.NamedEnum;
import org.jetbrains.annotations.NonNls;

public enum OptimisticLockType implements NamedEnum {
  NONE("none"), VERSION("version"), DIRTY("dirty"), ALL("all");

  private final String myValue;
  private OptimisticLockType(final @NonNls String value) {
    myValue = value;
  }

  public String getValue() {
    return myValue;
  }
}
