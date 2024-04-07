package model.adt.stack;

import java.util.ArrayDeque;

public interface StackInterface<TElem> {
    public TElem pop();
    public void push(TElem newElem);
    public boolean isEmpty();
}
