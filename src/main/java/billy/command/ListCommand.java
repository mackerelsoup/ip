package billy.command;

import billy.task.TaskList;
import billy.ui.Ui;

public class ListCommand extends Command {
    public ListCommand(String input) {
        super(input);
    }

    @Override
    public void execute(TaskList tasklist, Ui ui) {
        ui.printTaskList(tasklist);
    }
}
