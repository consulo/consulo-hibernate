// Generated on Fri Nov 17 19:09:30 MSK 2006
// DTD/Schema  :    hibernate-mapping-3.0.dtd

package com.intellij.hibernate.model.xml.mapping;

import com.intellij.javaee.model.xml.JavaeeDomModelElement;
import com.intellij.java.language.psi.PsiClass;
import consulo.xml.dom.GenericAttributeValue;
import consulo.xml.dom.Required;
import consulo.xml.dom.Convert;
import com.intellij.hibernate.model.converters.MappingClassResolveConverter;
import jakarta.annotation.Nonnull;

/**
 * hibernate-mapping-3.0.dtd:meta-value interface.
 */
public interface HbmMetaValue extends JavaeeDomModelElement {

	/**
	 * Returns the value of the class child.
	 * Attribute class
	 * @return the value of the class child.
	 */
	@Nonnull
	@consulo.xml.dom.Attribute ("class")
	@Required
        @Convert(MappingClassResolveConverter.class)
        GenericAttributeValue<PsiClass> getClazz();


	/**
	 * Returns the value of the value child.
	 * Attribute value
	 * @return the value of the value child.
	 */
	@Nonnull
	@Required
	GenericAttributeValue<String> getValue();


}
