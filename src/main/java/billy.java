import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;
import java.io.File;
import java.io.FileWriter;


public class billy {

    public static void divider() {
        String divider = "_".repeat(50);
        System.out.println(divider);
    }

    public static void addTask(ArrayList<Task> tasks, Task newTask) {
        tasks.add(newTask);
        System.out.println("Got it. I've added this task:");
        System.out.print("    ");
        tasks.getLast().printStatus();
        System.out.println("Now you have " + tasks.size() + " tasks in the list");
    }

    public static void writeFile(ArrayList<Task> tasks) {
        try {
            FileWriter fileWriter = new FileWriter("./data/initialList.txt");
            for (Task task : tasks) {
                fileWriter.write(task.getFileString());
            }

            fileWriter.close();
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage() );
        }


    }


    private static void intro() {
        divider();
        System.out.println("Hello, I'm billy");
        System.out.println("What can I do for you?");
        divider();
    }


    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        File file = new File("./data/initialList.txt");
        ArrayList<Task> tasks = new ArrayList<>();

        try {
            Parser.parseFile(file, tasks);
        } catch (IllegalArgumentException exception) {
            System.out.println(exception.getMessage());
            return;
        } catch (FileNotFoundException exception) {
            System.out.println(exception.getMessage());
        }

        intro();

        String line;
        while (true) {
            System.out.print("Your input: ");
            line = input.nextLine();
            String lowerLine = line.toLowerCase();
            String[] parts = lowerLine.split("\\s+", 2);
            Commands command = Parser.parseCommand((parts[0]));

            if (command == Commands.BYE) {
                break;
            }

            try {
                switch (command) {
                    case LIST: {
                        System.out.println("Here are the tasks in your list");
                        for (int i = 0; i < tasks.size(); ++i) {
                            System.out.printf("%d.", i + 1);
                            tasks.get(i).printStatus();
                        }
                        break;
                    }
                    case MARK:
                    case UNMARK:
                    case DELETE: {
                        if (parts.length < 2) {
                            throw new IllegalArgumentException("Specify task number");
                        }

                        int taskIndex = Integer.parseInt(parts[1].trim());
                        if (taskIndex < 1 || taskIndex > tasks.size()) {
                            throw new ArrayIndexOutOfBoundsException("");
                        }

                        if (command == Commands.MARK) {
                            tasks.get(taskIndex - 1).setDone();
                            System.out.println("Nice! I've marked this task as done:");
                        }
                        else if (command == Commands.UNMARK) {
                            tasks.get(taskIndex - 1).setUndone();
                            System.out.println("Nice! I've marked this task as not done yet:");
                        }
                        else {
                            System.out.println("Noted I've removed this task:");
                            System.out.print("   ");
                            tasks.get(taskIndex - 1).printStatus();
                            tasks.remove(taskIndex - 1);
                            System.out.println("Now you have " + tasks.size() + " tasks in the list");
                            break;
                        }

                        System.out.print("   ");
                        tasks.get(taskIndex - 1).printStatus();
                        break;

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
                            addTask(tasks, new Deadlines(description, false, deadline));
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
                            addTask(tasks, new Events(description, false, eventStart, eventEnd));
                            break;
                        }
                        case TODO: {
                            if (parts.length < 2 || parts[1].trim().isEmpty()) {
                                throw new IllegalArgumentException("Description of a todo cannot be empty");
                            }
                            addTask(tasks, new ToDos(parts[1].trim()));
                            break;
                        }
                        case UNKNOWN: {
                            throw new IllegalArgumentException("Unknown command, try another command");
                        }

                    }

                } catch (NumberFormatException e) {
                System.out.println("Enter a valid index");
                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                } catch (ArrayIndexOutOfBoundsException e) {
                    System.out.println("Task number is out of range");
                } finally {
                    divider();
                }

        }

        writeFile(tasks);
        divider();
        System.out.println("Bye. Hope to see you again soon!");
        divider();
    }
}