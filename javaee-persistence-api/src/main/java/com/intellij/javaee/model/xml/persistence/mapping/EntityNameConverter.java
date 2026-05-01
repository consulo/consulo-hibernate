package com.intellij.javaee.model.xml.persistence.mapping;

import com.intellij.java.language.psi.PsiClass;
import com.intellij.persistence.model.PersistentEntity;
import consulo.xml.dom.ConvertContext;
import consulo.xml.dom.Converter;
import consulo.xml.dom.GenericValue;
import jakarta.annotation.Nullable;
import org.jetbrains.annotations.NonNls;

public class EntityNameConverter extends Converter<String> {
	@Nullable
	@Override
	public String fromString(@Nullable @NonNls String s, ConvertContext convertContext) {
		return s;
	}

	@Nullable
	@Override
	public String toString(@Nullable String s, ConvertContext convertContext) {
		return s;
	}

	@Nullable
	protected String getEntityName(final PersistentEntity entity) {
		final GenericValue<String> name = entity.getName();
		if (name != null) {
			final String value = name.getStringValue();
			if (value != null) {
				return value;
			}
		}
		final GenericValue<PsiClass> clazz = entity.getClazz();
		if (clazz != null) {
			final PsiClass psiClass = clazz.getValue();
			if (psiClass != null) {
				return psiClass.getName();
			}
			return clazz.getStringValue();
		}
		return null;
	}
}
