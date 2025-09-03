package billy.command;

import billy.task.Events;
import billy.task.TaskList;
import billy.ui.Ui;

public class EventCommand extends Command {
    public EventCommand (String input) {
        super(input);
    }

    @Override
    public void execute (TaskList taskList, Ui ui) {
        input = input.trim();
        try {
            if (input.isEmpty()) {
                throw new IllegalArgumentException("Use the proper syntax: event <description> /from <start> /to <end>");
            }

            String[] eventParts = input.split("/from|/to");
            if (eventParts.length < 3) {
                throw new IllegalArgumentException("Use the proper syntax: event <description> /from <start> /to <end>");
            }

            String description = eventParts[0].trim();
            String eventStart = eventParts[1].trim();
            String eventEnd = eventParts[2].trim();
            if (description.isEmpty()) {
                throw new IllegalArgumentException("Event description cannot be empty");
            }

            taskList.addTask(new Events(description, false, eventStart, eventEnd));
            ui.printAddTask(taskList);

        } catch (IllegalArgumentException e) {
            ui.showIllegalArgumentMessage(e.getMessage());
        } catch (Exception e) {
            ui.showUnknownErrorMessage();
        }

    }
}
