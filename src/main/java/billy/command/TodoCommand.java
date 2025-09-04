package billy.command;

import billy.task.TaskList;
import billy.task.ToDos;
import billy.ui.Ui;

/**
 * Represents a command to create and add a {@link ToDos} task to the {@link TaskList}.
 * <p>
 * This command expects user input to provide a description for the to-do task.
 * Example:
 * <pre>
 *     todo Buy groceries
 * </pre>
 * will add a new to-do task with the description "Buy groceries".
 * </p>
 * If the description is empty, an error message is displayed to the user
 * through {@link Ui}.
 */
public class TodoCommand extends Command {
    public TodoCommand(String input) {
        super(input);
    }

    @Override
    public void execute(TaskList taskList, Ui ui) {
        this.input = input.trim();
        try {
            if (input.isEmpty()) {
                throw new IllegalArgumentException("Description of a todo cannot be empty");
            }
            taskList.addTask(new ToDos(input));
            ui.printAddTask(taskList);
        } catch (IllegalArgumentException e) {
            ui.showIllegalArgumentMessage(e.getMessage());
        } catch (Exception e) {
            ui.showUnknownErrorMessage();
        }

    }
}
