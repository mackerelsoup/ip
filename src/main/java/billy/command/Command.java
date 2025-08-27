package billy.command;

import billy.task.TaskList;
import billy.task.Deadlines;
import billy.task.Events;
import billy.task.ToDos;
import billy.task.Task;
import billy.ui.Ui;

public class Command {
    private Commands command;
    private String remainingCommand;
    private Boolean exit;

    public Command(Commands command, String remainingCommand) {
        this.command = command;
        this.remainingCommand = remainingCommand.trim();
        exit = false;
    }

    public Command(Commands command) {
        this.command = command;
        this.remainingCommand = null;
        exit = false;
    }

    public void execute(TaskList taskList, Ui ui) {
        try {
            switch (command) {
            case LIST: {
                ui.printTaskList(taskList);
                break;
            }
            case MARK:
            case UNMARK:
            case DELETE: {

                if (remainingCommand.trim().isEmpty()) {
                    throw new ArrayIndexOutOfBoundsException("");
                }

                int taskIndex = Integer.parseInt(remainingCommand.trim());
                if (taskIndex < 1 || taskIndex > taskList.getSize()) {
                    throw new ArrayIndexOutOfBoundsException("");
                }

                if (command == Commands.MARK) {
                    taskList.markTask(taskIndex - 1);
                    ui.markTask(taskList, taskIndex - 1);
                }
                else if (command == Commands.UNMARK) {
                    taskList.unmarkTask(taskIndex - 1);
                    ui.unmarkTask(taskList, taskIndex - 1);
                }
                else {
                    Task removed = taskList.removeTask(taskIndex - 1);
                    ui.removeTask(taskList, removed);
                    break;
                }
                break;
            }
            case DEADLINE: {
                if (remainingCommand.trim().isEmpty()) {
                    throw new IllegalArgumentException("Use the proper syntax: deadline <description> /by <deadline>");
                }

                String[] deadlineParts = remainingCommand.split("/by", 2);
                if (deadlineParts.length < 2) {
                    throw new IllegalArgumentException("Use the proper syntax: deadline <description> /by <deadline>");
                }

                String description = deadlineParts[0].trim();
                String deadline = deadlineParts[1].trim();
                if (description.isEmpty()) {
                    throw new IllegalArgumentException("Deadline description cannot be empty");
                }
                taskList.addTask(new Deadlines(description, false, deadline));
                ui.addTask(taskList);
                break;
            }
            case EVENT: {
                if (remainingCommand.trim().isEmpty()) {
                    throw new IllegalArgumentException("Use the proper syntax: event <description> /from <start> /to <end>");
                }

                String[] eventParts = remainingCommand.split("/from|/to");
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
                ui.addTask(taskList);
                break;
            }
            case TODO: {
                if (remainingCommand.trim().isEmpty()) {
                    throw new IllegalArgumentException("Description of a todo cannot be empty");
                }
                taskList.addTask(new ToDos(remainingCommand.trim()));
                ui.addTask(taskList);
                break;
            }
            case BYE: {
                this.exit = true;
                break;
            }
            case UNKNOWN: {
                throw new IllegalArgumentException("Unknown command, try another command");
            }
            }

        } catch (NumberFormatException e) {
            ui.showInvalidIndexMessage();
        } catch (IllegalArgumentException e) {
            ui.showIllegalArgumentMessage(e.getMessage());
        } catch (ArrayIndexOutOfBoundsException e) {
            ui.showOutOfRangeMessage();
        } catch (Exception e) {
            ui.showUnknwonErrorMessage();
        }
    }

    public Commands getCommand() {

        return this.command;
    }

    public String getRemainingCommand() {
        return this.remainingCommand;
    }

    public boolean isExit() {
        return exit;
    }
}
