public class ToDos extends Task{
    public ToDos(String description) {
        super(description);
    }

    @Override
    public void printStatus() {
        System.out.printf("[T][%s] %s\n", getStatusIcon(), this.description);
    }
}
