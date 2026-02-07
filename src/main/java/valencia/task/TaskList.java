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

    /**
     * Finds task by keyword and return the tasks that matches.
     * @param keyword User keyword (e.g. book)
     * @return List of tasks that match
     */
    public List<Task> findByKeyword(String keyword) {
        List<Task> matches = new ArrayList<>();
        String key = keyword.toLowerCase();

        for (Task t : tasks) {
            if (t.getDescription().toLowerCase().contains(key)) {
                matches.add(t);
            }
        }
        return matches;
    }

    /**
     * Print out tasks that matches
     *
     * @param keyword User keyword (e.g. book)
     *
     */
    public void printMatches(String keyword) {
        int count = 0;
        for (int i = 0; i < tasks.size(); i++) {
            if (tasks.get(i).getDescription().toLowerCase().contains(keyword.toLowerCase())) {
                count++;
                System.out.println(String.format("%d.%s", count, tasks.get(i)));
            }
        }
    }
    // =========================
    // GUI
    // =========================

    /** Returns the full task list as a string (for GUI). */
    public String formatList() {
        if (tasks.isEmpty()) {
            return "(no tasks yet)";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < tasks.size(); i++) {
            sb.append(i + 1).append(". ").append(tasks.get(i)).append("\n");
        }
        return sb.toString().trim();
    }

    /** Returns matching tasks as a string (for GUI). */
    public String formatMatches(String keyword) {
        String key = keyword.toLowerCase();
        int count = 0;
        StringBuilder sb = new StringBuilder();

        for (Task t : tasks) {
            if (t.getDescription().toLowerCase().contains(key)) {
                count++;
                sb.append(count).append(". ").append(t).append("\n");
            }
        }

        if (count == 0) {
            return "(no matching tasks)";
        }
        return sb.toString().trim();
    }
}
