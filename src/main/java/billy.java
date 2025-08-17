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

        String line;
        while(!(line = input.nextLine()).equals("bye")) {
            divider();
            System.out.println(line);
            divider();
        }

        divider();
        System.out.println("Bye. Hope to see you again soon!");
        divider();
    }
}
