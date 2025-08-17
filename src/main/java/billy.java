import java.util.Scanner;

public class billy {

    public static void divider() {
        String divider = "_".repeat(50);
        System.out.println(divider);
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
                    System.out.println("Got it. I've added this task:");
                    int byIndex = lowerLine.indexOf("/by");
                    String description = lowerLine.substring(spaceIndex + 1, byIndex - 1);
                    String deadline = lowerLine.substring(byIndex + 4);
                    tasks[index++] = new Deadlines(description, deadline);
                    System.out.print("    ");
                    tasks[index - 1].printStatus();
                    System.out.println("Now you have " + index + " tasks in the list");
                } else if (firstWord.equals("event")) {
                    System.out.println("Got it. I've added this task:");
                    int fromIndex = lowerLine.indexOf("/from");
                    int toIndex = lowerLine.indexOf("/to");
                    String description = lowerLine.substring(spaceIndex + 1, fromIndex - 1);
                    String eventStart = lowerLine.substring(fromIndex + 6, toIndex - 1);
                    String eventEnd = lowerLine.substring(toIndex + 4);
                    tasks[index++] = new Events(description, eventStart, eventEnd);
                    System.out.print("    ");
                    tasks[index - 1].printStatus();
                    System.out.println("Now you have " + index + " tasks in the list");
                } else if (firstWord.equals("todo")) {
                    System.out.println("Got it. I've added this task:");
                    String description = lowerLine.substring(5);
                    tasks[index++] = new ToDos(description);
                    System.out.print("    ");
                    tasks[index - 1].printStatus();
                    System.out.println("Now you have " + index + " tasks in the list");
                }
                divider();
            }

        } while (!lowerLine.equals("bye"));

        divider();
        System.out.println("Bye. Hope to see you again soon!");
        divider();
    }
}
