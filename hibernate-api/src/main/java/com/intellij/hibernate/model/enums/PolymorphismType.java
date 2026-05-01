package com.intellij.hibernate.model.enums;

import consulo.xml.dom.NamedEnum;
import org.jetbrains.annotations.NonNls;

public enum PolymorphismType implements NamedEnum {
  IMPLICIT("implicit"), EXPLICIT("explicit");

  private final String myValue;
  private PolymorphismType(final @NonNls String value) {
    myValue = value;
  }

  public String getValue() {
    return myValue;
  }
}
