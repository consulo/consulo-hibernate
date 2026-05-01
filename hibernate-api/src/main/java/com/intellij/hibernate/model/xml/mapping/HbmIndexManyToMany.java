// Generated on Fri Nov 17 19:09:30 MSK 2006
// DTD/Schema  :    hibernate-mapping-3.0.dtd

package com.intellij.hibernate.model.xml.mapping;

import com.intellij.hibernate.model.converters.MappingClassResolveConverter;
import com.intellij.javaee.model.xml.JavaeeDomModelElement;
import com.intellij.java.language.psi.PsiClass;
import consulo.xml.dom.Convert;
import consulo.xml.dom.GenericAttributeValue;
import consulo.xml.dom.Required;
import jakarta.annotation.Nonnull;

import java.util.List;

/**
 * hibernate-mapping-3.0.dtd:index-many-to-many interface.
 */
public interface HbmIndexManyToMany extends JavaeeDomModelElement, HbmColumnsHolderBase {

	/**
	 * Returns the value of the foreign-key child.
	 * Attribute foreign-key
	 * @return the value of the foreign-key child.
	 */
	@Nonnull
	GenericAttributeValue<String> getForeignKey();


	/**
	 * Returns the value of the column child.
	 * Attribute column
	 * @return the value of the column child.
	 */
	@Nonnull
        GenericAttributeValue<String> getColumn();


	/**
	 * Returns the value of the class child.
	 * Attribute class
	 * @return the value of the class child.
	 */
	@Nonnull
	@consulo.xml.dom.Attribute ("class")
	@Required
        @Convert(MappingClassResolveConverter.class)
        GenericAttributeValue<PsiClass> getClazz();


	/**
	 * Returns the value of the entity-name child.
	 * Attribute entity-name
	 * @return the value of the entity-name child.
	 */
	@Nonnull
	GenericAttributeValue<String> getEntityName();


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


}
