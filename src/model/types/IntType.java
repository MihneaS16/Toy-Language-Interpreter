package model.types;

import model.values.IntValue;
import model.values.ValueInterface;

public class IntType implements TypeInterface {
    @Override
    public ValueInterface defaultValue() {
        return new IntValue();
    }

    @Override
    public boolean equals(Object another) {
        return (another instanceof IntType);
    }

    @Override
    public String toString() {
        return "int";
    }
}
