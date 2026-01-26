import java.util.Scanner; // Import the Scanner tool to read keyboard input

public class SimpleCalculator {
    public static void main(String[] args) {
        
        // 1. Create a Scanner object for user input
        Scanner reader = new Scanner(System.in);
        char continueChoice;

        // 2. Start a 'do-while' loop to ensure the calculator runs at least once
        do {
            System.out.println("\n--- Java Calculator ---");

            // 3. Get input: First Number
            System.out.print("Enter first number: ");
            // We use 'double' to allow decimal numbers (like 5.5)
            while (!reader.hasNextDouble()) { 
                System.out.println("Invalid input! Please enter a number.");
                reader.next(); // Clear the bad input
                System.out.print("Enter first number: ");
            }
            double num1 = reader.nextDouble();

            // 4. Get input: Operator
            System.out.print("Enter an operator (+, -, *, /): ");
            char operator = reader.next().charAt(0);

            // 5. Get input: Second Number
            System.out.print("Enter second number: ");
            while (!reader.hasNextDouble()) {
                System.out.println("Invalid input! Please enter a number.");
                reader.next(); 
                System.out.print("Enter second number: ");
            }
            double num2 = reader.nextDouble();

            double result = 0;
            boolean validOperation = true;

            // 6. Perform calculation using a Switch statement
            switch (operator) {
                case '+':
                    result = num1 + num2;
                    break;

                case '-':
                    result = num1 - num2;
                    break;

                case '*':
                    result = num1 * num2;
                    break;

                case '/':
                    // Check for division by zero
                    if (num2 != 0) {
                        result = num1 / num2;
                    } else {
                        System.out.println("Error! Division by zero is not allowed.");
                        validOperation = false;
                    }
                    break;

                default:
                    System.out.println("Error! Invalid operator. Please use +, -, *, or /");
                    validOperation = false;
            }

            // 7. Display the result if everything went well
            if (validOperation) {
                // %.2f limits the output to 2 decimal places
                System.out.printf("Result: %.2f %c %.2f = %.2f\n", num1, operator, num2, result);
            }

            // 8. Ask user if they want to continue
            System.out.print("\nDo you want to calculate again? (y/n): ");
            continueChoice = reader.next().charAt(0);

        } while (continueChoice == 'y' || continueChoice == 'Y');

        System.out.println("Calculator closed.");
        reader.close();
    }
}