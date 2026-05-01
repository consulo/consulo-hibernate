// Generated on Fri Nov 17 19:09:30 MSK 2006
// DTD/Schema  :    hibernate-mapping-3.0.dtd

package com.intellij.hibernate.model.xml.mapping;

import com.intellij.javaee.model.xml.JavaeeDomModelElement;
import com.intellij.java.language.psi.PsiClass;
import consulo.xml.dom.GenericAttributeValue;
import consulo.xml.dom.Required;
import consulo.xml.dom.Convert;
import com.intellij.hibernate.model.converters.MappingClassResolveConverter;
import com.intellij.hibernate.model.converters.ParamValueConverter;
import jakarta.annotation.Nonnull;

import java.util.List;

/**
 * hibernate-mapping-3.0.dtd:type interface.
 * Type type documentation
 * <pre>
 *  Declares the type of the containing property (overrides an eventually existing type
 * attribute of the property). May contain param elements to customize a ParametrizableType. 
 * </pre>
 */
public interface HbmType extends JavaeeDomModelElement {

	/**
	 * Returns the value of the name child.
	 * Attribute name
	 * @return the value of the name child.
	 */
	@Nonnull
	@Required
        @Convert(MappingClassResolveConverter.class)
        GenericAttributeValue<PsiClass> getName();


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
