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
 * hibernate-mapping-3.0.dtd:collection-id interface.
 * Type collection-id documentation
 * <pre>
 * - default: Hibernate.CLASS 
 * </pre>
 */
public interface HbmCollectionId extends JavaeeDomModelElement, HbmColumnsHolderBase {

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
	@Required
        GenericAttributeValue<String> getColumn();


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
	 * Returns the value of the generator child.
	 * Type generator documentation
	 * <pre>
	 *  Generators generate unique identifiers. The class attribute specifies a Java 
	 * class implementing an id generation algorithm. 
	 * </pre>
	 * @return the value of the generator child.
	 */
	@Nonnull
	@Required
	HbmGenerator getGenerator();


}
