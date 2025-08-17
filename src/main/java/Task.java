public class Task {
    private String description;
    private boolean isDone;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public void printTask() {
        System.out.printf("[%s] %s", getStatusIcon(), this.description);
    }

    public String getStatusIcon() {
        return (isDone? "X" : " ");
    }

    public void markDone() {
        this.isDone = true;
    }

    public void markUndone() {
        this.isDone = false;
    }
}
