/*
 * Copyright (c) 2000-2007 JetBrains s.r.o. All Rights Reserved.
 */

package com.intellij.hibernate.springIntegration;

import consulo.module.Module;
import consulo.module.ModuleManager;
import consulo.project.Project;
import consulo.util.lang.Pair;
import com.intellij.persistence.DataSourceInfoProvider;
import consulo.language.psi.PsiElement;
import com.intellij.spring.impl.ide.SpringManager;
import com.intellij.spring.impl.ide.SpringModel;
import com.intellij.spring.impl.ide.model.SpringUtils;
import com.intellij.spring.impl.ide.model.xml.beans.SpringBaseBeanPointer;
import com.intellij.spring.impl.ide.model.xml.beans.SpringBean;
import com.intellij.spring.impl.ide.model.xml.beans.SpringPropertyDefinition;
import java.util.function.Function;
import org.jetbrains.annotations.NonNls;
import jakarta.annotation.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import consulo.annotation.component.ExtensionImpl;

/**
 * @author Gregory.Shrago
 */
@ExtensionImpl
public class SpringDataSourceInfoProvider extends DataSourceInfoProvider {

  @NonNls private final List<Pair<String, Function<SpringBean, DataSourceInfo>>> myProviders = new ArrayList<Pair<String, Function<SpringBean, DataSourceInfo>>>();

  public SpringDataSourceInfoProvider() {

    //c3p0 0.9.1.1:
    //com.mchange.v2.c3p0.ComboPooledDataSource
    //driverClass
    //jdbcUrl
    //user
    //password
    myProviders.add(Pair.<String, Function<SpringBean, DataSourceInfo>>create("com.mchange.v2.c3p0.ComboPooledDataSource", new Function<SpringBean, DataSourceInfo>() {
      public DataSourceInfo apply(final SpringBean springBean) {
        return createInfo(springBean, "c3p0", "driverClass", "jdbcUrl", "user", "password");
      }
    }));
    //jakarta commons-dbcp 1.2.1:
    //org.apache.commons.dbcp.BasicDataSource
    //driverClassName
    //url
    //username
    //password
    myProviders.add(Pair.<String, Function<SpringBean, DataSourceInfo>>create("org.apache.commons.dbcp.BasicDataSource", new Function<SpringBean, DataSourceInfo>() {
      public DataSourceInfo apply(final SpringBean springBean) {
        return createInfo(springBean, "jakarta commons-dbcp", "driverClassName", "url", "username", "password");
      }
    }));
    //spring 2.0.3
    //org.springframework.jdbc.datasource.DriverManagerDataSource
    //driverClassName
    //url
    //username
    //password
    myProviders.add(Pair.<String, Function<SpringBean, DataSourceInfo>>create("org.springframework.jdbc.datasource.DriverManagerDataSource", new Function<SpringBean, DataSourceInfo>() {
      public DataSourceInfo apply(final SpringBean springBean) {
        return createInfo(springBean, "spring", "driverClassName", "url", "username", "password");
      }
    }));
    //proxool 0.9.0rc3:
    //org.logicalcobwebs.proxool.ProxoolDataSource
    //driver
    //driverUrl
    //user
    //password
    myProviders.add(Pair.<String, Function<SpringBean, DataSourceInfo>>create("org.logicalcobwebs.proxool.ProxoolDataSource", new Function<SpringBean, DataSourceInfo>() {
      public DataSourceInfo apply(final SpringBean springBean) {
        return createInfo(springBean, "proxool", "driver", "driverUrl", "user", "password");
      }
    }));
  }

  public Collection<Pair<PsiElement, DataSourceInfo>> getDataSources(final Project project) {
    final ArrayList<Pair<PsiElement, DataSourceInfo>> result = new ArrayList<Pair<PsiElement, DataSourceInfo>>();
    final Module[] modules = ModuleManager.getInstance(project).getModules();
    final SpringManager manager = SpringManager.getInstance(project);
    for (Module module : modules) {
      final SpringModel springModel = manager.getCombinedModel(module);
      if (springModel != null) {
        for (SpringBaseBeanPointer bean : springModel.getAllDomBeans()) {
          final Function<SpringBean, DataSourceInfo> provider = getProvider(project, bean);
          if (provider != null) {
            final PsiElement psiElement = bean.getPsiElement();
            final DataSourceInfo dataSourceInfo = provider.apply((SpringBean)bean.getSpringBean());
            if (dataSourceInfo != null) {
              result.add(Pair.create(psiElement, dataSourceInfo));
            }
          }
        }
      }
    }
    return result;
  }

  @Nullable
  private Function<SpringBean, DataSourceInfo> getProvider(final Project project, final SpringBaseBeanPointer bean) {
    return IntegrationUtil.getProvider(bean, myProviders, true);
  }

  @Nullable
  private static DataSourceInfo createInfo(final SpringBean bean, @NonNls final String type,
                                   @NonNls final String driver, @NonNls final String url,
                                   @NonNls final String userName, @NonNls final String password) {
    return DataSourceInfoProvider.getInfo(
      type, bean.getBeanName(),
      getStringValue(bean, driver), getStringValue(bean, url),
      getStringValue(bean, userName), getStringValue(bean, password));
  }

  @Nullable
  private static Collection<String> getStringValue(final SpringBean bean, final String property) {
    final SpringPropertyDefinition value = SpringUtils.findPropertyByName(bean, property, true);
    if (value != null) {
      return SpringUtils.getValueVariants(value);
    }
    return Collections.emptyList();
  }

}
