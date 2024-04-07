package model.statements;

import exceptions.MyException;
import model.ProgramState;
import model.adt.dictionary.DictionaryInterface;
import model.adt.heap.HeapInterface;
import model.expressions.ExpressionInterface;
import model.types.TypeInterface;
import model.values.ValueInterface;

public class AssignmentStatement implements StatementInterface {

    private final String id;
    private final ExpressionInterface expression;

    public AssignmentStatement(String id, ExpressionInterface expression) {
        this.id = id;
        this.expression = expression;
    }

    @Override
    public String toString() {
        return id + "=" + expression.toString();
    }

    @Override
    public StatementInterface deepCopy() {
        return new AssignmentStatement(id, expression);
    }

    @Override
    public DictionaryInterface<String, TypeInterface> typeCheck(DictionaryInterface<String, TypeInterface> typeEnv) throws MyException {
        TypeInterface typeVariable = typeEnv.getValue(this.id);
        TypeInterface typeExpression = expression.typeCheck(typeEnv);
        if(typeVariable.equals(typeExpression)){
            return typeEnv;
        }
        else throw new MyException("Assignment: right hand side and left hand side have different types!\n");
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        DictionaryInterface<String, ValueInterface> symbolTable = state.getSymbolTable();
        HeapInterface<Integer, ValueInterface> heap = state.getHeap();

        if (symbolTable.containsKey(id)) {
            ValueInterface newExpressionValue = expression.evaluate(symbolTable, heap);
            symbolTable.update(id, newExpressionValue);
        } else {
            throw new MyException("Variable " + id + " is not defined");
        }
        return null;
    }
}
