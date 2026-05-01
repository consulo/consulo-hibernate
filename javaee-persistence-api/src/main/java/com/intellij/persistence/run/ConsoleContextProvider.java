package com.intellij.persistence.run;

import consulo.annotation.component.ComponentScope;
import consulo.annotation.component.ExtensionAPI;
import consulo.component.extension.ExtensionPointName;
import consulo.execution.runner.ExecutionEnvironment;
import consulo.language.psi.PsiElement;
import consulo.process.ExecutionException;
import consulo.process.event.ProcessListener;
import consulo.execution.ui.console.TextConsoleBuilder;
import consulo.module.Module;
import consulo.project.Project;
import consulo.ui.ex.awt.SimpleColoredComponent;
import consulo.ui.ex.action.AnAction;
import consulo.ui.image.Image;
import consulo.util.dataholder.Key;
import org.jdom.Element;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

/**
 * Stub for ConsoleContextProvider - provides context for persistence query consoles.
 */
@ExtensionAPI(ComponentScope.PROJECT)
public interface ConsoleContextProvider {
    ExtensionPointName<ConsoleContextProvider> EP_NAME = ExtensionPointName.create(ConsoleContextProvider.class);

    Key<RunContext> RUN_CONTEXT = Key.create("ConsoleContextProvider.RUN_CONTEXT");

    @Nonnull
    String getId();

    String getDisplayName();

    Image getIcon();

    ConsoleContext createConsoleContext();

    boolean isAvailable(Project project);

    void setupContextPresentation(Project project, ConsoleContext context, SimpleColoredComponent component);

    boolean editContext(ConsoleContext context, Project project);

    interface ConsoleContext {
        Element getState();

        void loadState(Element state);

        @Nonnull
        ConsoleContextProvider getProvider();

        RunContext createRunContext(ExecutionEnvironment env) throws ExecutionException;

        ConsoleContext clone();
    }

    interface RunContext {
        @Nonnull
        Module getModule();

        ProcessListener getProcessListener();

        String getMainClassName();

        String[] getClassPath();

        String getJarPath();

        TextConsoleBuilder getConsoleBuilder();

        AnAction[] getActions();

        RunContext checkRunContext() throws ExecutionException;
    }
}
