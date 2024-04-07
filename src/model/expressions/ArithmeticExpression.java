package model.expressions;

import exceptions.MyException;
import model.adt.dictionary.DictionaryInterface;
import model.adt.heap.HeapInterface;
import model.types.BoolType;
import model.types.IntType;
import model.types.TypeInterface;
import model.values.IntValue;
import model.values.ValueInterface;

public class ArithmeticExpression implements ExpressionInterface {

    private final ExpressionInterface firstExpression;
    private final ExpressionInterface secondExpression;
    private final int operator;

    public ArithmeticExpression(ExpressionInterface firstExpression, ExpressionInterface secondExpression, int operator) {
        this.firstExpression = firstExpression;
        this.secondExpression = secondExpression;
        this.operator = operator;
    }

    @Override
    public String toString() {
        char op = '0';
        if (operator == 1)
            op = '+';
        else if (operator == 2)
            op = '-';
        else if (operator == 3)
            op = '*';
        else if (operator == 4)
            op = '/';
        return firstExpression.toString() + op + secondExpression.toString();
    }

    @Override
    public TypeInterface typeCheck(DictionaryInterface<String, TypeInterface> typeEnv) throws MyException {
        TypeInterface type1;
        TypeInterface type2;
        type1 = this.firstExpression.typeCheck(typeEnv);
        type2 = this.secondExpression.typeCheck(typeEnv);

        if (!type1.equals(new IntType())) {
            throw new MyException("Invalid type for the first expression\n");
        }

        if (!type2.equals(new IntType())) {
            throw new MyException("Invalid type for the second expression\n");
        }

        return new IntType();
    }

    public ValueInterface evaluate(DictionaryInterface<String, ValueInterface> table, HeapInterface<Integer, ValueInterface> heap) throws MyException {
        ValueInterface firstValue, secondValue;
        firstValue = firstExpression.evaluate(table, heap);
        secondValue = secondExpression.evaluate(table, heap);
        if (!firstValue.getType().equals(new IntType()) || !secondValue.getType().equals(new IntType()))
            throw new MyException("Both values should be integers");
        int firstInt = ((IntValue) firstValue).getValue();
        int secondInt = ((IntValue) secondValue).getValue();
        if (operator == 1) {
            return new IntValue(firstInt + secondInt);
        }
        if (operator == 2) {
            return new IntValue(firstInt - secondInt);
        }
        if (operator == 3) {
            return new IntValue(firstInt * secondInt);
        }
        if (operator == 4) {
            if (secondInt == 0)
                throw new MyException("Division by zero!");
            else
                return new IntValue(firstInt / secondInt);
        } else {
            throw new MyException("Wrong operator");
        }
    }
}
