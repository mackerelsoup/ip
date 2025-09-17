package billy;


import java.io.IOException;
import java.util.ArrayList;

import billy.command.Command;
import billy.command.CommandResponse;
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
    private String fileStatus;

    public BillyGui() {
        this.ui = new Ui();
        this.storage = new Storage("./data/initialList.txt");
        ParseResult parseResult;
        try {
            parseResult = Parser.parseStorageLines(storage.readFile(), ui);
        } catch (IOException e) {
            parseResult = new ParseResult(new ArrayList<>(), ui.getFileLoadError(e.getMessage()));
        }
        this.fileStatus = parseResult.getMessage();
        taskList = new TaskList(parseResult.getTaskList());
    }

    public String getInto() {
        return ui.getIntro();
    }

    public String getFileStatus() {
        return fileStatus;
    }

    public CommandResponse getResponse(String input) {
        Command command = Parser.parseCommand(input);

        // Capture console output into a string
        String response = command.execute(taskList, ui);

        if (command.shouldExit()) {
            storage.writeFile(taskList);
        }
        return new CommandResponse(response, command.shouldExit());
    }
}
