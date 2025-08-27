package billy.task;

import billy.parser.Parser;

import java.time.LocalDateTime;
import java.util.Optional;

public class Deadlines extends Task{
    protected String deadline;
    protected Optional<LocalDateTime> deadlineTime;

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
        return String.format("deadline | %d | %s | %s\n", this.isDone? 1 : 0, this.description,
                this.deadlineTime.map(Parser::getIsoTime).orElseGet(() -> this.deadline));
    }

}
