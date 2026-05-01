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

import consulo.ui.ex.action.AnActionEvent;
import consulo.ui.ex.action.Presentation;
import consulo.language.psi.PsiElement;
import consulo.xml.dom.NameValue;
import consulo.xml.dom.PrimaryKey;
import consulo.language.util.IncorrectOperationException;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import consulo.ui.image.Image;
import java.util.Collection;
import java.util.Map;

/**
 * @author Gregory.Shrago
 */
public abstract class AbstractPersistenceAction<V extends PersistenceManipulator> implements PersistenceAction {
  
  private final Presentation myPresentation;
  private final V myManipulator;
  private final String myActionName;

  public AbstractPersistenceAction(final V manipulator, final String actionName, final String actionTitle,
                                   final Image icon) {
    myManipulator = manipulator;
    myActionName = actionName;
    myPresentation = new Presentation();
    myPresentation.setText(actionTitle);
    myPresentation.setIcon(icon);
  }

  public V getManipulator() {
    return myManipulator;
  }

  public Object getActionKey() {
    return getActionName();
  }

  @PrimaryKey
  @NameValue
  public String getActionName() {
    return myActionName;
  }

  @Nonnull
  public Presentation getPresentation() {
    return myPresentation;
  }

  @Nullable
  public PersistenceManipulator getActiveManipulator() {
    return getPresentation().isEnabled()? getManipulator() : null;
  }

  public void update(final AnActionEvent e) {
  }


  public boolean preInvoke(final UserResponse response) {
    return true;
  }

  public final void putTargetElement(final Map<PersistenceAction, PsiElement> targetMap) {
    final PsiElement targetElement = getTargetElement();
    if (targetElement != null) {
      assert !targetMap.containsValue(targetElement) : "target element locked";
      targetMap.put(this, targetElement);
    }
  }

  public boolean postInvoke(final PersistenceAction action, final UserResponse response) {
    return true;
  }

  @Nullable
  protected abstract PsiElement getTargetElement();

  public abstract void invokeAction(@Nonnull final Collection<PsiElement> result) throws IncorrectOperationException;

}
