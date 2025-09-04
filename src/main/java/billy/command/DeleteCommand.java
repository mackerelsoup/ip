package billy.command;

import billy.task.Task;
import billy.task.TaskList;
import billy.ui.Ui;

/**
 * Represents a command to delete a task from the {@link TaskList}.
 * <p>
 * This command expects user input to be the index (1-based) of the task
 * to be removed. For example:
 * <pre>
 *     delete 3
 * </pre>
 * would delete the third task in the task list.
 * </p>
 * If the index is invalid, empty, or out of range, an appropriate error message
 * will be displayed to the user.
 */
public class DeleteCommand extends Command {
    public DeleteCommand(String input) {
        super(input);
    }

    /**
     * Executes the command by:
     * <ol>
     *     <li>Validating that the input is not empty.</li>
     *     <li>Parsing the task index from the input string.</li>
     *     <li>Checking that the index is within the valid range of the {@link TaskList}.</li>
     *     <li>Removing the corresponding {@link Task} from the task list.</li>
     *     <li>Displaying a confirmation message through the {@link Ui}.</li>
     * </ol>
     * <p>
     * Expected input format: {@code delete <task_index>} (1-based index)
     * </p>
     *
     * @param taskList the list of tasks from which a task will be removed
     * @param ui       the user interface used to display messages to the user
     */
    @Override
    public String execute(TaskList taskList, Ui ui) {
        try {
            this.input = input.trim();
            if (input.isEmpty()) {
                throw new IllegalArgumentException();
            }

            int taskIndex = Integer.parseInt(input);
            if (taskIndex < 1 || taskIndex > taskList.getSize()) {
                throw new ArrayIndexOutOfBoundsException("");
            }

            Task removedTask = taskList.removeTask(taskIndex - 1);
            return ui.getRemoveTask(taskList, removedTask);


        } catch (IllegalArgumentException e) {
            return ui.getIllegalArgumentMessage(e.getMessage());
        } catch (ArrayIndexOutOfBoundsException e) {
            return ui.getOutOfRangeMessage();
        } catch (Exception e) {
            return ui.getUnknownErrorMessage();
        }

    }

}
