package com.intellij.hibernate.impl.model.xml.impl.config;

import com.intellij.hibernate.model.xml.config.Mapping;
import com.intellij.hibernate.model.xml.config.Property;
import com.intellij.hibernate.model.xml.config.SessionFactory;
import com.intellij.hibernate.util.HibernateUtil;
import com.intellij.hibernate.impl.model.xml.impl.HibernateBaseImpl;
import com.intellij.persistence.model.PersistenceListener;
import com.intellij.persistence.model.PersistenceMappings;
import com.intellij.persistence.model.helpers.PersistenceUnitModelHelper;
import com.intellij.java.language.psi.PsiClass;
import consulo.language.psi.PsiFile;
import com.intellij.java.language.psi.PsiJavaPackage;
import consulo.application.util.CachedValue;
import consulo.application.util.CachedValueProvider;
import java.util.function.Function;
import consulo.util.collection.ContainerUtil;
import consulo.xml.dom.GenericAttributeValue;
import consulo.xml.dom.GenericValue;
import com.intellij.hibernate.impl.model.xml.impl.HibernateReadOnlyValue;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

/**
 * @author Gregory.Shrago
 */
public abstract class SessionFactoryImpl extends HibernateBaseImpl implements SessionFactory, PersistenceUnitModelHelper {

  private CachedValue<Properties> myProperties;

  public PersistenceUnitModelHelper getModelHelper() {
    return this;
  }

  @Nonnull
  public List<? extends PersistenceListener> getPersistentListeners() {
    return Collections.emptyList(); // TODO
  }

  @Nonnull
  public List<? extends GenericValue<PsiJavaPackage>> getPackages() {
    return ContainerUtil.mapNotNull(getMappings(), new Function<Mapping, GenericValue<PsiJavaPackage>>() {
      public GenericValue<PsiJavaPackage> apply(final Mapping mapping) {
        final GenericAttributeValue<PsiJavaPackage> packageValue = mapping.getPackage();
        return packageValue.getValue() != null ? packageValue : null;
      }
    });
  }

  @Nullable
  public GenericValue<String> getDataSourceName() {
    return null;
  }

  @Nonnull
  public Properties getPersistenceUnitProperties() {
    if (myProperties == null) {
      myProperties = consulo.application.util.CachedValuesManager.getManager(getPsiManager().getProject()).createCachedValue(new CachedValueProvider<Properties>() {
        public Result<Properties> compute() {
          return new Result<Properties>(getPersistenceUnitPropertiesInner(), getContainingFile());
        }
      }, false);
    }
    return myProperties.getValue();
  }

  @Nonnull
  private Properties getPersistenceUnitPropertiesInner() {
    final Properties result = new Properties();
    for (Property<Object> property : getProperties()) {
      result.put(HibernateUtil.getFullPropertyName(property.getName().getValue()), property.getStringValue());
    }
    return result;
  }

  @Nonnull
  public List<? extends GenericValue<PsiClass>> getClasses() {
    return ContainerUtil.mapNotNull(getMappings(), new Function<Mapping, GenericValue<PsiClass>>() {
      public GenericValue<PsiClass> apply(final Mapping mapping) {
        final GenericAttributeValue<PsiClass> classValue = mapping.getClazz();
        return classValue.getValue() != null ? classValue : null;
      }
    });
  }

  @Nonnull
  public List<? extends GenericValue<PsiFile>> getJarFiles() {
    return ContainerUtil.mapNotNull(getMappings(), new Function<Mapping, GenericValue<PsiFile>>() {
      public GenericValue<PsiFile> apply(final Mapping mapping) {
        final GenericAttributeValue<PsiFile> jarFile = mapping.getJar();
        return jarFile.getValue() != null? jarFile : null;
      }
    });
  }

  @Nonnull
  public <V extends PersistenceMappings> List<? extends GenericValue<V>> getMappingFiles(final Class<V> mappingsClass) {
    final ArrayList<V> mappings = HibernateUtil.getDomMappings(this, mappingsClass, new ArrayList<V>());
    final ArrayList<GenericValue<V>> list = new ArrayList<GenericValue<V>>();
    for (V mapping : mappings) {
      list.add(HibernateReadOnlyValue.getInstance(mapping));
    }
    return list;
  }
}
