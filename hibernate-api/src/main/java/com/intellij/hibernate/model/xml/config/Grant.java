// Generated on Fri Nov 17 19:09:30 MSK 2006
// DTD/Schema  :    hibernate-configuration-3.0.dtd

package com.intellij.hibernate.model.xml.config;

import consulo.xml.dom.GenericAttributeValue;
import consulo.xml.dom.Required;
import com.intellij.javaee.model.xml.JavaeeDomModelElement;
import jakarta.annotation.Nonnull;

/**
 * hibernate-configuration-3.0.dtd:grant interface.
 * Type grant documentation
 * <pre>
 * the JACC contextID
 * </pre>
 */
public interface Grant extends JavaeeDomModelElement {

	/**
	 * Returns the value of the entity-name child.
	 * Attribute entity-name
	 * @return the value of the entity-name child.
	 */
	@Nonnull
	@Required
	GenericAttributeValue<String> getEntityName();


	/**
	 * Returns the value of the actions child.
	 * Attribute actions
	 * @return the value of the actions child.
	 */
	@Nonnull
	@Required
	GenericAttributeValue<String> getActions();


	/**
	 * Returns the value of the role child.
	 * Attribute role
	 * @return the value of the role child.
	 */
	@Nonnull
	@Required
	GenericAttributeValue<String> getRole();


}
