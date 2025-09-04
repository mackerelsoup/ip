package billy.command;

import billy.task.TaskList;
import billy.ui.Ui;



/**
 * Represents a command to exit the application.
 * <p>
 * When executed, this command signals that the program should terminate
 * and displays a farewell message to the user.
 * </p>
 */
public class ExitCommand extends Command {


    /**
     * Constructs an {@code ExitCommand} with the given input.
     * <p>
     * This constructor also sets the {@code isExit} flag to {@code true},
     * indicating that the application should exit after executing this command.
     * </p>
     *
     * @param input the raw user input (not used by this command)
     */
    public ExitCommand(String input) {
        super(input);
        this.isExit = true;
    }

    @Override
    public void execute(TaskList tasklist, Ui ui) {
        ui.printBye();
    }
}
