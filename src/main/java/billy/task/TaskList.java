package billy.task;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import billy.calendar.Calendar;

/**
 * Represents a list of tasks and provides operations for managing them.
 * <p>
 * This class maintains a collection of {@link Task} objects and allows
 * adding, removing, retrieving, marking, unmarking, and printing tasks.
 * It also manages a calendar for tracking events and finding free time slots.
 * </p>
 */
public class TaskList {
    private ArrayList<Task> tasks;
    private Calendar calendar;

    /**
     * Constructs a TaskList with the given list of tasks.
     * Initializes the calendar and adds all complete event periods to it.
     *
     * @param tasks the initial list of tasks
     */
    public TaskList(ArrayList<Task> tasks) {
        assert tasks != null;
        this.tasks = tasks;
        this.calendar = new Calendar();

        initialiseCalendar();
    }

    /**
     * Initializes the calendar by adding all complete event periods from the task list.
     */
    private void initialiseCalendar() {
        tasks.stream()
                .filter(task -> task instanceof Events)
                .map(task -> (Events) task)
                .filter(Events::isCompleteTimePeriod)
                .forEach(event -> calendar.add(event));

    }

    /**
     * Adds an event to the task list and checks for conflicts with existing events.
     *
     * @param event the event to add
     * @return list of conflicting events, empty if no conflicts
     */
    public ArrayList<Events> addEventWithConflictCheck(Events event) {
        ArrayList<Events> conflicts = new ArrayList<>();
        if (event.isCompleteTimePeriod()) {
            conflicts = calendar.add(event);
        }
        this.tasks.add(event);
        return conflicts;
    }

    /**
     * Finds the earliest free time slot that can accommodate the specified duration.
     *
     * @param currentTime the starting time to search from
     * @param duration the required duration in hours
     * @return the earliest available time slot
     */
    public LocalDateTime getEarliestFreeTime(LocalDateTime currentTime, int duration) {
        return calendar.getEarliestFreeTime(currentTime, duration);
    }


    /**
     * Validates that the given index is within the valid range for the task list.
     *
     * @param index the 0-based index to validate
     * @throws ArrayIndexOutOfBoundsException if the index is invalid
     */
    private void validateIndex(int index) {
        if (index < 0 || index >= this.tasks.size()) {
            throw new ArrayIndexOutOfBoundsException();
        }
    }

    /**
     * Adds a task to the list.
     *
     * @param task the task to add
     * @return {@code true} if the task was added successfully
     */
    public boolean addTask(Task task) {
        assert task != null;
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
        validateIndex(index);
        return this.tasks.remove(index);
    }

    /**
     * Retrieves a task by its index.
     *
     * @param index the index of the task to retrieve (0-based)
     * @return the {@link Task} at the specified index
     * @throws ArrayIndexOutOfBoundsException if the index is invalid
     */
    public Task getTask(int index) {
        validateIndex(index);
        return this.tasks.get(index);
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
        validateIndex(index);
        this.tasks.get(index).setDone();
    }

    /**
     * Marks a task as not done by its index.
     *
     * @param index the index of the task to unmark (0-based)
     * @throws ArrayIndexOutOfBoundsException if the index is invalid
     */
    public void unmarkTask(int index) {
        validateIndex(index);
        this.tasks.get(index).setUndone();
    }

    /**
     * Prints the status of a specific task to the console.
     *
     * @param index the index of the task to print (0-based)
     * @throws ArrayIndexOutOfBoundsException if the index is invalid
     */
    public void printTask(int index) {
        validateIndex(index);
        this.tasks.get(index).printStatus();
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

    /**
     * Returns a copy of the task list.
     *
     * @return a new ArrayList containing all tasks
     */
    public List<Task> getTasks() {
        return new ArrayList<>(this.tasks);
    }

}
