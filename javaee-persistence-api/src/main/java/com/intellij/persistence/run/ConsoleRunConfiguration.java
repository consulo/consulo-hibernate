package com.intellij.persistence.run;

import consulo.codeEditor.Editor;
import consulo.execution.configuration.RunProfile;
import consulo.project.Project;
import consulo.ui.ex.action.AnActionEvent;
import consulo.application.util.function.Processor;
import jakarta.annotation.Nullable;

/**
 * Stub for ConsoleRunConfiguration - run configuration for persistence query consoles.
 * Implements RunProfile so it can be used with ExecutionEnvironment.
 */
public class ConsoleRunConfiguration implements RunProfile {

    public String USER_CFG_CLASS;

    private ConsoleContextProvider.ConsoleContext myConsoleContext;
    private String myName;
    private Project myProject;

    public ConsoleRunConfiguration() {
    }

    public ConsoleContextProvider.ConsoleContext getConsoleContext() {
        return myConsoleContext;
    }

    public void setConsoleContext(ConsoleContextProvider.ConsoleContext context) {
        myConsoleContext = context;
    }

    public String getName() {
        return myName;
    }

    public void setName(String name) {
        myName = name;
    }

    public Project getProject() {
        return myProject;
    }

    public void setProject(Project project) {
        myProject = project;
    }

    public ConsoleRunConfiguration clone() {
        ConsoleRunConfiguration copy = new ConsoleRunConfiguration();
        copy.myConsoleContext = myConsoleContext;
        copy.myName = myName;
        copy.myProject = myProject;
        copy.USER_CFG_CLASS = USER_CFG_CLASS;
        return copy;
    }

    public static void chooseConfigurationType(Project project, Editor editor, AnActionEvent event,
                                                Processor<ConsoleRunConfiguration> processor) {
        // TODO: implement configuration type chooser
        ConsoleRunConfiguration config = new ConsoleRunConfiguration();
        config.myProject = project;
        processor.process(config);
    }

    @Override
    public consulo.execution.configuration.RunProfileState getState(consulo.execution.executor.Executor executor, consulo.execution.runner.ExecutionEnvironment env) {
        return null;
    }

    @Override
    public consulo.ui.image.Image getIcon() {
        return null;
    }
}
