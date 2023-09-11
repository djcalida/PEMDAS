public class PEMDASCalculator {
    public static void main(String[] args) {
        String expression = ""; // Add your expression here, e.g., "2 * 3 + 5 / 4 - 6"
        double result = calculateExpression(expression);
        System.out.println("Result: " + result);
    }

    public static double calculateExpression(String expression) {
        double num = 2 * 3 + 5.0 / 4 - 6; // Use double to handle decimal results
        return num;
    }
}