// Generated on Fri Nov 17 19:09:30 MSK 2006
// DTD/Schema  :    hibernate-mapping-3.0.dtd

package com.intellij.hibernate.model.xml.mapping;

import jakarta.annotation.Nonnull;

import com.intellij.javaee.model.xml.JavaeeDomModelElement;
import consulo.xml.dom.GenericAttributeValue;
import consulo.xml.dom.Required;
import consulo.xml.dom.Resolve;

/**
 * hibernate-mapping-3.0.dtd:filter interface.
 * Type filter documentation
 * <pre>
 * 	FILTER element; used to apply a filter.
 * </pre>
 */
public interface HbmFilter extends JavaeeDomModelElement
{

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
	 * Returns the value of the name child.
	 * Attribute name
	 * @return the value of the name child.
	 */
	@Nonnull
	@Required
        @Resolve (HbmFilterDef.class)
        GenericAttributeValue<String> getName();


	/**
	 * Returns the value of the condition child.
	 * Attribute condition
	 * @return the value of the condition child.
	 */
	@Nonnull
	GenericAttributeValue<String> getCondition();


}
