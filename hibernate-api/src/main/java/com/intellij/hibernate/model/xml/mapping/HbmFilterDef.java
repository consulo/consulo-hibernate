// Generated on Fri Nov 17 19:09:30 MSK 2006
// DTD/Schema  :    hibernate-mapping-3.0.dtd

package com.intellij.hibernate.model.xml.mapping;

import com.intellij.javaee.model.xml.JavaeeDomModelElement;
import consulo.xml.dom.GenericAttributeValue;
import consulo.xml.dom.NameValue;
import consulo.xml.dom.Required;
import jakarta.annotation.Nonnull;

import java.util.List;

/**
 * hibernate-mapping-3.0.dtd:filter-def interface.
 * Type filter-def documentation
 * <pre>
 * 	FILTER-DEF element; top-level filter definition.
 * </pre>
 */
public interface HbmFilterDef extends JavaeeDomModelElement {

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
        @NameValue
        GenericAttributeValue<String> getName();


	/**
	 * Returns the value of the condition child.
	 * Attribute condition
	 * @return the value of the condition child.
	 */
	@Nonnull
	GenericAttributeValue<String> getCondition();


	/**
	 * Returns the list of filter-param children.
	 * Type filter-param documentation
	 * <pre>
	 * 	FILTER-PARAM element; qualifies parameters found within a FILTER-DEF
	 * 	condition.
	 * </pre>
	 * @return the list of filter-param children.
	 */
	@Nonnull
        List<HbmFilterParam> getFilterParams();
	/**
	 * Adds new child to the list of filter-param children.
	 * @return created child
	 */
	HbmFilterParam addFilterParam();
}
