// Generated on Fri Nov 17 19:09:30 MSK 2006
// DTD/Schema  :    hibernate-configuration-3.0.dtd

package com.intellij.hibernate.model.xml.config;

import java.util.List;

import jakarta.annotation.Nonnull;

import com.intellij.hibernate.model.converters.ParamValueConverter;
import com.intellij.hibernate.model.converters.SessionFactoryNameConverter;
import com.intellij.javaee.model.xml.JavaeeDomModelElement;
import com.intellij.persistence.model.PersistencePackage;
import consulo.xml.dom.Convert;
import consulo.xml.dom.GenericAttributeValue;
import consulo.xml.dom.NameValue;

/**
 * hibernate-configuration-3.0.dtd:session-factory interface.
 */
public interface SessionFactory extends JavaeeDomModelElement, PersistencePackage {

	/**
	 * Returns the value of the name child.
	 * Attribute name
	 * @return the value of the name child.
	 */
	@Nonnull
        @NameValue
        @Convert(SessionFactoryNameConverter.class)  
        GenericAttributeValue<String> getName();


	/**
	 * Returns the list of property children.
	 * @return the list of property children.
	 */
	@Nonnull
        @Convert(ParamValueConverter.class)
        List<Property<Object>> getProperties();
	/**
	 * Adds new child to the list of property children.
	 * @return created child
	 */
	Property<Object> addProperty();


	/**
	 * Returns the list of mapping children.
	 * @return the list of mapping children.
	 */
	@Nonnull
	List<Mapping> getMappings();
	/**
	 * Adds new child to the list of mapping children.
	 * @return created child
	 */
	Mapping addMapping();


	/**
	 * Returns the list of event children.
	 * @return the list of event children.
	 */
	@Nonnull
	List<Event> getEvents();
	/**
	 * Adds new child to the list of event children.
	 * @return created child
	 */
	Event addEvent();


	/**
	 * Returns the list of listener children.
	 * @return the list of listener children.
	 */
	@Nonnull
	List<Listener> getListeners();
	/**
	 * Adds new child to the list of listener children.
	 * @return created child
	 */
	Listener addListener();


	/**
	 * Returns the list of class-cache children.
	 * @return the list of class-cache children.
	 */
	@Nonnull
	List<ClassCache> getClassCaches();
	/**
	 * Adds new child to the list of class-cache children.
	 * @return created child
	 */
	ClassCache addClassCache();


	/**
	 * Returns the list of collection-cache children.
	 * @return the list of collection-cache children.
	 */
	@Nonnull
	List<CollectionCache> getCollectionCaches();
	/**
	 * Adds new child to the list of collection-cache children.
	 * @return created child
	 */
	CollectionCache addCollectionCache();


}
