package com.intellij.hibernate.model.converters;

import com.intellij.persistence.model.PersistenceMappings;
import consulo.language.psi.PsiFile;
import consulo.xml.language.psi.XmlFile;
import consulo.xml.dom.DomElement;
import consulo.xml.dom.DomManager;
import consulo.xml.dom.DomFileElement;

/**
 * @author Gregory.Shrago
 */
public class MappingFileConverter extends MappingElementConverterBase {

  protected boolean isFileAccepted(final PsiFile file) {
    if (file instanceof XmlFile) {
      final DomFileElement fileElement = DomManager.getDomManager(file.getProject()).getFileElement((XmlFile)file, DomElement.class);
      if (fileElement != null) {
        return fileElement.getRootElement() instanceof PersistenceMappings;
      }
    }
    return false;
  }

}
