import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Storage {
    private final Path filePath;

    public Storage(String relativePath) {
        this.filePath = Paths.get(relativePath);
    }

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

                }
            }
        } catch (IOException e) {

        }

        return taskList;
    }

    private String serializeTask(Task t) {
        String done = t.isDone() ? "1" : "0";
        if (t instanceof Todo) {
            return String.join(" | ", "T", done, t.getDescription());
        }
        if (t instanceof Deadline) {
            Deadline d = (Deadline) t;
            return String.join(" | ", "D", done, t.getDescription(), d.getBy());
        }
        if (t instanceof Event) {
            Event e = (Event) t;
            return String.join(" | ", "E", done, t.getDescription(), e.getFrom(), e.getTo());
        }
        // fallback
        return String.join(" | ", "T", done, t.getDescription());
    }

    private Task parseTask(String line) {
        String[] parts = line.split("\\s*\\|\\s*");
        if (parts.length < 3) return null;

        String type = parts[0];
        boolean done = "1".equals(parts[1]);
        String desc = parts[2];

        Task t;
        switch (type) {
            case "T":
                t = new Todo(desc);
                break;
            case "D":
                if (parts.length < 4) return null;
                t = new Deadline(desc, parts[3]);
                break;
            case "E":
                if (parts.length < 5) return null;
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

        }
    }
}