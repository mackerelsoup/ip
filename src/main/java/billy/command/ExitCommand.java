package billy.command;

import billy.ui.Ui;
import billy.task.TaskList;

public class ExitCommand extends Command {

    public ExitCommand(String input) {
        super(input);
        this.isExit = true;
    }

    @Override
    public void execute (TaskList tasklist, Ui ui) {
        ui.printBye();
    }
}
