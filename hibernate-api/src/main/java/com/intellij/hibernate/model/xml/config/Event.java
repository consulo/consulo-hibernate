// Generated on Fri Nov 17 19:09:30 MSK 2006
// DTD/Schema  :    hibernate-configuration-3.0.dtd

package com.intellij.hibernate.model.xml.config;

import com.intellij.hibernate.model.enums.EventType;
import consulo.xml.dom.GenericAttributeValue;
import consulo.xml.dom.Required;
import com.intellij.javaee.model.xml.JavaeeDomModelElement;
import jakarta.annotation.Nonnull;

import java.util.List;

/**
 * hibernate-configuration-3.0.dtd:event interface.
 */
public interface Event extends JavaeeDomModelElement {

	/**
	 * Returns the value of the type child.
	 * Attribute type
	 * @return the value of the type child.
	 */
	@Nonnull
	@Required
	GenericAttributeValue<EventType> getType();


	/**
	 * Returns the list of listener children.
	 * @return the list of listener children.
	 */
	@Nonnull
	List<Listener> getListeners();
	/**
	 * Adds new child to the list of listener children.
	 * @return created child
	 */
	Listener addListener();


}
