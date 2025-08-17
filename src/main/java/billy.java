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
            int markIndex = 99;
            if (spaceIndex != -1) {
                firstWord = lowerLine.substring(0, spaceIndex);
            } else {
                firstWord = lowerLine;
            }


            if (!lowerLine.equals("bye")) {
                divider();
                if (lowerLine.equals("list")) {
                    for (int i = 0; i < index; ++i) {
                        System.out.printf("%d.", i + 1);
                        tasks[i].printStatus();
                    }
                }
                else if (firstWord.equals("mark")) {
                    markIndex = Integer.parseInt(lowerLine.substring(spaceIndex + 1));
                    tasks[markIndex - 1].setDone();
                    System.out.println("Nice! I've marked this task as done:");
                    System.out.print("   ");
                    tasks[markIndex - 1].printStatus();
                }
                else if (firstWord.equals("unmark")) {
                    markIndex = Integer.parseInt(lowerLine.substring(spaceIndex + 1));
                    tasks[markIndex - 1].setDone();
                    System.out.println("Nice! I've marked this task as not done yet:");
                    System.out.print("   ");
                    tasks[markIndex - 1].printStatus();
                }
                else {
                    tasks[index++] = new Task(line);
                    System.out.println("added: " + line);

                }
                divider();
            }

        } while (!lowerLine.equals("bye"));

        divider();
        System.out.println("Bye. Hope to see you again soon!");
        divider();
    }
}
