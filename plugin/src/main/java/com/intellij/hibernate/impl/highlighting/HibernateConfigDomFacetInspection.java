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

package com.intellij.hibernate.impl.highlighting;

import consulo.language.editor.rawHighlight.HighlightDisplayLevel;
import consulo.language.editor.DaemonCodeAnalyzer;
import consulo.language.editor.inspection.IntentionAndQuickFixAction;
import com.intellij.hibernate.localize.HibernateLocalize;
import com.intellij.hibernate.facet.HibernateFacet;
import com.intellij.hibernate.impl.facet.HibernateFacetType;
import com.intellij.hibernate.model.HibernateConstants;
import static com.intellij.hibernate.model.HibernateDescriptorsConstants.HIBERNATE_CONFIGURATION_META_DATA;
import com.intellij.hibernate.model.xml.config.HibernateConfiguration;
import com.intellij.jam.model.util.JamCommonUtil;
import consulo.language.editor.annotation.HighlightSeverity;
import consulo.language.editor.WriteCommandAction;
import consulo.codeEditor.Editor;
import consulo.module.Module;
import consulo.module.ModuleManager;
import consulo.project.Project;
import consulo.ui.ex.popup.JBPopupFactory;
import consulo.ui.ex.popup.ListPopup;
import consulo.ui.ex.popup.PopupStep;
import consulo.ui.ex.popup.BaseListPopupStep;
import consulo.util.lang.Comparing;
import consulo.virtualFileSystem.VirtualFile;
import consulo.project.ui.wm.WindowManager;
import consulo.language.psi.PsiFile;
import java.util.function.Function;
import consulo.util.collection.ContainerUtil;
import com.intellij.java.impl.util.descriptors.ConfigFile;
import consulo.xml.dom.DomFileElement;
import consulo.xml.dom.editor.BasicDomElementsInspection;
import consulo.xml.dom.editor.DomElementAnnotationHolder;
import consulo.xml.dom.editor.DomElementAnnotationsManager;
import org.jetbrains.annotations.NonNls;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import consulo.annotation.component.ExtensionImpl;
import consulo.localize.LocalizeValue;
import consulo.dataContext.DataManager;
import consulo.ui.image.Image;
import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * @author Gregory.Shrago
 */
@ExtensionImpl
public class HibernateConfigDomFacetInspection extends BasicDomElementsInspection<HibernateConfiguration, Void> {

  public HibernateConfigDomFacetInspection() {
    super(HibernateConfiguration.class);
  }

  @Nonnull
  public LocalizeValue getGroupDisplayName() {
    return HibernateLocalize.inspectionGroupNameHibernateIssues();
  }

  @Nonnull
  public LocalizeValue getDisplayName() {
    return HibernateLocalize.inspectionNameHibernateConfigurationFacet();
  }

  @Nonnull
  @NonNls
  public String getShortName() {
    return "HibernateConfigDomFacetInspection";
  }

  @Nonnull
  @Override
  public HighlightDisplayLevel getDefaultLevel() {
    return HighlightDisplayLevel.WARNING;
  }

  public void checkFileElement(final DomFileElement<HibernateConfiguration> element, final DomElementAnnotationHolder holder) {
    final Module elementModule = element.getModule();
    final VirtualFile virtualFile = element.getFile().getVirtualFile();
    assert virtualFile != null;
    boolean found = false;
    final Project project = element.getManager().getProject();
    final Module[] modulesToLookAt = elementModule == null
                                     ? ModuleManager.getInstance(project).getModules()
                                     : JamCommonUtil.getAllDependentModules(elementModule);
    if (modulesToLookAt.length == 0) return;
    // TODO: migrate to ModuleExtension-based facet discovery
    final List<HibernateFacet> facets = ContainerUtil.concat(modulesToLookAt, new Function<Module, Collection<? extends HibernateFacet>>() {
      public Collection<? extends HibernateFacet> apply(final Module module) {
        final HibernateFacet facet = HibernateFacet.getInstance(module);
        return facet != null ? Collections.singletonList(facet) : Collections.emptyList();
      }
    });
    for (HibernateFacet facet : facets) {
      for (ConfigFile configFile : facet.getDescriptors()) {
        if (HIBERNATE_CONFIGURATION_META_DATA == configFile.getMetaData() &&
            virtualFile.equals(configFile.getVirtualFile())) {
          found = true;
        }
      }
    }
    if (!found) {
      for (HibernateFacet facet : facets) {
        if (facet.getPersistenceUnits().contains(element.getRootElement().getSessionFactory())) {
          found = true;
          break;
        }
      }
    }
    if (facets.isEmpty()) {
      holder.createProblem(element, HighlightSeverity.WARNING, HibernateLocalize.warningCfgXmlNotInAnyFacet().get(), new AddFacetFix(modulesToLookAt, facets));
    }
    else if (!found) {
      holder.createProblem(element, HighlightSeverity.WARNING, HibernateLocalize.warningCfgXmlNotInAnyFacet().get(), new AddToFacetConfigurationFix(modulesToLookAt, facets));
    }
  }

