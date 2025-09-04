package billy;


import billy.command.Command;
import billy.parser.ParseResult;
import billy.parser.Parser;
import billy.storage.Storage;
import billy.task.TaskList;
import billy.ui.Ui;



/**
 * Entry point for the Billy task management application.
 * <p>
 * This class initializes the user interface, loads tasks from storage, and
 * starts the main command processing loop. It allows users to manage tasks
 * through various commands such as adding, marking, unmarking, deleting, and
 * finding tasks.
 * </p>
 */
public class BillyGui {
    private Ui ui;
    private TaskList taskList;
    private Storage storage;

    public BillyGui() {
        this.ui = new Ui();
        this.storage = new Storage("./data/initialList.txt");
        ParseResult parseResult;
        parseResult = Parser.parseStorageLines(storage.readFile(), ui);
        taskList = new TaskList(parseResult.getTaskList());
    }

    /**
     * Process a single line of user input and return output as a string.
     *
     * @param input the user's input
     * @return the response to display in the GUI
     */
    public String getResponse(String input) {
        Command command = Parser.parseCommand(input);

        // Capture console output into a string
        String response = command.execute(taskList, ui);
        // Save tasks if this was an exit command
        if (command.shouldExit()) {
            storage.writeFile(taskList);
        }

        return response;
    }
}
