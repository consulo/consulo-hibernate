/*
 * Copyright 2000-2009 JetBrains s.r.o.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.intellij.hibernate.impl.model.annotations.mapping;

import static com.intellij.hibernate.model.HibernateConstants.COLLECTION_OF_ELEMENTS_ANNO;
import static com.intellij.java.language.patterns.PsiJavaPatterns.psiClass;
import static com.intellij.java.language.patterns.PsiJavaPatterns.psiMember;
import com.intellij.java.language.patterns.PsiMemberPattern;
import consulo.language.pattern.PatternCondition;
import consulo.language.sem.SemContributor;
import consulo.language.sem.SemRegistrar;
import com.intellij.java.language.psi.PsiMember;
import com.intellij.java.language.psi.PsiType;
import com.intellij.java.language.psi.util.PropertyUtil;
import consulo.language.util.ProcessingContext;
import com.intellij.persistence.util.JavaContainerType;
import com.intellij.persistence.util.PersistenceCommonUtil;
import static com.intellij.persistence.roles.PersistenceClassRoleEnum.EMBEDDABLE;
import consulo.util.lang.Pair;
import com.intellij.jpa.model.annotations.mapping.AttributeBaseImpl;
import com.intellij.jpa.model.annotations.mapping.JamJpaEntity;
import com.intellij.jpa.util.JpaUtil;
import com.intellij.javaee.model.common.persistence.JavaeePersistenceConstants;
import jakarta.annotation.Nonnull;
import consulo.annotation.component.ExtensionImpl;

/**
 * @author Gregory.Shrago
 */
@ExtensionImpl
public class HibernateJamContributor extends SemContributor{

  static {
    JpaJamExtender.extendJpa();
  }

  public void registerSemProviders(final SemRegistrar registrar) {
    // Hibernate-specific collection mapping
    CollectionOfElementsImpl.COLLECTION_OF_ELEMENTS_ATTR_META.register(registrar, psiMember().withAnnotation(COLLECTION_OF_ELEMENTS_ANNO).andNot(createHasEmbeddableTargetPattern()));
    CollectionOfElementsImpl.COLLECTION_OF_EMBEDDED_ELEMENTS_ATTR_META.register(registrar, psiMember().withAnnotation(COLLECTION_OF_ELEMENTS_ANNO).and(createHasEmbeddableTargetPattern()));

    // JPA class-level annotations
    JamJpaEntity.META.register(registrar, psiClass().withAnnotation(JavaeePersistenceConstants.ENTITY_ANNO));

    // JPA member-level annotations
    AttributeBaseImpl.ID_ATTR_META.register(registrar, psiMember().withAnnotation(JavaeePersistenceConstants.ID_ANNO));
    AttributeBaseImpl.EMBEDDED_ID_ATTR_META.register(registrar, psiMember().withAnnotation(JavaeePersistenceConstants.EMBEDDED_ID_ANNO));
    AttributeBaseImpl.VERSION_ATTR_META.register(registrar, psiMember().withAnnotation(JavaeePersistenceConstants.VERSION_ANNO));
    AttributeBaseImpl.BASIC_ATTR_META.register(registrar, psiMember().withAnnotation(JavaeePersistenceConstants.BASIC_ANNO));
    AttributeBaseImpl.ONE_TO_ONE_ATTR_META.register(registrar, psiMember().withAnnotation(JavaeePersistenceConstants.ONE_TO_ONE_ANNO));
    AttributeBaseImpl.ONE_TO_MANY_ATTR_META.register(registrar, psiMember().withAnnotation(JavaeePersistenceConstants.ONE_TO_MANY_ANNO));
    AttributeBaseImpl.MANY_TO_ONE_ATTR_META.register(registrar, psiMember().withAnnotation(JavaeePersistenceConstants.MANY_TO_ONE_ANNO));
    AttributeBaseImpl.MANY_TO_MANY_ATTR_META.register(registrar, psiMember().withAnnotation(JavaeePersistenceConstants.MANY_TO_MANY_ANNO));
    AttributeBaseImpl.EMBEDDED_ATTR_META.register(registrar, psiMember().withAnnotation(JavaeePersistenceConstants.EMBEDDED_ANNO));
  }

  private static PsiMemberPattern.Capture createHasEmbeddableTargetPattern() {
    return psiMember().with(new PatternCondition<PsiMember>("hasEmbeddableTarget") {
      @Override
      public boolean accepts(@Nonnull final PsiMember psiMember, final ProcessingContext context) {
        final Pair<JavaContainerType, PsiType> pair = PersistenceCommonUtil.getContainerType(PropertyUtil.getPropertyType(psiMember));
        return JpaUtil.isPersistentObject(pair.getSecond(), EMBEDDABLE);
      }
    });
  }


}
