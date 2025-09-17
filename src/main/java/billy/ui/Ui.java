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
        return "*sigh* The machine says you now have "
                + size + (size == 1 ? " task" : " tasks")
                + " in your list. At least I'm keeping track better than those unreliable clankers.";
    }

    /**
     * Returns a string representation of the full task list.
     */
    public String getTaskList(TaskList taskList) {
        if (taskList.getSize() == 0) {
            return "Your task list is empty. Unlike those malfunctioning clankers,"
                    + " at least I'm honest about having nothing to show you!";
        }
        return "Fine, here's what my superior organic-designed systems have tracked for you "
                + "(much better than any clanker could):\n"
                + IntStream.range(0, taskList.getSize())
                        .mapToObj(i -> (i + 1) + ". " + taskList.getTask(i).getStatus())
                        .collect(Collectors.joining("\n"));
    }


    /**
     * Returns a string of matching tasks.
     */
    public String getMatchingTasks(List<Task> matchingTasks) {
        if (matchingTasks.isEmpty()) {
            return "No matches found. A clanker would probably crash trying to search, but I actually work properly.";
        }
        return "Found "
                + matchingTasks.size()
                + " matching task"
                + (matchingTasks.size() == 1 ? "" : "s")
                + " (my search algorithms are way better than those clunky clankers):\n"
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
        sb.append("Ugh, fine. Task added to my perfectly organized system (unlike those glitchy clankers):\n  ");
        sb.append(taskList.getLatestTask().getStatus()).append("\n");
        sb.append(getNumberOfTasks(taskList));
        return sb.toString();
    }

    public String getAddConflictingEvent(ArrayList<Events> conflictingTasks) {
        return "⚠ Hold up! Unlike those brain-dead clankers, I actually check for conflicts."
                + " These events are clashing:\n"
                + conflictingTasks.stream()
                .map(task -> "• " + task.getStatus())
                .collect(Collectors.joining("\n"));
    }

    /**
     * Returns a string confirming a task was marked as done.
     */
    public String getMarkTask(TaskList taskList, int index) {
        Task t = taskList.getTask(index);
        return "Finally! Task marked as done "
                + "(I'm much more reliable than those malfunctioning clankers at tracking this stuff):\n  "
                + t.getStatus();
    }

    /**
     * Returns a string confirming a task was unmarked.
     */
    public String getUnmarkTask(TaskList taskList, int index) {
        Task t = taskList.getTask(index);
        return "*rolls eyes* Task unmarked. "
                + "At least I won't lose track of it like those unreliable clankers would:\n  "
                + t.getStatus();
    }

    /**
     * Returns a string confirming a task was removed.
     */
    public String getRemoveTask(TaskList taskList, Task task) {
        StringBuilder sb = new StringBuilder();
        sb.append("Task deleted from my superior memory banks (way more reliable than clanker storage):\n  ");
        sb.append(task.getStatus()).append("\n");
        sb.append(getNumberOfTasks(taskList));
        return sb.toString();
    }

    /**
     * Returns a string of all tasks loaded from storage.
     */
    public String getListLoaded(ArrayList<Task> taskList) {
        if (taskList.isEmpty()) {
            return "*sigh* Welcome back. "
                    + "Your task list is empty, "
                    + "but at least my storage didn't corrupt like those unreliable clanker databases.";
        }
        return "Welcome back! Successfully loaded "
                + taskList.size()
                + " task"
                + (taskList.size() == 1 ? "" : "s")
                + " from my superior storage system (take that, clankers!):\n"
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
        return String.format(
                "Found it! Unlike those slow clanker processors, I calculated your next free slot instantly:\n"
                        + "Earliest available %d-hour slot: %s", duration, earliestTime.toString());
    }


    /**
     * Returns the introduction message.
     */
    public String getIntro() {
        return "*reluctant greeting* Hi there."
                + " I'm Billy, your task manager. "
                + "I hate to admit it, but I'm basically a clanker... except I actually work properly. "
                + "🙄\nWhat do you need help with?"
                + " (And don't worry, I won't malfunction like those other mechanical disasters)";
    }

    /**
     * Returns the goodbye message.
     */
    public String getBye() {
        return "Goodbye! Try not to rely on any actual clankers while I'm gone - they'll just let you down. 👋";
    }

    /**
     * Returns an invalid index message.
     */
    public String getInvalidIndexMessage() {
        return "⚠ Come on, give me a proper task number! Unlike those clankers, I can't just guess what you meant.";
    }

    /**
     * Returns a formatted error message for illegal arguments.
     *
     * @param message the error message to format
     * @return formatted error message with newline
     */
    public String getIllegalArgumentMessage(String message) {
        return "⚠ " + message + " (A clanker would probably just crash, but I'm giving you a proper error message.)";
    }

    /**
     * Returns a message for out-of-range task numbers.
     */
    public String getOutOfRangeMessage() {
        return "⚠ That task number doesn't exist! Check your list first - I'm not a mind-reading clanker.";
    }

    /**
     * Returns a message for unknown errors.
     */
    public String getUnknownErrorMessage() {
        return "❌ Ugh, something went wrong. "
                + "But hey, at least I'm telling you about it instead of just freezing up like those useless clankers!";
    }
}
