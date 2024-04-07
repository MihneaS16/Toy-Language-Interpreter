package model.types;

import model.values.ReferenceValue;
import model.values.ValueInterface;


public class ReferenceType implements TypeInterface{

    TypeInterface innerValue;

    public ReferenceType(TypeInterface inner) {
        this.innerValue = inner;
    }

    public TypeInterface getInner() {
        return this.innerValue;
    }

    public boolean equals(Object another) {
        if (another instanceof ReferenceType)
            return innerValue.equals(((ReferenceType) another).getInner());
        else
            return false;
    }

    public String toString() {
        return "Ref(" + innerValue.toString() + ")";
    }

    @Override
    public ValueInterface defaultValue() {
        return new ReferenceValue(0, innerValue);
    }
}
