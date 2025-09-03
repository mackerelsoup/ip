package billy.command;

import billy.task.Task;
import billy.task.TaskList;
import billy.ui.Ui;

public class DeleteCommand extends Command {
    public DeleteCommand(String input) {
        super(input);
    }

    @Override
    public void execute (TaskList taskList, Ui ui) {
        try {
            this.input = input.trim();
            if (input.isEmpty()) {
                throw new IllegalArgumentException();
            }

            int taskIndex = Integer.parseInt(input);
            if (taskIndex < 1 || taskIndex > taskList.getSize()) {
                throw new ArrayIndexOutOfBoundsException("");
            }

            Task removedTask = taskList.removeTask(taskIndex - 1);
            ui.printRemoveTask(taskList, removedTask);


        } catch (IllegalArgumentException e) {
            ui.showIllegalArgumentMessage(e.getMessage());
        } catch (ArrayIndexOutOfBoundsException e) {
            ui.showOutOfRangeMessage();
        } catch (Exception e) {
            ui.showUnknownErrorMessage();
        }

    }

}
