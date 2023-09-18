import java.util.Scanner; // Scan the equation
import java.util.Stack; // To be able to calculate the PMDAS Rule

public class PMDASCalculator {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        System.out.print("Enter an expression (e.g., 1 + 2 * (3 - 1)): ");
        String input = scan.nextLine().replaceAll("\\s", "");

        try {
            float result = evaluateExpression(input);
            System.out.println("Result: " + result); // It try if there are any block of codes that has errors while executed if not it will print the result
            System.out.println("\nI admited sir that this code is not mine i really have no idea what or how to build this particular program cause my fundamentals in java programming");
            // Please help me sir malooy ka :(
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage()); // The catch statement allows you to define a block of code to be executed, if an error occurs in the try block.
        }
    }

    // Evaluate an expression following PMDAS rules
    private static float evaluateExpression(String expression) {
        Stack<Float> operands = new Stack<>(); // This stack used to stack numeric values
        Stack<Character> operators = new Stack<>(); // This stack used to stack characters

        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);

            if (Character.isDigit(c)) {
                float operand = 0;
                // Parse the operand
                while (i < expression.length() && (Character.isDigit(expression.charAt(i)) || expression.charAt(i) == '.')) {
                    operand = operand * 10 + Float.parseFloat(Character.toString(expression.charAt(i)));
                    i++;
                }
                operands.push(operand);
                i--;
            } else if (isOperator(c)) {
                // Process operators based on precedence
                while (!operators.isEmpty() && hasPrecedence(c, operators.peek())) {
                    char operator = operators.pop();
                    float operand2 = operands.pop();
                    float operand1 = operands.pop();
                    operands.push(applyOperator(operand1, operand2, operator));
                }
                operators.push(c);
            } else if (c == '(') {
                operators.push(c);
            } else if (c == ')') {
                // Process operators inside parentheses
                while (!operators.isEmpty() && operators.peek() != '(') {
                    char operator = operators.pop();
                    float operand2 = operands.pop();
                    float operand1 = operands.pop();
                    operands.push(applyOperator(operand1, operand2, operator));
                }
                if (!operators.isEmpty() && operators.peek() == '(') {
                    operators.pop();
                } else {
                    throw new IllegalArgumentException("Mismatched parentheses.");
                }
            } else {
                throw new IllegalArgumentException("Invalid character in the expression.");
            }
        }

        // Process any remaining operators in the stacks
        while (!operators.isEmpty()) {
            char operator = operators.pop();
            float operand2 = operands.pop();
            float operand1 = operands.pop();
            operands.push(applyOperator(operand1, operand2, operator));
        }

        if (operands.size() != 1) {
            throw new IllegalArgumentException("Invalid equation format");
        }

        return operands.pop();
    }

    // Check if a character is an operator (+, -, *, /)
    private static boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/';
    }

    // Check operator precedence following PMDAS rules
    private static boolean hasPrecedence(char operator1, char operator2) {
        if (operator2 == '(' || operator2 == ')') {
            return false; // Parentheses take precedence over everything
        }
        if ((operator1 == '*' || operator1 == '/') && (operator2 == '+' || operator2 == '-')) {
            return false; // Multiplication and division take precedence over addition and subtraction
        }
        return true;
    }

    // Apply an operator to two operands
    private static float applyOperator(float operand1, float operand2, char operator) {
        switch (operator) {
            case '+':
                return operand1 + operand2;
            case '-':
                return operand1 - operand2;
            case '*':
                return operand1 * operand2;
            case '/':
                if (operand2 != 0) {
                    return operand1 / operand2;
                } else {
                    throw new ArithmeticException("Division by zero is not allowed.");
                }
            default:
                throw new IllegalArgumentException("Invalid operator: " + operator);
        }
    }
}
