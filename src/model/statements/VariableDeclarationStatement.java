package model.statements;

import exceptions.MyException;
import model.ProgramState;
import model.adt.dictionary.DictionaryInterface;
import model.types.BoolType;
import model.types.IntType;
import model.types.StringType;
import model.types.TypeInterface;
import model.values.BoolValue;
import model.values.IntValue;
import model.values.StringValue;
import model.values.ValueInterface;

public class VariableDeclarationStatement implements StatementInterface {

    private final String name;
    private final TypeInterface type;

    public VariableDeclarationStatement(String name, TypeInterface type) {
        this.name = name;
        this.type = type;
    }

    @Override
    public String toString() {
        return type.toString() + " " + name;
    }

    @Override
    public StatementInterface deepCopy() {
        return new VariableDeclarationStatement(name, type);
    }

    @Override
    public DictionaryInterface<String, TypeInterface> typeCheck(DictionaryInterface<String, TypeInterface> typeEnv) throws MyException {
        typeEnv.insert(this.name, this.type);
        return typeEnv;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        DictionaryInterface<String, ValueInterface> symbolTable = state.getSymbolTable();
        if(symbolTable.containsKey(name)) {
            throw new MyException("Variable " + name + " is already declared");
        }
        symbolTable.insert(name, type.defaultValue());
        return null;
    }
}
