package billy.command;

import billy.task.TaskList;
import billy.ui.Ui;

public abstract class Command {
    protected Boolean isExit;
    protected String input;

    public Command(String input) {
        this.input = input;
        this.isExit = false;
    }

    public abstract void execute (TaskList tasklist, Ui ui);

    public Boolean shouldExit() {
        return isExit;
    }
}
