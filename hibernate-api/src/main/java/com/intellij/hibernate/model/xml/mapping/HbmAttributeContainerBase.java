package com.intellij.hibernate.model.xml.mapping;

import com.intellij.javaee.model.xml.JavaeeDomModelElement;

/**
 * @author Gregory.Shrago
 */
public interface HbmAttributeContainerBase extends JavaeeDomModelElement
{
  void visitAttributes(final HbmAttributeVisitor visitor);
}