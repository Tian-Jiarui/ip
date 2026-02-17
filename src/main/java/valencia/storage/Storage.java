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
        assert relativePath != null && !relativePath.isBlank() : "relativePath must be non-null and non-blank";
        this.filePath = Paths.get(relativePath);
        assert this.filePath != null : "filePath should be initialized";
    }

    /**
     * Loads tasks from the file (if it exists). If file is missing or unreadable,
     * returns an empty TaskList.
     *
     * @return TaskList containing tasks loaded from file.
     */
    public TaskList load() {
        assert filePath != null : "filePath should not be null";

        TaskList taskList = new TaskList();
        assert taskList != null : "taskList should not be null";

        if (!Files.exists(filePath)) {
            return taskList;
        }

        try {
            List<String> lines = Files.readAllLines(filePath);
            assert lines != null : "readAllLines should not return null";

            for (String line : lines) {
                // invalid lines are ignored by design
                if (line == null || line.isBlank()) {
                    continue;
                }
                try {
                    Task t = parseTask(line);
                    if (t != null) {
                        taskList.add(t);
                    }
                } catch (Exception ignore) {
                    // Ignore invalid lines when loading
                }
            }
        } catch (IOException e) {
            // Ignore and return empty list if file cannot be read
        }

        return taskList;
    }

    /**
     * Converts a Task object into a single-line string format for saving.
     */
    private String serializeTask(Task t) {
        assert t != null : "task to serialize should not be null";
        assert t.getDescription() != null : "task description should not be null";

        String done = t.isDone() ? "1" : "0";

        if (t instanceof Todo) {
            String out = String.join(" | ", "T", done, t.getDescription());
            assert !out.isBlank() : "serialized output should not be blank";
            return out;
        }
        if (t instanceof Deadline) {
            Deadline d = (Deadline) t;
            assert d.getBy() != null : "deadline by-date should not be null";
            String out = String.join(" | ", "D", done, t.getDescription(), d.getBy().toString());
            assert !out.isBlank() : "serialized output should not be blank";
            return out;
        }
        if (t instanceof Event) {
            Event e = (Event) t;
            assert e.getFrom() != null : "event from should not be null";
            assert e.getTo() != null : "event to should not be null";
            String out = String.join(" | ", "E", done, t.getDescription(), e.getFrom(), e.getTo());
            assert !out.isBlank() : "serialized output should not be blank";
            return out;
        }

        // fallback
        String out = String.join(" | ", "T", done, t.getDescription());
        assert !out.isBlank() : "serialized output should not be blank";
        return out;
    }

    /**
     * Parses a single saved line into a Task object.
     *
     * @param line One line from the save file.
     * @return Parsed Task, or null if the line is invalid.
     */
    private Task parseTask(String line) {
        assert line != null : "line to parse should not be null";

        String[] parts = line.split("\\s*\\|\\s*");
        assert parts != null : "split result should not be null";

        if (parts.length < 3) {
            return null;
        }

        String type = parts[0];
        boolean done = "1".equals(parts[1]);
        String desc = parts[2];

        assert type != null : "type should not be null";
        assert desc != null : "description should not be null";

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

        assert t != null : "parsed task should not be null";

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
        assert filePath != null : "filePath should not be null";
        assert taskList != null : "taskList to save should not be null";

        try {
            Path parent = filePath.getParent();
            if (parent != null) {
                Files.createDirectories(parent); // create data/ folder if missing
            }

            StringBuilder sb = new StringBuilder();
            for (Task t : taskList.getTasks()) {
                assert t != null : "task in taskList should not be null";
                sb.append(serializeTask(t)).append(System.lineSeparator());
            }

            Files.writeString(filePath, sb.toString());
        } catch (IOException e) {
            // Ignore save failures
        }
    }
}
