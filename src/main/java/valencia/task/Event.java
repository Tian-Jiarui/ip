package valencia.task;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Optional;

/**
 * Represents an event task with a start time and end time.
 */
public class Event extends Task {
    private String from;
    private String to;

    /**
     * Creates an Event task.
     *
     * @param description Description of the event.
     * @param from        Start time/date string.
     * @param to          End time/date string.
     */
    public Event(String description, String from, String to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    /**
     * Returns the start time/date of the event.
     *
     * @return Start time/date string.
     */
    public String getFrom() {
        return from;
    }

    /**
     * Returns the end time/date of the event.
     *
     * @return End time/date string.
     */
    public String getTo() {
        return to;
    }

    /**
     * Returns the string representation of an event task.
     *
     * @return Formatted event string.
     */
    @Override
    public String toString() {
        return String.format("[E] %s (from: %s to: %s)", super.toString(), this.from, this.to);
    }

    @Override
    public Optional<LocalDateTime> getReminderDateTime() {
        try {
            return Optional.of(LocalDateTime.parse(from,
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm")));
        } catch (DateTimeParseException ignored) {
            // try date-only
        }

        try {
            return Optional.of(LocalDate.parse(from).atStartOfDay());
        } catch (DateTimeParseException e) {
            return Optional.empty();
        }
    }
}
