package Calculator;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.math;

public class Calculator {

    private static final  String OPERATORS = ("[-+/*^]");

    public Calculator(){}

    public boolean endsWithOperator(String operation){
        String[] toCheck = operation.split(" ");
        String possibleOperator = toCheck[toCheck.length - 1];
        return isOperator(possibleOperator);
    }

    public boolean isValid(String operation){
        return getOperator(operation) != " ";
    }

    public String[] returnFormattedExp(String op){
        List<String> toRet = new ArrayList<>();
        Scanner scan = new Scanner(op.trim());
        String checking;
        while (scan.hasNext()){
            checking = scan.next();
            if(isOperator(checking)){
                toRet.add(checking);
            }
            else{
                toRet.add(String.valueOf(Double.parseDouble(checking)));
            }
        }
        return toRet.toArray(new String[toRet.size()]);
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

    public double makeOperation(String operation) throws IllegalArgumentException, ArithmeticException{
        Stack<String> ops = new Stack<>();
        Stack<Double> numbers = new Stack<>();
        Scanner scan = new Scanner(operation.trim());
        while (scan.hasNext()){
            String checking = scan.next();
            if(isOperator(checking)) {
                while (!ops.empty() && hasPrecedence(checking, ops.peek())) {
                    String precedenceOP = ops.pop();
                    double n2 = numbers.pop();
                    double n1 = numbers.pop();
                    numbers.push(makeEvaluation(precedenceOP, n1, n2));
                }
                ops.push(checking);
            }
            else{
                numbers.push(Double.parseDouble(checking));
            }
        }

        while (!ops.empty()) {
            String op = ops.pop();
            double n2 = numbers.pop();
            double n1 = numbers.pop();
            numbers.push(makeEvaluation(op, n1, n2));
        }

        return numbers.pop();
    }

    private boolean hasPrecedence(String currentOp, String possibleBeforeOp){
        if((currentOp.equals("*") || currentOp.equals("/")) && (possibleBeforeOp.equals("+") || possibleBeforeOp.equals("-"))){
            return false;
        }
        return true;
    }

    private double makeEvaluation(String op, double n1, double n2) throws ArithmeticException{
        double result = 0.0;
        switch (op) {
            case "+":
                result = this.add(n1, n2);
                break;
            case "-":
                result = this.subtract(n1, n2);
                break;
            case "*":
                result = this.multiply(n1, n2);
                break;
            case "/":
                result = this.divide(n1, n2);
                break;
            case "^":
                result = this.exp(n1,n2);
            default:
                break;
        }
        return result;
    }

    private double makeEvaluation(String op, double n1) throws ArithmeticException{
        double result = 0.0;
        switch (op) {
            case "+":
                result = this.add(n1);
                break;
            case "-":
                result = this.subtract(n1);
                break;
            case "*":
                result = this.multiply(n1);
                break;
            case "/":
                result = this.divide(n1);
                break;
            default:
                break;
        }
        return result;
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

    private double exp(double n1, double n2){
        return Math.exp(n1,n2);
    }

    private double divide(double n1, double n2) throws ArithmeticException{
        if(n2 == 0.0) throw new ArithmeticException("Result is undefined!");
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

}
