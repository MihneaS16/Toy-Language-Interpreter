package model.adt.dictionary;

import exceptions.MyException;

import java.util.*;

public class MyDictionary<TKey, TValue> implements DictionaryInterface<TKey, TValue> {

    private final Map<TKey, TValue> dictionary;

    public MyDictionary() {
        this.dictionary = new HashMap<TKey, TValue>();
    }

    @Override
    public String toString() {
        String representation = "[ \n";
        Collection<TKey> allKeys = dictionary.keySet();
        for (TKey key: allKeys) {
            representation += (key.toString() + ": " + dictionary.get(key) + ", \n");
        }
        representation += "]";
        return representation;
    }

    @Override
    public boolean containsKey(TKey key) {
        return dictionary.containsKey(key);
    }

    @Override
    public void update(TKey key, TValue newValue) throws MyException {
        if (!this.dictionary.containsKey(key))
            throw new MyException("Key not in dictionary");
        this.dictionary.replace(key, newValue);
    }

    @Override
    public void insert(TKey key, TValue newValue) throws MyException {
        if (this.dictionary.containsKey(key))
            throw new MyException("Key already in dictionary");
        this.dictionary.put(key, newValue);
    }

    @Override
    public TValue remove(TKey key) { return this.dictionary.remove(key); }

    @Override
    public Collection<TValue> getAllValues() { return this.dictionary.values(); }

    @Override
    public Set<TKey> getAllKeys() {
        return this.dictionary.keySet();
    }

    @Override
    public TValue getValue(TKey key) {
        return this.dictionary.get(key);
    }

    @Override
    public DictionaryInterface<TKey, TValue> copy() throws MyException {
        DictionaryInterface<TKey, TValue> newDictionary = new MyDictionary<>();

        for(TKey key: this.dictionary.keySet()) {
            newDictionary.insert(key, this.dictionary.get(key));
        }
        return newDictionary;
    }


}
