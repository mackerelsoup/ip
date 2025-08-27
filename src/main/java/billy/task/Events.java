package billy.task;

import billy.parser.Parser;

import java.time.LocalDateTime;
import java.util.Optional;

public class Events extends Task{
    protected String eventStart;
    protected String eventEnd;
    protected Optional<LocalDateTime> eventStartTime;
    protected Optional<LocalDateTime> eventEndTime;

    public Events(String description, boolean done, String eventStart, String eventEnd) {
        super(description, done);
        this.eventStart = eventStart;
        this.eventEnd = eventEnd;

        this.eventStartTime = Parser.tryParse(eventStart, false);
        this.eventEndTime   = Parser.tryParse(eventEnd, true);
    }

    @Override
    public void printStatus() {
        System.out.printf("[E][%s] %s (from: %s to: %s)\n", getStatusIcon(), this.description,
                this.eventStartTime.map(Parser::getTime).orElseGet(() -> this.eventStart),
                this.eventEndTime.map(Parser::getTime).orElseGet(() -> this.eventEnd)
                );
    }

    @Override
    public String getFileString() {
        return String.format("event | %d | %s | %s | %s\n", this.isDone? 1 : 0, this.description,
                this.eventStartTime.map(Parser::getIsoTime).orElseGet(() -> this.eventStart),
                this.eventEndTime.map(Parser::getIsoTime).orElseGet(() -> this.eventEnd));
    }
}
