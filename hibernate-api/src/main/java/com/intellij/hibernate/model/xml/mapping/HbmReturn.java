// Generated on Fri Nov 17 19:09:30 MSK 2006
// DTD/Schema  :    hibernate-mapping-3.0.dtd

package com.intellij.hibernate.model.xml.mapping;

import com.intellij.hibernate.model.enums.LockModeType;
import com.intellij.hibernate.model.converters.MappingClassResolveConverter;
import com.intellij.javaee.model.xml.JavaeeDomModelElement;
import com.intellij.java.language.psi.PsiClass;
import consulo.xml.dom.GenericAttributeValue;
import consulo.xml.dom.Convert;
import jakarta.annotation.Nonnull;

import java.util.List;

/**
 * hibernate-mapping-3.0.dtd:return interface.
 * Type return documentation
 * <pre>
 * 	Defines a return component for a sql-query.  Alias refers to the alias
 * 	used in the actual sql query; lock-mode specifies the locking to be applied
 * 	when the query is executed.  The class, collection, and role attributes are mutually exclusive;
 * 	class refers to the class name of a "root entity" in the object result; collection refers
 * 	to a collection of a given class and is used to define custom sql to load that owned collection
 * 	and takes the form "ClassName.propertyName"; role refers to the property path for an eager fetch
 * 	and takes the form "owningAlias.propertyName"
 * </pre>
 */
public interface HbmReturn extends JavaeeDomModelElement {

	/**
	 * Returns the value of the alias child.
	 * Attribute alias
	 * @return the value of the alias child.
	 */
	@Nonnull
	GenericAttributeValue<String> getAlias();


	/**
	 * Returns the value of the class child.
	 * Attribute class
	 * @return the value of the class child.
	 */
	@Nonnull
	@consulo.xml.dom.Attribute ("class")
        @Convert(MappingClassResolveConverter.class)
        GenericAttributeValue<PsiClass> getClazz();


	/**
	 * Returns the value of the lock-mode child.
	 * Attribute lock-mode
	 * @return the value of the lock-mode child.
	 */
	@Nonnull
	GenericAttributeValue<LockModeType> getLockMode();


	/**
	 * Returns the value of the entity-name child.
	 * Attribute entity-name
	 * @return the value of the entity-name child.
	 */
	@Nonnull
	GenericAttributeValue<String> getEntityName();


	/**
	 * Returns the list of return-discriminator children.
	 * @return the list of return-discriminator children.
	 */
	@Nonnull
	List<HbmReturnDiscriminator> getReturnDiscriminators();
	/**
	 * Adds new child to the list of return-discriminator children.
	 * @return created child
	 */
	HbmReturnDiscriminator addReturnDiscriminator();


	/**
	 * Returns the list of return-property children.
	 * @return the list of return-property children.
	 */
	@Nonnull
	List<HbmReturnProperty> getReturnProperties();
	/**
	 * Adds new child to the list of return-property children.
	 * @return created child
	 */
	HbmReturnProperty addReturnProperty();


}
