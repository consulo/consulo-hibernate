// Generated on Fri Nov 17 19:09:30 MSK 2006
// DTD/Schema  :    hibernate-mapping-3.0.dtd

package com.intellij.hibernate.model.xml.mapping;

import consulo.xml.dom.*;
import com.intellij.hibernate.model.converters.CascadeTypeListConverter;
import com.intellij.hibernate.model.converters.LazyTypeConverter;
import com.intellij.hibernate.model.enums.*;
import com.intellij.java.language.psi.PsiClass;
import com.intellij.javaee.model.xml.JavaeeDomModelElement;
import com.intellij.javaee.model.xml.converters.AttributeConverter;
import com.intellij.persistence.model.PersistentEntity;
import com.intellij.persistence.model.PersistentAttribute;
import jakarta.annotation.Nonnull;

import java.util.List;

/**
 * hibernate-mapping-3.0.dtd:one-to-one interface.
 * Type one-to-one documentation
 * <pre>
 *  Declares a one-to-one association between two entities (Or from a component, 
 * component element, etc. to an entity). 
 * </pre>
 */
public interface HbmOneToOne extends JavaeeDomModelElement, HbmRelationAttributeBase.AnyToOneBase {

	/**
	 * Returns the value of the fetch child.
	 * Attribute fetch
	 * @return the value of the fetch child.
	 */
	@Nonnull
	GenericAttributeValue<FetchType> getFetch();


	/**
	 * Returns the value of the name child.
	 * Attribute name
	 * @return the value of the name child.
	 */
	//@Nonnull
	//@Required
	//GenericAttributeValue<String> getName();


	/**
	 * Returns the value of the lazy child.
	 * Attribute lazy
	 * @return the value of the lazy child.
	 */
	@Nonnull
        @Convert(LazyTypeConverter.class)
        GenericAttributeValue<LazyType> getLazy();


	/**
	 * Returns the value of the constrained child.
	 * Attribute constrained
	 * @return the value of the constrained child.
	 */
	@Nonnull
	GenericAttributeValue<Boolean> getConstrained();


	/**
	 * Returns the value of the node child.
	 * Attribute node
	 * @return the value of the node child.
	 */
	@Nonnull
	GenericAttributeValue<String> getNode();


	/**
	 * Returns the value of the foreign-key child.
	 * Attribute foreign-key
	 * @return the value of the foreign-key child.
	 */
	@Nonnull
	GenericAttributeValue<String> getForeignKey();


	/**
	 * Returns the value of the entity-name child.
	 * Attribute entity-name
	 * @return the value of the entity-name child.
	 */
	@Nonnull
        @SubTag("entity-name")
        GenericAttributeValue<PersistentEntity> getEntityNameValue();


	/**
	 * Returns the value of the class child.
	 * Attribute class
	 * @return the value of the class child.
	 */
	@Nonnull
	@consulo.xml.dom.Attribute ("class")
	GenericAttributeValue<PsiClass> getClazz();


	/**
	 * Returns the value of the access child.
	 * Attribute access
	 * @return the value of the access child.
	 */
	@Nonnull
	GenericAttributeValue<AccessType> getAccess();


	/**
	 * Returns the value of the embed-xml child.
	 * Attribute embed-xml
	 * @return the value of the embed-xml child.
	 */
	@Nonnull
	GenericAttributeValue<Boolean> getEmbedXml();


	/**
	 * Returns the value of the outer-join child.
	 * Attribute outer-join
	 * @return the value of the outer-join child.
	 */
	@Nonnull
	GenericAttributeValue<String> getOuterJoin();


	/**
	 * Returns the value of the property-ref child.
	 * Attribute property-ref
	 * @return the value of the property-ref child.
	 */
	@Nonnull
        @Convert(AttributeConverter.class)
        GenericAttributeValue<PersistentAttribute> getPropertyRef();


	/**
	 * Returns the value of the cascade child.
	 * Attribute cascade
	 * @return the value of the cascade child.
	 */
	@Nonnull
        @Convert(CascadeTypeListConverter.class)
        GenericAttributeValue<List<CascadeType>> getCascade();


	/**
	 * Returns the value of the formula child.
	 * Attribute formula
	 * @return the value of the formula child.
	 */
	@Nonnull
	GenericAttributeValue<String> getFormula();


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
