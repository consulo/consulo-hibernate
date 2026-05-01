package com.intellij.javaee.model;

import consulo.xml.dom.ConvertContext;
import consulo.xml.dom.ResolvingConverter;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import org.jetbrains.annotations.NonNls;

import java.util.Collection;
import java.util.Collections;

/**
 * Stub for JPA ORM resolve converters that resolve database object references
 * (tables, columns, schemas, catalogs, sequences) in persistence mapping XML files.
 */
public class JavaeePersistenceORMResolveConverters {

  public abstract static class ResolverBase extends ResolvingConverter<String> {

    @Nonnull
    @Override
    public Collection<? extends String> getVariants(ConvertContext context) {
      return Collections.emptyList();
    }

    @Nullable
    @Override
    public String fromString(@Nullable @NonNls String s, ConvertContext context) {
      return s;
    }

    @Nullable
    @Override
    public String toString(@Nullable String s, ConvertContext context) {
      return s;
    }
  }

  public static class SchemaResolver extends ResolverBase {
  }

  public static class CatalogResolver extends ResolverBase {
  }

  public static class TableResolver extends ResolverBase {
  }

  public static class ColumnResolver extends ResolverBase {
  }

  public static class SequenceResolver extends ResolverBase {
  }
}
