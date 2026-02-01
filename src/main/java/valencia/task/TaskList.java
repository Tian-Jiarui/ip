package valencia.task;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

public class TaskList {
    private final ArrayList<Task> tasks = new ArrayList<>();

    public void add(Task task) {
        tasks.add(task);
    }

    public Task get(int index) {
        return tasks.get(index);
    }

    public int size() {
        return tasks.size();
    }

    public void printList() {
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println(String.format("%s. %s", i+1, tasks.get(i)));
        }
    }

    public void markDone(int index) {
        tasks.get(index).markDone();;
    }

    public void unmarkDone(int index) {
        tasks.get(index).unmarkDone();
    }

    public Task remove(int index) {
        return tasks.remove(index);
    }

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

    /** Print out tasks that matches
     * @param keyword User keyword (e.g. book)
     * @return String of tasks that matches
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

}
