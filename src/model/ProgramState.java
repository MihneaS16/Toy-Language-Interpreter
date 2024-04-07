package model;

import exceptions.MyException;
import model.adt.dictionary.DictionaryInterface;
import model.adt.heap.HeapInterface;
import model.adt.list.ListInterface;
import model.adt.stack.StackInterface;
import model.statements.StatementInterface;
import model.values.StringValue;
import model.values.ValueInterface;

import java.io.BufferedReader;

public class ProgramState {
    private StackInterface<StatementInterface> executionStack;
    private DictionaryInterface<String, ValueInterface> symbolTable;
    private DictionaryInterface<StringValue, BufferedReader> fileTable;
    private ListInterface<ValueInterface> output;
    private HeapInterface<Integer, ValueInterface> heap;
    private StatementInterface originalProgram;
    private int id;

    private static int nextId = 0;

    public static synchronized int generateNewId() {
        return nextId++;
    }

    public ProgramState(StackInterface<StatementInterface> executionStack,
                        DictionaryInterface<String, ValueInterface> symbolTable,
                        ListInterface<ValueInterface> output, DictionaryInterface<StringValue, BufferedReader> fileTable,
                        HeapInterface<Integer, ValueInterface> heap, StatementInterface program) {
        this.executionStack = executionStack;
        this.symbolTable = symbolTable;
        this.output = output;
        this.fileTable = fileTable;
        this.heap = heap;
        this.id = generateNewId();
        this.originalProgram = program.deepCopy();
        this.executionStack.push(program);
    }

    @Override
    public String toString(){
        String representation = "";
        representation += "\n------------------\n";
        representation += "Program ID: " + id + "\n";
        representation += "Execution Stack: \n";
        representation += this.executionStack.toString();
        representation += "\nSymbol Table: \n";
        representation += this.symbolTable.toString();
        representation += "\nOutput Table: \n";
        representation += this.output.toString();
        representation += "\nFile Table: \n";
        representation += this.fileTable.getAllKeys();
        representation += "\nHeap: \n";
        representation += this.heap.toString();
        return representation;
    }

    public StackInterface<StatementInterface> getExecutionStack(){
        return executionStack;
    }

    public DictionaryInterface<String, ValueInterface> getSymbolTable(){
        return symbolTable;
    }

    public DictionaryInterface<StringValue, BufferedReader> getFileTable() { return this.fileTable; }

    public ListInterface<ValueInterface> getOutput(){
        return output;
    }

    public HeapInterface<Integer, ValueInterface> getHeap() {
        return this.heap;
    }

    public int getId() { return this.id; }

    public void setExecutionStack(StackInterface<StatementInterface> newStack){
        this.executionStack = newStack;
    }

    public void setSymbolTable(DictionaryInterface<String, ValueInterface> newSymTable){
        this.symbolTable = newSymTable;
    }

    public void setFileTable(DictionaryInterface<StringValue, BufferedReader> newFileTable) { this.fileTable = newFileTable; }

    public void setOutput(ListInterface<ValueInterface> newOutput){
        this.output = newOutput;
    }

    public void setHeap(HeapInterface<Integer, ValueInterface> newHeap) { this.heap = newHeap; }

    public ProgramState oneStepExecution() throws Exception {
        if(this.executionStack.isEmpty()) throw new MyException("ProgramState stack is empty!");
        StatementInterface currentStatement = this.executionStack.pop();
        return currentStatement.execute(this);
    }

    public boolean isNotCompleted() {
        return !this.executionStack.isEmpty();
    }

}
