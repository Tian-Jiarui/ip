package valencia.task;

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
        this.description = description;
        this.isDone = false;
    }

    /**
     * Returns the description of the task.
     *
     * @return Task description.
     */
    public String getDescription() {
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
    }

    /**
     * Marks the task as not done.
     */
    public void unmarkDone() {
        this.isDone = false;
    }

    /**
     * Returns a status icon string depending on done state.
     *
     * @return "[X]" if done, otherwise "[ ]".
     */
    public String checkDone() {
        if (this.isDone) {
            return "[X]";
        } else {
            return "[ ]";
        }
    }

    /**
     * Returns the string representation of a task (status + description).
     *
     * @return Formatted task string.
     */
    @Override
    public String toString() {
            return checkDone() + " " + this.description;
        }
    }
