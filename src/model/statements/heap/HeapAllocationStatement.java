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


public class HeapAllocationStatement implements StatementInterface {
    private final String variableName;
    private final ExpressionInterface expression;

    public HeapAllocationStatement(String variableName, ExpressionInterface expression) {
        this.variableName = variableName;
        this.expression = expression;
    }


    @Override
    public ProgramState execute(ProgramState state) throws MyException {
        DictionaryInterface<String, ValueInterface> symTable = state.getSymbolTable();
        HeapInterface<Integer, ValueInterface> heap = state.getHeap();

        if (!symTable.containsKey(this.variableName)) {
            throw new MyException(this.variableName + "is not defined in the symbol table\n");
        }
        ValueInterface expValue = this.expression.evaluate(symTable, heap);
        ReferenceValue refValue = ((ReferenceValue) symTable.getValue(this.variableName));
        if (!expValue.getType().equals(refValue.getLocationType())) {
            throw new MyException("types are not equal");
        }
        TypeInterface innerType = ((ReferenceType) refValue.getType()).getInner();
        int copyAddress = heap.getFirstAvailablePos();
        heap.insert(copyAddress, expValue);
        symTable.update(this.variableName, new ReferenceValue(copyAddress, innerType));

        return null;
    }

    @Override
    public String toString() {
        String representation = "";
        representation += ("new(" + this.variableName + "," + this.expression.toString() + ");\n");
        return representation;
    }

    @Override
    public StatementInterface deepCopy() {
        return new HeapAllocationStatement(this.variableName, this.expression);
    }

    @Override
    public DictionaryInterface<String, TypeInterface> typeCheck(DictionaryInterface<String, TypeInterface> typeEnv) throws MyException {
        TypeInterface typeVariable = typeEnv.getValue(this.variableName);
        TypeInterface typeExpression = expression.typeCheck(typeEnv);
        if(typeVariable.equals(new ReferenceType(typeExpression))){
            return typeEnv;
        }
        else
            throw new MyException("HeapAllocationStatement: right hand side and left hand side have different types!\n");
    }
}
