package model.statements.files;

import exceptions.MyException;
import model.adt.dictionary.DictionaryInterface;
import model.ProgramState;
import model.adt.heap.HeapInterface;
import model.expressions.ExpressionInterface;
import model.statements.StatementInterface;
import model.types.IntType;
import model.types.StringType;
import model.types.TypeInterface;
import model.values.IntValue;
import model.values.StringValue;
import model.values.ValueInterface;

import java.io.BufferedReader;
import java.io.IOException;

public class ReadFileStatement implements StatementInterface {

    private final ExpressionInterface filePath;
    private final String variableName;

    public ReadFileStatement(ExpressionInterface filePath, String variableName){
        this.filePath = filePath;
        this.variableName = variableName;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException{
        DictionaryInterface<String, ValueInterface> symTable = state.getSymbolTable();
        DictionaryInterface<StringValue, BufferedReader> fileTable = state.getFileTable();
        HeapInterface<Integer, ValueInterface> heap = state.getHeap();

        if(!symTable.containsKey(this.variableName)){
            throw new MyException("The variable name is not defined in the symbol table!\n");
        }
        ValueInterface filePathValue = filePath.evaluate(symTable, heap);

        if(!(filePathValue.getType() instanceof StringType)) {
            throw new MyException("File path should be of type string");
        }

        if(!fileTable.containsKey((StringValue) filePathValue)){
            throw new MyException("The file path value is not defined in file table!\n");
        }
        try {
            BufferedReader fileBuffer = fileTable.getValue((StringValue) filePathValue);
            String line = fileBuffer.readLine();
            if (line == null)
            {
                symTable.update(this.variableName, new IntValue());
            }
            else
            {
                try {
                    symTable.update(this.variableName, new IntValue(Integer.parseInt(line)));
                } catch (Exception ignored) {
                    throw new MyException("Cannot read value because EOF has been reached!\n");
                }
            }
        }
        catch(IOException ex){
            throw new MyException("An error has occurred while reading!\n");
        }
        return null;

    }

    @Override
    public String toString(){
        return "readFile(" + this.filePath + ");\n";
    }

    @Override
    public StatementInterface deepCopy() {
        return new ReadFileStatement(filePath, variableName);
    }

    @Override
    public DictionaryInterface<String, TypeInterface> typeCheck(DictionaryInterface<String, TypeInterface> typeEnv) throws MyException {
        TypeInterface typeVariable = typeEnv.getValue(this.variableName);
        TypeInterface typeExpression = this.filePath.typeCheck(typeEnv);
        if(typeVariable.equals(new IntType())){
            if(typeExpression.equals(new StringType())){
                return typeEnv;
            }
            else
                throw new MyException("ReadFileStatement: file path be a stringValue!\n");
        }
        else
            throw new MyException("ReadFileStatement" + this.variableName + " is not an integer!\n");
    }

}