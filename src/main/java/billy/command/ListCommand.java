package billy.command;

import billy.task.TaskList;
import billy.ui.Ui;

/**
 * Represents a command to display all tasks currently in the {@link TaskList}.
 * <p>
 * This command simply prints the entire task list to the user interface
 * without modifying any tasks.
 * </p>
 */
public class ListCommand extends Command {
    public ListCommand(String input) {
        super(input);
    }

    @Override
    public void execute(TaskList tasklist, Ui ui) {
        ui.printTaskList(tasklist);
    }
}
