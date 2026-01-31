package valencia.parser;

import valencia.exception.ValenciaException;
import valencia.task.Deadline;
import valencia.task.Event;
import valencia.task.TaskList;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class Parser {

    public static int parseTaskNumber(String input, String commandWord) {
        String rest = input.substring(commandWord.length()).trim();
        try {
            return Integer.parseInt(rest);
        } catch (NumberFormatException e) {
            throw new ValenciaException("Sorry! I need a task number!");
        }
    }

    public static void validateTaskNumber(int taskNum, TaskList taskList) {
        if (taskNum < 1 || taskNum > taskList.size()) {
            throw new ValenciaException("That task number does not exist :P");
        }
    }

    public static String parseTodoDescription(String input) {
        String desc = input.substring(4).trim();
        if (desc.isEmpty()) {
            throw new ValenciaException("I did not receive a task :(");
        }
        return desc;
    }

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

