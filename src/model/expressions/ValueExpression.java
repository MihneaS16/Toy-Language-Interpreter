package model.expressions;

import exceptions.MyException;
import model.adt.dictionary.DictionaryInterface;
import model.adt.heap.HeapInterface;
import model.types.TypeInterface;
import model.values.ValueInterface;

public class ValueExpression implements ExpressionInterface{
    private final ValueInterface value;

    public ValueExpression(ValueInterface value) {
        this.value = value;
    }

    @Override
    public ValueInterface evaluate(DictionaryInterface<String, ValueInterface> table, HeapInterface<Integer, ValueInterface> heap) throws MyException {
        return value;
    }

    public String toString(){
        return value.toString();
    }

    @Override
    public TypeInterface typeCheck(DictionaryInterface<String, TypeInterface> typeEnv) throws MyException {
        return this.value.getType();
    }

}
