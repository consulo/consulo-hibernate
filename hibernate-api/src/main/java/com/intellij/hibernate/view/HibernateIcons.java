package com.intellij.hibernate.view;

import com.intellij.hibernate.icon.icon.HibernateIconGroup;
import com.intellij.persistence.PersistenceIcons;
import consulo.ui.image.Image;

/**
 * @author Gregory.Shrago
 */
public interface HibernateIcons {
  Image HIBERNATE_ICON = HibernateIconGroup.hibernate();

  Image SESSION_FACTORY_ICON = PersistenceIcons.PERSISTENCE_UNIT_ICON;

  Image ABSTRACT_CLASS_ICON = PersistenceIcons.MAPPED_SUPERCLASS_ICON;
  Image CLASS_ICON = PersistenceIcons.ENTITY_ICON;
  Image COMPONENT_ICON = PersistenceIcons.EMBEDDABLE_ICON;

  Image ID_ATTRIBUTE_ICON = PersistenceIcons.ID_ATTRIBUTE_ICON;
  Image ATTRIBUTE_ICON = PersistenceIcons.ATTRIBUTE_ICON;
  Image RELATIONSHIP_ICON = PersistenceIcons.RELATIONSHIP_ICON;
  Image ID_RELATIONSHIP_ICON = PersistenceIcons.ID_RELATIONSHIP_ICON;

  Image HIBERNATE_MAPPING_ICON = HibernateIconGroup.hibernatemapping();
  Image HIBERNATE_CONFIG_ICON = HibernateIconGroup.hibernateconfig();
}
