package model.statements.heap;

import exceptions.MyException;
import model.ProgramState;
import model.adt.dictionary.DictionaryInterface;
import model.adt.heap.HeapInterface;
import model.expressions.ExpressionInterface;
import model.statements.StatementInterface;
import model.types.ReferenceType;
import model.types.TypeInterface;
import model.values.ReferenceValue;
import model.values.ValueInterface;

public class HeapWritingStatement implements StatementInterface {

    private final String variableName;
    private final ExpressionInterface expression;

    public HeapWritingStatement(String variableName, ExpressionInterface expression) {
        this.variableName = variableName;
        this.expression = expression;
    }

    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        DictionaryInterface<String, ValueInterface> symTable = state.getSymbolTable();
        HeapInterface<Integer, ValueInterface> heap = state.getHeap();

        if(!symTable.containsKey(this.variableName)) {
            throw new MyException("The variable: " + this.variableName + " is not defined in SymTable");
        }

        ValueInterface varValue = symTable.getValue(this.variableName);
        if (!(varValue instanceof ReferenceValue)) {
            throw new MyException(varValue + "is not of ReferenceType");
        }

        ReferenceValue refValue = (ReferenceValue) varValue;
        int address = refValue.getHeapAddress();

        if(!heap.containsKey(address)) {
            throw new MyException("The given key address is not defined in the heap");
        }

        ValueInterface expValue = this.expression.evaluate(symTable, heap);
        if (!expValue.getType().equals(refValue.getLocationType())) {
            throw new MyException("The value of " + expValue + " is not of type " + refValue.getLocationType());
        }

        heap.update(address, expValue);

        return null;
    }

    @Override
    public String toString() {
        String representation = "";
        representation += ("writeHeap(" + this.variableName + "," + this.expression.toString() + ");\n");
        return representation;
    }

    @Override
    public StatementInterface deepCopy() {
        return new HeapWritingStatement(this.variableName, this.expression);
    }

    @Override
    public DictionaryInterface<String, TypeInterface> typeCheck(DictionaryInterface<String, TypeInterface> typeEnv) throws MyException {
        TypeInterface typeVariable = typeEnv.getValue(this.variableName);
        TypeInterface typeExpression = this.expression.typeCheck(typeEnv);
        if(typeVariable.equals(new ReferenceType(typeExpression))){
            return typeEnv;
        }
        else
            throw new MyException("HeapWritingStatement: Expression cannot be evaluated to : " + typeExpression);
    }
}
