package billy.command;

import billy.task.TaskList;
import billy.ui.Ui;


/**
 * Represents an abstract command that can be executed in the application.
 *
 * <p>Each {@code Command} encapsulates a user input string and defines behavior
 * through its {@link #execute(TaskList, Ui)} method. Subclasses should implement
 * this method to specify how the command manipulates the {@code TaskList},
 * interacts with the {@code Ui}, and potentially signals program termination.</p>
 */
public abstract class Command {
    protected Boolean isExit;
    protected String input;

    /**
     * Constructs a {@code Command} with the given user input.
     *
     * @param input the raw input string representing this command
     */
    public Command(String input) {
        this.input = input;
        this.isExit = false;
    }


    public abstract void execute(TaskList tasklist, Ui ui);

    public Boolean shouldExit() {
        return isExit;
    }
}
