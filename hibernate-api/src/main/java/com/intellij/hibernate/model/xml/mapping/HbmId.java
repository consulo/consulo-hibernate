// Generated on Fri Nov 17 19:09:30 MSK 2006
// DTD/Schema  :    hibernate-mapping-3.0.dtd

package com.intellij.hibernate.model.xml.mapping;

import com.intellij.hibernate.model.converters.AttributeMemberConverter;
import com.intellij.hibernate.model.converters.IdUnsavedValueTypeConverter;
import com.intellij.hibernate.model.converters.PropertyTypeResolvingConverter;
import com.intellij.hibernate.model.enums.AccessType;
import com.intellij.hibernate.model.enums.IdUnsavedValueType;
import com.intellij.javaee.model.xml.JavaeeDomModelElement;
import com.intellij.java.language.psi.PsiMember;
import com.intellij.java.language.psi.PsiType;
import consulo.xml.dom.Attribute;
import consulo.xml.dom.Convert;
import consulo.xml.dom.GenericAttributeValue;
import consulo.xml.dom.Required;
import jakarta.annotation.Nonnull;

import java.util.List;

/**
 * hibernate-mapping-3.0.dtd:id interface.
 * Type id documentation
 * <pre>
 *  Declares the id type, column and generation algorithm for an entity class.
 * If a name attribut is given, the id is exposed to the application through the 
 * named property of the class. If not, the id is only exposed to the application 
 * via Session.getIdentifier() 
 * </pre>
 */
public interface HbmId extends JavaeeDomModelElement, HbmPropertyBase {

	/**
	 * Returns the value of the access child.
	 * Attribute access
	 * @return the value of the access child.
	 */
	@Nonnull
	GenericAttributeValue<AccessType> getAccess();


	/**
	 * Returns the value of the unsaved-value child.
	 * Attribute unsaved-value
	 * @return the value of the unsaved-value child.
	 */
	@Nonnull
        @Convert(IdUnsavedValueTypeConverter.class)
        GenericAttributeValue<IdUnsavedValueType> getUnsavedValue();


	/**
	 * Returns the value of the name child.
	 * Attribute name
	 * @return the value of the name child.
	 */
        @Required
        @Attribute("name")
        @Convert(AttributeMemberConverter.class)
        GenericAttributeValue<PsiMember> getTargetMember();


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
	 * Returns the value of the column child.
	 * Attribute column
	 * @return the value of the column child.
	 */
	@Nonnull
        GenericAttributeValue<String> getColumn();


	/**
	 * Returns the value of the length child.
	 * Attribute length
	 * @return the value of the length child.
	 */
	@Nonnull
	GenericAttributeValue<Integer> getLength();


	/**
	 * Returns the value of the node child.
	 * Attribute node
	 * @return the value of the node child.
	 */
	@Nonnull
	GenericAttributeValue<String> getNode();


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
	HbmGenerator getGenerator();


}
