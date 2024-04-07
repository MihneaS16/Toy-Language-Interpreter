package model.statements;

import exceptions.MyException;
import model.ProgramState;
import model.adt.dictionary.DictionaryInterface;
import model.adt.heap.HeapInterface;
import model.adt.stack.StackInterface;
import model.expressions.ExpressionInterface;
import model.types.BoolType;
import model.types.TypeInterface;
import model.values.BoolValue;
import model.values.ValueInterface;

public class IfStatement implements StatementInterface {

    private final ExpressionInterface expression;
    private final StatementInterface trueStatement;
    private final StatementInterface falseStatement;

    public IfStatement(ExpressionInterface expression, StatementInterface trueStatement, StatementInterface falseStatement) {
        this.expression = expression;
        this.trueStatement = trueStatement;
        this.falseStatement = falseStatement;
    }

    @Override
    public String toString() {
        return "IF(" + expression.toString() + ")THEN(" + trueStatement.toString() + ")ELSE(" + falseStatement.toString() + "))";
    }

    @Override
    public StatementInterface deepCopy() {
        return new IfStatement(expression, trueStatement, falseStatement);
    }

    @Override
    public DictionaryInterface<String, TypeInterface> typeCheck(DictionaryInterface<String, TypeInterface> typeEnv) throws MyException {
        TypeInterface typeExpression = this.expression.typeCheck(typeEnv);
        if(typeExpression.equals(new BoolType())){
            this.trueStatement.typeCheck(typeEnv.copy());
            this.falseStatement.typeCheck(typeEnv.copy());
            return typeEnv;
        }
        else throw new MyException("The condition of IF is not of type bool!\n");
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        StackInterface<StatementInterface> stack = state.getExecutionStack();
        DictionaryInterface<String, ValueInterface> symbolTable = state.getSymbolTable();
        HeapInterface<Integer, ValueInterface> heap = state.getHeap();

        ValueInterface conditionalExpressionVal = expression.evaluate(symbolTable, heap);
        if(((BoolValue)conditionalExpressionVal).getValue()) {
            stack.push(trueStatement);
        } else {
            stack.push(falseStatement);
        }
        return null;
    }
}
