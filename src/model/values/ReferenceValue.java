package model.values;

import model.types.ReferenceType;
import model.types.TypeInterface;

public class ReferenceValue implements ValueInterface {
    private final int heapAddress;
    private final TypeInterface locationType;

    public ReferenceValue(int heapAddress, TypeInterface locationType) {
        this.heapAddress = heapAddress;
        this.locationType = locationType;
    }

    public int getHeapAddress() {
        return this.heapAddress;
    }

    public TypeInterface getLocationType() {
        return this.locationType;
    }

    @Override
    public String toString() {
        return "(" + this.heapAddress + "," + locationType.toString() + ")";
    }

    @Override
    public TypeInterface getType() {
        return new ReferenceType(this.locationType);
    }
}
