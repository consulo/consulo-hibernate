// Generated on Fri Nov 17 19:09:30 MSK 2006
// DTD/Schema  :    hibernate-mapping-3.0.dtd

package com.intellij.hibernate.model.xml.mapping;

import java.util.List;

import jakarta.annotation.Nonnull;

import com.intellij.hibernate.model.converters.MappingClassResolveConverter;
import com.intellij.hibernate.model.converters.ParamValueConverter;
import com.intellij.javaee.model.xml.JavaeeDomModelElement;
import com.intellij.java.language.psi.PsiClass;
import consulo.xml.dom.Convert;
import consulo.xml.dom.GenericAttributeValue;
import consulo.xml.dom.NameValue;
import consulo.xml.dom.Required;

/**
 * hibernate-mapping-3.0.dtd:typedef interface.
 * Type typedef documentation
 * <pre>
 * 	TYPEDEF element definition; defines a new name for a Hibernate type. May
 * 	contain parameters for parameterizable types.
 * </pre>
 */
public interface HbmTypedef extends JavaeeDomModelElement
{

	/**
	 * Returns the value of the name child.
	 * Attribute name
	 * @return the value of the name child.
	 */
	@Nonnull
        @NameValue
        @Required
	GenericAttributeValue<String> getName();


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
	 * Returns the list of param children.
	 * @return the list of param children.
	 */
	@Nonnull
        @Convert(ParamValueConverter.class)
        List<HbmParam<Object>> getParams();
	/**
	 * Adds new child to the list of param children.
	 * @return created child
	 */
	HbmParam<Object> addParam();


}
