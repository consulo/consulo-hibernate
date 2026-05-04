package consulo.hibernate.maven.impl;

import consulo.annotation.component.ExtensionImpl;
import consulo.maven.importing.MavenImporterFromDependency;
import consulo.maven.rt.server.common.model.MavenArtifact;
import consulo.module.Module;
import consulo.module.content.layer.ModifiableRootModel;
import consulo.module.extension.MutableModuleExtension;
import org.jetbrains.idea.maven.importing.MavenModifiableModelsProvider;
import org.jetbrains.idea.maven.importing.MavenRootModelAdapter;
import org.jetbrains.idea.maven.project.MavenProject;
import org.jetbrains.idea.maven.project.MavenProjectChanges;
import org.jetbrains.idea.maven.project.MavenProjectsProcessorTask;
import org.jetbrains.idea.maven.project.MavenProjectsTree;

import java.util.List;
import java.util.Map;

/**
 * Enables the {@code hibernate} module extension whenever a Maven module pulls in
 * a Hibernate ORM dependency.
 *
 * @author VISTALL
 * @since 2026-05-04
 */
@ExtensionImpl
public class HibernateMavenImporter extends MavenImporterFromDependency {

    private static final String HIBERNATE_GROUP = "org.hibernate.orm";
    private static final String HIBERNATE_ARTIFACT = "hibernate-core";
    private static final String LEGACY_HIBERNATE_GROUP = "org.hibernate";

    public HibernateMavenImporter() {
        super(HIBERNATE_GROUP, HIBERNATE_ARTIFACT);
    }

    @Override
    public boolean isApplicable(MavenProject mavenProject) {
        if (super.isApplicable(mavenProject)) {
            return true;
        }
        // Hibernate < 6 used the org.hibernate group.
        List<MavenArtifact> legacy = mavenProject.findDependencies(LEGACY_HIBERNATE_GROUP, HIBERNATE_ARTIFACT);
        return !legacy.isEmpty();
    }

    @Override
    public void preProcess(Module module,
                           MavenProject mavenProject,
                           MavenProjectChanges mavenProjectChanges,
                           MavenModifiableModelsProvider mavenModifiableModelsProvider) {
    }

    @Override
    public void process(MavenModifiableModelsProvider mavenModifiableModelsProvider,
                        Module module,
                        MavenRootModelAdapter mavenRootModelAdapter,
                        MavenProjectsTree mavenProjectsTree,
                        MavenProject mavenProject,
                        MavenProjectChanges mavenProjectChanges,
                        Map<MavenProject, String> map,
                        List<MavenProjectsProcessorTask> list) {
        ModifiableRootModel rootModel = mavenModifiableModelsProvider.getRootModel(module);
        MutableModuleExtension extension = rootModel.getExtensionWithoutCheck("hibernate");
        if (extension != null) {
            extension.setEnabled(true);
        }
    }
}
