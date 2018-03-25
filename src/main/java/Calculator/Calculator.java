package Calculator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Calculator {

    private static final  String OPERATORS = ("[-+/*]");

    public Calculator(){}

    public boolean endsWithOperator(String operation){
        String[] toCheck = operation.split(" ");
        String possibleOperator = toCheck[toCheck.length - 1];
        return isOperator(possibleOperator);
    }

    public boolean isValid(String operation){
        return getOperator(operation) != " ";
    }

    private boolean isOperator(String operation){
        Matcher match = Pattern.compile(OPERATORS).matcher(operation);
        return match.matches();
    }

    private String getOperator(String operation){
        Matcher match = Pattern.compile(OPERATORS).matcher(operation);
        if(match.matches()){
            return match.group(0);
        }
        else{
            return " ";
        }
    }

    private Stack<String> getOperators(String operation){
        Matcher match = Pattern.compile(OPERATORS).matcher(operation);
        Stack<String> toRet = new Stack<>();
        while (match.find()){
            toRet.push(match.group());
        }
        return toRet;
    }

    public double makeOperation(String operation) throws IllegalArgumentException, ArithmeticException{
        String[] numbersStr = operation.trim().split(OPERATORS);
        Stack<String> ops = getOperators(operation);
        Stack<Double> numbers = new Stack<>();
        for (String num : numbersStr){
            numbers.push(Double.parseDouble(num));
        }
        Collections.reverse(ops);
        Collections.reverse(numbers);
        while (!ops.empty()) {
            String op = ops.pop();
            double n1 = numbers.pop();
            double n2 = numbers.pop();
            switch (op) {
                case "+":
                    numbers.push(this.add(n1, n2));
                    break;
                case "-":
                    numbers.push(this.subtract(n1, n2));
                    break;
                case "*":
                    numbers.push(this.multiply(n1, n2));
                    break;
                case "/":
                    numbers.push(this.divide(n1, n2));
                    break;
                default:
                    break;

            }
        }
        return numbers.pop();
    }

    private double add(double n1, double n2){
        return n1 + n2;
    }

    private double subtract(double n1, double n2){
        return n1 - n2;
    }

    private double multiply(double n1, double n2){
        return n1 * n2;
    }

    private double divide(double n1, double n2) throws ArithmeticException{
        if(n2 == 0.0) throw new ArithmeticException();
        return n1 / n2;
    }

    private double add(double n1){
        return n1 + n1;
    }

    private double subtract(double n1){
        return n1 - n1;
    }

    private double multiply(double n1){
        return n1 * n1;
    }

    private double divide(double n1) throws ArithmeticException{
        if(n1 == 0.0) throw new ArithmeticException("Result is undefined!");
        return n1 / n1;
    }

    /*public double readInput (String exp){
        Scanner input = new Scanner(exp);
        Stack<Double> num = new Stack<Double>();
        Stack<String> op = new Stack<String>();

        while (input.hasNext()){
            token = input.next();
            if(isNumber(token)){
                num.push(Double.parseDouble(token));
            } else if (token.equals("(")){
                op.push(token);
            } else if (token.equals(")")){
                while(!op.peek().equals("(")){
                    if (op.peek().equals("sqrt")){
                        num.push(Math.sqrt(num.pop()));
                        op.pop();
                    } else
                        num.push(evaluate(op.pop(), num.pop(), num.pop()));
                }
                op.pop();
            } else {
                while(!op.empty() && hasPrecedence(token, op.peek())){
                    if (op.peek().equals("sqrt")){
                        num.push(Math.sqrt(num.pop()));
                        op.pop();
                    } else
                        num.push(evaluate(op.pop(), num.pop(), num.pop()));
                } op.push(token);
            }
        }

        while (!op.empty()) {
            if (op.peek().equals("sqrt")){
                num.push(Math.sqrt(num.pop()));
                op.pop();
            } else
                num.push(evaluate(op.pop(), num.pop(), num.pop()));
        } return num.pop();
    }*/

}
