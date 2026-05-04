/*
 * Copyright (c) 2000-2007 JetBrains s.r.o. All Rights Reserved.
 */

package com.intellij.hibernate.impl.intentions;

import consulo.language.editor.intention.IntentionAction;
import com.intellij.hibernate.localize.HibernateLocalize;
import com.intellij.hibernate.impl.engine.HibernateEngine;
import com.intellij.hibernate.model.xml.mapping.HbmSqlQuery;
import com.intellij.jam.JamMessages;
import com.intellij.javaee.model.common.persistence.mapping.NamedNativeQuery;
import com.intellij.jam.model.util.JamCommonUtil;
import com.intellij.jpa.QueryReferencesUtil;
import com.intellij.jpa.util.JpaUtil;
import consulo.codeEditor.Editor;
import consulo.dataContext.DataManager;
import consulo.localize.LocalizeValue;
import consulo.module.Module;
import consulo.language.util.ModuleUtilCore;
import consulo.project.Project;
import consulo.ui.ex.popup.JBPopupFactory;
import consulo.util.lang.Comparing;
import consulo.util.lang.Pair;
import consulo.util.lang.ref.Ref;
import consulo.util.lang.StringUtil;
import com.intellij.persistence.facet.PersistenceFacetBase;
import com.intellij.persistence.facet.PersistenceFacetConfiguration;
import com.intellij.persistence.model.PersistencePackage;
import com.intellij.persistence.model.PersistenceQuery;
import com.intellij.persistence.util.PersistenceCommonUtil;
import com.intellij.java.language.psi.*;
import consulo.language.psi.PsiElement;
import consulo.language.psi.PsiFile;
import consulo.language.psi.PsiReference;
import consulo.language.psi.PsiWhiteSpace;
import consulo.language.psi.util.PsiTreeUtil;
import consulo.xml.language.psi.XmlTag;
import com.intellij.ql.psi.QlFile;
import consulo.ui.ex.awt.ColoredListCellRenderer;
import consulo.ui.ex.SimpleTextAttributes;
import consulo.language.util.IncorrectOperationException;
import consulo.xml.dom.DomElement;
import consulo.xml.dom.DomManager;
import consulo.xml.dom.DomUtil;
import consulo.xml.dom.ElementPresentationManager;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author Gregory.Shrago
 */
public abstract class QueryIntentionActionBase implements IntentionAction {
  @Nonnull
  public LocalizeValue getText() {
    return getFamilyName();
  }

  @Nonnull
  protected LocalizeValue getFamilyName() {
    return HibernateLocalize.hibernateQuickfixFamily();
  }

  public boolean isAvailable(@Nonnull final Project project, final Editor editor, final PsiFile file) {
    if (file == null) return false;
    final PsiElement element = file.findElementAt(editor.getCaretModel().getOffset());
    if (element == null || element instanceof PsiWhiteSpace) return false;
    if (!(JamCommonUtil.isPlainJavaFile(file) || JamCommonUtil.isPlainXmlFile(file))) return false;
    final Module module = ModuleUtilCore.findModuleForPsiElement(file);
    if (module == null || PersistenceCommonUtil.getAllPersistenceFacetsWithDependencies(module).isEmpty()) return false;
    if (!HibernateEngine.isAvailable(module)) return false;
    return findQuery(editor, file, element) != null;
  }

  @Nullable
  private static String findQuery(final Editor editor, final PsiFile file, final PsiElement element) {
    final String result;
    if (JamCommonUtil.isPlainJavaFile(file)) {
      final String query;
      final PsiReference reference = file.findReferenceAt(editor.getCaretModel().getOffset());
      // Simplified: skip QueryReference resolution since the inner classes were removed
      final PsiLiteralExpression expression = PsiTreeUtil.getParentOfType(element, PsiLiteralExpression.class);
      if (expression != null) {
        Object value = expression.getValue();
        query = value instanceof String ? (String) value : null;
      }
      else {
        query = null;
      }
      result = query == null? null : StringUtil.unescapeStringCharacters(query);
    }
    else {
      final String query;
      final XmlTag xmlTag = PsiTreeUtil.getParentOfType(element, XmlTag.class);
      final DomElement domElement = xmlTag == null? null : DomManager.getDomManager(file.getProject()).getDomElement(xmlTag);
      final PersistenceQuery domQuery = DomUtil.getParentOfType(domElement, PersistenceQuery.class, false);
      if (!(domQuery instanceof NamedNativeQuery || domQuery instanceof HbmSqlQuery)) {
        query = domQuery == null? null : domQuery.getQuery().getStringValue();
      }
      else {
        query = null;
      }
      result = query == null? null : StringUtil.unescapeXml(query);
    }
    return result;
  }

