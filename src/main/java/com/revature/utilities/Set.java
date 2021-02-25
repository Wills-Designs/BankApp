package com.revature.utilities;
import java.lang.reflect.Array;

/**
 * custom implemented set data structure, does not use java.util
 *  * used to hold the screens in the app
 * @param <T>
 */
public class Set<T> {

    private Map<T, Object> map;

    public Set() {
        this.map = new Map<>();
    }

    public boolean add(T data) {
        return this.map.put(data, data.hashCode()) == null;
    }

    public boolean contains(T data) {
        return this.map.containsKey(data);
    }

    public boolean isEmpty() {
        return this.map.isEmpty();
    }

    public boolean remove(T data) {
        this.map.remove(data);
        return true;
    }

    /**
     * returns size of set
     * @return
     */
    public int size() {
        return this.map.size();
    }

    @SuppressWarnings("unchecked")
    public T[] toArray(Class<T> clazz) {
        LinkedList<T> keys = this.map.keyList();
        T[] keyArr = (T[]) Array.newInstance(clazz, size());
        for (int i = 0; i < size(); i++) {
            T t = keys.pop();
            keyArr[i] = t;
        }
        return keyArr;
    }

}