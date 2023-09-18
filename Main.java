import java.util.Stack;

public class Main {

    public static void main(String[] args) {

        String equation = "1+1*5";

        int num = evaluateEquation(equation);

        System.out.print(num);
    }

    public static int evaluateEquation(String equation) {
        Stack<Integer> operands = new Stack<>();
        Stack<Character> operators = new Stack<>();

        for (int i = 0; i < equation.length(); i++) {
            char currentChar = equation.charAt(i);

            if (Character.isDigit(currentChar)) {
                int operand = 0;
                while (i < equation.length() && Character.isDigit(equation.charAt(i))) {
                    operand = operand * 10 + (equation.charAt(i) - '0');
                    i++;
                }
                operands.push(operand);
                i--; // Decrement i to account for the loop increment
            } else if (isOperator(currentChar)) {
                while (!operators.isEmpty() && hasPrecedence(currentChar, operators.peek())) {
                    char operator = operators.pop();
                    int operand2 = operands.pop();
                    int operand1 = operands.pop();
                    operands.push(applyOperator(operand1, operand2, operator));
                }
                operators.push(currentChar);
            }
        }

        while (!operators.isEmpty()) {
            char operator = operators.pop();
            int operand2 = operands.pop();
            int operand1 = operands.pop();
            operands.push(applyOperator(operand1, operand2, operator));
        }

        if (operands.size() != 1) {
            throw new IllegalArgumentException("Invalid equation format");
        }

        return operands.pop();
    }

    private static boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/';
    }

    private static boolean hasPrecedence(char operator1, char operator2) {
        return (operator1 == '*' || operator1 == '/') && (operator2 == '+' || operator2 == '-');
    }

    private static int applyOperator(int operand1, int operand2, char operator) {
        switch (operator) {
            case '+':
                return operand1 + operand2;
            case '-':
                return operand1 - operand2;
            case '*':
                return operand1 * operand2;
            case '/':
                if (operand2 == 0) {
                    throw new ArithmeticException("Division by zero is not allowed.");
                }
                return operand1 / operand2;
            default:
                throw new IllegalArgumentException("Invalid operator: " + operator);
        }
    }
}
