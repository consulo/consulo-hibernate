package com.intellij.persistence.run;

import consulo.ui.ex.action.AnAction;
import consulo.ui.ex.action.AnActionEvent;
import consulo.util.lang.function.PairProcessor;

import java.util.ArrayList;
import java.util.List;

/**
 * Stub for ConsoleHistoryModel - manages command history for query consoles.
 */
public class ConsoleHistoryModel {

    private final List<String> myHistory = new ArrayList<>();
    private int myCurrentIndex = -1;

    public ConsoleHistoryModel() {
    }

    public void addToHistory(String command) {
        if (command != null && !command.trim().isEmpty()) {
            myHistory.add(command);
            myCurrentIndex = myHistory.size();
        }
    }

    public boolean hasHistory(boolean forward) {
        if (forward) {
            return myCurrentIndex < myHistory.size() - 1;
        } else {
            return myCurrentIndex > 0;
        }
    }

    public String getHistoryNext() {
        if (myCurrentIndex < myHistory.size() - 1) {
            myCurrentIndex++;
            return myHistory.get(myCurrentIndex);
        }
        return null;
    }

    public String getHistoryPrev() {
        if (myCurrentIndex > 0) {
            myCurrentIndex--;
            return myHistory.get(myCurrentIndex);
        }
        return null;
    }

    public static AnAction createHistoryAction(final ConsoleHistoryModel model, final boolean next,
                                                final PairProcessor<AnActionEvent, String> processor) {
        return new AnAction(next ? "Next History" : "Previous History") {
            @Override
            public void actionPerformed(AnActionEvent e) {
                String text = next ? model.getHistoryNext() : model.getHistoryPrev();
                processor.process(e, text);
            }
        };
    }
}
