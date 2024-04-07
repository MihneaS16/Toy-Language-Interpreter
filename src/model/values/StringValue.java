package model.values;

import model.types.StringType;
import model.types.TypeInterface;

public class StringValue implements ValueInterface{

    public static final String DEFAULT_STRING = "";
    public final String value;

    public StringValue(){ this.value = StringValue.DEFAULT_STRING;}

    public StringValue(String value) { this.value = value; }

    @Override
    public boolean equals(Object another) { return (another instanceof StringValue && ((StringValue)another).getValue() == this.value); }

    public String getValue() { return value; }

    @Override
    public String toString() { if(value.isEmpty()){ return "''";} return value;}

    @Override
    public TypeInterface getType() {
        return new StringType();
    }
}
