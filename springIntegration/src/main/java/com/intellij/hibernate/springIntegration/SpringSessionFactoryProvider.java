package com.intellij.hibernate.springIntegration;

import com.intellij.hibernate.facet.HibernateFacet;
import com.intellij.hibernate.facet.HibernateSessionFactoryProvider;
import consulo.util.lang.Pair;
import com.intellij.persistence.model.PersistencePackage;
import com.intellij.spring.impl.ide.SpringManager;
import com.intellij.spring.impl.ide.SpringModel;
import com.intellij.spring.impl.ide.model.xml.beans.SpringBaseBeanPointer;
import com.intellij.spring.impl.ide.model.xml.beans.SpringBean;
import java.util.function.Function;
import jakarta.annotation.Nonnull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import consulo.annotation.component.ExtensionImpl;

/**
 * @author Gregory.Shrago
 */
@ExtensionImpl
public class SpringSessionFactoryProvider implements HibernateSessionFactoryProvider {

  @Nonnull
  public List<PersistencePackage> getSessionFactories(final HibernateFacet facet) {
    final SpringModel springModel = SpringManager.getInstance(facet.getModule().getProject()).getCombinedModel(facet.getModule());
    if (springModel != null) {
      final ArrayList<PersistencePackage> result = new ArrayList<PersistencePackage>();
      for (SpringBaseBeanPointer bean : springModel.getAllDomBeans()) {
        final Function<Pair<SpringBean, HibernateFacet>, PersistencePackage> provider =
          IntegrationUtil.getProvider(bean, IntegrationUtil.myProviders, true);
        if (provider != null) {
          final PersistencePackage unit = provider.apply(Pair.create((SpringBean)bean.getSpringBean(), facet));
          if (!bean.isAbstract()) {
            result.add(unit);
          }
        }
      }
      return result;
    }
    return Collections.emptyList();
  }

}
