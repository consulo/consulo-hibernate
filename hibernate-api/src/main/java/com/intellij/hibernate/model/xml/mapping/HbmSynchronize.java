// Generated on Fri Nov 17 19:09:30 MSK 2006
// DTD/Schema  :    hibernate-mapping-3.0.dtd

package com.intellij.hibernate.model.xml.mapping;

import jakarta.annotation.Nonnull;

import consulo.xml.dom.GenericAttributeValue;
import consulo.xml.dom.Required;
import consulo.xml.dom.Convert;
import com.intellij.javaee.model.xml.JavaeeDomModelElement;
import com.intellij.javaee.model.JavaeePersistenceORMResolveConverters;

/**
 * hibernate-mapping-3.0.dtd:synchronize interface.
 */
public interface HbmSynchronize extends JavaeeDomModelElement {

	/**
	 * Returns the value of the table child.
	 * Attribute table
	 * @return the value of the table child.
	 */
	@Nonnull
	@Required
        @Convert(JavaeePersistenceORMResolveConverters.TableResolver.class)
        GenericAttributeValue<String> getTable();


}
