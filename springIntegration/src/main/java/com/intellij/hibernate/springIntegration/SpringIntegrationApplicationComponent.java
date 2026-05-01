package com.intellij.hibernate.springIntegration;

import com.intellij.hibernate.facet.HibernateFacet;
import com.intellij.jam.model.common.CommonModelManager;
import com.intellij.jam.model.common.CommonModelElement;
import com.intellij.jam.view.JamDeleteHandler;
import com.intellij.persistence.facet.PersistenceHelper;
import com.intellij.spring.impl.ide.model.xml.beans.Beans;
import consulo.annotation.component.ComponentScope;
import consulo.annotation.component.ServiceAPI;
import consulo.annotation.component.ServiceImpl;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Singleton;

import java.util.Collection;

/**
 * @author Gregory.Shrago
 */
@Singleton
@ServiceAPI(ComponentScope.APPLICATION)
@ServiceImpl
public class SpringIntegrationApplicationComponent {

  @PostConstruct
  public void initComponent() {
    // ElementPresentationManager.registerIcon() was removed from the platform API
    // Icon registration for SpringBeanPersistenceUnit is no longer supported this way

    PersistenceHelper.getHelper().getManipulatorsRegistry().registerManipulator(SpringBeanPersistenceUnit.class, SpringBeanPersistenceUnitManipulator.class);
    PersistenceHelper.getHelper().getManipulatorsRegistry().registerManipulator(HibernateFacet.class, HibernateFacetManipulator.class);
    PersistenceHelper.getHelper().getManipulatorsRegistry().registerManipulator(Beans.class, SpringBeansManipulator.class);

    CommonModelManager.getInstance().registerDeleteHandler(new JamDeleteHandler() {
      public void addModelElements(final CommonModelElement element, final Collection<CommonModelElement> result) {
        if (element instanceof SpringBeanPersistenceUnit) {
          result.add(((SpringBeanPersistenceUnit)element).getBean());
        }
      }
    });
  }
}
