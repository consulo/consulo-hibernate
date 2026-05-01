package com.intellij.hibernate.impl.model.xml.impl.mapping;

import gnu.trove.THashMap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import com.intellij.hibernate.model.enums.AccessType;
import com.intellij.hibernate.model.xml.mapping.HbmClassBase;
import com.intellij.hibernate.model.xml.mapping.HbmEmbeddedAttributeBase;
import com.intellij.hibernate.model.xml.mapping.HbmHibernateMapping;
import com.intellij.hibernate.model.xml.mapping.HbmImport;
import com.intellij.hibernate.model.xml.mapping.HbmQuery;
import com.intellij.hibernate.model.xml.mapping.HbmSqlQuery;
import com.intellij.hibernate.model.xml.mapping.HbmTypedef;
import com.intellij.hibernate.util.HibernateUtil;
import com.intellij.hibernate.impl.model.xml.impl.HibernateBaseImpl;
import com.intellij.jpa.model.xml.impl.converters.ClassConverterBase;
import consulo.util.lang.StringUtil;
import com.intellij.persistence.model.PersistenceListener;
import com.intellij.persistence.model.PersistentEmbeddable;
import com.intellij.persistence.model.helpers.PersistenceMappingsModelHelper;
import com.intellij.persistence.util.PersistenceCommonUtil;
import com.intellij.java.language.psi.PsiClass;
import consulo.language.psi.PsiManager;
import com.intellij.java.language.psi.PsiNameHelper;
import consulo.application.util.CachedValue;
import consulo.application.util.CachedValueProvider;
import com.intellij.java.language.psi.util.PropertyMemberType;
import java.util.function.Consumer;
import com.intellij.persistence.util.BidirectionalMultiMap;
import consulo.xml.dom.DomUtil;
import consulo.xml.dom.GenericAttributeValue;

/**
 * @author Gregory.Shrago
 */
public abstract class HbmHibernateMappingImpl extends HibernateBaseImpl implements HbmHibernateMapping, PersistenceMappingsModelHelper {

  private CachedValue<BidirectionalMultiMap<String, String>> myImportMapValue;
  private CachedValue<BidirectionalMultiMap<String, String>> myTypedefMapValue;
  private CachedValue<Map<String, List<HbmEmbeddedAttributeBase>>> myEmbeddablesAttributes;
  private final Map<String, HbmEmbeddableImpl> myEmbeddables = new THashMap<String, HbmEmbeddableImpl>();

  @Nonnull
  public PersistenceMappingsModelHelper getModelHelper() {
    return this;
  }

  public PropertyMemberType getDeclaredAccess() {
    final AccessType type = getDefaultAccess().getValue();
    return type == AccessType.FIELD? PropertyMemberType.FIELD : type == AccessType.PROPERTY? PropertyMemberType.GETTER : null;
  }

  public List<? extends PersistenceListener> getPersistentListeners() {
    return Collections.emptyList(); // TODO
  }

  @Nonnull
  public List<HbmClassBase> getPersistentEntities() {
    final ArrayList<HbmClassBase> result = new ArrayList<HbmClassBase>();
    final Consumer<HbmClassBase> consumer = new Consumer<HbmClassBase>() {
      public void accept(final HbmClassBase classBase) {
        result.add(classBase);
      }
    };
    HibernateUtil.consumePersistentObjects(this, consumer, null);
    return result;
  }

  @Nonnull
  public List<HbmClassBase> getPersistentSuperclasses() {
    return Collections.emptyList();
  }


  @Nonnull
  public List<? extends PersistentEmbeddable> getPersistentEmbeddables() {
    final Set<String> keys = getEmbeddableAttributesMap().keySet();
    myEmbeddables.keySet().retainAll(keys);
    for (String key : keys) {
      if (!myEmbeddables.containsKey(key)) {
        myEmbeddables.put(key, new HbmEmbeddableImpl(this, key));
      }
    }
    return new ArrayList<PersistentEmbeddable>(myEmbeddables.values());
  }

  private Map<String, List<HbmEmbeddedAttributeBase>> getEmbeddableAttributesMap() {
    if (myEmbeddablesAttributes == null) {
      myEmbeddablesAttributes = consulo.application.util.CachedValuesManager.getManager(getManager().getProject())
        .createCachedValue(new CachedValueProvider<Map<String, List<HbmEmbeddedAttributeBase>>>() {
          public Result<Map<String, List<HbmEmbeddedAttributeBase>>> compute() {
            return new Result<Map<String, List<HbmEmbeddedAttributeBase>>>(getEmbeddableAttributesMapInner(), DomUtil.getFile(HbmHibernateMappingImpl.this));
          }
        }, false);
    }
    return myEmbeddablesAttributes.getValue();
  }

