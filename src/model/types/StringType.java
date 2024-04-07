package model.types;

import model.values.StringValue;
import model.values.ValueInterface;

import javax.management.StringValueExp;

public class StringType implements TypeInterface {

    @Override
    public ValueInterface defaultValue() {
        return new StringValue();
    }

    @Override
    public boolean equals(Object another) { return (another instanceof StringType); }

    @Override
    public String toString() { return "String"; }
}
