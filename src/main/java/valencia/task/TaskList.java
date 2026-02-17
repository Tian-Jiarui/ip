package valencia.task;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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
        assert task != null : "task to add should not be null";
        tasks.add(task);
        assert tasks.contains(task) : "task should be present after add";
    }

    /**
     * Gets a task by index.
     *
     * @param index Index in the list (0-based).
     * @return The task at the index.
     */
    public Task get(int index) {
        assert index >= 0 && index < tasks.size() : "index out of bounds: " + index;
        Task task = tasks.get(index);
        assert task != null : "stored task should not be null";
        return task;
    }

    /**
     * Returns the number of tasks in the list.
     *
     * @return Task count.
     */
    public int size() {
        assert tasks.size() >= 0 : "tasks size should never be negative";
        return tasks.size();
    }

    /**
     * Prints all tasks with numbering to stdout.
     */
    public void printList() {
        for (int i = 0; i < tasks.size(); i++) {
            assert tasks.get(i) != null : "stored task should not be null";
            System.out.println(String.format("%s. %s", i + 1, tasks.get(i)));
        }
    }

    /**
     * Marks a task as done by index.
     *
     * @param index Index in the list (0-based).
     */
    public void markDone(int index) {
        assert index >= 0 && index < tasks.size() : "index out of bounds: " + index;
        assert tasks.get(index) != null : "stored task should not be null";
        tasks.get(index).markDone();
    }

    /**
     * Marks a task as not done by index.
     *
     * @param index Index in the list (0-based).
     */
    public void unmarkDone(int index) {
        assert index >= 0 && index < tasks.size() : "index out of bounds: " + index;
        assert tasks.get(index) != null : "stored task should not be null";
        tasks.get(index).unmarkDone();
    }

    /**
     * Removes a task from the list and returns it.
     *
     * @param index Index in the list (0-based).
     * @return The removed task.
     */
    public Task remove(int index) {
        assert index >= 0 && index < tasks.size() : "index out of bounds: " + index;
        Task removed = tasks.remove(index);
        assert removed != null : "remove should return a non-null task";
        return removed;
    }

    /**
     * Returns an unmodifiable view of the task list (read-only).
     *
     * @return Unmodifiable list of tasks.
     */
    public List<Task> getTasks() {
        List<Task> view = Collections.unmodifiableList(tasks);
        assert view != null : "unmodifiable view should not be null";
        return view;
    }

    /**
     * Finds task by keyword and return the tasks that matches.
     *
     * @param keyword User keyword (e.g. book)
     * @return List of tasks that match
     */
    public List<Task> findByKeyword(String keyword) {
        assert keyword != null : "keyword should not be null";
        String key = keyword.toLowerCase();

        return tasks.stream()
                .filter(t -> t.getDescription().toLowerCase().contains(key))
                .collect(Collectors.toList());
    }

    /**
     * Print out tasks that matches
     *
     * @param keyword User keyword (e.g. book)
     */
    public void printMatches(String keyword) {
        assert keyword != null : "keyword should not be null";

        int count = 0;
        String key = keyword.toLowerCase();

        for (int i = 0; i < tasks.size(); i++) {
            Task t = tasks.get(i);
            assert t != null : "stored task should not be null";
            assert t.getDescription() != null : "task description should not be null";

            if (t.getDescription().toLowerCase().contains(key)) {
                count++;
                System.out.println(String.format("%d. %s", count, tasks.get(i)));
            }
        }
        assert count >= 0 : "match count should never be negative";
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
            assert tasks.get(i) != null : "stored task should not be null";
            sb.append(i + 1).append(". ").append(tasks.get(i)).append("\n");
        }
        String result = sb.toString().trim();
        assert !result.isBlank() : "formatted list should not be blank when tasks is non-empty";
        return result;
    }

    /** Returns matching tasks as a string (for GUI). */
    public String formatMatches(String keyword) {
        assert keyword != null : "keyword should not be null";

        String key = keyword.toLowerCase();
        int count = 0;
        StringBuilder sb = new StringBuilder();

        for (Task t : tasks) {
            assert t != null : "stored task should not be null";
            assert t.getDescription() != null : "task description should not be null";

            if (t.getDescription().toLowerCase().contains(key)) {
                count++;
                sb.append(count).append(". ").append(t).append("\n");
            }
        }

        if (count == 0) {
            return "(no matching tasks)";
        }
        String result = sb.toString().trim();
        assert !result.isBlank() : "formatted matches should not be blank when there are matches";
        return result;
    }

    /**
     * Returns upcoming tasks (deadlines/events) within the next {@code days} days.
     *
     * @param days Number of days from now (must be positive).
     * @return Formatted upcoming tasks, or "(no upcoming tasks)" if none.
     */
    public String formatUpcoming(int days) {
        assert days > 0 : "days should be positive";

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime end = now.plusDays(days);

        List<Task> upcoming = tasks.stream()
                .filter(t -> t.getReminderDateTime().isPresent())
                .filter(t -> {
                    LocalDateTime dt = t.getReminderDateTime().get();
                    return !dt.isBefore(now) && !dt.isAfter(end);
                })
                .sorted(Comparator.comparing(t -> t.getReminderDateTime().get()))
                .collect(Collectors.toList());

        if (upcoming.isEmpty()) {
            return "(no upcoming tasks)";
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < upcoming.size(); i++) {
            sb.append(i + 1).append(". ").append(upcoming.get(i)).append("\n");
        }
        return sb.toString().trim();
    }
}
