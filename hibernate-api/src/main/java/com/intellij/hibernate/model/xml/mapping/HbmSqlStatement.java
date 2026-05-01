package com.intellij.hibernate.model.xml.mapping;

import jakarta.annotation.Nonnull;

import consulo.xml.dom.GenericAttributeValue;
import com.intellij.javaee.model.xml.JavaeeDomModelElement;

/**
 * hibernate-mapping-3.0.dtd:sql-delete interface.
 */
public interface HbmSqlStatement extends JavaeeDomModelElement {

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
	 * Returns the value of the callable child.
	 * Attribute callable
	 * @return the value of the callable child.
	 */
	@Nonnull
        GenericAttributeValue<Boolean> getCallable();


	/**
	 * Returns the value of the check child.
	 * Attribute check
	 * @return the value of the check child.
	 */
	@Nonnull
	GenericAttributeValue<Boolean> getCheck();


}
