// Generated on Fri Nov 17 19:09:30 MSK 2006
// DTD/Schema  :    hibernate-mapping-3.0.dtd

package com.intellij.hibernate.model.xml.mapping;

import jakarta.annotation.Nonnull;

import com.intellij.hibernate.model.enums.NotFoundType;
import com.intellij.javaee.model.xml.JavaeeDomModelElement;
import com.intellij.java.language.psi.PsiClass;
import consulo.xml.dom.GenericAttributeValue;
import consulo.xml.dom.SubTag;
import com.intellij.persistence.model.PersistentEntity;

/**
 * hibernate-mapping-3.0.dtd:one-to-many interface.
 * Type one-to-many documentation
 * <pre>
 *  One to many association. This tag declares the entity-class
 * element type of a collection and specifies a one-to-many relational model 
 * </pre>
 */
public interface HbmOneToMany extends JavaeeDomModelElement, HbmRelationAttributeBase.AnyToManyBase {

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
	GenericAttributeValue<PsiClass> getClazz();


	/**
	 * Returns the value of the entity-name child.
	 * Attribute entity-name
	 * @return the value of the entity-name child.
	 */
	@Nonnull
        @SubTag("entity-name")
        GenericAttributeValue<PersistentEntity> getEntityNameValue();


	/**
	 * Returns the value of the embed-xml child.
	 * Attribute embed-xml
	 * @return the value of the embed-xml child.
	 */
	@Nonnull
	GenericAttributeValue<Boolean> getEmbedXml();


	/**
	 * Returns the value of the not-found child.
	 * Attribute not-found
	 * @return the value of the not-found child.
	 */
	@Nonnull
	GenericAttributeValue<NotFoundType> getNotFound();


}
