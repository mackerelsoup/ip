package billy.command;

import java.util.ArrayList;

import billy.task.Task;
import billy.task.TaskList;
import billy.ui.Ui;

/**
 * Represents a command to search for tasks in the {@link TaskList}
 * that contain a given keyword in their description.
 * <p>
 * This command expects user input in the following format:
 * <pre>
 *     find &lt;keyword&gt;
 * </pre>
 * For example:
 * <pre>
 *     find report
 * </pre>
 * will display all tasks whose descriptions contain the word "report".
 * </p>
 * If the input is empty or invalid, an error message is shown to the user.
 */
public class FindCommand extends Command {
    public FindCommand(String input) {
        super(input);
    }


    /**
     * Executes the command by:
     * <ol>
     *     <li>Validating that the input is not empty.</li>
     *     <li>Iterating over the {@link TaskList} to find tasks
     *         whose descriptions contain the specified keyword.</li>
     *     <li>Collecting all matching {@link Task} objects.</li>
     *     <li>Displaying the results to the user through the {@link Ui}.</li>
     * </ol>
     * <p>
     * Expected input format: {@code find <keyword>}
     * </p>
     *
     * @param taskList the list of tasks to search
     * @param ui       the user interface used to display the results
     */
    @Override
    public String execute(TaskList taskList, Ui ui) {
        try {
            this.input = input.trim();
            if (input.isEmpty()) {
                throw new IllegalArgumentException("Use the proper syntax: find <task>");
            }

            ArrayList<Task> matching = new ArrayList<>();
            for (int i = 0; i < taskList.getSize(); i++) {
                Task current = taskList.getTask(i);
                if (current.getDescription().contains(input)) {
                    matching.add(current);
                }
            }

            return ui.getMatchingTasks(matching);
        } catch (IllegalArgumentException e) {
            return ui.getIllegalArgumentMessage(e.getMessage());
        } catch (ArrayIndexOutOfBoundsException e) {
            return ui.getOutOfRangeMessage();
        } catch (Exception e) {
            return ui.getUnknownErrorMessage();
        }

    }
}
