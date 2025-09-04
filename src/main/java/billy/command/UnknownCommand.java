package billy.command;

import billy.task.TaskList;
import billy.ui.Ui;

/**
 * Represents a command that handles unrecognized or invalid user input.
 * <p>
 * When executed, this command informs the user that the entered command is
 * not recognized and prompts them to try another command.
 * </p>
 */
public class UnknownCommand extends Command {
    public UnknownCommand(String input) {
        super(input);
    }

    @Override
    public void execute(TaskList taskList, Ui ui) {
        ui.showIllegalArgumentMessage("Unknown command, try another command");
    }
}
