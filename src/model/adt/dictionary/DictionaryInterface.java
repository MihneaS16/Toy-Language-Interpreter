package model.adt.dictionary;

import exceptions.MyException;

import java.util.Collection;
import java.util.Dictionary;
import java.util.Map;
import java.util.Set;

public interface DictionaryInterface<TKey, TValue> {
    public boolean containsKey(TKey key);
    public void update(TKey key, TValue newValue) throws MyException;
    public void insert(TKey key, TValue newValue) throws MyException;
    public TValue remove(TKey key);
    public Collection<TValue> getAllValues();
    public Set<TKey> getAllKeys();
    public TValue getValue(TKey key);
    public DictionaryInterface<TKey, TValue> copy() throws MyException;

}
