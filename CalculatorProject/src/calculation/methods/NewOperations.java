package calculation.methods;

import classes.MyStack;
import exceptions.WrongInput;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.DataFormatException;

public class NewOperations {

    private static List <NewOperations> allNewOperation = new ArrayList<>();

    private String operationEquation; //the equation with variables
    private String operationName; //the actual name
    private Character [] variables;
    private MyStack <Character> myStack;
    private String order;
    private boolean isTwoParts;





    static final Pattern DIGIT_OR_X = Pattern.compile("[\\d]");
    static final Pattern OPERATION = Pattern.compile("\\([\\d][+*/%-][\\d]\\)");


    @Override
    public String toString() {
        return "NewOperations{" +
                "operationEquation='" + operationEquation + '\'' +
                ", operationName='" + operationName + '\'' +
                ", variables=" + Arrays.toString(variables) +
                '}';
    }

    public NewOperations(String operation , String operationEquation ) throws DataFormatException {
        this.operationEquation ="(" +operationEquation+")";
        variables=new Character[2];
        myStack=new MyStack<>();

        if (operation.split("\\s").length == 3)
            addOperation3(operation);
        else if (operation.split("\\s").length == 2)
            addOperation2(operation);
        else
            throw new WrongInput();

        for (NewOperations  string : allNewOperation)
        {
            if (string.operationName.equals(this.operationName))
                throw new DataFormatException("duplicate formula name");
        }

        if (! isFormula())
            throw new WrongInput();

        allNewOperation.add(this);

    }

    public String addNumberToTheEquation(double a)
    {
        String equation = "(";

        for (int i =0 ; i<operationEquation.length() ; ++i)
        {
            if (operationEquation.charAt(i) == variables[0])
                equation+=a;
            else
                equation+=Character.toString(operationEquation.charAt(i));
        }

        equation+=")";

        return equation;
    }

    public String addNumberToTheEquation(double a , double b)
    {
        String equation="(";

        for (int i = 0 ; i<operationEquation.length() ; ++i)
        {
            if (operationEquation.charAt(i) == variables[0])
                equation+=Double.toString(a);
            else if (operationEquation.charAt(i) == variables[1])
                equation+=Double.toString(b);
            else
                equation+=Character.toString(operationEquation.charAt(i));
        }

        equation+=")";

        return equation;
    }


    public  boolean isFormula() {

        //adding these numbers just for checking
        MyStack<String> myStack = new MyStack<>();
        String equation0;
        if (isTwoParts)
            equation0 = addNumberToTheEquation(1);
        else equation0 = addNumberToTheEquation(1, 1);

        for (int i = equation0.length() - 1; i >= 0; --i)
            myStack.push(Character.toString(equation0.charAt(i)));

        String equation = "";
        while (!myStack.isEmpty()) {

            if (Character.isDigit(myStack.top().charAt(0)) && myStack.top().length() != 1) {
                double aDouble = Double.parseDouble(myStack.top());
                int s = (int) aDouble;
                equation += s;
                myStack.pop();
            } else if (Character.isLetter(myStack.top().charAt(0)))
                throw new WrongInput();

            else equation += myStack.pop();

        }

//PATTERN

        // capture a text starting with one opening parenthesis,
        // ending with one closing and having no parentheses inside
        Pattern p = Pattern.compile("\\([^()]*\\)");
        Matcher m;
        while ((m = p.matcher(equation)).find())
            equation = m.replaceAll("");
        return !(equation.contains("(") || equation.contains(")"));


    }


    private void addOperation2(String operation){

        isTwoParts = true;
        String [] operations=operation.split("\\s");
        if (operations [0] .length() == 1   ||  operations[1].length() != 1 )
            throw new WrongInput();


        variables[0]= operations[1].charAt(0);

        if (!Character.isLetter(variables[0]))
            throw new WrongInput();

        operationName=operations[0];
    }

    private void addOperation3(String operation)
    {
        isTwoParts = false;
        String [] operations=operation.split("\\s");
            if (operations [0] .length() != 1  || operations[2].length() !=1 || operations[1].length() == 1 )
                throw new WrongInput();


            variables[0]= operations[0].charAt(0);
            variables[1]= operations[2].charAt(0);

       /* if (Character.isLetter(variables[0]) || Character.isLetter(variables[1]))
            throw new WrongInput();


        */
            operationName=operations[1];

    }


    public static List<NewOperations> getAllNewOperation() {
        return allNewOperation;
    }

    public static void setAllNewOperation(List<NewOperations> allNewOperation) {
        NewOperations.allNewOperation = allNewOperation;
    }

    public String getOperationEquation() {
        return operationEquation;
    }



    public void setOperationEquation(String operationEquation) {
        this.operationEquation = operationEquation;
    }

    public String getOperationName() {
        return operationName;
    }

    public void setOperationName(String operationName) {
        this.operationName = operationName;
    }

    public Character[] getVariables() {
        return variables;
    }

    public void setVariables(Character[] variables) {
        this.variables = variables;
    }

    public MyStack<Character> getMyStack() {
        return myStack;
    }

    public void setMyStack(MyStack<Character> myStack) {
        this.myStack = myStack;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public boolean isTwoParts() {
        return isTwoParts;
    }

    public void setTwoParts(boolean twoParts) {
        isTwoParts = twoParts;
    }
}
