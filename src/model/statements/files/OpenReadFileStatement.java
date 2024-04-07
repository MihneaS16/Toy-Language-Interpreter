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
import java.io.FileNotFoundException;
import java.io.FileReader;

public class OpenReadFileStatement implements StatementInterface {

    private final ExpressionInterface filePath;

    public OpenReadFileStatement(ExpressionInterface filePath){
        this.filePath = filePath;
    }

    public ProgramState execute(ProgramState state) throws MyException{

        DictionaryInterface<String, ValueInterface> symTable = state.getSymbolTable();
        DictionaryInterface<StringValue, BufferedReader> fileTable = state.getFileTable();
        HeapInterface<Integer, ValueInterface> heap = state.getHeap();

        ValueInterface filePathValue = this.filePath.evaluate(symTable, heap);

        if(!(filePathValue.getType() instanceof StringType)) {
            throw new MyException("File path should be of type string");
        }

        if(fileTable.containsKey((StringValue) filePathValue)){
            throw new MyException("The filepath is already a key in FileTable!\n");
        }

        try {
            BufferedReader fileBuffer = new BufferedReader(new FileReader(((StringValue) filePathValue).getValue()));
            fileTable.insert((StringValue) filePathValue, fileBuffer);
        }
        catch (FileNotFoundException ex){
            throw new MyException(ex.getMessage());
        }
        return null;
    }


    public String toString(){
        return "openRead(" + this.filePath + ");\n";
    }

    @Override
    public StatementInterface deepCopy() {
        return new OpenReadFileStatement(filePath);
    }

    @Override
    public DictionaryInterface<String, TypeInterface> typeCheck(DictionaryInterface<String, TypeInterface> typeEnv) throws MyException {
        TypeInterface typeExpression = this.filePath.typeCheck(typeEnv);
        if(!typeExpression.equals(new StringType())){
            throw new MyException("OpenReadFileStatement: file path should be a stringValue!\n");
        }
        return typeEnv;
    }

}