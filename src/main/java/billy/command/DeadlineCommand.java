package billy.command;

import billy.task.Deadlines;
import billy.task.TaskList;
import billy.ui.Ui;

public class DeadlineCommand extends Command {
    public DeadlineCommand (String input) {
        super(input);
    }

    @Override
    public void execute (TaskList taskList, Ui ui) {
        input = input.trim();
        try {
            if (input.isEmpty()) {
                throw new IllegalArgumentException("Use the proper syntax: deadline <description> /by <deadline>");
            }

            String[] deadlineParts = input.split("/by", 2);
            if (deadlineParts.length < 2) {
                throw new IllegalArgumentException("Use the proper syntax: deadline <description> /by <deadline>");
            }

            String description = deadlineParts[0].trim();
            String deadline = deadlineParts[1].trim();
            if (description.isEmpty()) {
                throw new IllegalArgumentException("Deadline description cannot be empty");
            }

            taskList.addTask(new Deadlines(description, false, deadline));
            ui.printAddTask(taskList);
        } catch (IllegalArgumentException e) {
            ui.showIllegalArgumentMessage(e.getMessage());
        } catch (Exception e) {
            ui.showUnknownErrorMessage();
        }

    }
}
