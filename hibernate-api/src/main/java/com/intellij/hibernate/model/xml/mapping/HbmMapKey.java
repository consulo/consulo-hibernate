// Generated on Fri Nov 17 19:09:30 MSK 2006
// DTD/Schema  :    hibernate-mapping-3.0.dtd

package com.intellij.hibernate.model.xml.mapping;

import com.intellij.hibernate.model.converters.PropertyTypeResolvingConverter;
import com.intellij.javaee.model.xml.JavaeeDomModelElement;
import com.intellij.java.language.psi.PsiType;
import consulo.xml.dom.Convert;
import consulo.xml.dom.GenericAttributeValue;
import consulo.xml.dom.GenericDomValue;
import consulo.xml.dom.Required;
import jakarta.annotation.Nonnull;

import java.util.List;

/**
 * hibernate-mapping-3.0.dtd:map-key interface.
 */
public interface HbmMapKey extends JavaeeDomModelElement, HbmColumnsHolderBase {

	/**
	 * Returns the value of the type child.
	 * Attribute type
	 * @return the value of the type child.
	 */
	@Nonnull
	@Required
        @Convert(PropertyTypeResolvingConverter.class)
        GenericAttributeValue<PsiType> getType();


	/**
	 * Returns the value of the column child.
	 * Attribute column
	 * @return the value of the column child.
	 */
	@Nonnull
        GenericAttributeValue<String> getColumn();


	/**
	 * Returns the value of the node child.
	 * Attribute node
	 * @return the value of the node child.
	 */
	@Nonnull
	GenericAttributeValue<String> getNode();


	/**
	 * Returns the value of the length child.
	 * Attribute length
	 * @return the value of the length child.
	 */
	@Nonnull
	GenericAttributeValue<Integer> getLength();


	/**
	 * Returns the value of the formula child.
	 * Attribute formula
	 * @return the value of the formula child.
	 */
	@Nonnull
	GenericAttributeValue<String> getFormula();


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
	List<HbmColumn> getColumns();
	/**
	 * Adds new child to the list of column children.
	 * @return created child
	 */
	HbmColumn addColumn();


	/**
	 * Returns the list of formula children.
	 * Type formula documentation
	 * <pre>
	 *  The formula and subselect elements allow us to map derived properties and 
	 * entities. 
	 * </pre>
	 * @return the list of formula children.
	 */
	@Nonnull
	List<GenericDomValue<String>> getFormulas();
	/**
	 * Adds new child to the list of formula children.
	 * @return created child
	 */
	GenericDomValue<String> addFormula();


}
