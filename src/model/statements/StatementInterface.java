package model.statements;

import exceptions.MyException;
import model.ProgramState;
import model.adt.dictionary.DictionaryInterface;
import model.types.TypeInterface;

import java.lang.reflect.Type;

public interface StatementInterface {

    ProgramState execute(ProgramState state) throws MyException;

    String toString();

    StatementInterface deepCopy();

    DictionaryInterface<String, TypeInterface> typeCheck(DictionaryInterface<String, TypeInterface> typeEnv) throws MyException;
}
