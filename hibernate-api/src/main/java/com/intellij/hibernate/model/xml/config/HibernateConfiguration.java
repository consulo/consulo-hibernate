// Generated on Fri Nov 17 19:09:30 MSK 2006
// DTD/Schema  :    hibernate-configuration-3.0.dtd

package com.intellij.hibernate.model.xml.config;

import com.intellij.javaee.model.xml.CommonDomModelRootElement;
import consulo.xml.dom.Required;
import jakarta.annotation.Nonnull;

/**
 * hibernate-configuration-3.0.dtd:hibernate-configuration interface.
 * Type hibernate-configuration documentation
 * <pre>
 *  Hibernate file-based configuration document.
 * <!DOCTYPE hibernate-configuration PUBLIC
 * 	"-//Hibernate/Hibernate Configuration DTD 3.0//EN"
 * 	"http://hibernate.sourceforge.net/hibernate-configuration-3.0.dtd">
 * An instance of this document contains property settings and references
 * to mapping files for a number of SessionFactory instances to be listed
 * in JNDI.
 * </pre>
 */
public interface HibernateConfiguration extends CommonDomModelRootElement {

	/**
	 * Returns the value of the session-factory child.
	 * @return the value of the session-factory child.
	 */
	@Nonnull
	@Required
	SessionFactory getSessionFactory();


	/**
	 * Returns the value of the security child.
	 * Type security documentation
	 * <pre>
	 *  the JNDI name 
	 * </pre>
	 * @return the value of the security child.
	 */
	@Nonnull
	Security getSecurity();


}
