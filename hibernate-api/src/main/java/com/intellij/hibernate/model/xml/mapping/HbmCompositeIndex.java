// Generated on Fri Nov 17 19:09:30 MSK 2006
// DTD/Schema  :    hibernate-mapping-3.0.dtd

package com.intellij.hibernate.model.xml.mapping;

import com.intellij.javaee.model.xml.JavaeeDomModelElement;
import com.intellij.java.language.psi.PsiClass;
import consulo.xml.dom.GenericAttributeValue;
import consulo.xml.dom.Required;
import consulo.xml.dom.Convert;
import com.intellij.hibernate.model.converters.MappingClassResolveConverter;
import jakarta.annotation.Nonnull;

import java.util.List;

/**
 * hibernate-mapping-3.0.dtd:composite-index interface.
 */
public interface HbmCompositeIndex extends JavaeeDomModelElement {

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
	@Nonnull
	List<HbmKeyProperty> getKeyProperties();
	/**
	 * Adds new child to the list of key-property children.
	 * @return created child
	 */
	HbmKeyProperty addKeyProperty();


	/**
	 * Returns the list of key-many-to-one children.
	 * Type key-many-to-one documentation
	 * <pre>
	 *  A many-to-one association embedded in a composite identifier or map index 
	 * (always not-null, never cascade). 
	 * </pre>
	 * @return the list of key-many-to-one children.
	 */
	@Nonnull
	List<HbmKeyManyToOne> getKeyManyToOnes();
	/**
	 * Adds new child to the list of key-many-to-one children.
	 * @return created child
	 */
	HbmKeyManyToOne addKeyManyToOne();


}
