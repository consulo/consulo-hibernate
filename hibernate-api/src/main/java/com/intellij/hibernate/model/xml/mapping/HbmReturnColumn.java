// Generated on Fri Nov 17 19:09:30 MSK 2006
// DTD/Schema  :    hibernate-mapping-3.0.dtd

package com.intellij.hibernate.model.xml.mapping;

import com.intellij.javaee.model.xml.JavaeeDomModelElement;
import consulo.xml.dom.GenericAttributeValue;
import consulo.xml.dom.Required;
import jakarta.annotation.Nonnull;

/**
 * hibernate-mapping-3.0.dtd:return-column interface.
 */
public interface HbmReturnColumn extends JavaeeDomModelElement {

	/**
	 * Returns the value of the name child.
	 * Attribute name
	 * @return the value of the name child.
	 */
	@Nonnull
	@Required
	GenericAttributeValue<String> getName();


}
