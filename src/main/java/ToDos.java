public class ToDos extends Task{
    public ToDos(String description) {
        super(description);
    }

    public ToDos(String description, boolean done) {super(description, done); }

    @Override
    public void printStatus() {
        System.out.printf("[T][%s] %s\n", getStatusIcon(), this.description);
    }

    @Override
    public String getFileString() {
        return String.format("todo | %d | %s", isDone? 1 : 0, this.description);
    }
}
