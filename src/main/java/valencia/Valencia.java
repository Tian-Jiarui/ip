package valencia;

import valencia.exception.ValenciaException;
import valencia.parser.Parser;
import valencia.storage.Storage;
import valencia.task.Task;
import valencia.task.TaskList;
import valencia.task.Todo;
import valencia.ui.Ui;

/**
 * Entry point of the Valencia task manager.
 * Handles reading user commands, delegating parsing, and coordinating storage + UI updates.
 */
public class Valencia {
    private final Ui ui;
    private final Storage storage;
    private final TaskList taskList;
    private String commandType = "Other";

    public Valencia(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        taskList = storage.load();
    }

    public static void main(String[] args) {
        new Valencia("data/valencia.txt").run();
    }

    public String getCommandType() {
        return commandType;
    }

    /**
     * Runs the CLI command loop until the user exits.
     */
    public void run() {
        ui.showLines(
                "_________________________________________________",
                "Hello! I'm Valencia.",
                "What can I do for you?",
                "_________________________________________________"
        );

        while (true) {
            String input = ui.readCommand();
            String response = getResponse(input);
            ui.showMessage(response);

            if (input.trim().equalsIgnoreCase("bye")) {
                return;
            }
        }
    }

    /**
     * Processes a user command and returns Valencia's reply.
     */
    public String getResponse(String input) {
        if (input == null) {
            commandType = "Error";
            return "OOPS!!! Please type a command!";
        }

        String trimmed = input.trim();

        try {
            if (trimmed.isEmpty()) {
                throw new ValenciaException("Please type a command!");
            }

            String commandWord = trimmed.split("\\s+", 2)[0].toLowerCase();

            switch (commandWord) {
            case "mark":
                commandType = "Mark";
                return handleMarkUnmark(trimmed, true);
            case "unmark":
                commandType = "Mark";
                return handleMarkUnmark(trimmed, false);
            case "todo":
                commandType = "Add";
                return handleTodo(trimmed);
            case "deadline":
                commandType = "Add";
                return handleDeadline(trimmed);
            case "event":
                commandType = "Add";
                return handleEvent(trimmed);
            case "delete":
                commandType = "Delete";
                return handleDelete(trimmed);
            case "find":
                commandType = "Find";
                return handleFind(trimmed);
            case "list":
                commandType = "List";
                return "Here are the tasks in your list:\n" + taskList.formatList();
            case "bye":
                commandType = "Bye";
                return "Bye. Hope to see you again soon!";
            default:
                commandType = "Unknown";
                throw new ValenciaException("I do not understand what you are saying :'(");
            }

        } catch (ValenciaException e) {
            commandType = "Error";
            return "OOPS!!! " + e.getMessage();
        }
    }

    private String handleMarkUnmark(String input, boolean isMark) throws ValenciaException {
        String commandWord = isMark ? "mark" : "unmark";
        int index = getValidatedIndex(input, commandWord);

        if (isMark) {
            taskList.markDone(index);
            saveTasks();
            return String.format("Nice! I've marked this task as done:\n%s", taskList.get(index));
        }

        taskList.unmarkDone(index);
        saveTasks();
        return String.format("OK, I've marked this task as not done yet:\n%s", taskList.get(index));
    }

    private String handleTodo(String input) throws ValenciaException {
        String desc = Parser.parseTodoDescription(input);
        Task todoTask = new Todo(desc);
        return addTask(todoTask);
    }

    private String handleDeadline(String input) throws ValenciaException {
        Task deadlineTask = Parser.parseDeadline(input);
        return addTask(deadlineTask);
    }

    private String handleEvent(String input) throws ValenciaException {
        Task eventTask = Parser.parseEvent(input);
        return addTask(eventTask);
    }

    private String addTask(Task task) {
        taskList.add(task);
        saveTasks();
        return String.format("Got it. I've added this task:\n%s\nNow you have %d tasks in the list.",
                task, taskList.size());
    }

    private String handleDelete(String input) throws ValenciaException {
        int index = getValidatedIndex(input, "delete");
        Task removedTask = taskList.remove(index);
        saveTasks();
        return String.format("Noted. I've removed this task:\n%s\nNow you have %d tasks in the list.",
                removedTask, taskList.size());
    }

    private String handleFind(String input) throws ValenciaException {
        String keyword = Parser.parseFindKeyword(input);
        return "Here are the matching tasks in your list:\n" + taskList.formatMatches(keyword);
    }

    private int getValidatedIndex(String input, String commandWord) throws ValenciaException {
        int taskNum = Parser.parseTaskNumber(input, commandWord);
        Parser.validateTaskNumber(taskNum, taskList);
        return taskNum - 1;
    }
    
    private void saveTasks() {
        storage.save(taskList);
    }
}
