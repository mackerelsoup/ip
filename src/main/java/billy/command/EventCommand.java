package billy.command;

import java.util.ArrayList;

import billy.task.Events;
import billy.task.TaskList;
import billy.ui.Ui;

/**
 * Represents a command to create and add an {@link Events} task to the {@link TaskList}.
 * <p>
 * This command expects the user input to specify an event description, a start time,
 * and an end time using the format:
 * <pre>
 *     event &lt;description&gt; /from &lt;start&gt; /to &lt;end&gt;
 * </pre>
 * Example:
 * <pre>
 *     event Team meeting /from 2025-09-05 10:00 /to 2025-09-05 12:00
 * </pre>
 * If the input does not follow this format or is missing details, an error message
 * will be displayed to the user through {@link Ui}.
 * </p>
 */
public class EventCommand extends Command {
    public EventCommand(String input) {
        super(input);
    }

    /**
     * Executes the command by:
     * <ol>
     *     <li>Validating that the input is not empty.</li>
     *     <li>Splitting the input into description, start time, and end time parts.</li>
     *     <li>Creating a new {@link Events} task with the provided details.</li>
     *     <li>Adding the new task to the {@link TaskList}.</li>
     *     <li>Displaying confirmation or error messages using {@link Ui}.</li>
     * </ol>
     * <p>
     * Expected input format: {@code event <description> /from <start> /to <end>}
     * </p>
     *
     * @param taskList the list of tasks to which the new event will be added
     * @param ui       the user interface used for displaying messages to the user
     */
    @Override
    public String execute(TaskList taskList, Ui ui) {
        input = input.trim();
        try {
            if (input.isEmpty()) {
                throw new IllegalArgumentException("Use the proper syntax: "
                        + "event <description> /from <start> /to <end>");
            }

            String[] eventParts = input.split("/from|/to");
            if (eventParts.length < 3) {
                throw new IllegalArgumentException("Use the proper syntax:"
                        + "event <description> /from <start> /to <end>");
            }

            String description = eventParts[0].trim();
            String eventStart = eventParts[1].trim();
            String eventEnd = eventParts[2].trim();
            if (description.isEmpty()) {
                throw new IllegalArgumentException("Event description cannot be empty");
            }

            Events event = new Events(description, false, eventStart, eventEnd);
            ArrayList<Events> conflictingEvents = taskList.addEventWithConflictCheck(event);

            String conflictMessage = conflictingEvents.size() > 0
                    ? ui.getAddConflictingEvent(conflictingEvents) : "";

            return ui.getAddTask(taskList) + conflictMessage;

        } catch (IllegalArgumentException e) {
            return ui.getIllegalArgumentMessage(e.getMessage());
        } catch (Exception e) {
            return ui.getUnknownErrorMessage();
        }

    }
}
