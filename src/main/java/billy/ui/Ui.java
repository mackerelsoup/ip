package billy.ui;

import java.util.ArrayList;

import billy.task.Task;
import billy.task.TaskList;

/**
 * Handles all user interactions by generating messages as strings.
 */
public class Ui {

    /**
     * Returns a divider line for formatting purposes.
     */
    public String divider() {
        return "_".repeat(50) + "\n";
    }

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
        StringBuilder sb = new StringBuilder();
        sb.append("Here are the tasks in your list:\n");
        for (int i = 0; i < taskList.getSize(); i++) {
            sb.append(i + 1).append(".").append(taskList.getTask(i).getStatusIcon())
                    .append(" ").append(taskList.getTask(i).getDescription()).append("\n");
        }
        return sb.toString();
    }

    /**
     * Returns a string of matching tasks.
     */
    public String getMatchingTasks(ArrayList<Task> matchingTasks) {
        StringBuilder sb = new StringBuilder();
        sb.append("Here are the matching tasks in your list:\n");
        for (int i = 0; i < matchingTasks.size(); i++) {
            sb.append(i + 1).append(".").append(matchingTasks.get(i).getStatusIcon())
                    .append(" ").append(matchingTasks.get(i).getDescription()).append("\n");
        }
        return sb.toString();
    }

    /**
     * Returns a string confirming a task was added.
     */
    public String getAddTask(TaskList taskList) {
        StringBuilder sb = new StringBuilder();
        sb.append("Got it, I've added this task:\n    ");
        sb.append(taskList.getLatestTask().getStatusIcon())
                .append(" ").append(taskList.getLatestTask().getDescription()).append("\n");
        sb.append(getNumberOfTasks(taskList));
        return sb.toString();
    }

    /**
     * Returns a string confirming a task was marked as done.
     */
    public String getMarkTask(TaskList taskList, int index) {
        Task t = taskList.getTask(index - 1);
        return "Nice! I've marked this task as done:\n    " + t.getStatusIcon() + " " + t.getDescription() + "\n";
    }

    /**
     * Returns a string confirming a task was unmarked.
     */
    public String getUnmarkTask(TaskList taskList, int index) {
        Task t = taskList.getTask(index - 1);
        return "Ok! I've marked this task as not done yet:\n    " + t.getStatusIcon() + " " + t.getDescription() + "\n";
    }

    /**
     * Returns a string confirming a task was removed.
     */
    public String getRemoveTask(TaskList taskList, Task task) {
        StringBuilder sb = new StringBuilder();
        sb.append("Noted, I've removed this task:\n    ");
        sb.append(task.getStatusIcon()).append(" ").append(task.getDescription()).append("\n");
        sb.append(getNumberOfTasks(taskList));
        return sb.toString();
    }

    /**
     * Returns a string of all tasks loaded from storage.
     */
    public String getListLoaded(ArrayList<Task> taskList) {
        StringBuilder sb = new StringBuilder();
        sb.append("List loaded:\n");
        for (Task t : taskList) {
            sb.append(t.getStatusIcon()).append(" ").append(t.getDescription()).append("\n");
        }
        return sb.toString();
    }

    /**
     * Returns the introduction message.
     */
    public String getIntro() {
        return divider() + "Hello, I'm Billy!\nWhat can I do for you?\n" + divider();
    }

    /**
     * Returns the goodbye message.
     */
    public String getBye() {
        return divider() + "Bye. Hope to see you again soon!\n" + divider();
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
