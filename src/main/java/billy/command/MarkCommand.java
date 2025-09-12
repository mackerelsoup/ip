package billy.command;

import billy.parser.Parser;
import billy.task.TaskList;
import billy.ui.Ui;

/**
 * Represents a command to mark a task in the {@link TaskList} as done.
 * <p>
 * This command expects user input to be the index (1-based) of the task
 * to mark as completed. For example:
 * <pre>
 *     mark 2
 * </pre>
 * will mark the second task in the task list as done.
 * </p>
 * If the input is empty, invalid, or out of range, an appropriate error
 * message will be displayed to the user through {@link Ui}.
 */
public class MarkCommand extends Command {
    public MarkCommand(String input) {
        super(input);
    }

    @Override
    public String execute(TaskList taskList, Ui ui) {
        try {
            int taskIndex = Parser.parseAndValidateTaskIndex(taskList, this.input);
            taskList.markTask(taskIndex);
            return ui.getMarkTask(taskList, taskIndex);
        } catch (IllegalArgumentException e) {
            return ui.getIllegalArgumentMessage(e.getMessage());
        } catch (ArrayIndexOutOfBoundsException e) {
            return ui.getOutOfRangeMessage();
        } catch (Exception e) {
            return ui.getUnknownErrorMessage();
        }

    }

}
