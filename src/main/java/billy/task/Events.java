package billy.task;

import java.time.LocalDateTime;
import java.util.Optional;

import billy.parser.Parser;


/**
 * Represents a task that spans a specific period of time.
 * <p>
 * An {@code Events} task includes a description, a start time, and an end time.
 * Both start and end times are stored as raw strings and optionally parsed
 * into {@link LocalDateTime} objects if possible.
 * </p>
 * Example:
 * <pre>
 *     event Team meeting /from 2025-09-04 10:00 /to 2025-09-04 11:30
 * </pre>
 */
public class Events extends Task {
    protected String eventStart;
    protected String eventEnd;
    protected Optional<LocalDateTime> eventStartTime;
    protected Optional<LocalDateTime> eventEndTime;

    /**
     * Constructs an {@code Events} task.
     *
     * @param description the description of the event
     * @param done        whether the event task is already marked as done
     * @param eventStart  the raw string for the event's start time
     * @param eventEnd    the raw string for the event's end time
     */
    public Events(String description, boolean done, String eventStart, String eventEnd) {
        super(description, done);
        this.eventStart = eventStart;
        this.eventEnd = eventEnd;

        this.eventStartTime = Parser.tryParse(eventStart, false);
        this.eventEndTime = Parser.tryParse(eventEnd, true);
    }

    @Override
    public void printStatus() {
        System.out.printf("[E][%s] %s (from: %s to: %s)\n", getStatusIcon(), this.description,
                this.eventStartTime.map(Parser::getTime).orElseGet(() -> this.eventStart),
                this.eventEndTime.map(Parser::getTime).orElseGet(() -> this.eventEnd));
    }

    @Override
    public String getStatus() {
        return String.format("[E][%s] %s (from: %s to: %s)",
                getStatusIcon(),
                this.description,
                this.eventStartTime.map(Parser::getTime).orElseGet(() -> this.eventStart),
                this.eventEndTime.map(Parser::getTime).orElseGet(() -> this.eventEnd));
    }


    @Override
    public String getFileString() {
        return String.format("event | %d | %s | %s | %s\n", this.isDone ? 1 : 0, this.description,
                this.eventStartTime.map(Parser::getIsoTime).orElseGet(() -> this.eventStart),
                this.eventEndTime.map(Parser::getIsoTime).orElseGet(() -> this.eventEnd));
    }
}
