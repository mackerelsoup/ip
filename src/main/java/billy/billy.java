package billy;

import java.util.ArrayList;

import billy.command.Command;
import billy.task.TaskList;
import billy.task.Task;
import billy.ui.Ui;
import billy.storage.Storage;
import billy.parser.Parser;



public class billy {

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
        billy.run(intialList, ui, storage);
    }
}