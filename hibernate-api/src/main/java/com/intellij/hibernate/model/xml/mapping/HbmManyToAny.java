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
 * hibernate-mapping-3.0.dtd:many-to-any interface.
 * Type many-to-any documentation
 * <pre>
 *  A "many to any" defines a polymorphic association to any table 
 * with the given identifier type. The first listed column is a VARCHAR column 
 * holding the name of the class (for that row). 
 * </pre>
 */
public interface HbmManyToAny extends JavaeeDomModelElement, HbmColumnsHolderBase {

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
	 * Returns the list of meta-value children.
	 * @return the list of meta-value children.
	 */
	@Nonnull
	List<HbmMetaValue> getMetaValues();
	/**
	 * Adds new child to the list of meta-value children.
	 * @return created child
	 */
	HbmMetaValue addMetaValue();


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
