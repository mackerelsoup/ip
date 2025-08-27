package billy.ui;

import java.util.Scanner;

import billy.task.Task;
import billy.task.TaskList;



public class Ui {
    private Scanner input;

    public Ui () {
        this.input = new Scanner(System.in);
    }

    public void divider() {
        String divider = "_".repeat(50);
        System.out.println(divider);
    }

    public void printNumberOfTasks(TaskList taskList) {
        System.out.println("Now you have " + taskList.getSize() + " task(s) in the list");
    }

    public void printTaskList(TaskList taskList) {
        taskList.printList();
    }

    public void addTask(TaskList taskList) {
        System.out.println("Got it, I've added this task:");
        System.out.print("    ");
        taskList.getLatestTask().printStatus();
        printNumberOfTasks(taskList);
    }

    public void markTask(TaskList taskList, int index) {
        System.out.println("Nice! I've marked this task as done:");
        System.out.print("    ");
        taskList.printTask(index);
    }

    public void unmarkTask(TaskList taskList, int index) {
        System.out.println("Ok! I've marked this task as not done yet:");
        System.out.print("    ");
        taskList.printTask(index);
    }

    public void removeTask(TaskList taskList, Task task) {
        System.out.println("Noted I've removed this task:");
        System.out.print("   ");
        task.printStatus();
        printNumberOfTasks(taskList);
    }

    public void intro() {
        divider();
        System.out.println("Hello, I'm billy.billy");
        System.out.println("What can I do for you?");
        divider();
    }

    public void bye() {
        divider();
        System.out.println("Bye. Hope to see you again soon!");
        divider();
    }

    public String getUserInput() {
        divider();
        System.out.print("Your input: ");
        return input.nextLine();
    }

    public void showInvalidIndexMessage() {
        System.out.println("Enter a valid index");
    }

    public void showIllegalArgumentMessage(String message) {
        System.out.println(message);
    }

    public void showOutOfRangeMessage() {
        System.out.println("billy.task.Task number is out of range");
    }

    public void showUnknwonErrorMessage() {
        System.out.println("An unknown error occurred");
    }

}
