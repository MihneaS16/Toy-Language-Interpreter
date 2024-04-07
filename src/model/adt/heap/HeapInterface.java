package model.adt.heap;

import exceptions.MyException;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public interface HeapInterface <TKey, TValue> {

    public void setContent(Map<TKey, TValue> newHeap);
    public void setFirstAvailablePos();
    public int getFirstAvailablePos();
    public boolean containsKey(TKey key);
    public void update(TKey key, TValue newValue) throws MyException;
    public void insert(TKey key, TValue newValue) throws MyException;
    public TValue remove(TKey key);
    public Collection<TValue> getAllValues();
    public Set<TKey> getAllKeys();
    public TValue getValue(TKey key);
    public Map<TKey, TValue> getContent();
}
