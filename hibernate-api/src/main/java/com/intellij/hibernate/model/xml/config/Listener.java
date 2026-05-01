// Generated on Fri Nov 17 19:09:30 MSK 2006
// DTD/Schema  :    hibernate-configuration-3.0.dtd

package com.intellij.hibernate.model.xml.config;

import jakarta.annotation.Nonnull;

import consulo.xml.dom.GenericAttributeValue;
import consulo.xml.dom.Required;
import com.intellij.hibernate.model.enums.ListenerType;
import com.intellij.java.language.psi.PsiClass;
import com.intellij.javaee.model.xml.JavaeeDomModelElement;

/**
 * hibernate-configuration-3.0.dtd:listener interface.
 */
public interface Listener extends JavaeeDomModelElement {

	/**
	 * Returns the value of the type child.
	 * Attribute type
	 * @return the value of the type child.
	 */
	@Nonnull
	GenericAttributeValue<ListenerType> getType();


	/**
	 * Returns the value of the class child.
	 * Attribute class
	 * @return the value of the class child.
	 */
	@Nonnull
	@consulo.xml.dom.Attribute ("class")
	@Required
	GenericAttributeValue<PsiClass> getClazz();


}
