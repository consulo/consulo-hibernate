package com.intellij.persistence.database.psi;

import java.util.EventListener;

/**
 * Listener for modification tracker changes.
 *
 * @author Gregory.Shrago
 */
public interface ModificationTrackerListener<T> extends EventListener {
  void modificationCountChanged(T source);
}
