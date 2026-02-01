package valencia.task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Represents a deadline task with a due date.
 */
public class Deadline extends Task {
    private static final DateTimeFormatter output = DateTimeFormatter.ofPattern("MMM dd yyyy");
    private final LocalDate by;


    /**
     * Creates a Deadline task with a description and due date.
     *
     * @param description Description of the deadline.
     * @param by          Due date (LocalDate).
     */
    public Deadline(String description, LocalDate by) {
        super(description);
        this.by = by;
    }

    /**
     * Returns the due date of this deadline.
     *
     * @return Due date.
     */
    public LocalDate getBy() {
        return by;
    }

    /**
     * Returns the string representation of a deadline task.
     *
     * @return Formatted deadline string.
     */
    @Override
    public String toString() {
        return String.format("[D] %s (by: %s)", super.toString(), by.format(output));
    }
}
