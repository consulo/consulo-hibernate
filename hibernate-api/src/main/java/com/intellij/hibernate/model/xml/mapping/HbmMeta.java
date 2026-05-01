// Generated on Fri Nov 17 19:09:30 MSK 2006
// DTD/Schema  :    hibernate-mapping-3.0.dtd

package com.intellij.hibernate.model.xml.mapping;

import jakarta.annotation.Nonnull;

import consulo.xml.dom.GenericAttributeValue;
import consulo.xml.dom.Required;
import com.intellij.javaee.model.xml.JavaeeDomModelElement;

/**
 * hibernate-mapping-3.0.dtd:meta interface.
 * Type meta documentation
 * <pre>
 * 	META element definition; used to assign meta-level attributes to a class
 * 	or property.  Is currently used by codegenerator as a placeholder for
 * 	values that is not directly related to OR mappings.
 * </pre>
 */
public interface HbmMeta extends JavaeeDomModelElement {

	/**
	 * Returns the value of the simple content.
	 * @return the value of the simple content.
	 */
	@Nonnull
	String getValue();
	/**
	 * Sets the value of the simple content.
	 * @param value the new value to set
	 */
	void setValue(String value);


	/**
	 * Returns the value of the inherit child.
	 * Attribute inherit
	 * @return the value of the inherit child.
	 */
	@Nonnull
	GenericAttributeValue<String> getInherit();


	/**
	 * Returns the value of the attribute child.
	 * Attribute attribute
	 * @return the value of the attribute child.
	 */
	@Nonnull
	@Required
	GenericAttributeValue<String> getAttribute();


}
