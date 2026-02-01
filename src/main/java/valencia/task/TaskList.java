package valencia.task;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Stores and manages a list of tasks.
 */
public class TaskList {
    private final ArrayList<Task> tasks = new ArrayList<>();

    /**
     * Adds a task to the list.
     *
     * @param task Task to add.
     */
    public void add(Task task) {
        tasks.add(task);
    }

    /**
     * Gets a task by index.
     *
     * @param index Index in the list (0-based).
     * @return The task at the index.
     */
    public Task get(int index) {
        return tasks.get(index);
    }

    /**
     * Returns the number of tasks in the list.
     *
     * @return Task count.
     */
    public int size() {
        return tasks.size();
    }


    /**
     * Prints all tasks with numbering to stdout.
     */
    public void printList() {
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println(String.format("%s. %s", i + 1, tasks.get(i)));
        }
    }

    /**
     * Marks a task as done by index.
     *
     * @param index Index in the list (0-based).
     */
    public void markDone(int index) {
        tasks.get(index).markDone();
    }

    /**
     * Marks a task as not done by index.
     *
     * @param index Index in the list (0-based).
     */
    public void unmarkDone(int index) {
        tasks.get(index).unmarkDone();
    }

    /**
     * Removes a task from the list and returns it.
     *
     * @param index Index in the list (0-based).
     * @return The removed task.
     */
    public Task remove(int index) {
        return tasks.remove(index);
    }

    /**
     * Returns an unmodifiable view of the task list (read-only).
     *
     * @return Unmodifiable list of tasks.
     */
    public List<Task> getTasks() {
        return Collections.unmodifiableList(tasks);
    }
}
