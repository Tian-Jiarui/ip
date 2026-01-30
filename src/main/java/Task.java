public class Task {
    private final String description;
    private boolean isDone;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public String getDescription() {
        return description;
    }

    public boolean isDone() {
        return isDone;
    }

    public void markDone() {
        this.isDone = true;
    }

    public void unmarkDone() {
        this.isDone = false;
    }

    public String checkDone() {
        if (this.isDone) {
            return "[X]";
        } else {
            return "[ ]";
        }
    }

    @Override
    public String toString() {
            return checkDone() + " " + this.description;
        }
    }
