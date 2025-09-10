package billy.task;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a list of tasks and provides operations for managing them.
 * <p>
 * This class maintains a collection of {@link Task} objects and allows
 * adding, removing, retrieving, marking, unmarking, and printing tasks.
 * </p>
 */
public class TaskList {
    private ArrayList<Task> tasks;

    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    /**
     * Adds a task to the list.
     *
     * @param task the task to add
     * @return {@code true} if the task was added successfully
     */
    public boolean addTask(Task task) {
        return this.tasks.add(task);
    }

    /**
     * Removes a task from the list by its index.
     *
     * @param index the index of the task to remove (0-based)
     * @return the removed {@link Task}
     * @throws ArrayIndexOutOfBoundsException if the index is invalid
     */
    public Task removeTask(int index) {
        if (index >= 0 && index < this.tasks.size()) {
            return this.tasks.remove(index);
        } else {
            throw new ArrayIndexOutOfBoundsException();
        }
    }

    /**
     * Retrieves a task by its index.
     *
     * @param index the index of the task to retrieve (0-based)
     * @return the {@link Task} at the specified index
     * @throws ArrayIndexOutOfBoundsException if the index is invalid
     */
    public Task getTask(int index) {
        if (index >= 0 && index < this.tasks.size()) {
            return this.tasks.get(index);
        } else {
            throw new ArrayIndexOutOfBoundsException();
        }
    }

    /**
     * Retrieves the most recently added task.
     *
     * @return the latest {@link Task} in the list
     * @throws IndexOutOfBoundsException if the list is empty
     */
    public Task getLatestTask() {
        return this.tasks.get(this.tasks.size() - 1);
    }

    /**
     * Marks a task as done by its index.
     *
     * @param index the index of the task to mark as done (0-based)
     * @throws ArrayIndexOutOfBoundsException if the index is invalid
     */
    public void markTask(int index) {
        if (index >= 0 && index < this.tasks.size()) {
            this.tasks.get(index).setDone();

        } else {
            throw new ArrayIndexOutOfBoundsException();
        }
    }

    /**
     * Marks a task as not done by its index.
     *
     * @param index the index of the task to unmark (0-based)
     * @throws ArrayIndexOutOfBoundsException if the index is invalid
     */
    public void unmarkTask(int index) {
        if (index >= 0 && index < this.tasks.size()) {
            this.tasks.get(index).setUndone();
        } else {
            throw new ArrayIndexOutOfBoundsException();
        }
    }

    /**
     * Prints the status of a specific task to the console.
     *
     * @param index the index of the task to print (0-based)
     * @throws ArrayIndexOutOfBoundsException if the index is invalid
     */
    public void printTask(int index) {
        if (index >= 0 && index < this.tasks.size()) {
            this.tasks.get(index).printStatus();
        } else {
            throw new ArrayIndexOutOfBoundsException();
        }
    }

    /**
     * Prints the entire task list to the console.
     */
    public void printList() {
        System.out.println("Here are the tasks in your list");
        for (Task task : this.tasks) {
            task.printStatus();
        }
    }

    /**
     * Returns the number of tasks in the list.
     *
     * @return the size of the task list
     */
    public int getSize() {
        return this.tasks.size();
    }

    public List<Task> getTasks() {
        return new ArrayList<>(this.tasks);
    }

}
