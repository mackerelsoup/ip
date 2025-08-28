package billy.command;

import java.util.ArrayList;

import billy.task.TaskList;
import billy.task.Deadlines;
import billy.task.Events;
import billy.task.ToDos;
import billy.task.Task;
import billy.ui.Ui;


/**
 * Represents a command issued by the user.
 * A Command can be one of the predefined types in {@link Commands} such as LIST, MARK, UNMARK, DELETE, DEADLINE,
 * EVENT, TODO, or BYE. It stores any remaining part of the user input (e.g., task description or index) and can
 * execute the appropriate action on a {@link TaskList} and display messages using {@link Ui}.
 */
public class Command {
    private Commands command;
    private String remainingCommand;
    private Boolean exit;

    /**
     * Constructs a Command with a type and remaining command string.
     *
     * @param command          The type of command (from {@link Commands} enum)
     * @param remainingCommand The remainder of the input string, e.g., task description ond/or deadlines
     */
    public Command(Commands command, String remainingCommand) {
        this.command = command;
        this.remainingCommand = remainingCommand.trim();
        exit = false;
    }

    /**
     * Constructs a Command with only a type.
     *
     * @param command The type of command (from {@link Commands} enum)
     */
    public Command(Commands command) {
        this.command = command;
        this.remainingCommand = null;
        exit = false;
    }

    /**
     * Executes this command on the given {@link TaskList} and interacts with {@link Ui}.
     * <p>
     * Handles different command types using a switch statement:
     * <ul>
     *     <li>LIST – prints the current task list</li>
     *     <li>MARK / UNMARK / DELETE – modifies tasks based on index</li>
     *     <li>DEADLINE – adds a new deadline task</li>
     *     <li>EVENT – adds a new event task</li>
     *     <li>TODO – adds a new todo task</li>
     *     <li>BYE – marks this command as exit</li>
     *     <li>UNKNOWN – throws an exception</li>
     * </ul>
     * </p>
     *
     * @param taskList The task list to operate on
     * @param ui       The UI to display messages
     */
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
            case FIND: {
                if (remainingCommand.trim().isEmpty()) {
                    throw new IllegalArgumentException("Use the proper syntax: find <task>");
                }

                String task = remainingCommand.trim();

                ArrayList<Task> matching = new ArrayList<>();
                for (int i = 0; i < taskList.getSize(); i++) {
                    Task current = taskList.getTask(i);
                    if (current.getDescription().contains(task)) {
                        matching.add(current);
                    }
                }

                ui.printMatchingTasks(matching);
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

    /**
     * Returns the type of this command.
     *
     * @return The {@link Commands} type of this command
     */
    public Commands getCommand() {

        return this.command;
    }


    /**
     * Returns the remaining user input for this command (e.g., description or index).
     *
     * @return The remaining command string, or null if none
     */
    public String getRemainingCommand() {
        return this.remainingCommand;
    }

    /**
     * Checks if this command signals the program to exit.
     *
     * @return true if this command is BYE, false otherwise
     */
    public boolean isExit() {
        return exit;
    }
}
