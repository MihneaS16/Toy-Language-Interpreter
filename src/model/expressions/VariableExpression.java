package model.expressions;

import exceptions.MyException;
import model.adt.dictionary.DictionaryInterface;
import model.adt.heap.HeapInterface;
import model.types.TypeInterface;
import model.values.ValueInterface;

public class VariableExpression implements ExpressionInterface{

    private final String id;

    public VariableExpression(String id) {
        this.id = id;
    }

    @Override
    public ValueInterface evaluate(DictionaryInterface<String, ValueInterface> table, HeapInterface<Integer, ValueInterface> heap) throws MyException {
        if (!table.containsKey(id)) {
            throw new MyException(id + "is not defined as a variable!");
        }
        return table.getValue(id);
    }

    public String toString(){
        return id;
    }

    @Override
    public TypeInterface typeCheck(DictionaryInterface<String, TypeInterface> typeEnv) throws MyException {
        return typeEnv.getValue(this.id);
    }

}
