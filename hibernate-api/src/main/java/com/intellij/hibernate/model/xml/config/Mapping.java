// Generated on Fri Nov 17 19:09:30 MSK 2006
// DTD/Schema  :    hibernate-configuration-3.0.dtd

package com.intellij.hibernate.model.xml.config;

import com.intellij.hibernate.model.converters.MappingClassResolveConverter;
import com.intellij.hibernate.model.converters.MappingFileConverter;
import com.intellij.hibernate.model.converters.MappingJarConverter;
import com.intellij.hibernate.model.converters.MappingResourceConverter;
import com.intellij.javaee.model.xml.JavaeeDomModelElement;
import com.intellij.javaee.model.xml.converters.PackageNameConverter;
import com.intellij.java.language.psi.PsiClass;
import consulo.language.psi.PsiFile;
import com.intellij.java.language.psi.PsiJavaPackage;
import consulo.xml.dom.Convert;
import consulo.xml.dom.GenericAttributeValue;
import jakarta.annotation.Nonnull;

/**
 * hibernate-configuration-3.0.dtd:mapping interface.
 */
public interface Mapping extends JavaeeDomModelElement {

	/**
	 * Returns the value of the package child.
	 * Attribute package
	 * @return the value of the package child.
	 */
	@Nonnull
        @Convert(PackageNameConverter.class)
        GenericAttributeValue<PsiJavaPackage> getPackage();


	/**
	 * Returns the value of the file child.
	 * Attribute file
	 * @return the value of the file child.
	 */
	@Nonnull
        @Convert(MappingFileConverter.class)
        GenericAttributeValue<PsiFile> getFile();


	/**
	 * Returns the value of the class child.
	 * Attribute class
	 * @return the value of the class child.
	 */
	@Nonnull
	@consulo.xml.dom.Attribute ("class")
        @Convert(MappingClassResolveConverter.class)
        GenericAttributeValue<PsiClass> getClazz();


	/**
	 * Returns the value of the jar child.
	 * Attribute jar
	 * @return the value of the jar child.
	 */
	@Nonnull
        @Convert(MappingJarConverter.class)
        GenericAttributeValue<PsiFile> getJar();


	/**
	 * Returns the value of the resource child.
	 * Attribute resource
	 * @return the value of the resource child.
	 */
	@Nonnull
        @Convert(MappingResourceConverter.class)  
        GenericAttributeValue<PsiFile> getResource();


}
