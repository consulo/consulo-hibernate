package com.intellij.hibernate.model.converters;

import consulo.virtualFileSystem.archive.ArchiveFileType;
import consulo.language.psi.PsiFile;

/**
 * @author Gregory.Shrago
 */
public class MappingJarConverter extends MappingElementConverterBase {

  protected boolean isFileAccepted(final PsiFile file) {
    return file != null && file.getFileType() instanceof ArchiveFileType;
  }
}