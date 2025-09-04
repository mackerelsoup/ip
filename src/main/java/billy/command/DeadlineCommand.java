package billy.command;

import billy.task.Deadlines;
import billy.task.TaskList;
import billy.ui.Ui;


/**
 * Represents a command to create and add a {@link Deadlines} task to the task list.
 * <p>
 * This command expects user input in the format:
 * <pre>
 *     deadline &lt;description&gt; /by &lt;deadline&gt;
 * </pre>
 * For example:
 * <pre>
 *     deadline Submit assignment /by 2025-09-05
 * </pre>
 * If the input does not match this format or the description is missing,
 * an appropriate error message is displayed to the user.
 * </p>
 */
public class DeadlineCommand extends Command {
    public DeadlineCommand(String input) {
        super(input);
    }

    /**
     * Executes the command by:
     * <ol>
     *     <li>Validating the command input format.</li>
     *     <li>Extracting the description and deadline information.</li>
     *     <li>Creating a new {@link Deadlines} task and adding it to the {@link TaskList}.</li>
     *     <li>Displaying confirmation or error messages through {@link Ui}.</li>
     * </ol>
     * <p>
     * Expected input format: {@code deadline <description> /by <deadline>}
     * </p>
     *
     * @param taskList the task list where the new deadline task will be added
     * @param ui       the user interface used for displaying output messages
     */
    @Override
    public void execute(TaskList taskList, Ui ui) {
        input = input.trim();
        try {
            if (input.isEmpty()) {
                throw new IllegalArgumentException("Use the proper syntax: deadline <description> /by <deadline>");
            }

            String[] deadlineParts = input.split("/by", 2);
            if (deadlineParts.length < 2) {
                throw new IllegalArgumentException("Use the proper syntax: deadline <description> /by <deadline>");
            }

            String description = deadlineParts[0].trim();
            String deadline = deadlineParts[1].trim();
            if (description.isEmpty()) {
                throw new IllegalArgumentException("Deadline description cannot be empty");
            }

            taskList.addTask(new Deadlines(description, false, deadline));
            ui.printAddTask(taskList);
        } catch (IllegalArgumentException e) {
            ui.showIllegalArgumentMessage(e.getMessage());
        } catch (Exception e) {
            ui.showUnknownErrorMessage();
        }

    }
}
