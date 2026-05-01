// Generated on Fri Nov 17 19:09:30 MSK 2006
// DTD/Schema  :    hibernate-mapping-3.0.dtd

package com.intellij.hibernate.model.xml.mapping;

import com.intellij.javaee.model.xml.JavaeeDomModelElement;
import consulo.xml.dom.GenericAttributeValue;
import consulo.xml.dom.Required;
import jakarta.annotation.Nonnull;

import java.util.List;

/**
 * hibernate-mapping-3.0.dtd:return-property interface.
 */
public interface HbmReturnProperty extends JavaeeDomModelElement {

	/**
	 * Returns the value of the name child.
	 * Attribute name
	 * @return the value of the name child.
	 */
	@Nonnull
	@Required
	GenericAttributeValue<String> getName();


	/**
	 * Returns the value of the column child.
	 * Attribute column
	 * @return the value of the column child.
	 */
	@Nonnull
	GenericAttributeValue<String> getColumn();


	/**
	 * Returns the list of return-column children.
	 * @return the list of return-column children.
	 */
	@Nonnull
	List<HbmReturnColumn> getReturnColumns();
	/**
	 * Adds new child to the list of return-column children.
	 * @return created child
	 */
	HbmReturnColumn addReturnColumn();


}
