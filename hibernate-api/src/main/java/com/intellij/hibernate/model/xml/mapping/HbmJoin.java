// Generated on Fri Nov 17 19:09:30 MSK 2006
// DTD/Schema  :    hibernate-mapping-3.0.dtd

package com.intellij.hibernate.model.xml.mapping;

import com.intellij.hibernate.model.enums.FetchType;
import com.intellij.javaee.model.xml.JavaeeDomModelElement;
import consulo.xml.dom.GenericAttributeValue;
import consulo.xml.dom.GenericDomValue;
import consulo.xml.dom.Required;
import consulo.xml.dom.Attribute;
import jakarta.annotation.Nonnull;

import java.util.List;

/**
 * hibernate-mapping-3.0.dtd:join interface.
 * Type join documentation
 * <pre>
 *  A join allows some properties of a class to be persisted to a second table 
 * </pre>
 */
public interface HbmJoin extends JavaeeDomModelElement, HbmAttributeContainerBase, HbmTableInfoProvider {

	/**
	 * Returns the value of the inverse child.
	 * Attribute inverse
	 * @return the value of the inverse child.
	 */
	@Nonnull
	GenericAttributeValue<Boolean> getInverse();


	/**
	 * Returns the value of the subselect child.
	 * Attribute subselect
	 * @return the value of the subselect child.
	 */
	@Nonnull
	@consulo.xml.dom.Attribute ("subselect")
	GenericAttributeValue<String> getSubselectAttr();


	/**
	 * Returns the value of the catalog child.
	 * Attribute catalog
	 * @return the value of the catalog child.
	 */
	@Nonnull
	GenericAttributeValue<String> getCatalog();


	/**
	 * Returns the value of the optional child.
	 * Attribute optional
	 * @return the value of the optional child.
	 */
	@Nonnull
	GenericAttributeValue<Boolean> getOptional();


	/**
	 * Returns the value of the fetch child.
	 * Attribute fetch
	 * @return the value of the fetch child.
	 */
	@Nonnull
	GenericAttributeValue<FetchType> getFetch();


	/**
	 * Returns the value of the schema child.
	 * Attribute schema
	 * @return the value of the schema child.
	 */
	@Nonnull
	GenericAttributeValue<String> getSchema();


	/**
	 * Returns the value of the table child.
	 * Attribute table
	 * @return the value of the table child.
	 */
	@Nonnull
	@Required
        @Attribute("table")
        GenericAttributeValue<String> getTableName();


	/**
	 * Returns the value of the subselect child.
	 * @return the value of the subselect child.
	 */
	@Nonnull
	GenericDomValue<String> getSubselect();


	/**
	 * Returns the value of the comment child.
	 * Type comment documentation
	 * <pre>
	 *  The comment element allows definition of a database table or column comment. 
	 * </pre>
	 * @return the value of the comment child.
	 */
	@Nonnull
	GenericDomValue<String> getComment();


	/**
	 * Returns the value of the key child.
	 * Type key documentation
	 * <pre>
	 *  Declares the column name of a foreign key. 
	 * </pre>
	 * @return the value of the key child.
	 */
	@Nonnull
	@Required
	HbmKey getKey();


	/**
	 * Returns the value of the sql-insert child.
	 * Type sql-insert documentation
	 * <pre>
	 *  custom sql operations 
	 * </pre>
	 * @return the value of the sql-insert child.
	 */
	@Nonnull
	HbmSqlStatement getSqlInsert();


	/**
	 * Returns the value of the sql-update child.
	 * @return the value of the sql-update child.
	 */
	@Nonnull
	HbmSqlStatement getSqlUpdate();


	/**
	 * Returns the value of the sql-delete child.
	 * @return the value of the sql-delete child.
	 */
	@Nonnull
	HbmSqlStatement getSqlDelete();


	/**
	 * Returns the list of property children.
	 * Type property documentation
	 * <pre>
	 *  Property of an entity class or component, component-element, composite-id, etc. 
	 * JavaBeans style properties are mapped to table columns. 
	 * </pre>
	 * @return the list of property children.
	 */
	@Nonnull
	List<HbmProperty> getProperties();
	/**
	 * Adds new child to the list of property children.
	 * @return created child
	 */
	HbmProperty addProperty();


	/**
	 * Returns the list of many-to-one children.
	 * Type many-to-one documentation
	 * <pre>
	 *  Declares an association between two entities (Or from a component, component element,
	 * etc. to an entity). 
	 * </pre>
	 * @return the list of many-to-one children.
	 */
	@Nonnull
	List<HbmManyToOne> getManyToOnes();
	/**
	 * Adds new child to the list of many-to-one children.
	 * @return created child
	 */
	HbmManyToOne addManyToOne();


	/**
	 * Returns the list of component children.
	 * Type component documentation
	 * <pre>
	 *  A component is a user-defined class, persisted along with its containing entity
	 * to the table of the entity class. JavaBeans style properties of the component are
	 * mapped to columns of the table of the containing entity. A null component reference
	 * is mapped to null values in all columns and vice versa. Components do not support
	 * shared reference semantics. 
	 * </pre>
	 * @return the list of component children.
	 */
	@Nonnull
	List<HbmComponent> getComponents();
	/**
	 * Adds new child to the list of component children.
	 * @return created child
	 */
	HbmComponent addComponent();


	/**
	 * Returns the list of dynamic-component children.
	 * Type dynamic-component documentation
	 * <pre>
	 *  A dynamic-component maps columns of the database entity to a java.util.Map 
	 * at the Java level 
	 * </pre>
	 * @return the list of dynamic-component children.
	 */
	@Nonnull
	List<HbmDynamicComponent> getDynamicComponents();
	/**
	 * Adds new child to the list of dynamic-component children.
	 * @return created child
	 */
	HbmDynamicComponent addDynamicComponent();


	/**
	 * Returns the list of any children.
	 * Type any documentation
	 * <pre>
	 *  An "any" association is a polymorphic association to any table with
	 * the given identifier type. The first listed column is a VARCHAR column 
	 * holding the name of the class (for that row). 
	 * </pre>
	 * @return the list of any children.
	 */
	@Nonnull
	List<HbmAny> getAnies();
	/**
	 * Adds new child to the list of any children.
	 * @return created child
	 */
	HbmAny addAny();


}
