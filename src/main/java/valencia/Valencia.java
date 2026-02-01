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

    public Valencia(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        taskList = storage.load();
    }

    /**
     * Launches the Valencia application.
     */
    public static void main(String[] args) {
        new Valencia("data/valencia.txt").run();
    }

    /**
     * Runs the main input loop until the user exits the program.
     */
    public void run() {
        ui.showWelcome();

        while (true) {
            String input = ui.readCommand();

            try {
                if (input.toLowerCase().startsWith("mark")) {
                    int taskNum = Parser.parseTaskNumber(input, "mark");
                    Parser.validateTaskNumber(taskNum, taskList);
                    taskList.markDone(taskNum - 1);
                    storage.save(taskList);
                    ui.showMessage(String.format("Nice! I've marked this task as done:\n %s", taskList.get(taskNum - 1)));
                    continue;

                } else if (input.toLowerCase().startsWith("unmark")) {
                    int taskNum = Parser.parseTaskNumber(input, "unmark");
                    Parser.validateTaskNumber(taskNum, taskList);
                    taskList.unmarkDone(taskNum - 1);
                    storage.save(taskList);
                    ui.showMessage(String.format("OK, I've marked this task as not done yet:\n %s", taskList.get(taskNum - 1)));
                    continue;

                } else if (input.toLowerCase().startsWith("todo")) {
                    String desc = Parser.parseTodoDescription(input);
                    Task todoTask = new Todo(desc);
                    taskList.add(todoTask);
                    storage.save(taskList);
                    ui.showMessage(String.format("Got it. I've added this task:\n %s\nNow you have %s tasks in the list", todoTask, taskList.size()));
                    continue;

                } else if (input.toLowerCase().startsWith("deadline")) {
                    Task deadlineTask = Parser.parseDeadline(input);
                    taskList.add(deadlineTask);
                    storage.save(taskList);
                    ui.showMessage(String.format("Got it. I've added this task:\n %s\nNow you have %s tasks in the list.", deadlineTask, taskList.size()));
                    continue;


                } else if (input.toLowerCase().startsWith("event")) {
                    Task eventTask = Parser.parseEvent((input));
                    taskList.add(eventTask);
                    storage.save(taskList);
                    ui.showMessage(String.format("Got it. I've added this task:\n %s\n Now you have %s in the list.", eventTask, taskList.size()));
                    continue;

                } else if (input.toLowerCase().startsWith("delete")) {
                    int taskNum = Parser.parseTaskNumber(input, "delete");
                    Parser.validateTaskNumber(taskNum, taskList);
                    Task removedTask = taskList.remove(taskNum - 1);
                    storage.save(taskList);
                    ui.showMessage(String.format("Noted. I've removed this task:\n %s\n Now you have %s tasks in the list.", removedTask, taskList.size()));
                    continue;
                }

                switch (input.toLowerCase()) {
                case "list":
                    ui.showMessage(String.format("Here are the tasks in your list:\n"));
                    taskList.printList();
                    break;
                case "bye":
                    ui.showBye();
                    return;
                default:
                    throw new ValenciaException("I do not understand what you are saying :'(");
                }
            } catch (ValenciaException e) {
                ui.showError(e.getMessage());
            }
        }
    }
}

