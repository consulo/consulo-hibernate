package com.intellij.persistence.run;

import consulo.process.event.ProcessEvent;
import consulo.process.event.ProcessListener;
import consulo.util.dataholder.Key;
import org.jdom.Element;

public abstract class ConsoleOutputProcessAdapter implements ProcessListener {
    @Override
    public void processTerminated(ProcessEvent event) {
    }

    @Override
    public void startNotified(ProcessEvent event) {
    }

    @Override
    public void processWillTerminate(ProcessEvent event, boolean willBeDestroyed) {
    }

    @Override
    public void onTextAvailable(ProcessEvent event, Key outputType) {
    }

    public abstract AbstractQueryLanguageConsole.OutputHandler getOutputHandler();

    public void resetCurrentResultNumber() {
    }

    protected int handleDefaultOutput(Element root) {
        return 0;
    }
}
