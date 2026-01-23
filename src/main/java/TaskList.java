import java.util.ArrayList;

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
}
