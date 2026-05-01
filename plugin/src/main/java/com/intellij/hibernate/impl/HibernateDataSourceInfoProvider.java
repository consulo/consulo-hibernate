/*
 * Copyright (c) 2000-2007 JetBrains s.r.o. All Rights Reserved.
 */

package com.intellij.hibernate.impl;

import com.intellij.hibernate.facet.HibernateFacet;
import com.intellij.hibernate.model.HibernatePropertiesConstants;
import com.intellij.hibernate.model.xml.config.SessionFactory;
import com.intellij.hibernate.util.HibernateUtil;
import com.intellij.persistence.DataSourceInfoProvider;
import com.intellij.persistence.model.PersistencePackage;
import consulo.annotation.component.ExtensionImpl;
import consulo.language.psi.PsiElement;
import consulo.module.Module;
import consulo.module.ModuleManager;
import consulo.project.Project;
import consulo.util.lang.Pair;
import jakarta.annotation.Nullable;
import org.jetbrains.annotations.NonNls;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

/**
 * @author Gregory.Shrago
 */
@ExtensionImpl
public class HibernateDataSourceInfoProvider extends DataSourceInfoProvider {
  public Collection<Pair<PsiElement,DataSourceInfo>> getDataSources(final Project project) {
    final ArrayList<Pair<PsiElement, DataSourceInfo>> result = new ArrayList<Pair<PsiElement, DataSourceInfo>>();
    final Module[] modules = ModuleManager.getInstance(project).getModules();
    for (Module module : modules) {
      // TODO: migrate to ModuleExtension-based facet discovery
      final HibernateFacet singleFacet = HibernateFacet.getInstance(module);
      final Collection<HibernateFacet> facets = singleFacet != null ? Collections.singletonList(singleFacet) : Collections.emptyList();
      for (HibernateFacet facet : facets) {
        for (PersistencePackage persistencePackage : facet.getPersistenceUnits()) {
          if (persistencePackage instanceof SessionFactory) {
            final SessionFactory factory = (SessionFactory)persistencePackage;
            final Map<String, String> map = HibernateUtil.getSessionFactoryProperties(factory);
            final String driverClass = map.get(HibernatePropertiesConstants.DRIVER);
            final String url = map.get(HibernatePropertiesConstants.URL);
            final String user = map.get(HibernatePropertiesConstants.USER);
            final String password = map.get(HibernatePropertiesConstants.PASS);
            final DataSourceInfo dataSourceInfo = getInfo("hibernate", factory.getName().getValue(), driverClass, url, user, password);
            if (dataSourceInfo != null) {
              result.add(Pair.<PsiElement, DataSourceInfo>create(factory.getXmlElement(), dataSourceInfo));
            }
          }
        }
      }
    }
    return result;
  }

  @Nullable
  public static DataSourceInfo getInfo(@NonNls final String type,
                                       @NonNls final String name,
                                       @NonNls final Collection<String> drivers,
                                       @NonNls final Collection<String> urls,
                                       @NonNls final Collection<String> userNames,
                                       @NonNls final Collection<String> passwords) {
    return DataSourceInfoProvider.getInfo(type, name, drivers, urls, userNames, passwords);
  }

  @Nullable
  public static DataSourceInfo getInfo(@NonNls final String type,
                                       @NonNls final String name,
                                       @NonNls final String driver,
                                       @NonNls final String url,
                                       @NonNls final String userName,
                                       @NonNls final String password) {
    return DataSourceInfoProvider.getInfo(type, name, driver, url, userName, password);
  }

}
