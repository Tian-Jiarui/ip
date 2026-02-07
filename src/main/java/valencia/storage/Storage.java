package valencia.storage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;

import valencia.task.Deadline;
import valencia.task.Event;
import valencia.task.Task;
import valencia.task.TaskList;
import valencia.task.Todo;

/**
 * Handles loading tasks from a local text file and saving tasks back into the same file.
 * Uses a simple line-based format: TYPE | DONE | DESC | (extra fields...)
 */
public class Storage {
    private final Path filePath;

    /**
     * Creates a Storage that reads/writes to the given relative file path.
     *
     * @param relativePath File path (e.g. "data/valencia.txt").
     */
    public Storage(String relativePath) {
        this.filePath = Paths.get(relativePath);
    }

    /**
     * Loads tasks from the file (if it exists). If file is missing or unreadable,
     * returns an empty TaskList.
     *
     * @return TaskList containing tasks loaded from file.
     */
    public TaskList load() {
        TaskList taskList = new TaskList();

        if (!Files.exists(filePath)) {
            return taskList;
        }

        try {
            List<String> lines = Files.readAllLines(filePath);
            for (String line : lines) {
                try {
                    Task t = parseTask(line);
                    if (t != null) {
                        taskList.add(t);
                    }
                } catch (Exception ignore) {
                    //Ignore invalid lines when loading
                }
            }
        } catch (IOException e) {
            //Ignore and return empty list if file cannot be read
        }

        return taskList;
    }

    /**
     * Converts a Task object into a single-line string format for saving.
     */
    private String serializeTask(Task t) {
        String done = t.isDone() ? "1" : "0";
        if (t instanceof Todo) {
            return String.join(" | ", "T", done, t.getDescription());
        }
        if (t instanceof Deadline) {
            Deadline d = (Deadline) t;
            return String.join(" | ", "D", done, t.getDescription(), d.getBy().toString());
        }
        if (t instanceof Event) {
            Event e = (Event) t;
            return String.join(" | ", "E", done, t.getDescription(), e.getFrom(), e.getTo());
        }
        // fallback
        return String.join(" | ", "T", done, t.getDescription());
    }

    /**
     * Parses a single saved line into a Task object.
     *
     * @param line One line from the save file.
     * @return Parsed Task, or null if the line is invalid.
     */
    private Task parseTask(String line) {
        String[] parts = line.split("\\s*\\|\\s*");
        if (parts.length < 3) {
            return null;
        }
        String type = parts[0];
        boolean done = "1".equals(parts[1]);
        String desc = parts[2];

        Task t;
        switch (type) {
        case "T":
            t = new Todo(desc);
            break;
        case "D":
            if (parts.length < 4) {
                return null;
            }
            LocalDate by = LocalDate.parse(parts[3]);
            t = new Deadline(desc, by);
            break;
        case "E":
            if (parts.length < 5) {
                return null;
            }
            t = new Event(desc, parts[3], parts[4]);
            break;
        default:
            return null;
        }

        if (done) {
            t.markDone();
        } else {
            t.unmarkDone();
        }
        return t;
    }

    /**
     * Saves the given TaskList to the file. Creates parent directories if needed.
     * Any IO errors are silently ignored.
     *
     * @param taskList TaskList to save.
     */
    public void save(TaskList taskList) {
        try {
            Path parent = filePath.getParent();
            if (parent != null) {
                Files.createDirectories(parent); // create data/ folder if missing
            }

            StringBuilder sb = new StringBuilder();
            for (Task t : taskList.getTasks()) {
                sb.append(serializeTask(t)).append(System.lineSeparator());
            }

            Files.writeString(filePath, sb.toString());
        } catch (IOException e) {
            //Ignore save failures
        }
    }
}
