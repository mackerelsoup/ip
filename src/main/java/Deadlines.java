public class Deadlines extends Task{
    protected String deadline;

    public Deadlines(String description, String deadline) {
        super(description);
        this.deadline = deadline;
    }

    @Override
    public void printStatus() {
        System.out.printf("[D][%s] %s (by: %s)", getStatusIcon(), this.description, this.deadline);
    }
}
