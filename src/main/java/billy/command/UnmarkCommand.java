package billy.command;

import billy.task.TaskList;
import billy.ui.Ui;

public class UnmarkCommand extends Command {
    public UnmarkCommand(String input) {
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

            taskList.unmarkTask(taskIndex - 1);
            ui.printUnmarkTask(taskList, taskIndex);


        } catch (IllegalArgumentException e) {
            ui.showIllegalArgumentMessage(e.getMessage());
        } catch (ArrayIndexOutOfBoundsException e) {
            ui.showOutOfRangeMessage();
        } catch (Exception e) {
            ui.showUnknownErrorMessage();
        }

    }

}
