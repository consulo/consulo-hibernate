// Generated on Fri Nov 17 19:09:30 MSK 2006
// DTD/Schema  :    hibernate-mapping-3.0.dtd

package com.intellij.hibernate.model.xml.mapping;

import jakarta.annotation.Nonnull;

import consulo.xml.dom.GenericAttributeValue;
import consulo.xml.dom.Required;
import com.intellij.javaee.model.xml.JavaeeDomModelElement;

/**
 * hibernate-mapping-3.0.dtd:loader interface.
 * Type loader documentation
 * <pre>
 *  The loader element allows specification of a named query to be used for fetching
 * an entity or collection 
 * </pre>
 */
public interface HbmLoader extends JavaeeDomModelElement {

	/**
	 * Returns the value of the query-ref child.
	 * Attribute query-ref
	 * @return the value of the query-ref child.
	 */
	@Nonnull
	@Required
	GenericAttributeValue<String> getQueryRef();


}
