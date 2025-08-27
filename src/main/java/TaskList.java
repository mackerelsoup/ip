import java.util.ArrayList;

public class TaskList {
    private ArrayList<Task> tasks;

    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    public boolean addTask(Task task) {
        return this.tasks.add(task);
    }

    public void removeTask(int index) {
        if (index >= 0 && index < this.tasks.size()) {
            this.tasks.remove(index);
        }
        throw new ArrayIndexOutOfBoundsException();
    }

    public void markTask(int index) {
        if (index >= 0 && index < this.tasks.size()) {
            this.tasks.get(index).setDone();
        }
        throw new ArrayIndexOutOfBoundsException();
    }

    public void unmarkTast(int index) {
        if (index >= 0 && index < this.tasks.size()) {
            this.tasks.get(index).setUndone();
        }
        throw new ArrayIndexOutOfBoundsException();
    }

    public void printList() {
        for (Task task : this.tasks) {
            task.printStatus();
        }
    }


}
