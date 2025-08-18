import java.util.Scanner;
import java.util.ArrayList;

public class billy {

    public static void divider() {
        String divider = "_".repeat(50);
        System.out.println(divider);
    }

    public static void addTask(ArrayList<Task> tasks, int index, Task newTask) {
        tasks.add(newTask);
        System.out.println("Got it. I've added this task:");
        System.out.print("    ");
        tasks.get(index).printStatus();
        System.out.println("Now you have " + (index + 1) + " tasks in the list");
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        divider();
        System.out.println("Hello, I'm billy");
        System.out.println("What can I do for you?");
        divider();

        String line;
        ArrayList<Task> tasks = new ArrayList<>();
        int index = 0;

        do {
            System.out.print("Your input: ");
            line = input.nextLine();
            String lowerLine = line.toLowerCase();
            String[] parts = lowerLine.split("\\s+", 2);

            if (!lowerLine.equals("bye")) {
                divider();

                try {
                    if (lowerLine.equals("list")) {
                        System.out.println("Here are the tasks in your list");
                        for (int i = 0; i < index; ++i) {
                            System.out.printf("%d.", i + 1);
                            tasks.get(i).printStatus();
                        }
                    }
                    else if (parts[0].equals("mark") || parts[0].equals("unmark") ||
                            parts[0].equals("delete")) {
                        if (parts.length < 2) {
                            throw new IllegalArgumentException("Specify task number");
                        }

                        int taskIndex = Integer.parseInt(parts[1].trim());
                        if (taskIndex > index || taskIndex < 1) {
                            throw new ArrayIndexOutOfBoundsException("");
                        }

                        if (parts[0].equals("mark")) {
                            tasks.get(taskIndex - 1).setDone();
                            System.out.println("Nice! I've marked this task as done:");
                        }
                        else if (parts[0].equals("unmark")) {
                            tasks.get(taskIndex - 1).setUndone();
                            System.out.println("Nice! I've marked this task as not done yet:");
                        }
                        else {
                            System.out.println("Noted I've removed this task:");
                            tasks.remove(taskIndex - 1);
                            --index;
                            System.out.println("Now you have " + index + " tasks in the list");
                            continue;
                        }
                        System.out.print("   ");
                        tasks.get(taskIndex - 1).printStatus();
                    }
                    else if (parts[0].equals("deadline")) {
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
                        addTask(tasks, index++, new Deadlines(description, deadline));
                    }
                    else if (parts[0].equals("event")) {
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
                        addTask(tasks, index++, new Events(description, eventStart, eventEnd));
                    }
                    else if (parts[0].equals("todo")) {
                        if (parts.length < 2 || parts[1].trim().isEmpty()) {
                            throw new IllegalArgumentException("Description of a todo cannot be empty");
                        }
                        addTask(tasks, index++, new ToDos(parts[1].trim()));
                    }
                    else {
                        throw new IllegalArgumentException("Unknown command, try another command");
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
        } while (!line.equals("bye"));

        divider();
        System.out.println("Bye. Hope to see you again soon!");
        divider();
    }
}