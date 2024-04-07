package model.statements;

import exceptions.MyException;
import model.ProgramState;
import model.adt.dictionary.DictionaryInterface;
import model.adt.heap.HeapInterface;
import model.adt.list.ListInterface;
import model.expressions.ExpressionInterface;
import model.types.TypeInterface;
import model.values.ValueInterface;

public class PrintStatement implements StatementInterface {

    private final ExpressionInterface expression;

    public PrintStatement(ExpressionInterface expression) {
        this.expression = expression;
    }

    @Override
    public String toString() {
        return "print(" + expression.toString() + ")";
    }

    @Override
    public StatementInterface deepCopy() {
        return new PrintStatement(expression);
    }

    @Override
    public DictionaryInterface<String, TypeInterface> typeCheck(DictionaryInterface<String, TypeInterface> typeEnv) throws MyException {
        this.expression.typeCheck(typeEnv);
        return typeEnv;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        ListInterface<ValueInterface> output = state.getOutput();
        DictionaryInterface<String, ValueInterface> symbolTable = state.getSymbolTable();
        HeapInterface<Integer, ValueInterface> heap = state.getHeap();

        output.add(expression.evaluate(symbolTable, heap));
        return null;
    }
}
