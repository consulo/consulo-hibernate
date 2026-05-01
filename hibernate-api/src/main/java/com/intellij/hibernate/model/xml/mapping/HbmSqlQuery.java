// Generated on Fri Nov 17 19:09:30 MSK 2006
// DTD/Schema  :    hibernate-mapping-3.0.dtd

package com.intellij.hibernate.model.xml.mapping;

import com.intellij.javaee.model.xml.JavaeeDomModelElement;
import consulo.xml.dom.*;
import com.intellij.persistence.model.PersistenceQuery;
import jakarta.annotation.Nonnull;

import java.util.List;

/**
 * hibernate-mapping-3.0.dtd:sql-query interface.
 * Type sql-query documentation
 * <pre>
 *  The sql-query element declares a named SQL query string 
 * </pre>
 */
public interface HbmSqlQuery extends JavaeeDomModelElement, GenericDomValue<String>, PersistenceQuery {

	/**
	 * Returns the value of the simple content.
	 * @return the value of the simple content.
	 */
	@Nonnull
	String getValue();
	/**
	 * Sets the value of the simple content.
	 * @param value the new value to set
	 */
	void setValue(String value);


	/**
	 * Returns the value of the name child.
	 * Attribute name
	 * @return the value of the name child.
	 */
        @Nonnull
        @PrimaryKey
        @NameValue
	@Required
	GenericAttributeValue<String> getName();


	/**
	 * Returns the value of the cacheable child.
	 * Attribute cacheable
	 * @return the value of the cacheable child.
	 */
	@Nonnull
	GenericAttributeValue<Boolean> getCacheable();


	/**
	 * Returns the value of the resultset-ref child.
	 * Attribute resultset-ref
	 * @return the value of the resultset-ref child.
	 */
	@Nonnull
	GenericAttributeValue<String> getResultsetRef();


	/**
	 * Returns the value of the flush-mode child.
	 * Attribute flush-mode
	 * @return the value of the flush-mode child.
	 */
	@Nonnull
	GenericAttributeValue<String> getFlushMode();


	/**
	 * Returns the value of the callable child.
	 * Attribute callable
	 * @return the value of the callable child.
	 */
	@Nonnull
	GenericAttributeValue<Boolean> getCallable();


	/**
	 * Returns the value of the comment child.
	 * Attribute comment
	 * @return the value of the comment child.
	 */
	@Nonnull
	GenericAttributeValue<String> getComment();


	/**
	 * Returns the value of the fetch-size child.
	 * Attribute fetch-size
	 * @return the value of the fetch-size child.
	 */
	@Nonnull
	GenericAttributeValue<Integer> getFetchSize();


	/**
	 * Returns the value of the read-only child.
	 * Attribute read-only
	 * @return the value of the read-only child.
	 */
	@Nonnull
	GenericAttributeValue<Boolean> getReadOnly();


	/**
	 * Returns the value of the cache-mode child.
	 * Attribute cache-mode
	 * @return the value of the cache-mode child.
	 */
	@Nonnull
	GenericAttributeValue<String> getCacheMode();


	/**
	 * Returns the value of the timeout child.
	 * Attribute timeout
	 * @return the value of the timeout child.
	 */
	@Nonnull
	GenericAttributeValue<Integer> getTimeout();


	/**
	 * Returns the value of the cache-region child.
	 * Attribute cache-region
	 * @return the value of the cache-region child.
	 */
	@Nonnull
	GenericAttributeValue<String> getCacheRegion();


	/**
	 * Returns the list of return-scalar children.
	 * @return the list of return-scalar children.
	 */
	@Nonnull
	List<HbmReturnScalar> getReturnScalars();
	/**
	 * Adds new child to the list of return-scalar children.
	 * @return created child
	 */
        HbmReturnScalar addReturnScalar();


	/**
	 * Returns the list of return children.
	 * Type return documentation
	 * <pre>
	 * 	Defines a return component for a sql-query.  Alias refers to the alias
	 * 	used in the actual sql query; lock-mode specifies the locking to be applied
	 * 	when the query is executed.  The class, collection, and role attributes are mutually exclusive;
	 * 	class refers to the class name of a "root entity" in the object result; collection refers
	 * 	to a collection of a given class and is used to define custom sql to load that owned collection
	 * 	and takes the form "ClassName.propertyName"; role refers to the property path for an eager fetch
	 * 	and takes the form "owningAlias.propertyName"
	 * </pre>
	 * @return the list of return children.
	 */
	@Nonnull
	List<HbmReturn> getReturns();
	/**
	 * Adds new child to the list of return children.
	 * @return created child
	 */
        HbmReturn addReturn();


	/**
	 * Returns the list of return-join children.
	 * @return the list of return-join children.
	 */
	@Nonnull
	List<HbmReturnJoin> getReturnJoins();
	/**
	 * Adds new child to the list of return-join children.
	 * @return created child
	 */
        HbmReturnJoin addReturnJoin();


	/**
	 * Returns the list of load-collection children.
	 * @return the list of load-collection children.
	 */
	@Nonnull
        List<HbmLoadCollection> getLoadCollections();
	/**
	 * Adds new child to the list of load-collection children.
	 * @return created child
	 */
        HbmLoadCollection addLoadCollection();


	/**
	 * Returns the list of synchronize children.
	 * @return the list of synchronize children.
	 */
	@Nonnull
	List<HbmSynchronize> getSynchronizes();
	/**
	 * Adds new child to the list of synchronize children.
	 * @return created child
	 */
	HbmSynchronize addSynchronize();


	/**
	 * Returns the list of query-param children.
	 * Type query-param documentation
	 * <pre>
	 *  The query-param element is used only by tools that generate
	 * finder methods for named queries
	 * </pre>
	 * @return the list of query-param children.
	 */
	@Nonnull
	List<HbmQueryParam> getQueryParams();
	/**
	 * Adds new child to the list of query-param children.
	 * @return created child
	 */
        HbmQueryParam addQueryParam();
}
