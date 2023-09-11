import java.util.Scanner;
import java.util.Stack;

public class PEMDASCalculatorByChatGPT {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter a mathematical expression using PEMDAS (e.g., 2 + 3 * 4 / 2 - 1):");
        String expression = scanner.nextLine();

        double result = evaluatePEMDAS(expression);

        System.out.println("Result: " + result);

        scanner.close();
    }

    public static double evaluatePEMDAS(String expression) {
        Stack<Double> numbers = new Stack<>();
        Stack<Character> operators = new Stack<>();

        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);

            if (Character.isDigit(c)) {
                StringBuilder numBuilder = new StringBuilder();
                numBuilder.append(c);

                while (i + 1 < expression.length() && (Character.isDigit(expression.charAt(i + 1)) || expression.charAt(i + 1) == '.')) {
                    numBuilder.append(expression.charAt(i + 1));
                    i++;
                }

                double num = Double.parseDouble(numBuilder.toString());
                numbers.push(num);
            } else if (c == '+' || c == '-' || c == '*' || c == '/') {
                while (!operators.isEmpty() && hasHigherPrecedence(operators.peek(), c)) {
                    double operand2 = numbers.pop();
                    double operand1 = numbers.pop();
                    char operator = operators.pop();
                    double result = applyOperator(operator, operand1, operand2);
                    numbers.push(result);
                }
                operators.push(c);
            } else if (c == '(') {
                operators.push(c);
            } else if (c == ')') {
                while (!operators.isEmpty() && operators.peek() != '(') {
                    double operand2 = numbers.pop();
                    double operand1 = numbers.pop();
                    char operator = operators.pop();
                    double result = applyOperator(operator, operand1, operand2);
                    numbers.push(result);
                }
                operators.pop(); // Pop the '('
            }
        }

        while (!operators.isEmpty()) {
            double operand2 = numbers.pop();
            double operand1 = numbers.pop();
            char operator = operators.pop();
            double result = applyOperator(operator, operand1, operand2);
            numbers.push(result);
        }

        return numbers.pop();
    }

    private static boolean hasHigherPrecedence(char operator1, char operator2) {
        return (operator1 == '*' || operator1 == '/') && (operator2 == '+' || operator2 == '-');
    }

    private static double applyOperator(char operator, double operand1, double operand2) {
        switch (operator) {
            case '+':
                return operand1 + operand2;
            case '-':
                return operand1 - operand2;
            case '*':
                return operand1 * operand2;
            case '/':
                if (operand2 == 0) {
                    System.out.println("Error: Division by zero");
                    System.exit(1);
                }
                return operand1 / operand2;
            default:
                throw new IllegalArgumentException("Invalid operator: " + operator);
        }
    }
}
