package com.intellij.hibernate.model.converters;

import consulo.project.Project;
import consulo.module.content.ProjectRootManager;
import consulo.virtualFileSystem.util.VirtualFileUtil;
import consulo.virtualFileSystem.VirtualFile;
import consulo.xml.dom.ConvertContext;
import consulo.xml.dom.Converter;
import org.jetbrains.annotations.NonNls;
import jakarta.annotation.Nullable;

/**
 * @author Gregory.Shrago
 */
public class SessionFactoryNameConverter extends Converter<String> {
  public String fromString(@Nullable @NonNls final String s, final ConvertContext context) {
    if (s != null) return s;
    final Project project = context.getFile().getProject();
    final VirtualFile vFile = context.getFile().getVirtualFile();
    final VirtualFile root = vFile == null? null : ProjectRootManager.getInstance(project).getFileIndex().getContentRootForFile(vFile);
    final String relativePath = root == null? null : VirtualFileUtil.getRelativePath(vFile, root, '/');

    final String fileName = relativePath != null? relativePath : context.getFile().getName();
    return fileName;
    //final PersistencePackage unit = DomUtil.getParentOfType(context.getInvocationElement(), PersistencePackage.class, true);
    //assert unit != null;
    //final String typeName = ElementPresentationManager.getTypeNameForObject(unit);
    //return typeName + ": " + fileName;
  }

  public String toString(@Nullable final String s, final ConvertContext context) {
    return s;
  }
}
