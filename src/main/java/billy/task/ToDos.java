package billy.task;

/**
 * Represents a simple to-do task.
 * <p>
 * A {@code ToDos} task has a description and a completion status but no associated
 * deadlines or time information. It extends the {@link Task} class.
 * </p>
 */
public class ToDos extends Task {

    /**
     * Constructs a new {@code ToDos} task with the specified description.
     * The task is initially marked as not done.
     *
     * @param description the description of the to-do task
     */
    public ToDos(String description) {
        super(description);
    }

    /**
     * Constructs a new {@code ToDos} task with the specified description and completion status.
     *
     * @param description the description of the to-do task
     * @param done        {@code true} if the task is completed, {@code false} otherwise
     */
    public ToDos(String description, boolean done) {
        super(description, done);
    }

    /**
     * Prints the status of this to-do task to the console.
     * <p>
     * The output format is:
     * <pre>
     * [T][X] description
     * </pre>
     * where {@code X} indicates that the task is done, and a blank space indicates not done.
     * </p>
     */
    @Override
    public void printStatus() {
        System.out.printf("[T][%s] %s\n", getStatusIcon(), this.description);
    }

    @Override
    public String getStatus() {
        return String.format("[T][%s] %s", getStatusIcon(), this.description);
    }


    /**
     * Returns a string representation of this task formatted for storage in a file.
     * <p>
     * The format is:
     * <pre>
     * todo | doneFlag | description
     * </pre>
     * where {@code doneFlag} is 1 if the task is done, 0 otherwise.
     * </p>
     *
     * @return a file-formatted string representing this task
     */
    @Override
    public String getFileString() {
        return String.format("todo | %d | %s\n", isDone ? 1 : 0, this.description);
    }
}
