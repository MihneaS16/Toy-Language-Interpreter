package model.statements;

import exceptions.MyException;
import model.ProgramState;
import model.adt.dictionary.DictionaryInterface;
import model.adt.heap.HeapInterface;
import model.adt.stack.StackInterface;
import model.expressions.ExpressionInterface;
import model.expressions.HeapReadingExpression;
import model.types.BoolType;
import model.types.TypeInterface;
import model.values.BoolValue;
import model.values.ValueInterface;

public class WhileStatement implements StatementInterface{

    private final ExpressionInterface whileCondition;
    private final StatementInterface whileBody;

    public WhileStatement(ExpressionInterface whileCondition, StatementInterface whileBody) {
        this.whileCondition = whileCondition;
        this.whileBody = whileBody;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        DictionaryInterface<String, ValueInterface> symbolTable = state.getSymbolTable();
        StackInterface<StatementInterface> executionStack = state.getExecutionStack();
        HeapInterface<Integer, ValueInterface> heap = state.getHeap();

        ValueInterface evaluatedExp = this.whileCondition.evaluate(symbolTable, heap);
        if (((BoolValue) evaluatedExp).getValue()) {
            executionStack.push(this);
            executionStack.push(this.whileBody);
        }
        return null;
    }

    @Override
    public String toString() {
        return "while(" + whileCondition.toString() + "){ " + whileBody.toString() + " }";
    }

    @Override
    public StatementInterface deepCopy() {
        return new WhileStatement(this.whileCondition, this.whileBody);
    }

    @Override
    public DictionaryInterface<String, TypeInterface> typeCheck(DictionaryInterface<String, TypeInterface> typeEnv) throws MyException {
        TypeInterface typeExpression = this.whileCondition.typeCheck(typeEnv);
        if(typeExpression.equals(new BoolType())){
            this.whileBody.typeCheck(typeEnv.copy());
            return typeEnv;
        }
        else throw new MyException("WhileStatement: Conditional expression is not boolean!\n");
    }
}
