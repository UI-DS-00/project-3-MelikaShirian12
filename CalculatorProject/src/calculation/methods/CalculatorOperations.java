package calculation.methods;

import classes.Equation;
import classes.MyStack;
import exceptions.WrongInput;
import exceptions.WrongMethodType;

import java.util.ArrayList;
import java.util.Scanner;

public class CalculatorOperations {

    public static ArrayList <Equation> equations = new ArrayList<>();
    public static Equation equation;

    public static Double calculation()
    {
        Scanner sc= new Scanner(System.in);
        System.out.println("please enter the equation");



        Double lastResult;
        try {
            equation = new Equation(sc.nextLine());
            equations.add(equation);
            ArrayList <String> postfix= infixToPostfix(equation);
            lastResult=calculatePostfix(postfix);
        }catch (Exception e)
        {
            e.printStackTrace();
            throw new WrongInput();
        }

        return lastResult;
    }


    public static ArrayList <String> infixToPostfix(Equation theEquation) {

        ArrayList <String> result = new ArrayList<>();

        //all the arguments of the equation is in this stack
        MyStack <String> stack = theEquation.makeEquationToStack();
        MyStack <String> stack1=new MyStack<>();
        while (! stack.isEmpty() ){

            String eachPhrase = stack.pop();


            if (Character.isLetterOrDigit(eachPhrase.charAt(0)))
            {
               if (Character.isDigit(eachPhrase.charAt(0))){

                   //take all of the digits of the number
                   while (!stack.isEmpty() && (Character.isDigit(stack.top().charAt(0)) || stack.top().charAt(0)=='.'))
                       eachPhrase+=stack.pop();

               //    if ( stack.isEmpty() || ! Character.isLetter(stack.top().charAt(0)))
                   result.add(eachPhrase);
               }else
               {
                   //this means tha we are using a handmade operation and we should probably find the hole operation code
                   MyStack <String> handmadeOpp = new MyStack<>();
                   handmadeOpp.push(eachPhrase);
                   while ( !stack.isEmpty() &&  Character.isLetter(stack.top().charAt(0)))
                       handmadeOpp.push(stack.pop());

                   String handmadeOperation="";
                   while (!handmadeOpp.isEmpty())
                       handmadeOperation = handmadeOpp.pop()+handmadeOperation;

                   boolean found = false;
                   for (NewOperations newOperations: NewOperations.getAllNewOperation()) {
                       // find the operation
                       if (newOperations.getOperationName().equals(handmadeOperation)) {



                           int prec=1;
                           if (newOperations.isTwoParts())
                               prec=5;

                           while (!stack1.isEmpty()
                                   && prec <= Prec(stack1.top().charAt(0))) {

                               result.add(stack1.top());
                               stack1.pop();
                           }
                           stack1.push(handmadeOperation);

                           found = true;
                           break;
                       }
                   }
                   if (! found )throw new WrongMethodType();

               }


            }

            else if (eachPhrase.equals("("))
                stack1.push(eachPhrase);

                //  If the scanned character is an ')',
                // pop and output from the stack
                // until an '(' is encountered.
            else if (eachPhrase.equals(")")) {
                while (!stack1.isEmpty()
                        && ! stack1.top() .equals("(") ){
                    result.add(stack1.pop());
                }

                stack1.pop();
            } else // an operator is encountered
            {
                boolean checkNegatives=false;
                if ((eachPhrase.charAt(0) == '-' ) )
                    if(((!stack1.isEmpty() && stack1.top().equals("("))
                        || stack.size() == theEquation.getEquation().length() - 1)) {
                    if (Character.isDigit(stack.top().charAt(0))) {
                        //take all the digit of that number
                        while (Character.isDigit(stack.top().charAt(0)))
                            eachPhrase += stack.pop();
                        result.add(eachPhrase);

                        checkNegatives = true;
                    } else throw new WrongInput();
                }

                if (! checkNegatives){
                while (!stack1.isEmpty()
                        && Prec(eachPhrase.charAt(0)) <= Prec(stack1.top().charAt(0))) {

                    result.add(stack1.top());
                    stack1.pop();
                }
                stack1.push(eachPhrase);
            }
            }
        }

        // pop all the operators from the stack
        while (!stack1.isEmpty()) {
            if (stack1.top().equals("("))
                throw new WrongInput();
            result.add(stack1.top());
            stack1.pop();
        }

        return result;

    }


