package model.adt.heap;

import exceptions.MyException;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MyHeap<TKey, TValue> implements HeapInterface<TKey, TValue>{

    private Map<TKey, TValue> heap;
    int firstAvailablePosition = 1;

    public MyHeap(){
        this.heap = new HashMap<>();
    }

    @Override
    public void setContent(Map<TKey, TValue> newHeap) {
        this.heap = newHeap;
    }

    @Override
    public void setFirstAvailablePos() {
        this.firstAvailablePosition = this.firstAvailablePosition + 1;
    }

    @Override
    public int getFirstAvailablePos() {
        int positionCopy = this.firstAvailablePosition;
        this.setFirstAvailablePos();
        return positionCopy;
    }

    @Override
    public boolean containsKey(TKey key) {
        return this.heap.containsKey(key);
    }

    @Override
    public void update(TKey key, TValue newValue) throws MyException {
        this.heap.replace(key, newValue);
    }

    @Override
    public void insert(TKey key, TValue newValue) throws MyException {
        this.heap.put(key, newValue);
    }

    @Override
    public TValue remove(TKey key) {
        return this.heap.remove(key);
    }

    @Override
    public Collection getAllValues() {
        return this.heap.values();
    }

    @Override
    public Set getAllKeys() {
        return this.heap.keySet();
    }

    @Override
    public TValue getValue(TKey key) {
        return this.heap.get(key);
    }

    @Override
    public Map<TKey, TValue> getContent() {
        return this.heap;
    }

    @Override
    public String toString() {
        String representation = "[ \n";
        Collection<TKey> allKeys = heap.keySet();
        for (TKey key: allKeys) {
            representation += (key.toString() + ": " + heap.get(key) + ", \n");
        }
        representation += "]";
        return representation;
    }
}
