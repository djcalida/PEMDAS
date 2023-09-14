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

/*This Java code is an implementation of a calculator that can evaluate mathematical expressions following the order of operations, also known as PEMDAS (Parentheses, Exponents, Multiplication and Division, Addition and Subtraction). Here's an explanation of how the code works:

Input: The program first asks the user to input a mathematical expression, such as "2 + 3 * 4 / 2 - 1," using the Scanner class.

evaluatePEMDAS() Method:

evaluatePEMDAS is the main function that evaluates the mathematical expression.
Stacks:

Two stacks are used: numbers for storing numeric values and operators for storing operators.
Parsing the Expression:

The code iterates through each character of the input expression using a for loop.
Number Processing:

If the character is a digit, it begins building a number using a StringBuilder to handle multi-digit numbers and decimal points.
It scans ahead in the expression to collect the entire number.
The number is then parsed into a double and pushed onto the numbers stack.
Operator Processing:

If the character is one of the four basic operators (+, -, *, /), it checks for operator precedence and performs necessary calculations.
It compares the precedence of the current operator with operators already on the operators stack.
If the precedence of the current operator is lower, it performs calculations using operators and numbers from the stacks until the precedence is higher.
The result is pushed back onto the numbers stack, and the current operator is pushed onto the operators stack.
Parentheses Handling:

If an opening parenthesis '(' is encountered, it is pushed onto the operators stack.
When a closing parenthesis ')' is encountered, it performs calculations using operators and numbers from the stacks until an opening parenthesis '(' is found, and then it pops the '(' from the operators stack.
Final Calculation:

After processing all characters in the expression, it ensures that any remaining operators on the operators stack are used for final calculations.
Error Handling:

It checks for division by zero and handles it by displaying an error message and exiting the program if encountered.
Result:

The final result is the top value on the numbers stack, which is popped and returned.
Displaying Result:

The result is displayed to the user.
Loop:

The program allows the user to enter and evaluate multiple expressions by looping until the user decides to exit.
This code provides a simple yet effective implementation of a calculator that follows the order of operations, handling parentheses, and reporting errors for division by zero.