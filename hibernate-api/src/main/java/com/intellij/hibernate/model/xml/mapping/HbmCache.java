// Generated on Fri Nov 17 19:09:30 MSK 2006
// DTD/Schema  :    hibernate-mapping-3.0.dtd

package com.intellij.hibernate.model.xml.mapping;

import jakarta.annotation.Nonnull;

import com.intellij.hibernate.model.enums.CacheIncludeType;
import com.intellij.hibernate.model.enums.CacheUsageType;
import com.intellij.javaee.model.xml.JavaeeDomModelElement;
import consulo.xml.dom.GenericAttributeValue;
import consulo.xml.dom.Required;

/**
 * hibernate-mapping-3.0.dtd:cache interface.
 * Type cache documentation
 * <pre>
 *  The cache element enables caching of an entity class. 
 * </pre>
 */
public interface HbmCache extends JavaeeDomModelElement
{

	/**
	 * Returns the value of the include child.
	 * Attribute include
	 * @return the value of the include child.
	 */
	@Nonnull
	GenericAttributeValue<CacheIncludeType> getInclude();


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


}
