package com.intellij.jpa.model.xml.impl.converters;

import com.intellij.java.language.psi.JavaPsiFacade;
import com.intellij.java.language.psi.PsiClass;
import consulo.language.editor.inspection.LocalQuickFix;
import consulo.language.psi.scope.GlobalSearchScope;
import consulo.module.Module;
import consulo.project.Project;
import consulo.xml.dom.ConvertContext;
import consulo.xml.dom.Converter;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import org.jetbrains.annotations.NonNls;

public class ClassConverterBase extends Converter<PsiClass> {
	@Nullable
	@Override
	public PsiClass fromString(@Nullable @NonNls String s, ConvertContext convertContext) {
		if (s == null) {
			return null;
		}
		final String defaultPackage = getDefaultPackageName(convertContext);
		final String qualifiedName;
		if (defaultPackage != null && !s.contains(".")) {
			qualifiedName = defaultPackage + "." + s;
		}
		else {
			qualifiedName = s;
		}
		final Project project = convertContext.getPsiManager().getProject();
		final GlobalSearchScope scope = getResolveSearchScope(convertContext);
		PsiClass result = JavaPsiFacade.getInstance(project).findClass(qualifiedName, scope);
		if (result == null && defaultPackage != null) {
			result = JavaPsiFacade.getInstance(project).findClass(s, scope);
		}
		return result;
	}

	@Nullable
	@Override
	public String toString(@Nullable PsiClass psiClass, ConvertContext convertContext) {
		if (psiClass == null) {
			return null;
		}
		return psiClass.getQualifiedName();
	}

	@Nullable
	protected String getDefaultPackageName(final ConvertContext context) {
		return null;
	}

	@Nonnull
	protected GlobalSearchScope getResolveSearchScope(final ConvertContext context) {
		final Module module = context.getModule();
		if (module != null) {
			return GlobalSearchScope.moduleWithDependenciesAndLibrariesScope(module);
		}
		return GlobalSearchScope.allScope(context.getPsiManager().getProject());
	}

	@Nullable
	public static String getQualifiedClassName(@Nullable String className, @Nullable String defaultPackage) {
		if (className == null) return null;
		if (defaultPackage != null && !className.contains(".")) {
			return defaultPackage + "." + className;
		}
		return className;
	}

	@Nullable
	protected String getBaseClassName(ConvertContext context) {
		return null;
	}

	public static LocalQuickFix[] getCreateClassQuickFixes(ConvertContext context, @Nullable String packageName) {
		return LocalQuickFix.EMPTY_ARRAY;
	}
}
