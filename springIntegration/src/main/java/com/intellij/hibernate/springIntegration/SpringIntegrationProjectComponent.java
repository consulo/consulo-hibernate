package com.intellij.hibernate.springIntegration;

import com.intellij.hibernate.springIntegration.converters.HibernatePropertyNameConverter;
import com.intellij.hibernate.springIntegration.converters.HibernatePropertyValueConverter;
import com.intellij.java.impl.util.xml.converters.values.GenericDomValueConvertersRegistry;
import com.intellij.spring.impl.ide.SpringManager;
import com.intellij.spring.impl.ide.model.converters.CustomConverterRegistry;
import com.intellij.spring.impl.ide.model.converters.PropertyKeyConverter;
import consulo.annotation.component.ComponentScope;
import consulo.annotation.component.ServiceAPI;
import consulo.annotation.component.ServiceImpl;
import consulo.project.Project;
import consulo.util.lang.Pair;
import consulo.xml.dom.Converter;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

/**
 * Registers custom converters for Hibernate properties in Spring bean configurations.
 */
@Singleton
@ServiceAPI(ComponentScope.PROJECT)
@ServiceImpl
public class SpringIntegrationProjectComponent {
  private final Project myProject;

  @Inject
  public SpringIntegrationProjectComponent(Project project) {
    myProject = project;
  }

  @PostConstruct
  public void initComponent() {
    final SpringManager springManager = SpringManager.getInstance(myProject);

    // Register Hibernate property name converter for Spring <prop key="..."> elements
    final HibernatePropertyNameConverter propertyNameConverter = new HibernatePropertyNameConverter();
    final CustomConverterRegistry customConverterRegistry = springManager.getCustomConverterRegistry();
    customConverterRegistry.registryConverter(
        PropertyKeyConverter.class,
        Pair.create(propertyNameConverter, (Converter)propertyNameConverter)
    );

    // Register Hibernate property value converter
    final HibernatePropertyValueConverter propertyValueConverter = new HibernatePropertyValueConverter();
    final GenericDomValueConvertersRegistry valueProvidersRegistry = springManager.getValueProvidersRegistry();
    valueProvidersRegistry.registerConverter(propertyValueConverter, propertyValueConverter);
  }
}
