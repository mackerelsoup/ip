package billy.command;

import java.util.ArrayList;

import billy.task.Task;
import billy.task.TaskList;
import billy.ui.Ui;

public class FindCommand extends Command {
    public FindCommand(String input) {
        super(input);
    }

    @Override
    public void execute (TaskList taskList, Ui ui) {
        try {
            this.input = input.trim();
            if (input.isEmpty()) {
                throw new IllegalArgumentException("Use the proper syntax: find <task>");
            }

            ArrayList<Task> matching = new ArrayList<>();
            for (int i = 0; i < taskList.getSize(); i++) {
                Task current = taskList.getTask(i);
                if (current.getDescription().contains(input)) {
                    matching.add(current);
                }
            }

            ui.printMatchingTasks(matching);
        } catch (IllegalArgumentException e) {
            ui.showIllegalArgumentMessage(e.getMessage());
        } catch (ArrayIndexOutOfBoundsException e) {
            ui.showOutOfRangeMessage();
        } catch (Exception e) {
            ui.showUnknownErrorMessage();
        }

    }
}
