// Generated on Fri Nov 17 19:09:30 MSK 2006
// DTD/Schema  :    hibernate-mapping-3.0.dtd

package com.intellij.hibernate.model.xml.mapping;

import com.intellij.hibernate.model.converters.PropertyTypeResolvingConverter;
import com.intellij.javaee.model.xml.JavaeeDomModelElement;
import com.intellij.java.language.psi.PsiType;
import consulo.xml.dom.Convert;
import consulo.xml.dom.GenericAttributeValue;
import consulo.xml.dom.Required;
import jakarta.annotation.Nonnull;

import java.util.List;

/**
 * hibernate-mapping-3.0.dtd:index-many-to-any interface.
 * Type index-many-to-any documentation
 * <pre>
 * - default: Hibernate.CLASS 
 * </pre>
 */
public interface HbmIndexManyToAny extends JavaeeDomModelElement, HbmColumnsHolderBase {

	/**
	 * Returns the value of the id-type child.
	 * Attribute id-type
	 * @return the value of the id-type child.
	 */
	@Nonnull
	@Required
        @Convert(PropertyTypeResolvingConverter.class)
        GenericAttributeValue<PsiType> getIdType();


	/**
	 * Returns the value of the meta-type child.
	 * Attribute meta-type
	 * @return the value of the meta-type child.
	 */
	@Nonnull
	GenericAttributeValue<String> getMetaType();


	/**
	 * Returns the list of column children.
	 * Type column documentation
	 * <pre>
	 *  The column element is an alternative to column attributes and required for 
	 * mapping associations to classes with composite ids. 
	 * </pre>
	 * @return the list of column children.
	 */
	@Nonnull
	@Required
	List<HbmColumn> getColumns();
	/**
	 * Adds new child to the list of column children.
	 * @return created child
	 */
	HbmColumn addColumn();


}
