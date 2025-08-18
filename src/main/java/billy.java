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

        //actual code
        divider();
        System.out.println("Hello, I'm billy");
        System.out.println("What can I do for you?");
        divider();

        String line, lowerLine, firstWord;
        ArrayList<Task> tasks = new ArrayList<>();
        int index = 0;

        do {
            System.out.print("Your input: ");
            line = input.nextLine();
            lowerLine = line.toLowerCase();
            int spaceIndex = lowerLine.indexOf(' ');
            int markIndex;


            if (spaceIndex != -1) {
                firstWord = lowerLine.substring(0, spaceIndex);
            } else {
                firstWord = lowerLine;
            }


            if (!lowerLine.equals("bye")) {
                divider();

                try {
                    if (lowerLine.equals("list")) {
                        System.out.println("Here are the tasks in your list");
                        for (int i = 0; i < index; ++i) {
                            System.out.printf("%d.", i + 1);
                            tasks.get(i).printStatus();
                        }
                    } else if (firstWord.equals("mark")) {
                        if (spaceIndex == -1) {
                            throw new IllegalArgumentException("Specify task number");
                        }

                        markIndex = Integer.parseInt(lowerLine.substring(spaceIndex + 1).trim());
                        if (markIndex > index) {
                            throw new ArrayIndexOutOfBoundsException("");
                        }
                        tasks.get(markIndex - 1).setDone();
                        System.out.println("Nice! I've marked this task as done:");
                        System.out.print("   ");
                        tasks.get(markIndex - 1).printStatus();
                    } else if (firstWord.equals("unmark")) {
                        if (spaceIndex == -1) {
                            throw new IllegalArgumentException("Specify task number");
                        }
                        markIndex = Integer.parseInt(lowerLine.substring(spaceIndex + 1).trim());
                        if (markIndex > index) {
                            throw new ArrayIndexOutOfBoundsException("");
                        }
                        tasks.get(markIndex - 1).setUndone();
                        System.out.println("Nice! I've marked this task as not done yet:");
                        System.out.print("   ");
                        tasks.get(markIndex - 1).printStatus();
                    } else if (firstWord.equals("delete")) {
                        if (spaceIndex == -1) {
                            throw new IllegalArgumentException("Specify task number to be deleted");
                        }
                        markIndex = Integer.parseInt(lowerLine.substring(spaceIndex + 1).trim());
                        if (markIndex > index) {
                            throw new ArrayIndexOutOfBoundsException("");
                        }
                        System.out.println("Noted I've removed this task:");
                        System.out.print("   ");
                        tasks.get(markIndex - 1).printStatus();
                        tasks.remove(markIndex - 1);
                        --index;
                        System.out.println("Now you have " + (index) + " tasks in the list");
                    } else if (firstWord.equals("deadline")) {
                        int byIndex = lowerLine.indexOf("/by");
                        if (byIndex == -1) {
                            throw new IllegalArgumentException("Use the proper syntax: deadline <description> /by <deadline>");
                        }
                        String description = line.substring(spaceIndex + 1, byIndex).trim();
                        String deadline = line.substring(byIndex + 4).trim();
                        if (description.isEmpty())
                            throw new IllegalArgumentException("Deadline description cannot be empty");
                        addTask(tasks, index++, new Deadlines(description, deadline));
                    } else if (firstWord.equals("event")) {
                        int fromIndex = lowerLine.indexOf("/from");
                        int toIndex = lowerLine.indexOf("/to");
                        if (fromIndex == -1 || toIndex == -1) {
                            throw new IllegalArgumentException("Use the proper syntax: event <description> /from <start> /to <end");
                        }
                        String description = line.substring(spaceIndex + 1, fromIndex).trim();
                        String eventStart = line.substring(fromIndex + 6, toIndex).trim();
                        String eventEnd = line.substring(toIndex + 4);
                        if (description.isEmpty()) throw new IllegalArgumentException("Event description cannot be empty");
                        addTask(tasks, index++, new Events(description, eventStart, eventEnd));

                    } else if (firstWord.equals("todo")) {
                        if (line.length() < 5) {
                            throw new IllegalArgumentException("Please enter a description");
                        }
                        String description = line.substring(5).trim();
                        if (description.isEmpty()) {
                            throw new IllegalArgumentException("Description of a todo cannot be empty");
                        }
                        addTask(tasks, index++, new ToDos(description));
                    } else {
                        throw new IllegalArgumentException("Unknown command, try another command");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Enter a valid index");
                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                } catch (ArrayIndexOutOfBoundsException e) {
                    System.out.println("Task number is out of range");
                }
                finally {
                    divider();
                }


            }

        } while (!lowerLine.equals("bye"));

        divider();
        System.out.println("Bye. Hope to see you again soon!");
        divider();
    }
}
