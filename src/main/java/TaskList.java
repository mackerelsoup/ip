import java.util.ArrayList;

public class TaskList {
    private ArrayList<Task> tasks;

    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    public boolean addTask(Task task) {
        return this.tasks.add(task);
    }

    public Task removeTask(int index) {
        if (index >= 0 && index < this.tasks.size()) {
            return this.tasks.remove(index);
        } else
            throw new ArrayIndexOutOfBoundsException();
    }

    public Task getTask(int index) {
        if (index >= 0 && index < this.tasks.size()) {
            return this.tasks.get(index);
        } else
            throw new ArrayIndexOutOfBoundsException();
    }

    public void markTask(int index) {
        if (index >= 0 && index < this.tasks.size()) {
            this.tasks.get(index).setDone();

        } else
            throw new ArrayIndexOutOfBoundsException();
    }

    public void unmarkTast(int index) {
        if (index >= 0 && index < this.tasks.size()) {
            this.tasks.get(index).setUndone();
        } else
            throw new ArrayIndexOutOfBoundsException();
    }

    public void printTask(int index) {
        if (index >= 0 && index < this.tasks.size()) {
            this.tasks.get(index).printStatus();
        } else
            throw new ArrayIndexOutOfBoundsException();
    }


    public void printList() {
        for (Task task : this.tasks) {
            task.printStatus();
        }
    }


}
