/*
 * Copyright 2000-2007 JetBrains s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.intellij.persistence.model.manipulators;

import java.util.Collection;

import jakarta.annotation.Nonnull;

import com.intellij.jam.model.common.CommonModelElement;
import consulo.util.lang.Comparing;
import consulo.language.psi.PsiElement;
import consulo.util.collection.ContainerUtil;

/**
 * @author Gregory.Shrago
 */
public abstract class AbstractPersistenceManipulator<T> implements PersistenceManipulator<T> {

  private final T myTarget;

  public AbstractPersistenceManipulator(final T target) {
    myTarget = target;
  }

  public T getManipulatorTarget() {
    return myTarget;
  }

  public void addAffectedElements(@Nonnull final Collection<PsiElement> affectedElements) {
    final T target = getManipulatorTarget();
    if (target instanceof CommonModelElement) {
      ContainerUtil.addIfNotNull(affectedElements, ((CommonModelElement)target).getIdentifyingPsiElement());
    }
  }

  public boolean equals(final Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    final AbstractPersistenceManipulator that = (AbstractPersistenceManipulator)o;

    if (!Comparing.equal(getManipulatorTarget(), that.getManipulatorTarget())) return false;

    return true;
  }

  public int hashCode() {
    final T target = getManipulatorTarget();
    return target != null? target.hashCode() : 0;
  }

}
