// Generated on Fri Nov 17 19:09:30 MSK 2006
// DTD/Schema  :    hibernate-mapping-3.0.dtd

package com.intellij.hibernate.model.xml.mapping;

import java.util.List;

import jakarta.annotation.Nonnull;

import com.intellij.hibernate.model.converters.GeneratorClassResolvingConverter;
import com.intellij.hibernate.model.converters.ParamValueConverter;
import com.intellij.javaee.model.xml.JavaeeDomModelElement;
import com.intellij.persistence.model.TableInfoProvider;
import com.intellij.java.language.psi.PsiClass;
import consulo.xml.dom.Convert;
import consulo.xml.dom.GenericAttributeValue;
import consulo.xml.dom.Required;

/**
 * hibernate-mapping-3.0.dtd:generator interface.
 * Type generator documentation
 * <pre>
 *  Generators generate unique identifiers. The class attribute specifies a Java 
 * class implementing an id generation algorithm. 
 * </pre>
 */
public interface HbmGenerator extends JavaeeDomModelElement, TableInfoProvider {

	/**
	 * Returns the value of the class child.
	 * Attribute class
	 * @return the value of the class child.
	 */
	@Nonnull
	@consulo.xml.dom.Attribute ("class")
	@Required
        @Convert(GeneratorClassResolvingConverter.class)
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
