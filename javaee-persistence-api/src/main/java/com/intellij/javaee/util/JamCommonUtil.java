package com.intellij.javaee.util;

import com.intellij.java.language.psi.PsiClass;
import consulo.module.Module;
import consulo.language.psi.PsiFile;
import jakarta.annotation.Nullable;

public class JamCommonUtil {
    public static Module[] getAllDependentModules(Module module) {
        return new Module[]{module};
    }

    public static boolean isPlainJavaFile(PsiFile file) {
        return false;
    }

    public static boolean isPlainXmlFile(PsiFile file) {
        return false;
    }

    @Nullable
    public static <V> V getRootElement(@Nullable PsiFile file, Class<V> mappingClass, @Nullable Module module) {
        // stub
        return null;
    }

    public static boolean isSuperClass(@Nullable PsiClass psiClass, @Nullable String superClassName) {
        // stub
        return false;
    }
}
