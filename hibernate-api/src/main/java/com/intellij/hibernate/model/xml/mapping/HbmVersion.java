// Generated on Fri Nov 17 19:09:30 MSK 2006
// DTD/Schema  :    hibernate-mapping-3.0.dtd

package com.intellij.hibernate.model.xml.mapping;

import java.util.List;

import jakarta.annotation.Nonnull;

import com.intellij.hibernate.model.converters.AttributeMemberConverter;
import com.intellij.hibernate.model.converters.PropertyTypeResolvingConverter;
import com.intellij.hibernate.model.enums.AccessType;
import com.intellij.hibernate.model.enums.GeneratedType;
import com.intellij.hibernate.model.enums.VersionUnsavedValueType;
import com.intellij.javaee.model.xml.JavaeeDomModelElement;
import com.intellij.java.language.psi.PsiMember;
import com.intellij.java.language.psi.PsiType;
import consulo.xml.dom.Attribute;
import consulo.xml.dom.Convert;
import consulo.xml.dom.GenericAttributeValue;
import consulo.xml.dom.Required;

/**
 * hibernate-mapping-3.0.dtd:version interface.
 * Type version documentation
 * <pre>
 *  Versioned data requires a column holding a version number. This is exposed to the
 * application through a property of the Java class. 
 * </pre>
 */
public interface HbmVersion extends JavaeeDomModelElement, HbmAttributeBase, HbmColumnsHolderBase, HbmTypeHolderBase {

	/**
	 * Returns the value of the access child.
	 * Attribute access
	 * @return the value of the access child.
	 */
	@Nonnull
	GenericAttributeValue<AccessType> getAccess();


	/**
	 * Returns the value of the generated child.
	 * Attribute generated
	 * @return the value of the generated child.
	 */
	@Nonnull
	GenericAttributeValue<GeneratedType> getGenerated();


	/**
	 * Returns the value of the unsaved-value child.
	 * Attribute unsaved-value
	 * @return the value of the unsaved-value child.
	 */
	@Nonnull
	GenericAttributeValue<VersionUnsavedValueType> getUnsavedValue();


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
        @consulo.xml.dom.Attribute("type")
        GenericAttributeValue<PsiType> getTypeAttr();


	/**
	 * Returns the value of the column child.
	 * Attribute column
	 * @return the value of the column child.
	 */
	@Nonnull
        GenericAttributeValue<String> getColumn();


	/**
	 * Returns the value of the node child.
	 * Attribute node
	 * @return the value of the node child.
	 */
	@Nonnull
	GenericAttributeValue<String> getNode();


	/**
	 * Returns the value of the insert child.
	 * Attribute insert
	 * @return the value of the insert child.
	 */
	@Nonnull
	GenericAttributeValue<Boolean> getInsert();


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


}
