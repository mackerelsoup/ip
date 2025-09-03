package billy.command;

import billy.task.TaskList;
import billy.ui.Ui;

public class MarkCommand extends Command {
    public MarkCommand(String input) {
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

            taskList.markTask(taskIndex - 1);
            ui.printMarkTask(taskList, taskIndex);


        } catch (IllegalArgumentException e) {
            ui.showIllegalArgumentMessage(e.getMessage());
        } catch (ArrayIndexOutOfBoundsException e) {
            ui.showOutOfRangeMessage();
        } catch (Exception e) {
            ui.showUnknownErrorMessage();
        }

    }

}
