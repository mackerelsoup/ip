package billy;

import java.util.ArrayList;

import billy.command.Command;
import billy.parser.Parser;
import billy.storage.Storage;
import billy.task.Task;
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
public class Billy {

    /**
     * Runs the main application loop for Billy.
     * <p>
     * This method:
     * <ul>
     *   <li>Initializes a {@link TaskList} with tasks loaded from storage.</li>
     *   <li>Continuously prompts the user for input.</li>
     *   <li>Parses user commands and executes them.</li>
     *   <li>Saves tasks back to storage when the user exits.</li>
     * </ul>
     * </p>
     *
     * @param initialList the list of tasks loaded from storage
     * @param ui the {@link Ui} instance for handling user interaction
     * @param storage the {@link Storage} instance for reading and writing tasks
     */
    public static void run(ArrayList<Task> initialList, Ui ui, Storage storage) {
        TaskList taskList = new TaskList(initialList);
        String line;
        boolean exit = false;
        while (!exit) {
            line = ui.getUserInput();
            Command command = Parser.parseCommand(line);
            command.execute(taskList, ui);
            exit = command.shouldExit();
        }
        storage.writeFile(taskList);
        ui.printBye();
    }

    public static void main(String[] args) {
        Ui ui = new Ui();
        Storage storage = new Storage("./data/initialList.txt");
        ArrayList<Task> intialList;

        intialList = Parser.parseStorageLines(storage.readFile(), ui);
        Billy.run(intialList, ui, storage);
    }
}
