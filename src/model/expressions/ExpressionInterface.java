package model.expressions;

import exceptions.MyException;
import model.adt.dictionary.DictionaryInterface;
import model.adt.heap.HeapInterface;
import model.types.TypeInterface;
import model.values.ValueInterface;

public interface ExpressionInterface {
    ValueInterface evaluate(DictionaryInterface<String, ValueInterface> table, HeapInterface<Integer, ValueInterface> heap) throws MyException;
    String toString();
    TypeInterface typeCheck(DictionaryInterface<String, TypeInterface> typeEnv) throws MyException;
}
