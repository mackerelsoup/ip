import java.time.LocalDateTime;
import java.util.Optional;

public class Deadlines extends Task{
    protected String deadline;
    protected Optional<LocalDateTime> deadlineTime;

    public Deadlines(String description, String deadline) {
        super(description);
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
        return String.format("D | %s | %s", this.description,
                this.deadlineTime.map(Parser::getTime).orElseGet(() -> this.deadline));
    }

}
