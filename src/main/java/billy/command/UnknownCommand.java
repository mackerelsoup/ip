package billy.command;

import billy.task.TaskList;
import billy.ui.Ui;

public class UnknownCommand extends Command {
    public UnknownCommand(String input) {
        super(input);
    }

    @Override
    public void execute (TaskList taskList, Ui ui) {
        ui.showIllegalArgumentMessage("Unknown command, try another command");
    }
}
