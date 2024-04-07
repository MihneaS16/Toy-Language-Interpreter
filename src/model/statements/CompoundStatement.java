package model.statements;

import exceptions.MyException;
import model.ProgramState;
import model.adt.dictionary.DictionaryInterface;
import model.adt.stack.StackInterface;
import model.types.TypeInterface;

public class CompoundStatement implements StatementInterface {

    private final StatementInterface firstStatement;
    private final StatementInterface secondStatement;

    public CompoundStatement(StatementInterface firstStatement, StatementInterface secondStatement) {
        this.firstStatement = firstStatement;
        this.secondStatement = secondStatement;
    }

    @Override
    public String toString() {
        return "(" + firstStatement.toString() + ";" + secondStatement.toString() + ")";
    }

    @Override
    public StatementInterface deepCopy() {
        return new CompoundStatement(firstStatement, secondStatement);
    }

    @Override
    public DictionaryInterface<String, TypeInterface> typeCheck(DictionaryInterface<String, TypeInterface> typeEnv) throws MyException {
        return this.secondStatement.typeCheck(this.firstStatement.typeCheck(typeEnv));
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        StackInterface<StatementInterface> stack = state.getExecutionStack();
        stack.push(secondStatement);
        stack.push(firstStatement);
        return null;
    }
}
