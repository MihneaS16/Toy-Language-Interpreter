package model.statements.files;

import exceptions.MyException;
import model.adt.dictionary.DictionaryInterface;
import model.ProgramState;
import model.adt.heap.HeapInterface;
import model.expressions.ExpressionInterface;
import model.statements.StatementInterface;
import model.types.StringType;
import model.types.TypeInterface;
import model.values.StringValue;
import model.values.ValueInterface;

import java.io.BufferedReader;
import java.io.IOException;

public class CloseReadFileStatement implements StatementInterface {

    private final ExpressionInterface filePath;

    public CloseReadFileStatement(ExpressionInterface filePath){
        this.filePath = filePath;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {

        DictionaryInterface<String, ValueInterface> symTable = state.getSymbolTable();
        DictionaryInterface<StringValue, BufferedReader> fileTable = state.getFileTable();
        HeapInterface<Integer, ValueInterface> heap = state.getHeap();

        ValueInterface filePathValue = filePath.evaluate(symTable, heap);

        if(!fileTable.containsKey((StringValue) filePathValue)){
            throw new MyException("The file path: " + filePathValue + "is not defined in the file table!\n");
        }

        BufferedReader fileBuffer = fileTable.getValue((StringValue) filePathValue);

        try {
            fileBuffer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        fileTable.remove((StringValue) filePathValue);

        return null;
    }
    public String toString(){
        return "closeRead(" + this.filePath + ");\n";
    }

    @Override
    public StatementInterface deepCopy() {
        return new CloseReadFileStatement(filePath);
    }

    @Override
    public DictionaryInterface<String, TypeInterface> typeCheck(DictionaryInterface<String, TypeInterface> typeEnv) throws MyException {
        TypeInterface typeExpression = this.filePath.typeCheck(typeEnv);
        if(!typeExpression.equals(new StringType())){
            throw new MyException("CloseReadFileStatement: file path should be a stringValue!");
        }
        return typeEnv;
    }

}