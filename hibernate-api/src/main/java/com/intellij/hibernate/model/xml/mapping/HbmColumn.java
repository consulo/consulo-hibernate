// Generated on Fri Nov 17 19:09:30 MSK 2006
// DTD/Schema  :    hibernate-mapping-3.0.dtd

package com.intellij.hibernate.model.xml.mapping;

import jakarta.annotation.Nonnull;

import consulo.xml.dom.*;
import com.intellij.javaee.model.xml.JavaeeDomModelElement;
import com.intellij.javaee.model.JavaeePersistenceORMResolveConverters;

/**
 * hibernate-mapping-3.0.dtd:column interface.
 * Type column documentation
 * <pre>
 *  The column element is an alternative to column attributes and required for 
 * mapping associations to classes with composite ids. 
 * </pre>
 */
public interface HbmColumn extends JavaeeDomModelElement {

	/**
	 * Returns the value of the name child.
	 * Attribute name
	 * @return the value of the name child.
	 */
	@Nonnull
	@Required
        @Convert(JavaeePersistenceORMResolveConverters.ColumnResolver.class)
        GenericAttributeValue<String> getName();


	/**
	 * Returns the value of the check child.
	 * Attribute check
	 * @return the value of the check child.
	 */
	@Nonnull
	GenericAttributeValue<Boolean> getCheck();


	/**
	 * Returns the value of the length child.
	 * Attribute length
	 * @return the value of the length child.
	 */
	@Nonnull
	GenericAttributeValue<Integer> getLength();


	/**
	 * Returns the value of the scale child.
	 * Attribute scale
	 * @return the value of the scale child.
	 */
	@Nonnull
	GenericAttributeValue<Integer> getScale();


	/**
	 * Returns the value of the unique child.
	 * Attribute unique
	 * @return the value of the unique child.
	 */
	@Nonnull
	GenericAttributeValue<Boolean> getUnique();


	/**
	 * Returns the value of the unique-key child.
	 * Attribute unique-key
	 * @return the value of the unique-key child.
	 */
	@Nonnull
	GenericAttributeValue<String> getUniqueKey();


	/**
	 * Returns the value of the precision child.
	 * Attribute precision
	 * @return the value of the precision child.
	 */
	@Nonnull
	GenericAttributeValue<Integer> getPrecision();


	/**
	 * Returns the value of the index child.
	 * Attribute index
	 * @return the value of the index child.
	 */
	@Nonnull
	GenericAttributeValue<String> getIndex();


	/**
	 * Returns the value of the sql-type child.
	 * Attribute sql-type
	 * @return the value of the sql-type child.
	 */
	@Nonnull
	GenericAttributeValue<String> getSqlType();


	/**
	 * Returns the value of the not-null child.
	 * Attribute not-null
	 * @return the value of the not-null child.
	 */
	@Nonnull
	GenericAttributeValue<Boolean> getNotNull();


	/**
	 * Returns the value of the default child.
	 * Attribute default
	 * @return the value of the default child.
	 */
	@Nonnull
	GenericAttributeValue<String> getDefault();


	/**
	 * Returns the value of the comment child.
	 * Type comment documentation
	 * <pre>
	 *  The comment element allows definition of a database table or column comment. 
	 * </pre>
	 * @return the value of the comment child.
	 */
	@Nonnull
	GenericDomValue<String> getComment();


}
