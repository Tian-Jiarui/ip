import java.util.Scanner;

public class Ui {
    private static final String line = "_________________________________________________\n";
    private static final String welcomeGreet = "Hello! I'm Valencia.\n"
            + "What can I do for you?\n";
    private static final String exitGreet = "Bye. Hope to see you again soon!\n";
    private final Scanner sc = new Scanner(System.in);

    public void showWelcome() {
        System.out.println(String.format("%s%s%s", line, welcomeGreet, line));
    }

    public void showBye() {
        System.out.println(String.format("%s%s%s", line, exitGreet, line));
    }

    public String readCommand() {
        return sc.nextLine().trim();
    }

    public void showError(String message) {
        System.out.println(String.format("%s\n%s\n%s", line, message, line));
    }

    public void showMessage(String message) {
        System.out.println(message);
    }
}