  @SuppressWarnings("unchecked")
  public void invoke(@Nonnull final Project project, final Editor editor, final PsiFile file) throws IncorrectOperationException {
    final Module module = ModuleUtilCore.findModuleForPsiElement(file);
    final List<Pair<PersistenceFacetBase<PersistenceFacetConfiguration, PersistencePackage>, PersistencePackage>> pairs =
      new ArrayList<Pair<PersistenceFacetBase<PersistenceFacetConfiguration, PersistencePackage>, PersistencePackage>>();
    for (Object ext : PersistenceCommonUtil.getAllPersistenceFacetsWithDependencies(module)) {
      if (!(ext instanceof PersistenceFacetBase)) continue;
      final PersistenceFacetBase<PersistenceFacetConfiguration, PersistencePackage> facet =
        (PersistenceFacetBase<PersistenceFacetConfiguration, PersistencePackage>) ext;
      if (facet.getQlLanguage() == null) continue;
      for (PersistencePackage unit : facet.getPersistenceUnits()) {
        pairs.add(Pair.create(facet, unit));
      }
    }
    if (pairs.isEmpty()) return;
    final String query = findQuery(editor, file, file.findElementAt(editor.getCaretModel().getOffset()));
    if (pairs.size() == 1) {
      invokeInner(project, editor, pairs.get(0).getFirst(), pairs.get(0).getSecond(), query);
    }
    else {
      Collections.sort(pairs, new Comparator<Pair<PersistenceFacetBase<PersistenceFacetConfiguration, PersistencePackage>, PersistencePackage>>() {
        public int compare(final Pair<PersistenceFacetBase<PersistenceFacetConfiguration, PersistencePackage>, PersistencePackage> o1,
                           final Pair<PersistenceFacetBase<PersistenceFacetConfiguration, PersistencePackage>, PersistencePackage> o2) {
          final int modules = Comparing.compare(o1.getFirst().getModule().getName(), o2.getFirst().getModule().getName());
          if (modules != 0) return modules;
          return Comparing.compare(o1.getSecond().getName().getValue(), o2.getSecond().getName().getValue());
        }
      });
      final JList list = new JList(pairs.toArray());
      list.setCellRenderer(new ColoredListCellRenderer() {
        protected void customizeCellRenderer(final JList list,
                                             final Object value, final int index, final boolean selected, final boolean hasFocus) {
          final Pair<PersistenceFacetBase<PersistenceFacetConfiguration, PersistencePackage>, PersistencePackage> pair =
            (Pair<PersistenceFacetBase<PersistenceFacetConfiguration, PersistencePackage>, PersistencePackage>)value;
          setIcon(ElementPresentationManager.getIcon(pair.getSecond()));
          final String unitName = pair.getSecond().getName().getValue();
          final String text = pair.getFirst().getModule().getName() + "/" +
                               (StringUtil.isNotEmpty(unitName) ? unitName : JamMessages.message("unnamed.element.presentable.name"));
          append(text, SimpleTextAttributes.REGULAR_ATTRIBUTES);
        }
      });
      JBPopupFactory.getInstance().createPopupChooserBuilder(pairs).
        setTitle(HibernateLocalize.titleChooseSessionFactory().get()).
        setRenderer(list.getCellRenderer()).
        setItemChosenCallback(new java.util.function.Consumer<Pair<PersistenceFacetBase<PersistenceFacetConfiguration, PersistencePackage>, PersistencePackage>>() {
          public void accept(Pair<PersistenceFacetBase<PersistenceFacetConfiguration, PersistencePackage>, PersistencePackage> pair) {
            if (pair != null) {
              invokeInner(project, editor, pair.getFirst(), pair.getSecond(), query);
            }
          }
        }).
        createPopup().showInBestPositionFor(DataManager.getInstance().getDataContext(editor.getComponent()));
    }
  }

  protected abstract void invokeInner(final Project project, final Editor editor, final PersistenceFacetBase<PersistenceFacetConfiguration, PersistencePackage> curFacet,
                                      final PersistencePackage curUnit,
                                      final String query);


  public boolean startInWriteAction() {
    return false;
  }

}
