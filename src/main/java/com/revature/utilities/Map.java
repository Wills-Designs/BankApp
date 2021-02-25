package com.revature.utilities;
import java.util.Arrays;

/**
 * custom map data structure implementation
 * @param <K>
 * @param <V>
 */
public class Map<K, V> {

    private int size;
    private final int DEFAULT_CAPACITY = 16;

    @SuppressWarnings("unchecked")
    private Entry<K, V>[] entries = new Entry[DEFAULT_CAPACITY];

    /**
     * loops through to check if the key is in the map
     * @param key
     * @return
     */
    public boolean containsKey(K key) {
        for (int i = 0; i < size; i++) {
            if (entries[i].key == null) {
                if (entries[i].key == key) {
                    return true;
                }
            } else if (entries[i].key.equals(key)) {
                return true;
            }
        }
        return false;
    }

    /**
     * list of all the keys inside the list
     * @return
     */
    public LinkedList<K> keyList() {
        LinkedList<K> keyList = new LinkedList<>();
        for (int i = 0; i < size; i++) {
            keyList.insert(entries[i].key);
        }
        return keyList;
    }

    public V get(K key) {
        for (int i = 0; i < size; i++) {
            if (entries[i].key == null) {
                if (entries[i].key == key) {
                    return entries[i].value;
                }
            } else if (entries[i].key.equals(key)) {
                return entries[i].value;
            }
        }
        return null;
    }

    public V getOrDefault(K key, V defaultValue) {

        if (!containsKey(key)) {
            return defaultValue;
        }
        return get(key);
    }

    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Adds a new entry to the map using the provided key and value. Returns the
     * value previously associated with the key. If the key was not in the map prior,
     * returns null
     * @param key
     * @param value
     * @return
     */
    public V put(K key, V value) {

        V previousValue = null;

        boolean wasInserted = true;
        for (int i = 0; i < size; i++) {

            if (entries[i].key == null) {
                if (entries[i].key == key) {
                    previousValue = entries[i].value;
                    entries[i].value = value;
                    wasInserted = false;
                    break;
                }
            } else if (entries[i].key.equals(key)) {
                previousValue = entries[i].value;
                entries[i].value = value;
                wasInserted = false;
                break;
            }

        }

        if (wasInserted) {
            ensureCapacity();
            entries[size++] = new Entry<>(key, value);
        }

        return previousValue;

    }

    /**
     * removes a key from the map
     * @param key
     */
    public void remove(K key) {

        boolean wasRemoved = false;
        for (int i = 0; i < size; i++) {
            if (entries[i].key == null) {
                if (entries[i].key == key) {
                    entries[i] = entries[size - 1];
                    size--;
                    wasRemoved = true;
                }
            } else if (entries[i].key.equals(key)) {
                entries[i] = entries[size - 1];
                size--;
                wasRemoved = true;
            }
        }

        if (wasRemoved) {
            condenseArray();
        }

    }

    /**
     * returns the size of the map
     * @return
     */
    public int size() {
        return size;
    }

    /** checks if map is full, doubles if it is, helpful for when adding a new entry */
    private void ensureCapacity() {
        if (size == entries.length) {
            entries = Arrays.copyOf(entries, entries.length * 2);
        }
    }

    /** condenses array, helpful after using the remove method */
    private void condenseArray() {
        if (size * 2 < entries.length) {
            entries = Arrays.copyOf(entries, entries.length / 2);
        }
    }

    private static class Entry<K, V> {
        private final K key;
        private V value;

        public Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public String toString() {
            return "Entry{" +
                    "key=" + key +
                    ", value=" + value +
                    '}';
        }

    }

}