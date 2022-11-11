package classes;

import java.util.ArrayList;

public class MyStack <MODEL>{
    private int size = 0;
    private int wrongPlace=0;
    private Integer first;
    private Integer rear;
    private ArrayList<MODEL> data;

    public MyStack() {
        data = new ArrayList<>();
        first = null;
        rear = null;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        if (size == 0)
            return true;
        return false;
    }

    public void push (MODEL data) {
        this.data.add(data);
        ++size;
    }

    public MODEL pop ()
    {
        MODEL data;
        if (!isEmpty()) {
            data = this.data.get(size-1);
            this.data.remove(size-1);
            --size;
            return data;
        }

        return null;

    }

    public MODEL top ()
    {
        return data.get(size-1);
    }

    public MyStack<MODEL> myClone()
    {
        MyStack <MODEL> myStack = new MyStack<>();
        for (int i=this.data.size()-1 ; i>=0 ;--i)
            myStack.push(data.get(i));

        return myStack;
    }


    //getter setter


    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getWrongPlace() {
        return wrongPlace;
    }

    public void setWrongPlace(int wrongPlace) {
        this.wrongPlace = wrongPlace;
    }

    public Integer getFirst() {
        return first;
    }

    public void setFirst(Integer first) {
        this.first = first;
    }

    public Integer getRear() {
        return rear;
    }

    public void setRear(Integer rear) {
        this.rear = rear;
    }

    public ArrayList<MODEL> getData() {
        return data;
    }

    public void setData(ArrayList<MODEL> data) {
        this.data = data;
    }
}