// Generated on Fri Nov 17 19:09:30 MSK 2006
// DTD/Schema  :    hibernate-mapping-3.0.dtd

package com.intellij.hibernate.model.xml.mapping;

import com.intellij.hibernate.model.converters.CascadeTypeListConverter;
import com.intellij.hibernate.model.converters.LazyTypeConverter;
import com.intellij.hibernate.model.enums.*;
import com.intellij.javaee.model.xml.JavaeeDomModelElement;
import com.intellij.javaee.model.xml.converters.AttributeConverter;
import com.intellij.persistence.model.PersistentAttribute;
import com.intellij.persistence.model.PersistentEntity;
import com.intellij.java.language.psi.PsiClass;
import consulo.xml.dom.Convert;
import consulo.xml.dom.GenericAttributeValue;
import consulo.xml.dom.GenericDomValue;
import consulo.xml.dom.SubTag;
import jakarta.annotation.Nonnull;

import java.util.List;

/**
 * hibernate-mapping-3.0.dtd:many-to-one interface.
 * Type many-to-one documentation
 * <pre>
 *  Declares an association between two entities (Or from a component, component element,
 * etc. to an entity). 
 * </pre>
 */
public interface HbmManyToOne extends JavaeeDomModelElement, HbmManyToOneBase {

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
	 * Returns the value of the cascade child.
	 * Attribute cascade
	 * @return the value of the cascade child.
	 */
	@Nonnull
        @Convert(CascadeTypeListConverter.class)
        GenericAttributeValue<List<CascadeType>> getCascade();


	/**
	 * Returns the value of the foreign-key child.
	 * Attribute foreign-key
	 * @return the value of the foreign-key child.
	 */
	@Nonnull
	GenericAttributeValue<String> getForeignKey();


	/**
	 * Returns the value of the access child.
	 * Attribute access
	 * @return the value of the access child.
	 */
	@Nonnull
	GenericAttributeValue<AccessType> getAccess();


	/**
	 * Returns the value of the property-ref child.
	 * Attribute property-ref
	 * @return the value of the property-ref child.
	 */
	@Nonnull
        @Convert(AttributeConverter.class)
        GenericAttributeValue<PersistentAttribute> getPropertyRef();


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
	 * Returns the value of the entity-name child.
	 * Attribute entity-name
	 * @return the value of the entity-name child.
	 */
	@Nonnull
        @SubTag("entity-name")
        GenericAttributeValue<PersistentEntity> getEntityNameValue();


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
	 * Returns the value of the outer-join child.
	 * Attribute outer-join
	 * @return the value of the outer-join child.
	 */
	@Nonnull
	GenericAttributeValue<String> getOuterJoin();


	/**
	 * Returns the value of the class child.
	 * Attribute class
	 * @return the value of the class child.
	 */
	@Nonnull
	@consulo.xml.dom.Attribute ("class")
	GenericAttributeValue<PsiClass> getClazz();


	/**
	 * Returns the value of the not-found child.
	 * Attribute not-found
	 * @return the value of the not-found child.
	 */
	@Nonnull
	GenericAttributeValue<NotFoundType> getNotFound();


	/**
	 * Returns the value of the embed-xml child.
	 * Attribute embed-xml
	 * @return the value of the embed-xml child.
	 */
	@Nonnull
	GenericAttributeValue<Boolean> getEmbedXml();


	/**
	 * Returns the value of the fetch child.
	 * Attribute fetch
	 * @return the value of the fetch child.
	 */
	@Nonnull
	GenericAttributeValue<FetchType> getFetch();


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
	 * Returns the value of the not-null child.
	 * Attribute not-null
	 * @return the value of the not-null child.
	 */
	@Nonnull
	GenericAttributeValue<Boolean> getNotNull();


	/**
	 * Returns the value of the name child.
	 * Attribute name
	 * @return the value of the name child.
	 */
	//@Nonnull
	//@Required
	//GenericAttributeValue<String> getName();


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
