// Generated on Fri Mar 30 16:27:19 MSD 2007
// DTD/Schema  :    hibernate-mapping-3.0.dtd

package com.intellij.hibernate.model.xml.mapping;

import jakarta.annotation.Nonnull;

import com.intellij.javaee.model.xml.JavaeeDomModelElement;
import consulo.xml.dom.GenericAttributeValue;
import consulo.xml.dom.Required;
import consulo.xml.dom.Convert;
import consulo.xml.dom.NameValue;
import com.intellij.hibernate.model.converters.PropertyTypeResolvingConverter;
import com.intellij.java.language.psi.PsiType;

/**
 * hibernate-mapping-3.0.dtd:filter-param interface.
 * Type filter-param documentation
 * <pre>
 * 	FILTER-PARAM element; qualifies parameters found within a FILTER-DEF
 * 	condition.
 * </pre>
 */
public interface HbmFilterParam extends JavaeeDomModelElement {

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
	 * Returns the value of the type child.
	 * Attribute type
	 * @return the value of the type child.
	 */
	@Nonnull
	@Required
        @Convert(PropertyTypeResolvingConverter.class)
        GenericAttributeValue<PsiType> getType();


}
