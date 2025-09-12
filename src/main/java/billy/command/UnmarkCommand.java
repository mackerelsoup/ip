package billy.command;

import billy.parser.Parser;
import billy.task.TaskList;
import billy.ui.Ui;

/**
 * Represents a command to unmark a task in the {@link TaskList} as not done.
 * <p>
 * This command expects user input to specify the index (1-based) of the task
 * to be marked as not completed. For example:
 * <pre>
 *     unmark 2
 * </pre>
 * will mark the second task in the task list as not done.
 * </p>
 * If the input is empty, invalid, or the index is out of range, an appropriate
 * error message will be displayed to the user through {@link Ui}.
 */
public class UnmarkCommand extends Command {
    public UnmarkCommand(String input) {
        super(input);
    }

    @Override
    public String execute(TaskList taskList, Ui ui) {
        try {
            int taskIndex = Parser.parseAndValidateTaskIndex(taskList, this.input);

            taskList.unmarkTask(taskIndex);
            return ui.getUnmarkTask(taskList, taskIndex);
        } catch (IllegalArgumentException e) {
            return ui.getIllegalArgumentMessage(e.getMessage());
        } catch (ArrayIndexOutOfBoundsException e) {
            return ui.getOutOfRangeMessage();
        } catch (Exception e) {
            return ui.getUnknownErrorMessage();
        }

    }

}
