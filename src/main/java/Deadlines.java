public class Deadlines extends Task{
    protected String deadline;

    public Deadlines(String description, String deadline) {
        super(description);
        this.deadline = deadline;
    }

    public Deadlines(String description, boolean done, String deadline) {
        super(description, done);
        this.deadline = deadline;
    }

    @Override
    public void printStatus() {
        System.out.printf("[D][%s] %s (by: %s)\n", getStatusIcon(), this.description, this.deadline);
    }
}