  private static class AddToFacetConfigurationFix extends IntentionAndQuickFixAction {
    private final Module[] myModules;
    private final List<HibernateFacet> myFacets;

    public AddToFacetConfigurationFix(final Module[] modules, final List<HibernateFacet> facets) {
      myModules = modules;
      myFacets = facets;
    }

    @Nonnull
    public LocalizeValue getName() {
      return HibernateLocalize.nameFixAddToFacetConfiguration();
    }

    @Nonnull
    public String getFamilyName() {
      return HibernateLocalize.hibernateQuickfixFamily().get();
    }

    @Override
    public boolean startInWriteAction() {
      return false;
    }

    public void applyFix(final Project project, final PsiFile file, @Nullable final Editor editor) {
      doFix(project, file.getVirtualFile(), editor);
    }

    private void doFix(final Project project, final VirtualFile file, final Editor editor) {
      final String url = file.getUrl();
      if (myModules.length == 1 && (myFacets.isEmpty() || myFacets.size() == 1)) {
        doAddToFacet(myModules[0], myFacets.isEmpty() ? null : myFacets.get(0), url);
      }
      else {
        final ArrayList<Object> list = new ArrayList<Object>();
        list.addAll(Arrays.asList(myModules));
        list.addAll(myFacets);
        for (HibernateFacet facet : myFacets) {
          list.remove(facet.getModule());
        }
        Collections.sort(list, new Comparator<Object>() {
          public int compare(final Object o1, final Object o2) {
            return Comparing.compare(getItemName(o1), getItemName(o2));
          }
        });
        final ListPopup popup = JBPopupFactory.getInstance().createListPopup(new BaseListPopupStep<Object>(HibernateLocalize.popupTitleChooseModuleAndFacet().get(), list) {
          @Nonnull
          public String getTextFor(final Object o) {
            return getItemName(o);
          }

          public Image getIconFor(final Object o) {
            return null;
          }

          public PopupStep onChosen(final Object o, final boolean finalChoice) {
            final Module module = o instanceof Module ? (Module)o : ((HibernateFacet)o).getModule();
            final HibernateFacet facet = o instanceof Module ? null : (HibernateFacet)o;
            doAddToFacet(module, facet, url);
            return PopupStep.FINAL_CHOICE;
          }
        });
        if (editor != null) {
          popup.showInBestPositionFor(DataManager.getInstance().getDataContext(editor.getComponent()));
        }
        else {
          popup.showInScreenCoordinates(WindowManager.getInstance().getFrame(project), MouseInfo.getPointerInfo().getLocation());
        }
      }
    }

    private static String getItemName(final Object o) {
      return o instanceof Module? ((Module)o).getName() : ((HibernateFacet)o).getModule().getName()+"/"+ ((HibernateFacet)o).getName();
    }

    private void doAddToFacet(@Nonnull final Module module, @Nullable final HibernateFacet facet, final String url) {
      new WriteCommandAction.Simple(module.getProject(), getName().get()) {
        protected void run() throws Throwable {
          // TODO: register the new facet with the module extension system
          final HibernateFacet chosenFacet = facet != null ? facet : HibernateFacetType.INSTANCE.createFacet(module, HibernateFacetType.INSTANCE.getDefaultFacetName(), HibernateFacetType.INSTANCE.createDefaultConfiguration());
          chosenFacet.getConfiguration().getDescriptorsConfiguration().addConfigFile(HIBERNATE_CONFIGURATION_META_DATA, url);
        }
      }.execute();
      DomElementAnnotationsManager.getInstance(module.getProject()).dropAnnotationsCache();
      DaemonCodeAnalyzer.getInstance(module.getProject()).restart();
    }
  }

  private static class AddFacetFix extends AddToFacetConfigurationFix {
    public AddFacetFix(final Module[] modules, final List<HibernateFacet> facets) {
      super(modules, facets);
    }

    @Nonnull
    public LocalizeValue getName() {
      return HibernateLocalize.nameFixCreateFacetAndAddToFacetConfiguration();
    }
  }

}