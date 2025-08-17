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

        String line, lowerLine;
        String[] memory = new String[100];
        int index = 0;

        do {
            System.out.print("Your input: ");
            line = input.nextLine();
            lowerLine = line.toLowerCase();

            if (!lowerLine.equals("bye")) {

                if (lowerLine.equals("list")) {
                    divider();
                    for (int i = 0; i < index; ++i) {
                        System.out.println((i + 1) + ". " + memory[i]);
                    }
                    divider();
                }
                else {
                    memory[index++] = line;
                    divider();
                    System.out.println("added: " + line);
                    divider();
                }
            }

        } while (!lowerLine.equals("bye"));

        divider();
        System.out.println("Bye. Hope to see you again soon!");
        divider();
    }
}
