import calculation.methods.CalculatorOperations;
import calculation.methods.NewOperations;
import classes.Equation;

import java.util.ArrayList;
import java.util.Scanner;

import static calculation.methods.CalculatorOperations.calculation;
import static calculation.methods.CalculatorOperations.equation;

public class Main {

    public static ArrayList <Equation> allTheEquations=new ArrayList<>();

    public static void main(String[] args) {


        //Character character='1';
       // Integer.parseInt(String.valueOf(character));

        int order = 6;
        Scanner sc= new Scanner(System.in);



            while (order != 0 ) {
                try {
                    System.out.println("""
                            1.calculate
                            2.show previous calculation history
                            3.make new operations
                            4.show available operations
                            0.end
                            """);
                    order = sc.nextInt();


                    switch (order) {
                        case 0:
                            return;
                        case 1:
                            System.out.println(calculation());
                            break;
                        case 2:
                            if (CalculatorOperations.equations.size() == 0)
                                System.out.println("no calculations yet");
                            for (int i = CalculatorOperations.equations.size() - 1; i >= 0; --i)
                                System.out.println(CalculatorOperations.equations.get(i).toString());
                            break;
                        case 3:
                            sc.nextLine();
                            System.out.println("first enter the name then enter the equation");
                            NewOperations newOperation = new NewOperations(sc.nextLine(), sc.nextLine());
                            break;
                        case 4:
                            for (NewOperations newOperations : NewOperations.getAllNewOperation())
                                System.out.println(newOperations.toString());
                            break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }


    }

}