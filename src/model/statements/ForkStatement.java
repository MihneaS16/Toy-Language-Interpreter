package model.statements;

import exceptions.MyException;
import model.ProgramState;
import model.adt.dictionary.DictionaryInterface;
import model.adt.stack.MyStack;
import model.adt.stack.StackInterface;
import model.types.TypeInterface;
import model.values.ValueInterface;

public class ForkStatement implements StatementInterface {

    StatementInterface statement;

    public ForkStatement(StatementInterface statement) {
        this.statement = statement;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        StackInterface<StatementInterface> newStack = new MyStack<>();
        DictionaryInterface<String, ValueInterface> symTable = state.getSymbolTable();
        DictionaryInterface<String, ValueInterface> newDictionary = symTable.copy();

        return new ProgramState(newStack, newDictionary, state.getOutput(), state.getFileTable(), state.getHeap(), this.statement);
    }

    @Override
    public StatementInterface deepCopy() {
        return new ForkStatement(this.statement);
    }

    @Override
    public DictionaryInterface<String, TypeInterface> typeCheck(DictionaryInterface<String, TypeInterface> typeEnv) throws MyException {
        this.statement.typeCheck(typeEnv.copy());
        return typeEnv;
    }

    @Override
    public String toString() {
        return "fork( " + this.statement + " );";
    }
}
