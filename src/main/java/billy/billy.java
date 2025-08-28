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
            Command command = Parser.parseFullCommand(line);
            command.execute(taskList, ui);
            exit = command.isExit();
        }
        storage.writeFile(taskList);
        ui.bye();
    }

    public static void main(String[] args) {
        Ui ui = new Ui();
        Storage storage = new Storage("./data/initialList.txt");
        ArrayList<Task> intialList;

        try {
            intialList = Parser.parseLines(storage.readFile());
        } catch (IllegalArgumentException exception) {
            System.out.println(exception.getMessage());
            intialList = new ArrayList<>();
        }


        billy.run(intialList, ui, storage);
    }
}