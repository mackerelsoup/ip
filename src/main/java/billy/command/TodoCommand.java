package billy.command;

import billy.task.TaskList;
import billy.task.ToDos;
import billy.ui.Ui;

public class TodoCommand extends Command {
    public TodoCommand(String input) {
        super(input);
    }

    @Override
    public void execute(TaskList taskList, Ui ui) {
        this.input = input.trim();
        try {
            if (input.isEmpty()) {
                throw new IllegalArgumentException("Description of a todo cannot be empty");
            }
            taskList.addTask(new ToDos(input));
            ui.printAddTask(taskList);
        } catch (IllegalArgumentException e) {
            ui.showIllegalArgumentMessage(e.getMessage());
        } catch (Exception e) {
            ui.showUnknownErrorMessage();
        }

    }
}
