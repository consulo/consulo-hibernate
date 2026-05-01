// Generated on Fri Nov 17 19:09:30 MSK 2006
// DTD/Schema  :    hibernate-mapping-3.0.dtd

package com.intellij.hibernate.model.xml.mapping;

import com.intellij.hibernate.model.converters.PropertyTypeResolvingConverter;
import com.intellij.hibernate.model.enums.AccessType;
import com.intellij.java.language.psi.PsiType;
import consulo.xml.dom.Convert;
import consulo.xml.dom.GenericAttributeValue;
import com.intellij.jam.model.common.CommonModelElement;
import jakarta.annotation.Nonnull;

import java.util.List;

/**
 * hibernate-mapping-3.0.dtd:key-property interface.
 * Type key-property documentation
 * <pre>
 *  A property embedded in a composite identifier or map index (always not-null). 
 * </pre>
 */
public interface HbmKeyProperty extends CommonModelElement, HbmPropertyBase {

	/**
	 * Returns the value of the access child.
	 * Attribute access
	 * @return the value of the access child.
	 */
	@Nonnull
	GenericAttributeValue<AccessType> getAccess();


	/**
	 * Returns the value of the column child.
	 * Attribute column
	 * @return the value of the column child.
	 */
	@Nonnull
        GenericAttributeValue<String> getColumn();


	/**
	 * Returns the value of the type child.
	 * Attribute type
	 * @return the value of the type child.
	 */
	@Nonnull
        @Convert(PropertyTypeResolvingConverter.class)
        @consulo.xml.dom.Attribute ("type")
	GenericAttributeValue<PsiType> getTypeAttr();


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
	 * Returns the list of meta children.
	 * Type meta documentation
	 * <pre>
	 * 	META element definition; used to assign meta-level attributes to a class
	 * 	or property.  Is currently used by codegenerator as a placeholder for
	 * 	values that is not directly related to OR mappings.
	 * </pre>
	 * @return the list of meta children.
	 */
	@Nonnull
	List<HbmMeta> getMetas();
	/**
	 * Adds new child to the list of meta children.
	 * @return created child
	 */
	HbmMeta addMeta();


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
	 * Returns the value of the type child.
	 * Type type documentation
	 * <pre>
	 *  Declares the type of the containing property (overrides an eventually existing type
	 * attribute of the property). May contain param elements to customize a ParametrizableType. 
	 * </pre>
	 * @return the value of the type child.
	 */
	@Nonnull
	HbmType getType();


}
