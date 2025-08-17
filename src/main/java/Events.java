public class Events extends Task{
    protected String eventPeriod;

    public Events(String description, String eventPeriod) {
        super(description);
        this.eventPeriod = eventPeriod;
    }

    @Override
    public void printStatus() {
        System.out.printf("[E][%s] %s (from: %s)", getStatusIcon(), this.description, this.eventPeriod);
    }
}
