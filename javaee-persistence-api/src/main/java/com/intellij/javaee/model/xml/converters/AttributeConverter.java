package com.intellij.javaee.model.xml.converters;

import com.intellij.persistence.model.PersistentAttribute;
import consulo.xml.dom.ConvertContext;
import consulo.xml.dom.ResolvingConverter;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import org.jetbrains.annotations.NonNls;

import java.util.Collection;
import java.util.Collections;

/**
 * DOM XML converter for resolving persistent attribute references.
 */
public class AttributeConverter extends ResolvingConverter<PersistentAttribute> {

  @Nonnull
  @Override
  public Collection<? extends PersistentAttribute> getVariants(ConvertContext context) {
    return Collections.emptyList();
  }

  @Nullable
  @Override
  public PersistentAttribute fromString(@Nullable @NonNls String s, ConvertContext context) {
    return null;
  }

  @Nullable
  @Override
  public String toString(@Nullable PersistentAttribute attribute, ConvertContext context) {
    return null;
  }
}
