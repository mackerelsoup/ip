package billy.task;


/**
 * Represents a generic task with a description and completion status.
 * <p>
 * A {@code Task} is the base class for different types of tasks such as
 * deadlines, events, and todos. It stores a textual description and a flag
 * indicating whether the task is completed.
 * </p>
 */
public class Task {
    protected String description;
    protected boolean isDone;

    /**
     * Constructs a new {@code Task} with the given description.
     * The task is initialized as not done.
     *
     * @param description the description of the task
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * Constructs a new {@code Task} with the given description and completion status.
     *
     * @param description the description of the task
     * @param isDone      whether the task is already marked as done
     */
    public Task(String description, boolean isDone) {
        assert description != null && !description.trim().isEmpty();
        this.description = description;
        this.isDone = isDone;
    }

    public void setDone() {
        this.isDone = true;
    }

    public void setUndone() {
        this.isDone = false;
    }

    public boolean isDone() {
        return isDone;
    }

    public String getStatusIcon() {
        return (isDone ? "X" : " "); // mark done task with X
    }

    public void printStatus() {
        System.out.printf("[%s] %s\n", getStatusIcon(), this.description);
    }

    public String getStatus() {
        return String.format("[%s] %s", getStatusIcon(), this.description);
    }


    public String getFileString() {
        return String.format("T | %s", this.description);
    }

    public String getDescription() {
        return this.description;
    }
}
