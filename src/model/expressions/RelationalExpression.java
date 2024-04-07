package model.expressions;

import exceptions.MyException;
import model.adt.dictionary.DictionaryInterface;
import model.adt.heap.HeapInterface;
import model.types.BoolType;
import model.types.IntType;
import model.types.TypeInterface;
import model.values.BoolValue;
import model.values.IntValue;
import model.values.ValueInterface;

public class RelationalExpression implements ExpressionInterface {

    private final ExpressionInterface firstExpression;
    private final ExpressionInterface secondExpression;
    private final String operator;

    public RelationalExpression(ExpressionInterface firstExpression, ExpressionInterface secondExpression, String operator){
        this.firstExpression = firstExpression;
        this.secondExpression = secondExpression;
        this.operator = operator;
    }

    public ValueInterface evaluate(DictionaryInterface<String, ValueInterface> table, HeapInterface<Integer, ValueInterface> heap) throws MyException{
        ValueInterface firstValue, secondValue;

        firstValue = this.firstExpression.evaluate(table, heap);
        secondValue = this.secondExpression.evaluate(table, heap);

        if(!(firstValue.getType() instanceof IntType)) {
            throw new MyException("first value is not an int value");
        }

        if(!(secondValue.getType() instanceof IntType)) {
            throw new MyException("second value is not an int value");
        }

        int firstInt = ((IntValue)firstValue).getValue();
        int secondInt = ((IntValue)secondValue).getValue();

        if(this.operator.equals("<")){
            return new BoolValue(firstInt < secondInt);
        }

        if(this.operator.equals("<=")){
            return new BoolValue(firstInt <= secondInt);
        }

        if(this.operator.equals(">")){
            return new BoolValue(firstInt > secondInt);
        }

        if(this.operator.equals(">=")){
            return new BoolValue(firstInt >= secondInt);
        }

        if(this.operator.equals("==")){
            return new BoolValue(firstInt == secondInt);
        }

        if(this.operator.equals("!=")){
            return new BoolValue(firstInt != secondInt);
        }
        else {

            throw new MyException();
        }
    }
    public String toString(){
        String representation = "";
        representation += (this.firstExpression.toString());
        representation += (" " + this.operator + " ");
        representation += (this.secondExpression.toString());
        return representation;
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

        return new BoolType();
    }

}