    public static Double calculatePostfix(ArrayList <String> postfix)
    {
        MyStack <Double> lastResult = new MyStack<>();


        ArrayList <String> history=new ArrayList<>();
        for (String phrase : postfix)
        {
            //if we get an exception that means that we don't have a digit but an operation
            try {
                if (phrase.length()!=1 && phrase.charAt(0) == '-')
                    try {
                        Double.parseDouble(phrase);
                    }catch (Exception e2){
                        phrase+=".0";
                    }


                lastResult.push(Double.parseDouble(phrase));
            }catch (Exception e){


                if (phrase.length() != 1) //because only the handmade operations have a bigger size than 1
                {
                    boolean found=false;
                    for (NewOperations newOperations : NewOperations.getAllNewOperation())
                    {
                        if (newOperations.getOperationName().equals(phrase))
                        {

                            // find the type of the equation and add digits into the equation
                            String equation1;
                            if (newOperations.isTwoParts())
                                equation1 = newOperations.addNumberToTheEquation(lastResult.pop());
                            else {
                                try {
                                    double a= lastResult.pop();
                                    double b = lastResult.pop();
                                    equation1= newOperations.addNumberToTheEquation(b, a);
                                    // we might have entered the first variable amount wrong
                                }catch (Exception e1){
                                    throw new WrongInput();
                                }
                            }


                            lastResult.push(calculatePostfix(infixToPostfix(new Equation(equation1))));

                            history.add(postfixIntoInfix(lastResult,postfix , postfix.indexOf(phrase)+1,lastResult.top()));

                            found = true;

                            break;
                        }
                    }
                    if (found)
                        continue;
                    else
                        throw new WrongInput();
                }

                double a = lastResult.pop();
                double b = lastResult.pop();

                switch (phrase.charAt(0))
                {
                    case '+': // addition
                        lastResult.push(b + a);
                        break;
                    case '-': // subtraction
                        lastResult.push(b - a);
                        break;
                    case '*': // multiplication
                        lastResult.push(b * a);
                        break;
                    case '/': // division
                        lastResult.push(b / a);
                        break;
                    case '^': // exponent
                        lastResult.push((Double) Math.pow(b , a));
                        break;
                }
                if (postfix.get(postfix.size()-1) == phrase) {
                    history.add(Double.toString(lastResult.top()));
                    break;
                }
                history.add(postfixIntoInfix(lastResult,postfix , postfix.indexOf(phrase)+1,lastResult.top()));
            }
        }
        equation.setHistory(history);
        return lastResult.top();
    }


    public static String postfixIntoInfix(MyStack<Double> myStack, ArrayList <String> postfix , int index , Double lastNumber) {


        MyStack  <Double> myStack2 =  myStack.myClone();

        MyStack <String> myStack1 = new MyStack<>();

        while (!myStack2.isEmpty())
            myStack1.push(Double.toString(myStack2.pop()));

       // myStack1.push(Double.toString(lastNumber));

        for (int i= index ; i<postfix.size() ;++i)
            myStack1.push(postfix.get(i));

        MyStack<String> stack = new MyStack<String>();

        ArrayList <String> exp = new ArrayList<>();
       // exp.add(Double.toString(lastNumber));

        while (!myStack1.isEmpty())
            exp.add(myStack1.pop());


        for (int i = exp.size()-1 ; i>=0; i--) {


            try {
                stack.push(Double.toString(Double.parseDouble(exp.get(i))));
            } catch (Exception e) {


                String op1 = stack.top();
                stack.pop();
                String op2 = stack.top();
                stack.pop();
                stack.push("(" + op2 + exp.get(i) +
                        op1 + ")");

            }
        }

        // There must be a single element
        // in stack now which is the required
        // infix.
        return stack.top();
    }


    public static int Prec(char ch)
    {
        if (Character.isLetter(ch))
            return 1;

        switch (ch) {
            case '+':
            case '-':
                return 1;

            case '*':
            case '/':
                return 2;

            case '^':
                return 3;
        }
        return -1;
    }

}
