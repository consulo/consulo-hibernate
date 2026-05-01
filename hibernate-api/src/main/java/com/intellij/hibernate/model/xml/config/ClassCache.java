// Generated on Fri Nov 17 19:09:29 MSK 2006
// DTD/Schema  :    hibernate-configuration-3.0.dtd

package com.intellij.hibernate.model.xml.config;

import jakarta.annotation.Nonnull;

import consulo.xml.dom.GenericAttributeValue;
import consulo.xml.dom.Required;
import com.intellij.hibernate.model.enums.CacheUsageType;
import com.intellij.hibernate.model.enums.CacheIncludeType;
import com.intellij.java.language.psi.PsiClass;
import com.intellij.javaee.model.xml.JavaeeDomModelElement;

/**
 * hibernate-configuration-3.0.dtd:class-cache interface.
 */
public interface ClassCache extends JavaeeDomModelElement {

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
	 * Returns the value of the class child.
	 * Attribute class
	 * @return the value of the class child.
	 */
	@Nonnull
	@consulo.xml.dom.Attribute ("class")
	@Required
	GenericAttributeValue<PsiClass> getClazz();


	/**
	 * Returns the value of the region child.
	 * Attribute region
	 * @return the value of the region child.
	 */
	@Nonnull
	GenericAttributeValue<String> getRegion();


}
