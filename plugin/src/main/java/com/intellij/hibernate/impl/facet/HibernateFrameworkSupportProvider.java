/*
 * Copyright (c) 2000-2007 JetBrains s.r.o. All Rights Reserved.
 */

package com.intellij.hibernate.impl.facet;

import com.intellij.hibernate.localize.HibernateLocalize;
import com.intellij.hibernate.facet.HibernateFacet;
import com.intellij.hibernate.model.HibernateDescriptorsConstants;
import consulo.fileTemplate.FileTemplate;
import consulo.fileTemplate.FileTemplateManager;
import consulo.fileTemplate.FileTemplateUtil;
import com.intellij.jpa.facet.JpaFrameworkSupportProvider;
import consulo.logging.Logger;
import consulo.module.Module;
import consulo.language.util.ModuleUtilCore;
import consulo.module.content.ModuleRootManager;
import consulo.virtualFileSystem.VirtualFile;
import consulo.language.psi.PsiDirectory;
import consulo.language.psi.PsiElement;
import consulo.language.psi.PsiFile;
import consulo.language.psi.PsiManager;
import consulo.xml.language.psi.XmlFile;
import org.jetbrains.annotations.NonNls;
import jakarta.annotation.Nonnull;

/**
 * @author Gregory.Shrago
 *
 * TODO: This class previously extended FacetBasedFrameworkSupportProvider which is no longer
 * available in Consulo 3. It needs to be reimplemented using the new framework support API.
 */
public class HibernateFrameworkSupportProvider {
  private static final Logger LOG = Logger.getInstance("com.intellij.hibernate.facet.HibernateFrameworkSupportProvider");

  public String getTitle() {
    return HibernateLocalize.frameworkTitleHibernate().get();
  }

  /**
   * Add Hibernate framework support to a module by creating the facet and optionally
   * generating a main class and config file.
   */
  public void addSupport(@Nonnull final Module module, boolean createMainClass, boolean dbImport) {
    // TODO: migrate to ModuleExtension-based facet creation
    final HibernateFacet facet = HibernateFacetType.INSTANCE.createFacet(
        module,
        HibernateFacetType.INSTANCE.getDefaultFacetName(),
        HibernateFacetType.INSTANCE.createDefaultConfiguration()
    );

    final Runnable runnable = new Runnable() {
      public void run() {
        try {
          if (createMainClass) {
            final VirtualFile[] sourceRoots = ModuleRootManager.getInstance(module).getSourceRoots();
            if (sourceRoots.length > 0) {
              final PsiDirectory directory = PsiManager.getInstance(module.getProject()).findDirectory(sourceRoots[0]);
              final String fileName = HibernateDescriptorsConstants.HIBERNATE_CONFIGURATION_META_DATA.getFileName();
              if (directory != null) {
                ModuleUtilCore.findModuleForPsiElement(directory);
                PsiElement configElement = directory.findFile(fileName);
                if (configElement == null) {
                  final FileTemplate configTemplate = FileTemplateManager.getInstance().getJ2eeTemplate(HibernateDescriptorsConstants.HIBERNATE_CONFIGURATION_META_DATA.getDefaultVersion().getTemplateName());
                  configElement = FileTemplateUtil.createFromTemplate(configTemplate, fileName, (java.util.Properties)null, directory);
                }
                if (configElement instanceof XmlFile) {
                  final XmlFile xmlFile = (XmlFile)configElement;
                  final String url = xmlFile.getVirtualFile().getUrl();
                  facet.getDescriptorsContainer().getConfiguration().addConfigFile(HibernateDescriptorsConstants.HIBERNATE_CONFIGURATION_META_DATA, url);
                }
                @NonNls
                final String mainFileName = "Main.java";
                PsiElement mainElement = directory.findFile(mainFileName);
                if (mainElement == null) {
                  final FileTemplate mainTemplate = FileTemplateManager.getInstance().getJ2eeTemplate(HibernateVersion.Hibernate_3_2_0.getMainTemplateName());
                  mainElement = FileTemplateUtil.createFromTemplate(mainTemplate, mainFileName, (java.util.Properties)null, directory);
                }
              }
            }
          }
          if (dbImport) {
            JpaFrameworkSupportProvider.scheduleDbImport(module, facet);
          }
        }
        catch (Exception e) {
          LOG.error(e);
        }
      }
    };
    JpaFrameworkSupportProvider.scheduleRunnable(module, runnable);
  }
}
