// Generated on Fri Nov 17 19:09:30 MSK 2006
// DTD/Schema  :    hibernate-mapping-3.0.dtd

package com.intellij.hibernate.model.xml.mapping;

import com.intellij.hibernate.model.converters.AttributeMemberConverter;
import com.intellij.hibernate.model.converters.LazyTypeConverter;
import com.intellij.hibernate.model.converters.PropertyTypeResolvingConverter;
import com.intellij.hibernate.model.enums.AccessType;
import com.intellij.hibernate.model.enums.LazyType;
import com.intellij.hibernate.model.enums.PropertyGeneratedType;
import com.intellij.javaee.model.xml.JavaeeDomModelElement;
import com.intellij.java.language.psi.PsiMember;
import com.intellij.java.language.psi.PsiType;
import consulo.xml.dom.*;
import jakarta.annotation.Nonnull;

import java.util.List;

/**
 * hibernate-mapping-3.0.dtd:property interface.
 * Type property documentation
 * <pre>
 *  Property of an entity class or component, component-element, composite-id, etc. 
 * JavaBeans style properties are mapped to table columns. 
 * </pre>
 */
public interface HbmProperty extends JavaeeDomModelElement, HbmPropertyBase {

	/**
	 * Returns the value of the formula child.
	 * Attribute formula
	 * @return the value of the formula child.
	 */
	@Nonnull
	GenericAttributeValue<String> getFormula();


	/**
	 * Returns the value of the insert child.
	 * Attribute insert
	 * @return the value of the insert child.
	 */
	@Nonnull
	GenericAttributeValue<Boolean> getInsert();


	/**
	 * Returns the value of the unique child.
	 * Attribute unique
	 * @return the value of the unique child.
	 */
	@Nonnull
	GenericAttributeValue<Boolean> getUnique();


	/**
	 * Returns the value of the column child.
	 * Attribute column
	 * @return the value of the column child.
	 */
	@Nonnull
        GenericAttributeValue<String> getColumn();


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
	 * Returns the value of the length child.
	 * Attribute length
	 * @return the value of the length child.
	 */
	@Nonnull
	GenericAttributeValue<Integer> getLength();


	/**
	 * Returns the value of the access child.
	 * Attribute access
	 * @return the value of the access child.
	 */
	@Nonnull
	GenericAttributeValue<AccessType> getAccess();


	/**
	 * Returns the value of the unique-key child.
	 * Attribute unique-key
	 * @return the value of the unique-key child.
	 */
	@Nonnull
	GenericAttributeValue<String> getUniqueKey();


	/**
	 * Returns the value of the index child.
	 * Attribute index
	 * @return the value of the index child.
	 */
	@Nonnull
	GenericAttributeValue<String> getIndex();


	/**
	 * Returns the value of the update child.
	 * Attribute update
	 * @return the value of the update child.
	 */
	@Nonnull
	GenericAttributeValue<Boolean> getUpdate();


	/**
	 * Returns the value of the optimistic-lock child.
	 * Attribute optimistic-lock
	 * @return the value of the optimistic-lock child.
	 */
	@Nonnull
	GenericAttributeValue<Boolean> getOptimisticLock();


	/**
	 * Returns the value of the node child.
	 * Attribute node
	 * @return the value of the node child.
	 */
	@Nonnull
	GenericAttributeValue<String> getNode();


	/**
	 * Returns the value of the lazy child.
	 * Attribute lazy
	 * @return the value of the lazy child.
	 */
	@Nonnull
        @Convert(LazyTypeConverter.class)
        GenericAttributeValue<LazyType> getLazy();


	/**
	 * Returns the value of the precision child.
	 * Attribute precision
	 * @return the value of the precision child.
	 */
	@Nonnull
	GenericAttributeValue<Integer> getPrecision();


	/**
	 * Returns the value of the not-null child.
	 * Attribute not-null
	 * @return the value of the not-null child.
	 */
	@Nonnull
	GenericAttributeValue<Boolean> getNotNull();


	/**
	 * Returns the value of the generated child.
	 * Attribute generated
	 * @return the value of the generated child.
	 */
	@Nonnull
	GenericAttributeValue<PropertyGeneratedType> getGenerated();


	/**
	 * Returns the value of the scale child.
	 * Attribute scale
	 * @return the value of the scale child.
	 */
	@Nonnull
	GenericAttributeValue<Integer> getScale();


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
	 * Returns the list of formula children.
	 * Type formula documentation
	 * <pre>
	 *  The formula and subselect elements allow us to map derived properties and 
	 * entities. 
	 * </pre>
	 * @return the list of formula children.
	 */
	@Nonnull
	List<GenericDomValue<String>> getFormulas();
	/**
	 * Adds new child to the list of formula children.
	 * @return created child
	 */
	GenericDomValue<String> addFormula();


}
