package valencia.ui;

import java.util.Scanner;

/**
 * Handles user interaction (printing messages + reading commands).
 */
public class Ui {
    private static final String line = "_________________________________________________\n";
    private static final String welcomeGreet = "Hello! I'm Valencia.\n"
            + "What can I do for you?\n";
    private static final String exitGreet = "Bye. Hope to see you again soon!\n";
    private final Scanner sc = new Scanner(System.in);

    /**
     * Shows the welcome message when the app starts.
     */
    public void showWelcome() {
        System.out.println(String.format("%s%s%s", line, welcomeGreet, line));
    }

    /**
     * Shows the goodbye message when the user input bye.
     */
    public void showBye() {
        System.out.println(String.format("%s%s%s", line, exitGreet, line));
    }

    /**
     * Reads the next line the user types as a command.
     *
     * @return The user's input (trimmed).
     */
    public String readCommand() {
        return sc.nextLine().trim();
    }

    /**
     * Prints an error message in a boxed format.
     *
     * @param message The error message to display.
     */
    public void showError(String message) {
        System.out.println(String.format("%s\n%s\n%s", line, message, line));
    }

    /**
     * Prints a normal message to the user.
     *
     * @param message The message to display.
     */
    public void showMessage(String message) {
        System.out.println(message);
    }
}
