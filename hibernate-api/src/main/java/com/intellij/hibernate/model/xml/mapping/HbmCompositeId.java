// Generated on Fri Nov 17 19:09:30 MSK 2006
// DTD/Schema  :    hibernate-mapping-3.0.dtd

package com.intellij.hibernate.model.xml.mapping;

import java.util.List;

import jakarta.annotation.Nonnull;

import com.intellij.hibernate.model.converters.MappingClassResolveConverter;
import com.intellij.hibernate.model.enums.AccessType;
import com.intellij.javaee.model.xml.JavaeeDomModelElement;
import com.intellij.java.language.psi.PsiClass;
import consulo.xml.dom.Convert;
import consulo.xml.dom.GenericAttributeValue;
import consulo.xml.dom.SubTagList;

/**
 * hibernate-mapping-3.0.dtd:composite-id interface.
 * Type composite-id documentation
 * <pre>
 *  A composite key may be modelled by a java class with a property for each 
 * key column. The class must implement java.io.Serializable and reimplement equals() 
 * and hashCode(). 
 * </pre>
 */
public interface HbmCompositeId extends JavaeeDomModelElement, HbmEmbeddedAttributeBase {

	/**
	 * Returns the value of the access child.
	 * Attribute access
	 * @return the value of the access child.
	 */
	@Nonnull
	GenericAttributeValue<AccessType> getAccess();


	/**
	 * Returns the value of the mapped child.
	 * Attribute mapped
	 * @return the value of the mapped child.
	 */
	@Nonnull
	GenericAttributeValue<Boolean> getMapped();


	/**
	 * Returns the value of the unsaved-value child.
	 * Attribute unsaved-value
	 * @return the value of the unsaved-value child.
	 */
	@Nonnull
	GenericAttributeValue<String> getUnsavedValue();


	/**
	 * Returns the value of the node child.
	 * Attribute node
	 * @return the value of the node child.
	 */
	@Nonnull
	GenericAttributeValue<String> getNode();


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
	 * Returns the list of key-property children.
	 * Type key-property documentation
	 * <pre>
	 *  A property embedded in a composite identifier or map index (always not-null).
	 * </pre>
	 * @return the list of key-property children.
	 */
        @SubTagList("key-property")
        @Nonnull
        List<HbmKeyProperty> getProperties();
	/**
	 * Adds new child to the list of key-property children.
	 * @return created child
	 */
        @SubTagList("key-property")
        HbmKeyProperty addProperty();


	/**
	 * Returns the list of key-many-to-one children.
	 * Type key-many-to-one documentation
	 * <pre>
	 *  A many-to-one association embedded in a composite identifier or map index 
	 * (always not-null, never cascade). 
	 * </pre>
	 * @return the list of key-many-to-one children.
	 */
        @SubTagList("key-many-to-one")
        @Nonnull
	List<HbmKeyManyToOne> getManyToOnes();
	/**
	 * Adds new child to the list of key-many-to-one children.
	 * @return created child
	 */
        @SubTagList("key-many-to-one")
        HbmKeyManyToOne addManyToOne();


}
