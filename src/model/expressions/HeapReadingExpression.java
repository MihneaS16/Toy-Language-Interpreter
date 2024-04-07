package model.expressions;

import exceptions.MyException;
import model.adt.dictionary.DictionaryInterface;
import model.adt.heap.HeapInterface;
import model.types.ReferenceType;
import model.types.TypeInterface;
import model.values.ReferenceValue;
import model.values.ValueInterface;

public class HeapReadingExpression implements ExpressionInterface {

    private final ExpressionInterface expression;

    public HeapReadingExpression(ExpressionInterface expression) {
        this.expression = expression;
    }

    @Override
    public ValueInterface evaluate(DictionaryInterface<String, ValueInterface> table, HeapInterface<Integer, ValueInterface> heap) throws MyException {
        ValueInterface value = expression.evaluate(table, heap);
        if (!(value instanceof ReferenceValue)) {
            throw new MyException("Value not of ReferenceType");
        }

        int address = ((ReferenceValue) value).getHeapAddress();
        if (!heap.containsKey(address)) {
            throw new MyException("The given key address is not defined in the heap\n");
        }
        return heap.getValue(address);
    }

    public String toString() {
        return "readHeap(" + expression.toString() + ")";
    }

    @Override
    public TypeInterface typeCheck(DictionaryInterface<String, TypeInterface> typeEnv) throws MyException {
        TypeInterface type = this.expression.typeCheck(typeEnv);
        if (type instanceof ReferenceType) {
            ReferenceType referenceType = (ReferenceType) type;
            return referenceType.getInner();
        } else {
            throw new MyException("the rH argument is not a Ref Type");
        }
    }
}
