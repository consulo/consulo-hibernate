package com.intellij.hibernate.impl;

import com.intellij.hibernate.HibernateConvertersRegistry;
import com.intellij.hibernate.HibernateManager;
import consulo.annotation.component.ServiceImpl;
import consulo.xml.dom.ConverterManager;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

/**
 * @author Gregory.Shrago
 */
@Singleton
@ServiceImpl
public class HibernateManagerImpl extends HibernateManager {
  private final HibernateConvertersRegistry myConvertersRegistry;

  @Inject
  public HibernateManagerImpl(ConverterManager converterManager) {
    myConvertersRegistry = new HibernateConvertersRegistry(converterManager);
  }

  public HibernateConvertersRegistry getConvertersRegistry() {
    return myConvertersRegistry;
  }
}
