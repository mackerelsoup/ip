import java.util.ArrayList;

public class billy {

    public static void run(ArrayList<Task> initialList, Ui ui, Storage storage) {
        TaskList taskList = new TaskList(initialList);
        String line;
        while (true) {
            line = ui.getUserInput();
            String[] parts = line.split("\\s+", 2);
            Commands command = Parser.parseCommand((parts[0].toUpperCase()));

            if (command == Commands.BYE) {
                break;
            }

            try {
                switch (command) {
                case LIST: {
                    ui.printTaskList(taskList);
                    break;
                }
                case MARK:
                case UNMARK:
                case DELETE: {
                    if (parts.length < 2) {
                        throw new IllegalArgumentException("Specify task number");
                    }

                    int taskIndex = Integer.parseInt(parts[1].trim());
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

                }
                case DEADLINE: {
                    if (parts.length < 2) {
                        throw new IllegalArgumentException("Use the proper syntax: deadline <description> /by <deadline>");
                    }

                    String[] deadlineParts = parts[1].split("/by", 2);
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
                    if (parts.length < 2) {
                        throw new IllegalArgumentException("Use the proper syntax: event <description> /from <start> /to <end>");
                    }

                    String[] eventParts = parts[1].split("/from|/to");
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
                    if (parts.length < 2 || parts[1].trim().isEmpty()) {
                        throw new IllegalArgumentException("Description of a todo cannot be empty");
                    }
                    taskList.addTask(new ToDos(parts[1].trim()));
                    ui.addTask(taskList);
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
            finally {
                ui.divider();
            }
        }
        storage.writeFile(taskList);
        ui.bye();
    }

    public static void main(String[] args) {
        Ui ui = new Ui();
        Storage storage = new Storage("./data/initialList.txt");
        ArrayList<Task> intialList;

        try {
            intialList = Parser.parseLines(storage.readFile());
        } catch (IllegalArgumentException exception) {
            System.out.println(exception.getMessage());
            intialList = new ArrayList<>();
        }


        billy.run(intialList, ui, storage);
    }
}