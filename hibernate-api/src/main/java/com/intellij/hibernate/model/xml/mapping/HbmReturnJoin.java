// Generated on Fri Nov 17 19:09:30 MSK 2006
// DTD/Schema  :    hibernate-mapping-3.0.dtd

package com.intellij.hibernate.model.xml.mapping;

import com.intellij.hibernate.model.enums.LockModeType;
import com.intellij.javaee.model.xml.JavaeeDomModelElement;
import consulo.xml.dom.GenericAttributeValue;
import consulo.xml.dom.Required;
import jakarta.annotation.Nonnull;

import java.util.List;

/**
 * hibernate-mapping-3.0.dtd:return-join interface.
 */
public interface HbmReturnJoin extends JavaeeDomModelElement {

	/**
	 * Returns the value of the alias child.
	 * Attribute alias
	 * @return the value of the alias child.
	 */
	@Nonnull
	@Required
	GenericAttributeValue<String> getAlias();


	/**
	 * Returns the value of the property child.
	 * Attribute property
	 * @return the value of the property child.
	 */
	@Nonnull
	@Required
	GenericAttributeValue<String> getProperty();


	/**
	 * Returns the value of the lock-mode child.
	 * Attribute lock-mode
	 * @return the value of the lock-mode child.
	 */
	@Nonnull
	GenericAttributeValue<LockModeType> getLockMode();


	/**
	 * Returns the list of return-property children.
	 * @return the list of return-property children.
	 */
	@Nonnull
	List<HbmReturnProperty> getReturnProperties();
	/**
	 * Adds new child to the list of return-property children.
	 * @return created child
	 */
	HbmReturnProperty addReturnProperty();


}
