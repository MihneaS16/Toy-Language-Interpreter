package model.adt.stack;

import java.util.ArrayDeque;

public class MyStack<TElem> implements StackInterface<TElem> {

    private final ArrayDeque<TElem> stack;

    public MyStack(){
        stack = new ArrayDeque<>();
    }

    @Override
    public String toString(){
        String representation = "";
        for(TElem current: stack){
            representation += current.toString() + "\n";
        }
        return representation.toString();
    }

    @Override
    public TElem pop() {
        return stack.pop();
    }


    @Override
    public void push(TElem newElem) {
        stack.push(newElem);
    }

    @Override
    public boolean isEmpty() {
        return stack.isEmpty();
    }

}
