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
        return "Now you have " + taskList.getSize() + " task(s) in the list\n";
    }

    /**
     * Returns a string representation of the full task list.
     */
    public String getTaskList(TaskList taskList) {
        return "Here are the tasks in your list:\n"
                + IntStream.range(0, taskList.getSize())
                        .mapToObj(i -> (i + 1) + ". " + taskList.getTask(i).getStatus())
                        .collect(Collectors.joining("\n")) + "\n";
    }


    /**
     * Returns a string of matching tasks.
     */
    public String getMatchingTasks(List<Task> matchingTasks) {
        return "Here are the matching tasks in your list:\n"
                + IntStream.range(0, matchingTasks.size())
                        .mapToObj(i -> {
                            Task task = matchingTasks.get(i);
                            return (i + 1) + "." + task.getStatusIcon() + " " + task.getDescription();
                        })
                        .collect(Collectors.joining("\n")) + "\n";
    }


    /**
     * Returns a string confirming a task was added.
     */
    public String getAddTask(TaskList taskList) {
        StringBuilder sb = new StringBuilder();
        sb.append("Got it, I've added this task:\n  ");
        sb.append(taskList.getLatestTask().getStatus()).append("\n");
        sb.append(getNumberOfTasks(taskList));
        return sb.toString();
    }

    public String getAddConflictingEvent(ArrayList<Events> conflictingTasks) {
        return "These events are conflicting with the event you have added:\n"
                + conflictingTasks.stream()
                .map(task -> task.getStatus())
                .collect(Collectors.joining("\n"));
    }

    /**
     * Returns a string confirming a task was marked as done.
     */
    public String getMarkTask(TaskList taskList, int index) {
        Task t = taskList.getTask(index - 1);
        return "Nice! I've marked this task as done:\n    " + t.getStatus() + "\n";
    }

    /**
     * Returns a string confirming a task was unmarked.
     */
    public String getUnmarkTask(TaskList taskList, int index) {
        Task t = taskList.getTask(index - 1);
        return "Ok! I've marked this task as not done yet:\n    " + t.getStatus() + "\n";
    }

    /**
     * Returns a string confirming a task was removed.
     */
    public String getRemoveTask(TaskList taskList, Task task) {
        StringBuilder sb = new StringBuilder();
        sb.append("Noted, I've removed this task:\n    ");
        sb.append(task.getStatus()).append("\n");
        sb.append(getNumberOfTasks(taskList));
        return sb.toString();
    }

    /**
     * Returns a string of all tasks loaded from storage.
     */
    public String getListLoaded(ArrayList<Task> taskList) {
        return "List loaded:\n"
                + taskList.stream()
                        .map(Task::getStatus)
                        .collect(Collectors.joining("\n")) + "\n";
    }

    public String getEarliestFreeTime(LocalDateTime earliestTime, int duration) {
        return String.format("The earliest time with free %d hour time slot is at: ", duration)
                + earliestTime.toString()
                + "\n";
    }


    /**
     * Returns the introduction message.
     */
    public String getIntro() {
        return "Hello, I'm Billy!\nWhat can I do for you?\n";
    }

    /**
     * Returns the goodbye message.
     */
    public String getBye() {
        return "Bye. Hope to see you again soon!\n";
    }

    /**
     * Returns an invalid index message.
     */
    public String getInvalidIndexMessage() {
        return "Enter a valid index\n";
    }

    /**
     * Returns a string for illegal argument messages.
     */
    public String getIllegalArgumentMessage(String message) {
        return message + "\n";
    }

    /**
     * Returns a message for out-of-range task numbers.
     */
    public String getOutOfRangeMessage() {
        return "Task number is out of range\n";
    }

    /**
     * Returns a message for unknown errors.
     */
    public String getUnknownErrorMessage() {
        return "An unknown error occurred\n";
    }
}
