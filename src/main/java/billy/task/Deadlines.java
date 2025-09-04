package billy.task;

import java.time.LocalDateTime;
import java.util.Optional;

import billy.parser.Parser;



/**
 * Represents a task with a specific deadline.
 * <p>
 * A {@code Deadlines} task includes a description, a deadline (as a raw string),
 * and optionally a parsed {@link LocalDateTime} representation if the deadline
 * can be successfully parsed.
 * </p>
 * Example:
 * <pre>
 *     deadline Finish report /by 2025-09-10 23:59
 * </pre>
 */
public class Deadlines extends Task {
    protected String deadline;
    protected Optional<LocalDateTime> deadlineTime;

    /**
     * Constructs a {@code Deadlines} task.
     *
     * @param description the description of the task
     * @param done        whether the task is already marked as done
     * @param deadline    the raw deadline string
     */
    public Deadlines(String description, boolean done, String deadline) {
        super(description, done);
        this.deadline = deadline;
        this.deadlineTime = Parser.tryParse(deadline, true);
    }


    @Override
    public void printStatus() {
        System.out.printf("[D][%s] %s (by: %s)\n", getStatusIcon(), this.description,
                this.deadlineTime.map(Parser::getTime).orElseGet(() -> this.deadline));
    }

    @Override
    public String getFileString() {
        return String.format("deadline | %d | %s | %s\n", this.isDone ? 1 : 0, this.description,
                this.deadlineTime.map(Parser::getIsoTime).orElseGet(() -> this.deadline));
    }

}
