package valencia.parser;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import valencia.exception.ValenciaException;
import valencia.task.Deadline;
import valencia.task.Event;
import valencia.task.TaskList;

/**
 * Parses user input strings into task-related data or Task objects.
 * Throws ValenciaException when input format is invalid.
 */
public class Parser {

    /**
     * Extracts the task number from commands like "delete 3" or "mark 2".
     *
     * @param input       Full user input.
     * @param commandWord The command word (e.g. "delete", "mark").
     * @return Parsed task number (1-based).
     * @throws ValenciaException If task number is missing or not a number.
     */
    public static int parseTaskNumber(String input, String commandWord) {
        String rest = input.substring(commandWord.length()).trim();
        try {
            return Integer.parseInt(rest);
        } catch (NumberFormatException e) {
            throw new ValenciaException("Sorry! I need a task number!");
        }
    }

    /**
     * Validates that the given task number is within the valid range of the list.
     *
     * @param taskNum  Task number (1-based).
     * @param taskList Current TaskList.
     * @throws ValenciaException If task number is out of range.
     */
    public static void validateTaskNumber(int taskNum, TaskList taskList) {
        if (taskNum < 1 || taskNum > taskList.size()) {
            throw new ValenciaException("That task number does not exist :P");
        }
    }

    /**
     * Parses the description for a todo command (e.g. "todo read book").
     *
     * @param input Full user input.
     * @return Todo description.
     * @throws ValenciaException If description is empty.
     */
    public static String parseTodoDescription(String input) {
        String desc = input.substring(4).trim();
        if (desc.isEmpty()) {
            throw new ValenciaException("I did not receive a task :(");
        }
        return desc;
    }

    /**
     * Parses a deadline command in the format: "deadline <desc> /by yyyy-MM-dd".
     *
     * @param input Full user input.
     * @return Deadline task created from the input.
     * @throws ValenciaException If format is wrong or date is invalid.
     */
    public static Deadline parseDeadline(String input) {
        String desc = input.substring(8).trim(); // remove "deadline"
        String[] parts = desc.split(" /by ", 2);
        if (parts.length < 2) {
            throw new ValenciaException("Wrong deadline format!");
        }

        String taskDesc = parts[0].trim();
        String dateStr = parts[1].trim();

        LocalDate by;
        try {
            by = LocalDate.parse(dateStr);
        } catch (DateTimeParseException e) {
            throw new ValenciaException("Wrong format! Date must be yyyy-MM-dd!");
        }

        return new Deadline(taskDesc, by);
    }

    /**
     * Parses an event command in the format: "event <desc> /from <from> /to <to>".
     *
     * @param input Full user input.
     * @return Event task created from the input.
     * @throws ValenciaException If format is wrong.
     */
    public static Event parseEvent(String input) {
        String desc = input.substring(5).trim();
        String[] firstPart = desc.split(" /from ", 2);
        if (firstPart.length < 2) {
            throw new ValenciaException("Wrong event format!");
        }
        String input1 = firstPart[0].trim();
        String[] secondPart = firstPart[1].split(" /to ", 2);
        if (secondPart.length < 2) {
            throw new ValenciaException("Wrong event format!");
        }
        String input2 = secondPart[0].trim();
        String input3 = secondPart[1].trim();

        return new Event(input1, input2, input3);
    }
}

