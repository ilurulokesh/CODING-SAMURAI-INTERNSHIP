import java.util.ArrayList;
import java.util.Scanner;

// 1. OOP: Create a class to represent a single Question
class Question {
    private String questionText;
    private String[] options;
    private int correctOptionIndex; // 1-based index (1, 2, 3, or 4)

    public Question(String questionText, String[] options, int correctOptionIndex) {
        this.questionText = questionText;
        this.options = options;
        this.correctOptionIndex = correctOptionIndex;
    }

    public String getQuestionText() {
        return questionText;
    }

    public String[] getOptions() {
        return options;
    }

    public boolean isCorrect(int userAnswer) {
        return userAnswer == correctOptionIndex;
    }

    public String getCorrectAnswerText() {
        return options[correctOptionIndex - 1];
    }
}

// 2. OOP: Create a class to manage the Quiz logic
class Quiz {
    private ArrayList<Question> questions;
    private int score;
    private Scanner scanner;

    public Quiz() {
        questions = new ArrayList<>();
        score = 0;
        scanner = new Scanner(System.in);
        loadQuestions(); // Load questions into the list
    }

    // Array/List Management: Adding questions to the ArrayList
    private void loadQuestions() {
        // Questions inspired by the referenced video content (Java basics)
        questions.add(new Question(
            "Number of primitive data types in Java are?",
            new String[]{"6", "7", "8", "9"},
            3 // Correct: 8
        ));

        questions.add(new Question(
            "What is the size of float and double in Java?",
            new String[]{"32 & 64", "32 & 32", "64 & 64", "64 & 32"},
            1 // Correct: 32 & 64
        ));

        questions.add(new Question(
            "Automatic type conversion is possible in which of the possible cases?",
            new String[]{"Byte to Int", "Int to Long", "Long to Int", "Short to Int"},
            2 // Correct: Int to Long
        ));

        questions.add(new Question(
            "When an array is passed to a method, what does the method receive?",
            new String[]{"The reference of the array", "A copy of the array", "Length of the array", "Copy of first element"},
            1 // Correct: The reference
        ));

        questions.add(new Question(
            "Arrays in Java are:",
            new String[]{"Object references", "Objects", "Primitive data type", "None"},
            2 // Correct: Objects
        ));
    }

    public void start() {
        System.out.println("---- Welcome to the Online Quiz Application ----");
        System.out.println("Total Questions: " + questions.size());
        System.out.println("------------------------------------------------\n");

        // Loop: Iterate through each question
        for (int i = 0; i < questions.size(); i++) {
            Question q = questions.get(i);
            System.out.println("Question " + (i + 1) + ": " + q.getQuestionText());

            String[] opts = q.getOptions();
            for (int j = 0; j < opts.length; j++) {
                System.out.println((j + 1) + ". " + opts[j]);
            }

            // User Input: Get valid input
            int answer = 0;
            while (true) {
                System.out.print("Enter your choice (1-4): ");
                if (scanner.hasNextInt()) {
                    answer = scanner.nextInt();
                    if (answer >= 1 && answer <= 4) break;
                } else {
                    scanner.next(); // Clear invalid input
                }
                System.out.println("Invalid input. Please enter a number between 1 and 4.");
            }

            // Check Answer
            if (q.isCorrect(answer)) {
                System.out.println("Correct!\n");
                score++;
            } else {
                System.out.println("Wrong! The correct answer was: " + q.getCorrectAnswerText() + "\n");
            }
        }
        
        showResult();
    }

    private void showResult() {
        System.out.println("------------------------------------------------");
        System.out.println("Quiz Finished!");
        System.out.println("Your Score: " + score + " / " + questions.size());
        
        double percentage = ((double) score / questions.size()) * 100;
        System.out.println("Percentage: " + String.format("%.2f", percentage) + "%");
        
        if (percentage >= 80) {
            System.out.println("Result: Excellent Work!");
        } else if (percentage >= 50) {
            System.out.println("Result: Good Effort!");
        } else {
            System.out.println("Result: Keep Practicing!");
        }
        System.out.println("------------------------------------------------");
    }
}

// 3. Main Class to run the program
public class QuizApplication {
    public static void main(String[] args) {
        Quiz quiz = new Quiz();
        quiz.start();
    }
}