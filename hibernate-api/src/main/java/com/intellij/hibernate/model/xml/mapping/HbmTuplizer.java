// Generated on Fri Nov 17 19:09:30 MSK 2006
// DTD/Schema  :    hibernate-mapping-3.0.dtd

package com.intellij.hibernate.model.xml.mapping;

import com.intellij.hibernate.model.converters.MappingClassResolveConverter;
import com.intellij.javaee.model.xml.JavaeeDomModelElement;
import com.intellij.java.language.psi.PsiClass;
import consulo.xml.dom.Convert;
import consulo.xml.dom.GenericAttributeValue;
import consulo.xml.dom.Required;
import jakarta.annotation.Nonnull;

/**
 * hibernate-mapping-3.0.dtd:tuplizer interface.
 * Type tuplizer documentation
 * <pre>
 *     TUPLIZER element; defines tuplizer to use for a component/entity for a given entity-mode
 * </pre>
 */
public interface HbmTuplizer extends JavaeeDomModelElement {

	/**
	 * Returns the value of the entity-mode child.
	 * Attribute entity-mode
	 * @return the value of the entity-mode child.
	 */
	@Nonnull
	GenericAttributeValue<String> getEntityMode();


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


}
