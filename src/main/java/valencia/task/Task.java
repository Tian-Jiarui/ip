package valencia.task;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Represents a generic task with a description and a completion status.
 * Other task types (Todo/Deadline/Event) extend this.
 */
public class Task {
    private final String description;
    private boolean isDone;

    /**
     * Creates a new task with the given description. New tasks are not done by default.
     *
     * @param description Task description.
     */
    public Task(String description) {
        assert description != null && !description.isBlank() : "Task description must be non-null and non-blank";
        this.description = description;
        this.isDone = false;
    }

    /**
     * Returns the description of the task.
     *
     * @return Task description.
     */
    public String getDescription() {
        assert description != null : "description should never be null";
        return description;
    }

    /**
     * Checks if the task is marked as done.
     *
     * @return True if done, false otherwise.
     */
    public boolean isDone() {
        return isDone;
    }

    /**
     * Marks the task as done.
     */
    public void markDone() {
        this.isDone = true;
        assert isDone : "isDone should be true after markDone";
    }

    /**
     * Marks the task as not done.
     */
    public void unmarkDone() {
        this.isDone = false;
        assert !isDone : "isDone should be false after unmarkDone";
    }

    /**
     * Returns a status icon string depending on done state.
     *
     * @return "[X]" if done, otherwise "[ ]".
     */
    public String checkDone() {
        // isDone is boolean so it is always a valid state
        return isDone ? "[X]" : "[ ]";
    }

    /**
     * Returns the string representation of a task (status + description).
     *
     * @return Formatted task string.
     */
    @Override
    public String toString() {
        assert description != null : "description should never be null";
        return checkDone() + " " + description;
    }

    /**
     * Returns the datetime used for reminder filtering.
     * Default: empty (task has no reminder time).
     *
     * @return Optional reminder datetime.
     */
    public Optional<LocalDateTime> getReminderDateTime() {
        return Optional.empty();
    }
}
