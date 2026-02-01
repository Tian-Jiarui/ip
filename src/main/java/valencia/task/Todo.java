package valencia.task;

/**
 * Represents a todo task (only description + done status).
 */
public class Todo extends Task {

    /**
     * Creates a Todo task.
     *
     * @param description Description of the todo.
     */
    public Todo(String description) {
        super(description);
    }

    /**
     * Returns the string representation of a todo task.
     *
     * @return Formatted todo string.
     */
    @Override
    public String toString() {
        return String.format("[T] %s", super.toString());
    }
}
