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
        assert input != null : "input should not be null";
        assert commandWord != null && !commandWord.isBlank() : "commandWord should be non-null and non-blank";
        assert input.length() >= commandWord.length() : "input should be at least as long as commandWord";

        String rest = input.substring(commandWord.length()).trim();
        try {
            int taskNum = Integer.parseInt(rest);
            assert taskNum > 0 : "Parsed task number should be positive";
            return taskNum;
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
        assert taskList != null : "taskList should not be null";
        assert taskNum > 0 : "taskNum should be positive";

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
        assert input != null : "input should not be null";
        assert input.length() >= 4 : "input should contain at least the command word 'todo'";

        String desc = input.substring(4).trim();
        if (desc.isEmpty()) {
            throw new ValenciaException("I did not receive a task :(");
        }
        assert !desc.isBlank() : "Todo description should not be blank after validation";
        return desc;
    }

    /**
     * Parses a deadline command in the format: {@code deadline <desc> /by yyyy-MM-dd}.
     *
     * @param input Full user input.
     * @return Deadline task created from the input.
     * @throws ValenciaException If format is wrong or date is invalid.
     */
    public static Deadline parseDeadline(String input) {
        assert input != null : "input should not be null";
        assert input.length() >= 8 : "input should contain at least the command word 'deadline'";

        String desc = input.substring(8).trim(); // remove "deadline"
        String[] parts = desc.split(" /by ", 2);
        if (parts.length < 2) {
            throw new ValenciaException("Wrong deadline format!");
        }

        String taskDesc = parts[0].trim();
        String dateStr = parts[1].trim();

        assert !taskDesc.isBlank() : "Deadline description should not be blank when '/by' is present";
        assert !dateStr.isBlank() : "Deadline date string should not be blank when '/by' is present";

        LocalDate by;
        try {
            by = LocalDate.parse(dateStr);
        } catch (DateTimeParseException e) {
            throw new ValenciaException("Wrong format! Date must be yyyy-MM-dd!");
        }

        Deadline deadline = new Deadline(taskDesc, by);
        assert deadline != null : "Deadline object should not be null";
        return deadline;
    }

    /**
     * Parses an event command in the format: {@code event <desc> /from <from> /to <to>}.
     *
     * @param input Full user input.
     * @return Event task created from the input.
     * @throws ValenciaException If format is wrong.
     */
    public static Event parseEvent(String input) {
        assert input != null : "input should not be null";
        assert input.length() >= 5 : "input should contain at least the command word 'event'";

        String desc = input.substring(5).trim(); // remove "event"
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

        assert !input1.isBlank() : "Event description should not be blank when '/from' is present";
        assert !input2.isBlank() : "Event start should not be blank when '/to' is present";
        assert !input3.isBlank() : "Event end should not be blank when '/to' is present";

        Event event = new Event(input1, input2, input3);
        assert event != null : "Event object should not be null";
        return event;
    }

    /**
     * Parses input for "find" and returns the keyword.
     *
     * @param input User input (e.g. "find book").
     * @return Keyword to search.
     */
    public static String parseFindKeyword(String input) {
        assert input != null : "input should not be null";
        assert input.length() >= 4 : "input should contain at least the command word 'find'";

        String keyword = input.substring(4).trim(); // remove "find"
        if (keyword.isEmpty()) {
            throw new ValenciaException("Find what?");
        }
        assert !keyword.isBlank() : "Find keyword should not be blank after validation";
        return keyword;
    }

    /**
     * Parses number of days for remind command.
     * Usage: "remind" (defaults to 7) or "remind 3".
     *
     * @param input Full user input.
     * @return Number of days to look ahead.
     * @throws ValenciaException If days is invalid.
     */
    public static int parseRemindDays(String input) throws ValenciaException {
        String trimmed = input.trim();
        String[] parts = trimmed.split("\\s+");

        if (parts.length == 1) {
            return 7;
        }

        if (parts.length == 2) {
            try {
                int days = Integer.parseInt(parts[1]);
                if (days <= 0) {
                    throw new ValenciaException("Days must be a positive integer.");
                }
                return days;
            } catch (NumberFormatException e) {
                throw new ValenciaException("Days must be a positive integer.");
            }
        }

        throw new ValenciaException("Usage: remind [days]");
    }
}

