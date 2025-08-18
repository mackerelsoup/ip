import java.util.Scanner;

public class billy {

    public static void divider() {
        String divider = "_".repeat(50);
        System.out.println(divider);
    }

    public static void addTask(Task[] tasks, int index, Task newTask) {
        tasks[index] = newTask;
        System.out.println("Got it. I've added this task:");
        System.out.print("    ");
        tasks[index].printStatus();
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
        Task[] tasks = new Task[100];
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
                if (lowerLine.equals("list")) {
                    System.out.println("Here are the tasks in your list");
                    for (int i = 0; i < index; ++i) {
                        System.out.printf("%d.", i + 1);
                        tasks[i].printStatus();
                    }
                } else if (firstWord.equals("mark")) {
                    markIndex = Integer.parseInt(lowerLine.substring(spaceIndex + 1));
                    tasks[markIndex - 1].setDone();
                    System.out.println("Nice! I've marked this task as done:");
                    System.out.print("   ");
                    tasks[markIndex - 1].printStatus();
                } else if (firstWord.equals("unmark")) {
                    markIndex = Integer.parseInt(lowerLine.substring(spaceIndex + 1));
                    tasks[markIndex - 1].setUndone();
                    System.out.println("Nice! I've marked this task as not done yet:");
                    System.out.print("   ");
                    tasks[markIndex - 1].printStatus();
                } else if (firstWord.equals("deadline")) {
                    int byIndex = lowerLine.indexOf("/by");
                    String description = line.substring(spaceIndex + 1, byIndex - 1);
                    String deadline = line.substring(byIndex + 4);
                    addTask(tasks, index++, new Deadlines(description, deadline));
                } else if (firstWord.equals("event")) {
                    int fromIndex = lowerLine.indexOf("/from");
                    int toIndex = lowerLine.indexOf("/to");
                    String description = line.substring(spaceIndex + 1, fromIndex - 1);
                    String eventStart = line.substring(fromIndex + 6, toIndex - 1);
                    String eventEnd = line.substring(toIndex + 4);
                    addTask(tasks, index++, new Events(description, eventStart, eventEnd));

                } else if (firstWord.equals("todo")) {
                    String description = line.substring(5);
                    addTask(tasks, index++, new ToDos(description));
                }
                divider();
            }

        } while (!lowerLine.equals("bye"));

        divider();
        System.out.println("Bye. Hope to see you again soon!");
        divider();
    }
}
