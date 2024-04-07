package model.statements;

import exceptions.MyException;
import model.ProgramState;
import model.adt.dictionary.DictionaryInterface;
import model.types.TypeInterface;

public class EmptyStatement implements StatementInterface {

    public EmptyStatement() {

    }

    @Override
    public String toString() {
        return "Empty statement";
    }

    @Override
    public StatementInterface deepCopy() {
        return new EmptyStatement();
    }

    @Override
    public DictionaryInterface<String, TypeInterface> typeCheck(DictionaryInterface<String, TypeInterface> typeEnv) throws MyException {
        return typeEnv;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        return null;
    }
}
