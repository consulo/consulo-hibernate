// Generated on Fri Mar 30 16:27:20 MSD 2007
// DTD/Schema  :    hibernate-mapping-3.0.dtd

package com.intellij.hibernate.model.xml.mapping;

import com.intellij.javaee.model.xml.JavaeeDomModelElement;
import consulo.xml.dom.GenericAttributeValue;
import consulo.xml.dom.Required;
import consulo.xml.dom.Convert;
import consulo.xml.dom.NameValue;
import com.intellij.hibernate.model.converters.PropertyTypeResolvingConverter;
import com.intellij.java.language.psi.PsiType;
import com.intellij.persistence.model.PersistenceQueryParam;
import jakarta.annotation.Nonnull;

/**
 * hibernate-mapping-3.0.dtd:query-param interface.
 * Type query-param documentation
 * <pre>
 *  The query-param element is used only by tools that generate
 * finder methods for named queries
 * </pre>
 */
public interface HbmQueryParam extends JavaeeDomModelElement, PersistenceQueryParam {

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
