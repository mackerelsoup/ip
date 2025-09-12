package billy.command;

import java.time.LocalDateTime;

import billy.task.TaskList;
import billy.ui.Ui;
import billy.parser.Parser;

public class FreeCommand extends Command {
    public FreeCommand(String input) {
        super(input);
    }

    @Override
    public String execute(TaskList taskList, Ui ui) {
        try {
            int duration = Parser.parseAndValidateTaskIndex(taskList, this.input);
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