  @Nonnull
  public List<HbmQuery> getNamedQueries() {
    return getQueries();
  }

  @Nonnull
  public List<HbmSqlQuery> getNamedNativeQueries() {
    return getSqlQueries();
  }

  @Nonnull
  public BidirectionalMultiMap<String, String> getImportsMap() {
    if (myImportMapValue == null) {
      myImportMapValue = consulo.application.util.CachedValuesManager.getManager(getManager().getProject())
        .createCachedValue(new CachedValueProvider<BidirectionalMultiMap<String, String>>() {
          public Result<BidirectionalMultiMap<String, String>> compute() {
            final BidirectionalMultiMap<String, String> result = new BidirectionalMultiMap<String, String>();
            for (HbmImport hbmImport : getImports()) {
              final String className = hbmImport.getClazz().getStringValue();
              final String renameTo = hbmImport.getRename().getStringValue();
              if (StringUtil.isNotEmpty(className)) {
                result.put(className, StringUtil.isEmpty(renameTo) ? PsiNameHelper.getShortClassName(className) : renameTo);
              }
            }
            return new Result<BidirectionalMultiMap<String, String>>(result, DomUtil.getFile(HbmHibernateMappingImpl.this));
          }
        }, false);
    }
    final BidirectionalMultiMap<String, String> map = myImportMapValue.getValue();
    assert map != null;
    return map;
  }

  @Nonnull
  public BidirectionalMultiMap<String, String> getTypedefMap() {
    if (myTypedefMapValue == null) {
      myTypedefMapValue = consulo.application.util.CachedValuesManager.getManager(getManager().getProject())
      .createCachedValue(new CachedValueProvider<BidirectionalMultiMap<String, String>>() {
        public Result<BidirectionalMultiMap<String, String>> compute() {
          final BidirectionalMultiMap<String, String> result = new BidirectionalMultiMap<String, String>();
          for (HbmTypedef hbmTypedef : getTypedefs()) {
            final GenericAttributeValue<PsiClass> clazzValue = hbmTypedef.getClazz();
            final PsiClass clazz = clazzValue.getValue();
            final String className = clazz == null ? clazzValue.getStringValue() : clazz.getQualifiedName();
            final String typeName = hbmTypedef.getName().getStringValue();
            if (StringUtil.isNotEmpty(className)) {
              result.put(className, StringUtil.isEmpty(typeName) ? PsiNameHelper.getShortClassName(className) : typeName);
            }
          }
          return new Result<BidirectionalMultiMap<String, String>>(result, DomUtil.getFile(HbmHibernateMappingImpl.this));
        }
      }, false);
    }
    final BidirectionalMultiMap<String, String> map = myTypedefMapValue.getValue();
    assert map != null;
    return map;
  }

  private Map<String, List<HbmEmbeddedAttributeBase>> getEmbeddableAttributesMapInner() {
    final Map<String, List<HbmEmbeddedAttributeBase>> map = new THashMap<String, List<HbmEmbeddedAttributeBase>>();
    HibernateUtil.consumePersistentObjects(this, null, new Consumer<HbmEmbeddedAttributeBase>() {
      public void accept(final HbmEmbeddedAttributeBase o) {
        final String key = getEmbeddableAttributeKey(o);
        if (key != null) {
          List<HbmEmbeddedAttributeBase> list = map.get(key);
          if (list == null) {
            map.put(key, list = new ArrayList<HbmEmbeddedAttributeBase>());
          }
          list.add(o);
        }
      }
    });
    return map;
  }

  @Nullable
  private String getEmbeddableAttributeKey(final HbmEmbeddedAttributeBase o) {
    final String key;
    final PsiClass psiClass = PersistenceCommonUtil.getTargetClass(o);
    if (psiClass == null) {
      key = ClassConverterBase.getQualifiedClassName(o.getClazz().getStringValue(), getPackage().getStringValue());
    }
    else {
      key = psiClass.getQualifiedName();
    }
    return key;
  }


  @Nullable
  HbmEmbeddableImpl getEmbeddableByAttribute(final HbmEmbeddedAttributeBase o) {
    final String key = getEmbeddableAttributeKey(o);
    if (key == null) return null;
    return myEmbeddables.get(key);
  }

  @Nullable
  List<HbmEmbeddedAttributeBase> getEmbeddableAttributesByKey(final String key) {
    return getEmbeddableAttributesMap().get(key);
  }

}
