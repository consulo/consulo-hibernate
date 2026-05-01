package com.intellij.hibernate.impl.model.xml.impl;

/**
 * Replacement for JAM's BaseImpl.
 * JAM's BaseImpl extends a class that uses the removed consulo.xml.util.xml.DomElement API.
 * This empty abstract class is used as the superclass for DOM impl classes instead.
 * The Consulo DOM framework proxy provides implementations for all DomElement interface
 * methods at runtime through the impl class's interface hierarchy.
 */
public abstract class HibernateBaseImpl {
}
