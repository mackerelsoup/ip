import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
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

        this.eventStartTime = tryParse(eventStart, false);
        this.eventEndTime   = tryParse(eventEnd, true);
    }

    private Optional<LocalDateTime> tryParse(String input, boolean isEnd) {
        try {
            return Optional.of(Parser.parseDateTime(input, isEnd));
        } catch (DateTimeParseException e) {
            return Optional.empty();
        }
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
        return String.format("E | %s | %s | %s\n", this.description,
                this.eventStartTime.map(Parser::getTime).orElseGet(() -> this.eventStart),
                this.eventEndTime.map(Parser::getTime).orElseGet(() -> this.eventEnd));
    }
}
