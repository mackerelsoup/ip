package billy.ui;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import billy.task.Events;
import billy.task.Task;
import billy.task.TaskList;

/**
 * Handles all user interactions by generating messages as strings.
 */
public class Ui {
    /**
     * Returns the total number of tasks message.
     */
    public String getNumberOfTasks(TaskList taskList) {
        int size = taskList.getSize();
        return "You now have " + size + (size == 1 ? " task" : " tasks") + " in your list.";
    }

    /**
     * Returns a string representation of the full task list.
     */
    public String getTaskList(TaskList taskList) {
        if (taskList.getSize() == 0) {
            return "Your task list is empty. Add some tasks to get started!";
        }
        return "Here are your tasks:\n"
                + IntStream.range(0, taskList.getSize())
                        .mapToObj(i -> (i + 1) + ". " + taskList.getTask(i).getStatus())
                        .collect(Collectors.joining("\n"));
    }


    /**
     * Returns a string of matching tasks.
     */
    public String getMatchingTasks(List<Task> matchingTasks) {
        if (matchingTasks.isEmpty()) {
            return "No matching tasks found. Try a different search term.";
        }
        return "Found " + matchingTasks.size() + " matching task" + (matchingTasks.size() == 1 ? "" : "s") + ":\n"
                + IntStream.range(0, matchingTasks.size())
                        .mapToObj(i -> {
                            Task task = matchingTasks.get(i);
                            return (i + 1) + ". " + task.getStatusIcon() + " " + task.getDescription();
                        })
                        .collect(Collectors.joining("\n"));
    }


    /**
     * Returns a string confirming a task was added.
     */
    public String getAddTask(TaskList taskList) {
        StringBuilder sb = new StringBuilder();
        sb.append("✓ Task added successfully:\n  ");
        sb.append(taskList.getLatestTask().getStatus()).append("\n");
        sb.append(getNumberOfTasks(taskList));
        return sb.toString();
    }

    public String getAddConflictingEvent(ArrayList<Events> conflictingTasks) {
        return "⚠Warning: The following events conflict with your new event:\n"
                + conflictingTasks.stream()
                .map(task -> "• " + task.getStatus())
                .collect(Collectors.joining("\n"));
    }

    /**
     * Returns a string confirming a task was marked as done.
     */
    public String getMarkTask(TaskList taskList, int index) {
        Task t = taskList.getTask(index);
        return "✓ Great! Task completed:\n  " + t.getStatus();
    }

    /**
     * Returns a string confirming a task was unmarked.
     */
    public String getUnmarkTask(TaskList taskList, int index) {
        Task t = taskList.getTask(index);
        return "Task unmarked:\n  " + t.getStatus();
    }

    /**
     * Returns a string confirming a task was removed.
     */
    public String getRemoveTask(TaskList taskList, Task task) {
        StringBuilder sb = new StringBuilder();
        sb.append("✓ Task removed:\n  ");
        sb.append(task.getStatus()).append("\n");
        sb.append(getNumberOfTasks(taskList));
        return sb.toString();
    }

    /**
     * Returns a string of all tasks loaded from storage.
     */
    public String getListLoaded(ArrayList<Task> taskList) {
        if (taskList.isEmpty()) {
            return "Welcome! Your task list is empty. Start by adding some tasks.";
        }
        return "Welcome back! Loaded " + taskList.size() + " task" + (taskList.size() == 1 ? "" : "s") + " from storage:\n"
                + taskList.stream()
                        .map(Task::getStatus)
                        .collect(Collectors.joining("\n"));
    }

    /**
     * Returns a formatted message showing the earliest available free time slot.
     *
     * @param earliestTime the earliest available start time
     * @param duration the requested duration in hours
     * @return formatted message with the free time information
     */
    public String getEarliestFreeTime(LocalDateTime earliestTime, int duration) {
        return String.format("✅ Found your next free slot!\nEarliest available %d-hour slot: %s", 
                duration, earliestTime.toString());
    }


    /**
     * Returns the introduction message.
     */
    public String getIntro() {
        return "Hello! I'm Billy, your personal task manager. 😊\nI can help you add, manage, and organize your tasks. What would you like to do?";
    }

    /**
     * Returns the goodbye message.
     */
    public String getBye() {
        return "Goodbye! Have a productive day! 👋";
    }

    /**
     * Returns an invalid index message.
     */
    public String getInvalidIndexMessage() {
        return "⚠Please enter a valid task number.";
    }

    /**
     * Returns a formatted error message for illegal arguments.
     *
     * @param message the error message to format
     * @return formatted error message with newline
     */
    public String getIllegalArgumentMessage(String message) {
        return "⚠" + message;
    }

    /**
     * Returns a message for out-of-range task numbers.
     */
    public String getOutOfRangeMessage() {
        return "⚠Task number is out of range. Please check your task list.";
    }

    /**
     * Returns a message for unknown errors.
     */
    public String getUnknownErrorMessage() {
        return "❌ Oops! Something went wrong. Please try again.";
    }
}
