package com.intellij.persistence.util;

import java.util.*;

/**
 * A bidirectional multi-map that maintains both key-to-values and value-to-keys mappings.
 * Replacement for consulo.ide.impl.idea.util.containers.BidirectionalMultiMap which is not
 * available in plugin modules (impl package).
 *
 * @param <K> key type
 * @param <V> value type
 */
public class BidirectionalMultiMap<K, V> {
  private final Map<K, Set<V>> myKeyToValues = new LinkedHashMap<>();
  private final Map<V, Set<K>> myValueToKeys = new LinkedHashMap<>();

  public void put(K key, V value) {
    myKeyToValues.computeIfAbsent(key, k -> new LinkedHashSet<>()).add(value);
    myValueToKeys.computeIfAbsent(value, v -> new LinkedHashSet<>()).add(key);
  }

  /**
   * Returns all keys in this map.
   */
  public Set<K> getKeys() {
    return Collections.unmodifiableSet(myKeyToValues.keySet());
  }

  /**
   * Returns all values associated with the given key.
   */
  public Set<V> getValues(K key) {
    Set<V> values = myKeyToValues.get(key);
    return values != null ? Collections.unmodifiableSet(values) : Collections.emptySet();
  }

  /**
   * Returns all keys associated with the given value (reverse lookup).
   */
  public Set<K> getKeys(V value) {
    Set<K> keys = myValueToKeys.get(value);
    return keys != null ? Collections.unmodifiableSet(keys) : Collections.emptySet();
  }

  public boolean containsKey(K key) {
    return myKeyToValues.containsKey(key);
  }

  public boolean containsValue(V value) {
    return myValueToKeys.containsKey(value);
  }

  public void clear() {
    myKeyToValues.clear();
    myValueToKeys.clear();
  }

  public boolean isEmpty() {
    return myKeyToValues.isEmpty();
  }
}
