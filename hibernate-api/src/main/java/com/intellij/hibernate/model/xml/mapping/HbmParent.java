// Generated on Fri Nov 17 19:09:30 MSK 2006
// DTD/Schema  :    hibernate-mapping-3.0.dtd

package com.intellij.hibernate.model.xml.mapping;

import jakarta.annotation.Nonnull;

import com.intellij.javaee.model.xml.JavaeeDomModelElement;
import consulo.xml.dom.GenericAttributeValue;
import consulo.xml.dom.Required;

/**
 * hibernate-mapping-3.0.dtd:parent interface.
 * Type parent documentation
 * <pre>
 *  The parent element maps a property of the component class as a pointer back to
 * the owning entity. 
 * </pre>
 */
public interface HbmParent extends JavaeeDomModelElement
{

	/**
	 * Returns the value of the name child.
	 * Attribute name
	 * @return the value of the name child.
	 */
	@Nonnull
	@Required
	GenericAttributeValue<String> getName();


}
