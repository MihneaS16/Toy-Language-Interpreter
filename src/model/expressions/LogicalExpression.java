package model.expressions;

import exceptions.MyException;
import model.adt.dictionary.DictionaryInterface;
import model.adt.heap.HeapInterface;
import model.types.BoolType;
import model.types.IntType;
import model.types.TypeInterface;
import model.values.BoolValue;
import model.values.ValueInterface;

public class LogicalExpression implements ExpressionInterface{

    private final ExpressionInterface firstExpression;
    private final ExpressionInterface secondExpression;
    private final int operator;

    public LogicalExpression(ExpressionInterface firstExpression, ExpressionInterface secondExpression, int operator) {
        this.firstExpression = firstExpression;
        this.secondExpression = secondExpression;
        this.operator = operator;
    }

    @Override
    public ValueInterface evaluate(DictionaryInterface<String, ValueInterface> table, HeapInterface<Integer, ValueInterface> heap) throws MyException {
        ValueInterface firstValue;
        ValueInterface secondValue;
        firstValue = this.firstExpression.evaluate(table, heap);
        secondValue = this.secondExpression.evaluate(table, heap);
        if (!firstValue.getType().equals(new BoolType()) || !secondValue.getType().equals(new BoolType()))
            throw new MyException("Both values should be booleans");
        boolean firstBoolean = ((BoolValue)firstValue).getValue();
        boolean secondBoolean = ((BoolValue)secondValue).getValue();

        if(operator == 1){
            return new BoolValue(firstBoolean && secondBoolean);
        }
        if(operator == 2){
            return new BoolValue(firstBoolean || secondBoolean);
        }
        else {
            throw new MyException("Wrong operator");
        }
    }

    @Override
    public TypeInterface typeCheck(DictionaryInterface<String, TypeInterface> typeEnv) throws MyException {
        TypeInterface type1;
        TypeInterface type2;
        type1 = this.firstExpression.typeCheck(typeEnv);
        type2 = this.secondExpression.typeCheck(typeEnv);

        if (!type1.equals(new BoolType())) {
                throw new MyException("Invalid type for the second expression\n");
        }

        if (!type2.equals(new BoolType())) {
            throw new MyException("Invalid type for the second expression\n");
        }

        return new BoolType();
    }
}
