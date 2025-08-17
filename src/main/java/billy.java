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
        String[] memory = new String[100];
        boolean[] done = new boolean[100];
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
                        char marked = ' ';
                        if (done[i])
                            marked = 'X';
                        System.out.printf("%d.[%c] %s \n", i + 1, marked, memory[i]);
                    }
                }
                else if (firstWord.equals("mark")) {
                    markIndex = Integer.parseInt(lowerLine.substring(spaceIndex + 1));
                    done[markIndex - 1] = true;
                    System.out.println("Nice! I've marked this task as done:");
                    System.out.printf("   [X] %s\n", memory[markIndex - 1]);
                }
                else if (firstWord.equals("unmark")) {
                    markIndex = Integer.parseInt(lowerLine.substring(spaceIndex + 1));
                    done[markIndex - 1] = false;
                    System.out.println("Nice! I've marked this task as not done yet:");
                    System.out.printf("   [] %s\n", memory[markIndex - 1]);
                }
                else {
                    memory[index++] = line;
                    done[index] = false;
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
