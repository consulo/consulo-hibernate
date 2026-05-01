// Generated on Fri Nov 17 19:09:30 MSK 2006
// DTD/Schema  :    hibernate-mapping-3.0.dtd

package com.intellij.hibernate.model.xml.mapping;

import java.util.List;

import jakarta.annotation.Nonnull;

import com.intellij.hibernate.model.converters.MappingClassResolveConverter;
import com.intellij.javaee.model.xml.JavaeeDomModelElement;
import com.intellij.persistence.model.PersistentEmbeddable;
import com.intellij.java.language.psi.PsiClass;
import consulo.xml.dom.Convert;
import consulo.xml.dom.GenericAttributeValue;
import consulo.xml.dom.Required;
import consulo.xml.dom.SubTagList;

/**
 * hibernate-mapping-3.0.dtd:composite-map-key interface.
 * Type composite-map-key documentation
 * <pre>
 *  Composite index of a map ie. a map keyed on components. 
 * </pre>
 */
public interface HbmCompositeMapKey extends JavaeeDomModelElement, HbmPersistentObjectBase, PersistentEmbeddable {

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
