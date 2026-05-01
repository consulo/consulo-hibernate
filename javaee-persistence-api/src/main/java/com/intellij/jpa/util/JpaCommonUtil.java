package com.intellij.jpa.util;

import consulo.language.psi.PsiFile;
import consulo.language.psi.scope.GlobalSearchScope;
import consulo.module.Module;
import consulo.project.Project;

public class JpaCommonUtil {
    public static GlobalSearchScope getORMClassesSearchScope(Project project, Module module, PsiFile psiFile) {
        return GlobalSearchScope.allScope(project);
    }
}
