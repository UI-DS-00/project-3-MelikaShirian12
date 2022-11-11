package classes;

import java.util.ArrayList;

public class Equation {

    private String equation;
    private ArrayList <String> history = null; // if it was null , that means the equation was invalid


    public Equation(String equation) {
        this.equation = equation;
    }

    @Override
    public String toString() {
        return "Equation{" +
                "equation='" + equation + '\'' +
                ", history=" + history +
                '}';
    }


    //========================================================================


    public MyStack makeEquationToStack()
    {
        String withoutWhiteSpaces=this.getEquation().replaceAll("\\s+","");
        String [] arguments = withoutWhiteSpaces.split("");


        MyStack <String> myStack = new MyStack();

        for (int i=arguments.length-1 ; i>=0 ; --i)
            myStack.push(arguments[i]);



        return myStack;
    }

    //getter setter===========================================================


    public String getEquation() {
        return equation;
    }

    public void setEquation(String equation) {
        this.equation = equation;
    }

    public ArrayList<String> getHistory() {
        return history;
    }

    public void setHistory(ArrayList<String> history) {
        this.history = history;
    }
}
