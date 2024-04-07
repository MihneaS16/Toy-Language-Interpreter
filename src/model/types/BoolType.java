package model.types;

import model.values.BoolValue;
import model.values.ValueInterface;

public class BoolType implements TypeInterface {

    @Override
    public ValueInterface defaultValue() {
        return new BoolValue();
    }

    @Override
    public boolean equals(Object another) {return (another instanceof BoolType);}

    @Override
    public String toString(){
        return "bool";
    }

}
