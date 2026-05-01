// Generated on Fri Nov 17 19:09:30 MSK 2006
// DTD/Schema  :    hibernate-mapping-3.0.dtd

package com.intellij.hibernate.model.xml.mapping;

import jakarta.annotation.Nonnull;

import com.intellij.hibernate.model.converters.ParamNameConverter;
import com.intellij.javaee.model.xml.JavaeeDomModelElement;
import consulo.xml.dom.Convert;
import consulo.xml.dom.GenericAttributeValue;
import consulo.xml.dom.GenericDomValue;
import consulo.xml.dom.NameValue;
import consulo.xml.dom.Required;

/**
 * hibernate-mapping-3.0.dtd:param interface.
 */
public interface HbmParam<T> extends JavaeeDomModelElement, GenericDomValue<T> {

	/**
	 * Returns the value of the simple content.
	 * @return the value of the simple content.
	 */
	@Nonnull
	T getValue();
	/**
	 * Sets the value of the simple content.
	 * @param value the new value to set
	 */
	void setValue(T value);


	/**
	 * Returns the value of the name child.
	 * Attribute name
	 * @return the value of the name child.
	 */
        @NameValue
        @Nonnull
	@Required
        @Convert(ParamNameConverter.class)
        GenericAttributeValue<String> getName();


}
