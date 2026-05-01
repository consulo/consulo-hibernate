package com.intellij.hibernate.model.xml.mapping;

import com.intellij.hibernate.model.converters.HbmClassEntityNameConverter;
import com.intellij.persistence.model.PersistentEntity;
import com.intellij.persistence.model.PersistentSuperclass;
import consulo.xml.dom.Convert;
import consulo.xml.dom.GenericAttributeValue;
import consulo.xml.dom.NameValue;
import jakarta.annotation.Nonnull;

import java.util.List;

/**
 * @author Gregory.Shrago
 */
public interface HbmClassBase extends PersistentEntity, HbmPersistentObjectBaseEx, PersistentSuperclass, HbmTableInfoProvider {

  @NameValue
  @Convert(HbmClassEntityNameConverter.class)
  GenericAttributeValue<String> getEntityName();

  GenericAttributeValue<Boolean> getAbstract();

  List<HbmQuery> getQueries();

  HbmQuery addQuery();

  List<HbmSqlQuery> getSqlQueries();

  HbmSqlQuery addSqlQuery();

  @Nonnull
  HbmSqlStatement getSqlInsert();

  @Nonnull
  HbmSqlStatement getSqlUpdate();

  @Nonnull
  HbmSqlStatement getSqlDelete();

  @Nonnull
  List<HbmResultset> getResultsets();

  HbmResultset addResultset();

  @Nonnull
  List<HbmIdbag> getIdbags();

  HbmIdbag addIdbag();


}