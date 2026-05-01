package com.intellij.hibernate.model.converters;

import com.intellij.jpa.model.xml.impl.converters.ClassConverterBase;
import consulo.util.lang.StringUtil;
import com.intellij.java.language.psi.PsiClass;
import consulo.xml.dom.ConvertContext;
import org.jetbrains.annotations.NonNls;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.util.Map;
import java.util.Set;

/**
 * @author Gregory.Shrago
 */
public abstract class ClassWithShortcutsResolvingConverter extends ClassConverterBase {

  @Nonnull
  protected abstract Map<String, String> getShortcutsMap();

  public PsiClass fromString(@Nullable @NonNls String s, final ConvertContext context) {
    if (s == null) return null;
    final String generatorClassName = getShortcutsMap().get(s);
    return super.fromString(StringUtil.isNotEmpty(generatorClassName) ? generatorClassName : s, context);
  }

  public Set<String> getAdditionalVariants() {
    return getShortcutsMap().keySet();
  }

  // Note: setJavaClassReferenceProviderOptions was removed in the migration to Consulo 3
  // The JavaClassReferenceProvider configuration is now handled differently.
}