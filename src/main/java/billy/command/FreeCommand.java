package billy.command;

import java.time.LocalDateTime;

import billy.parser.Parser;
import billy.task.TaskList;
import billy.ui.Ui;

/**
 * Represents a command to find the earliest free time slot with a specified duration.
 * The free command searches for the next available time period that can accommodate
 * the requested duration without conflicting with existing events.
 */
public class FreeCommand extends Command {
    /**
     * Constructs a FreeCommand with the specified input.
     *
     * @param input the duration in hours for the free time slot
     */
    public FreeCommand(String input) {
        super(input);
    }

    /**
     * Executes the free command to find the earliest available time slot.
     * Parses the duration from input and searches for the next free time period
     * that can accommodate the requested duration without conflicts.
     *
     * @param taskList the list of tasks containing events to check against
     * @param ui the UI instance for generating response messages
     * @return formatted message with the earliest free time slot or error message
     */
    @Override
    public String execute(TaskList taskList, Ui ui) {
        try {
            int duration = Parser.parseDuration(this.input);
            LocalDateTime earliestTime = taskList.getEarliestFreeTime(LocalDateTime.now(), duration);
            return ui.getEarliestFreeTime(earliestTime, duration);
        } catch (IllegalArgumentException e) {
            return ui.getIllegalArgumentMessage(e.getMessage());
        } catch (ArrayIndexOutOfBoundsException e) {
            return ui.getOutOfRangeMessage();
        } catch (Exception e) {
            return ui.getUnknownErrorMessage();
        }
    }
}
