/*
 * Copyright (c) 2000-2007 JetBrains s.r.o. All Rights Reserved.
 */

package com.intellij.hibernate.model.xml.mapping;

import jakarta.annotation.Nonnull;

import java.util.List;

/**
 * @author Gregory.Shrago
 */
public interface HbmPersistentObjectBaseEx extends HbmPersistentObjectBase {
  @Nonnull
  List<HbmMeta> getMetas();
  HbmMeta addMeta();

  @Nonnull
  List<HbmTuplizer> getTuplizers();
  HbmTuplizer addTuplizer();

  @Nonnull
  List<HbmProperty> getProperties();
  HbmProperty addProperty();

  @Nonnull
  List<HbmManyToOne> getManyToOnes();
  HbmManyToOne addManyToOne();

  @Nonnull
  List<HbmOneToOne> getOneToOnes();
  HbmOneToOne addOneToOne();

  @Nonnull
  List<HbmComponent> getComponents();
  HbmComponent addComponent();

  @Nonnull
  List<HbmDynamicComponent> getDynamicComponents();
  HbmDynamicComponent addDynamicComponent();

  @Nonnull
  List<HbmAny> getAnies();
  HbmAny addAny();

  @Nonnull
  List<HbmMap> getMaps();
  HbmMap addMap();

  @Nonnull
  List<HbmSet> getSets();
  HbmSet addSet();

  @Nonnull
  List<HbmList> getLists();
  HbmList addList();

  @Nonnull
  List<HbmBag> getBags();
  HbmBag addBag();

  @Nonnull
  List<HbmArray> getArrays();
  HbmArray addArray();

  @Nonnull
  List<HbmPrimitiveArray> getPrimitiveArrays();
  HbmPrimitiveArray addPrimitiveArray();

}