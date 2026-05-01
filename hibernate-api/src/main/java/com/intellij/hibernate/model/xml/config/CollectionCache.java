// Generated on Fri Nov 17 19:09:30 MSK 2006
// DTD/Schema  :    hibernate-configuration-3.0.dtd

package com.intellij.hibernate.model.xml.config;

import jakarta.annotation.Nonnull;

import consulo.xml.dom.GenericAttributeValue;
import consulo.xml.dom.Required;
import com.intellij.hibernate.model.enums.CacheUsageType;
import com.intellij.javaee.model.xml.JavaeeDomModelElement;

/**
 * hibernate-configuration-3.0.dtd:collection-cache interface.
 */
public interface CollectionCache extends JavaeeDomModelElement {

	/**
	 * Returns the value of the usage child.
	 * Attribute usage
	 * @return the value of the usage child.
	 */
	@Nonnull
	@Required
	GenericAttributeValue<CacheUsageType> getUsage();


	/**
	 * Returns the value of the region child.
	 * Attribute region
	 * @return the value of the region child.
	 */
	@Nonnull
	GenericAttributeValue<String> getRegion();


	/**
	 * Returns the value of the collection child.
	 * Attribute collection
	 * @return the value of the collection child.
	 */
	@Nonnull
	@Required
	GenericAttributeValue<String> getCollection();


}
