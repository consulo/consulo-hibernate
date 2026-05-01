package com.intellij.hibernate.util;

import consulo.language.editor.completion.lookup.LookupElement;
import com.intellij.hibernate.model.HibernateConstants;
import com.intellij.hibernate.model.HibernatePropertiesConstants;
import com.intellij.hibernate.model.xml.config.Mapping;
import com.intellij.hibernate.model.xml.config.Property;
import com.intellij.hibernate.model.xml.config.SessionFactory;
import com.intellij.hibernate.model.xml.mapping.*;
import com.intellij.javaee.JavaeeUtil;
import com.intellij.javaee.util.JamCommonUtil;
import consulo.module.Module;
import consulo.util.lang.Comparing;
import consulo.util.lang.StringUtil;
import consulo.virtualFileSystem.archive.ArchiveVfsUtil;
import consulo.virtualFileSystem.VirtualFile;
import com.intellij.java.language.psi.*;
import consulo.language.psi.PsiElement;
import consulo.language.psi.PsiFile;
import consulo.language.psi.PsiManager;
import consulo.language.psi.PsiReference;
import consulo.xml.language.psi.XmlFile;
import java.util.function.Consumer;
import consulo.application.util.function.Processor;
import consulo.util.collection.ContainerUtil;
import consulo.xml.dom.GenericValue;
import gnu.trove.THashMap;

import java.util.*;

import jakarta.annotation.Nullable;

/**
 * @author Gregory.Shrago
 */
public class HibernateUtil {

  private HibernateUtil() {
  }
  
  public static String getFullPropertyName(final String hibernateProperty) {
    return hibernateProperty.startsWith(HibernatePropertiesConstants.HIBERNATE_PREFIX) ?
           hibernateProperty : HibernatePropertiesConstants.HIBERNATE_PREFIX + hibernateProperty;
  }

  public static boolean isHibernateConfig(final XmlFile file, final Module module) {
    return JavaeeUtil.checkXmlType(file, HibernateConstants.CFG_XML_ROOT_TAG, HibernateConstants.HIBERNATE_CONFIGURATION_3_0);
  }

  public static boolean isHibernateMapping(final XmlFile file, final Module module) {
    return JavaeeUtil.checkXmlType(file, HibernateConstants.HBM_XML_ROOT_TAG, HibernateConstants.HIBERNATE_MAPPING_3_0);
  }

  public static <V, T extends Collection<V>> T getDomMappings(final SessionFactory sessionFactory, final Class<V> mappingClass, final T result) {
    for (Mapping mapping : sessionFactory.getMappings()) {
      PsiFile psiFile;
      if ((psiFile = mapping.getResource().getValue()) != null
        || (psiFile = mapping.getFile().getValue()) != null) {
        ContainerUtil.addIfNotNull(result, JamCommonUtil.getRootElement(psiFile, mappingClass, sessionFactory.getModule()));
      }
      else if ((psiFile = mapping.getJar().getValue()) != null) {
        final PsiManager psiManager = psiFile.getManager();
        final Module module = sessionFactory.getModule();
        processFilesInJar(psiFile.getVirtualFile(), new Processor<VirtualFile>() {
          public boolean process(VirtualFile virtualFile) {
            final PsiFile aFile = psiManager.findFile(virtualFile);
            ContainerUtil.addIfNotNull(result, JamCommonUtil.getRootElement(aFile, mappingClass, module));
            return true;
          }
        });
      }
    }
    return result;
  }

  private static void processFilesInJar(final VirtualFile virtualFile, final Processor<VirtualFile> processor) {
    final VirtualFile jarFile = ArchiveVfsUtil.getArchiveRootForLocalFile(virtualFile);
    class Visitor {

      boolean accept(final VirtualFile file) {
        if (file.isDirectory()) {
          for (VirtualFile child : file.getChildren()) {
            if (!accept(child)) {
              return false;
            }
          }
        }
        else {
          return processor.process(file);
        }
        return true;
      }

    }
    if (jarFile != null) {
      new Visitor().accept(jarFile);
    }
  }

