package model.adt.list;

import exceptions.MyException;

import java.util.ArrayList;
import java.util.List;

public class MyList<TElem> implements ListInterface<TElem> {

    private final List<TElem> list;

    public MyList(){
        this.list = new ArrayList<TElem>();
    }

    @Override
    public String toString(){
        StringBuilder representation = new StringBuilder();
        for(TElem elem: list){
            representation.append(elem.toString()).append("\n");
        }

        return representation.toString();
    }

    @Override
    public void add(TElem newElem) {
        list.add(newElem);
    }


}
