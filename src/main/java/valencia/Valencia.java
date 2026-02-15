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

    /**
     * Creates a Valencia instance that loads tasks from the given file path.
     *
     * @param filePath Path to the save file.
     */
    public Valencia(String filePath) {
        assert filePath != null && !filePath.isBlank() : "filePath must be non-null and non-blank";
        ui = new Ui();
        storage = new Storage(filePath);
        taskList = storage.load();

        assert ui != null : "ui should be initialized";
        assert storage != null : "storage should be initialized";
        assert taskList != null : "taskList should not be null after loading";
    }

    /**
     * Launches the Valencia application.
     */
    public static void main(String[] args) {
        new Valencia("data/valencia.txt").run();
    }

    public String getCommandType() {
        return commandType;
    }

    /**
     * For CLI
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
            assert input != null : "Ui.readCommand() should not return null";
            String response = getResponse(input);
            ui.showMessage(response);

            if (input.trim().equalsIgnoreCase("bye")) {
                return;
            }
        }
    }

    /**
     * GUI + CLI
     * Take user input and returns Valencia reply as a string
     */
    public String getResponse(String input) {
        assert input != null : "input passed into getResponse should not be null";
        assert taskList != null : "taskList should be initialized";
        assert storage != null : "storage should be initialized";
        String trimmed = input.trim();
        try {
            if (trimmed.isEmpty()) {
                throw new ValenciaException("Please type a command!");
            }
            String lower = trimmed.toLowerCase();

            if (lower.startsWith("mark")) {
                commandType = "Mark";
                int taskNum = Parser.parseTaskNumber(trimmed, "mark");
                Parser.validateTaskNumber(taskNum, taskList);
                int index = taskNum - 1;
                assert index >= 0 && index < taskList.size() : "Validated taskNum should map to a valid index";
                taskList.markDone(index);
                storage.save(taskList);
                return String.format("Nice! I've marked this task as done:\n%s", taskList.get(index));
            }

            if (lower.startsWith("unmark")) {
                commandType = "Mark";
                int taskNum = Parser.parseTaskNumber(trimmed, "unmark");
                Parser.validateTaskNumber(taskNum, taskList);
                int index = taskNum - 1;
                assert index >= 0 && index < taskList.size() : "Validated taskNum should map to a valid index";
                taskList.unmarkDone(index);
                storage.save(taskList);
                return String.format("OK, I've marked this task as not done yet:\n%s", taskList.get(taskNum - 1));
            }

            if (lower.startsWith("todo")) {
                commandType = "Add";
                String desc = Parser.parseTodoDescription(trimmed);
                assert desc != null : "Todo description should not be null";
                Task todoTask = new Todo(desc);
                assert todoTask != null : "todoTask should not be null";
                taskList.add(todoTask);
                storage.save(taskList);
                return String.format("Got it. I've added this task:\n%s\nNow you have %d tasks in the list.",
                        todoTask, taskList.size());
            }

            if (lower.startsWith("deadline")) {
                commandType = "Add";
                Task deadlineTask = Parser.parseDeadline(trimmed);
                assert deadlineTask != null : "parseDeadline should not return null";
                taskList.add(deadlineTask);
                storage.save(taskList);
                return String.format("Got it. I've added this task:\n%s\nNow you have %d tasks in the list.",
                        deadlineTask, taskList.size());
            }

            if (lower.startsWith("event")) {
                commandType = "Add";
                Task eventTask = Parser.parseEvent(trimmed);
                assert eventTask != null : "parseEvent should not return null";
                taskList.add(eventTask);
                storage.save(taskList);
                return String.format("Got it. I've added this task:\n%s\nNow you have %d tasks in the list.",
                        eventTask, taskList.size());
            }

            if (lower.startsWith("delete")) {
                commandType = "Delete";
                int taskNum = Parser.parseTaskNumber(trimmed, "delete");
                Parser.validateTaskNumber(taskNum, taskList);
                int index = taskNum - 1;
                assert index >= 0 && index < taskList.size() : "Validated taskNum should map to a valid index";
                Task removedTask = taskList.remove(index);
                assert removedTask != null : "remove should return a Task";
                storage.save(taskList);
                return String.format("Noted. I've removed this task:\n%s\nNow you have %d tasks in the list.",
                        removedTask, taskList.size());
            }

            if (lower.startsWith("find")) {
                commandType = "Find";
                String keyword = Parser.parseFindKeyword(trimmed);
                assert keyword != null : "find keyword should not be null";
                return "Here are the matching tasks in your list:\n" + taskList.formatMatches(keyword);
            }

            switch (lower) {
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
}