  public static void consumePersistentObjects(final HbmHibernateMapping mappings,
                                              final Consumer<? super HbmClassBase> classConsumer,
                                              final Consumer<? super HbmEmbeddedAttributeBase> componentConsumer) {
    final HbmAttributeVisitorAdapter componentVisitor = new HbmAttributeVisitorAdapter() {

      @Override public void visitEmbeddedAttributeBase(final HbmEmbeddedAttributeBase embeddedAttributeBase) {
        componentConsumer.accept(embeddedAttributeBase);
        embeddedAttributeBase.visitAttributes(this);
      }

    };
    final Consumer<HbmClassBase> consumer = new Consumer<HbmClassBase>() {
      public void accept(final HbmClassBase hbmClassBase) {
        if (classConsumer != null) {
          classConsumer.accept(hbmClassBase);
        }
        if (componentConsumer != null) {
          hbmClassBase.visitAttributes(componentVisitor);
        }
      }
    };
    final Processor<HbmSubclass> subclassProcessor = new Processor<HbmSubclass>() {
      public boolean process(final HbmSubclass hbmSubclass) {
        consumer.accept(hbmSubclass);
        ContainerUtil.process(hbmSubclass.getSubclasses(), this);
        return true;
      }
    };
    final Processor<HbmUnionSubclass> unionSubclassProcessor = new Processor<HbmUnionSubclass>() {
      public boolean process(final HbmUnionSubclass hbmSubclass) {
        consumer.accept(hbmSubclass);
        ContainerUtil.process(hbmSubclass.getUnionSubclasses(), this);
        return true;
      }
    };
    final Processor<HbmJoinedSubclass> joinedSubclassProcessor = new Processor<HbmJoinedSubclass>() {
      public boolean process(final HbmJoinedSubclass hbmSubclass) {
        consumer.accept(hbmSubclass);
        ContainerUtil.process(hbmSubclass.getJoinedSubclasses(), this);
        return true;
      }
    };
    final Processor<HbmClass> classProcessor = new Processor<HbmClass>() {
      public boolean process(final HbmClass hbmClass) {
        consumer.accept(hbmClass);
        ContainerUtil.process(hbmClass.getSubclasses(), subclassProcessor);
        ContainerUtil.process(hbmClass.getJoinedSubclasses(), joinedSubclassProcessor);
        ContainerUtil.process(hbmClass.getUnionSubclasses(), unionSubclassProcessor);
        return true;
      }
    };
    ContainerUtil.process(mappings.getClasses(), classProcessor);
    ContainerUtil.process(mappings.getJoinedSubclasses(), joinedSubclassProcessor);
    ContainerUtil.process(mappings.getSubclasses(), subclassProcessor);
    ContainerUtil.process(mappings.getUnionSubclasses(), unionSubclassProcessor);
  }

  public static <T, S extends GenericValue<T>, U extends Collection<T>, V extends Collection<S>> U getGenericValues(final V values, final U result) {
    for (S value : values) {
      ContainerUtil.addIfNotNull(result, value.getValue());
    }
    return result;
  }

  public static <S extends GenericValue, U extends Collection<String>, V extends Collection<S>> U getStringValues(final V values, final U result) {
    for (S value : values) {
      ContainerUtil.addIfNotNull(result, value.getStringValue());
    }
    return result;
  }

  public static boolean isEmbedded(final HbmCompositeId compositeId) {
    return StringUtil.isNotEmpty(compositeId.getClazz().getStringValue()) && !Boolean.TRUE.equals(compositeId.getMapped().getValue());
  }

  public static Map<String, String> getSessionFactoryProperties(final SessionFactory factory) {
    final THashMap<String, String> result = new THashMap<String, String>();
    for (Property<Object> property : factory.getProperties()) {
      final String name = property.getName().getValue();
      final String value = property.getStringValue();
      final String shortName = getPropertyShortName(name);
      if (StringUtil.isNotEmpty(shortName) && StringUtil.isNotEmpty(value)) {
        result.put(shortName, value);
      }
    }
    return result;
  }

  @Nullable
  public static String getPropertyShortName(final String name) {
    return name != null && name.startsWith(HibernatePropertiesConstants.HIBERNATE_PREFIX) ?
           name.substring(HibernatePropertiesConstants.HIBERNATE_PREFIX.length()) : name;
  }

  @Nullable
  public static Property<Object> findSessionFactoryProperty(final SessionFactory factory, final String propertyName) {
    final String propertyShortName = getPropertyShortName(propertyName);
    for (Property<Object> property : factory.getProperties()) {
      final String name = property.getName().getValue();
      final String shortName = getPropertyShortName(name);
      if (StringUtil.isNotEmpty(shortName) && Comparing.strEqual(propertyShortName, shortName)) {
        return property;
      }
    }
    return null;
  }

  public static Property<Object> setSessionFactoryProperty(final SessionFactory factory, final String propertyName, final String value) {
    final Property<Object> existingProperty = findSessionFactoryProperty(factory, propertyName);
    final Property<Object> property = existingProperty == null ? factory.addProperty() : existingProperty;
    if (existingProperty == null) property.getName().setStringValue(propertyName);
    property.setStringValue(value);
    return property;
  }

  public static String getDefaultDialectValue(final PsiElement element, final String jdbcUrl) {
    final StringTokenizer st = new StringTokenizer(jdbcUrl, ":");
    if (st.hasMoreTokens()) st.nextToken(); else  return "";
    if (!st.hasMoreTokens()) return "";
    final String token = st.nextToken().toLowerCase();
    final ArrayList<String> dialects = new ArrayList<String>();
    for (PsiReference reference : element.getReferences()) {
      final Object[] variants = reference.getVariants();
      for (Object variant : variants) {
        final Object obj = variant instanceof LookupElement ? ((LookupElement)variant).getObject() : null;
        final String variantStr = obj instanceof PsiClass? ((PsiClass)obj).getQualifiedName() : null;
        if (variantStr != null && variantStr.toLowerCase().contains(token)) {
          dialects.add(variantStr);
        }
      }
    }
    if (dialects.isEmpty()) return "";
    else {
      Collections.sort(dialects);
      return dialects.get(0);
    }
  }
}
