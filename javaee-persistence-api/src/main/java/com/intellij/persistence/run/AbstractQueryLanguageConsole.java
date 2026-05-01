package com.intellij.persistence.run;

import consulo.disposer.Disposable;
import consulo.execution.ui.layout.RunnerLayoutUi;
import consulo.execution.ui.console.ConsoleView;
import consulo.execution.ui.console.ConsoleViewContentType;
import consulo.execution.ui.console.TextConsoleBuilderFactory;
import consulo.execution.ui.console.TextConsoleBuilder;
import consulo.logging.Logger;
import consulo.project.Project;
import consulo.ui.ex.action.ActionGroup;
import jakarta.annotation.Nonnull;

import javax.swing.*;

/**
 * Stub for AbstractQueryLanguageConsole - base class for query language consoles.
 */
public abstract class AbstractQueryLanguageConsole implements Disposable {

    protected static final Logger LOG = Logger.getInstance(AbstractQueryLanguageConsole.class);
    protected static final String ID_INPUT = "input";
    protected static final String ID_OUTPUT = "output";

    private final Project myProject;
    private final String myTitle;
    private ConsoleView myConsoleView;
    private RunnerLayoutUi myUi;
    private TextConsoleBuilder myConsoleBuilder;
    private TabularDataHandler myTabularDataHandler;

    public AbstractQueryLanguageConsole(Project project, String title) {
        myProject = project;
        myTitle = title;
        myConsoleBuilder = TextConsoleBuilderFactory.getInstance().createBuilder(project);
        myTabularDataHandler = new TabularDataHandler();
    }

    public Project getProject() {
        return myProject;
    }

    public ConsoleView getConsoleView() {
        if (myConsoleView == null) {
            myConsoleView = myConsoleBuilder.getConsole();
        }
        return myConsoleView;
    }

    public RunnerLayoutUi getUi() {
        return myUi;
    }

    public void setUi(RunnerLayoutUi ui) {
        myUi = ui;
    }

    public TextConsoleBuilder getTextConsoleBuilder() {
        return myConsoleBuilder;
    }

    public TabularDataHandler getTabularDataHandler() {
        return myTabularDataHandler;
    }

    protected OutputHandler createOutputHandler() {
        return new OutputHandler();
    }

    public void runTask(Runnable task) {
        task.run();
    }

    protected void printError(Throwable throwable) {
        getConsoleView().print(throwable.getMessage() + "\n", ConsoleViewContentType.ERROR_OUTPUT);
    }

    protected abstract void buildConsoleUi(ConsoleView consoleView);

    protected abstract JComponent getEditorComponent();

    @Nonnull
    protected abstract String getConsoleId();

    protected JComponent wrap(JComponent component) {
        return component;
    }

    @Override
    public void dispose() {
    }

    public static String getConsoleJarPath(Class<?> clazz, String mainClass) {
        return consulo.util.io.ClassPathUtil.getJarPathForClass(clazz);
    }

    public static String encodeMap(java.util.Map<String, String> map) {
        if (map == null || map.isEmpty()) return "";
        StringBuilder sb = new StringBuilder();
        for (java.util.Map.Entry<String, String> entry : map.entrySet()) {
            if (sb.length() > 0) sb.append("&");
            sb.append(entry.getKey()).append("=").append(entry.getValue());
        }
        return sb.toString();
    }

    public static class OutputHandler {
        public void error(Object context, Throwable e) {
            // stub: log error
        }
    }

    public static class TabularDataHandler {
        public void resetOutputTabCounter() {
        }
    }
}
