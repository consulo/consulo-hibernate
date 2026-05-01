// Generated on Fri Nov 17 19:09:30 MSK 2006
// DTD/Schema  :    hibernate-configuration-3.0.dtd

package com.intellij.hibernate.model.xml.config;

import java.util.List;

import jakarta.annotation.Nonnull;

import com.intellij.javaee.model.xml.JavaeeDomModelElement;
import consulo.xml.dom.GenericAttributeValue;
import consulo.xml.dom.Required;

/**
 * hibernate-configuration-3.0.dtd:security interface.
 * Type security documentation
 * <pre>
 *  the JNDI name 
 * </pre>
 */
public interface Security extends JavaeeDomModelElement
{

	/**
	 * Returns the value of the context child.
	 * Attribute context
	 * @return the value of the context child.
	 */
	@Nonnull
	@Required
	GenericAttributeValue<String> getContext();


	/**
	 * Returns the list of grant children.
	 * Type grant documentation
	 * <pre>
	 * the JACC contextID
	 * </pre>
	 * @return the list of grant children.
	 */
	@Nonnull
	List<Grant> getGrants();
	/**
	 * Adds new child to the list of grant children.
	 * @return created child
	 */
	Grant addGrant();


}
