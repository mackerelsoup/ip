public class Events extends Task{
    protected String eventStart;
    protected String eventEnd;

    public Events(String description, String eventStart, String eventEnd) {
        super(description);
        this.eventStart = eventStart;
        this.eventEnd = eventEnd;
    }

    public Events(String description, boolean done, String eventStart, String eventEnd ) {
        super(description, done);
        this.eventStart = eventStart;
        this.eventEnd = eventEnd;
    }

    @Override
    public void printStatus() {
        System.out.printf("[E][%s] %s (from: %s to: %s)\n", getStatusIcon(), this.description, this.eventStart, this.eventEnd);
    }
}
