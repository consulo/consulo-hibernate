// Generated on Fri Nov 17 19:09:30 MSK 2006
// DTD/Schema  :    hibernate-mapping-3.0.dtd

package com.intellij.hibernate.model.xml.mapping;

import consulo.xml.dom.GenericAttributeValue;
import consulo.xml.dom.Required;
import com.intellij.javaee.model.xml.JavaeeDomModelElement;
import jakarta.annotation.Nonnull;

/**
 * hibernate-mapping-3.0.dtd:return-discriminator interface.
 */
public interface HbmReturnDiscriminator extends JavaeeDomModelElement {

	/**
	 * Returns the value of the column child.
	 * Attribute column
	 * @return the value of the column child.
	 */
	@Nonnull
	@Required
	GenericAttributeValue<String> getColumn();


}
