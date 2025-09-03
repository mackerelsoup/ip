package billy.ui;

import java.util.Scanner;

import billy.task.Task;
import billy.task.TaskList;

import java.util.ArrayList;
import java.util.Scanner;


/**
 * Handles all user interactions such as displaying messages,
 * receiving user input, and formatting outputs.
 */
public class Ui {
    private final Scanner input;

    /**
     * Constructs a Ui object with a Scanner for user input.
     */
    public Ui () {
        this.input = new Scanner(System.in);
    }

    /**
     * Prints a divider line for formatting purposes.
     */
    public void divider() {
        String divider = "_".repeat(50);
        System.out.println(divider);
    }

    /**
     * Prints the total number of tasks in the task list.
     *
     * @param taskList the list of tasks
     */
    public void printNumberOfTasks(TaskList taskList) {
        System.out.println("Now you have " + taskList.getSize() + " task(s) in the list");
    }

    /**
     * Prints all tasks in the task list.
     *
     * @param taskList the list of tasks
     */
    public void printTaskList(TaskList taskList) {
        taskList.printList();
    }

    public void printMatchingTasks(ArrayList<Task> matchingTasks) {
        System.out.println("Here are the matching tasks in your list:");
        for (int i = 0; i < matchingTasks.size(); i++) {
            System.out.print(i + ".");
            matchingTasks.get(i).printStatus();
        }
    }

    /**
     * Prints confirmation that a task was added to the list.
     *
     * @param taskList the list of tasks
     */
    public void printAddTask(TaskList taskList) {
        System.out.println("Got it, I've added this task:");
        System.out.print("    ");
        taskList.getLatestTask().printStatus();
        printNumberOfTasks(taskList);
    }

    /**
     * Prints confirmation that a task was marked as done.
     *
     * @param taskList the list of tasks
     * @param index the index of the task in the list
     */
    public void printMarkTask(TaskList taskList, int index) {
        System.out.println("Nice! I've marked this task as done:");
        System.out.print("    ");
        taskList.printTask(index);
    }

    /**
     * Prints confirmation that a task was unmarked (not done).
     *
     * @param taskList the list of tasks
     * @param index the index of the task in the list
     */
    public void printUnmarkTask(TaskList taskList, int index) {
        System.out.println("Ok! I've marked this task as not done yet:");
        System.out.print("    ");
        taskList.printTask(index);
    }

    /**
     * Prints confirmation that a task was removed from the list.
     *
     * @param taskList the list of tasks
     * @param task the task that was removed
     */
    public void printRemoveTask(TaskList taskList, Task task) {
        System.out.println("Noted I've removed this task:");
        System.out.print("   ");
        task.printStatus();
        printNumberOfTasks(taskList);
    }

    public void printListLoaded(ArrayList<Task> taskList) {
        System.out.println("List loaded: ");
        for (Task task : taskList) {
            task.printStatus();
        }
    }

    /**
     * Prints the introduction message at program start.
     */
    public void intro() {
        divider();
        System.out.println("Hello, I'm billy.billy");
        System.out.println("What can I do for you?");
        divider();
    }

    /**
     * Prints the goodbye message at program exit.
     */
    public void printBye() {
        divider();
        System.out.println("Bye. Hope to see you again soon!");
        divider();
    }

    /**
     * Prompts the user for input and returns it as a string.
     *
     * @return the user input
     */
    public String getUserInput() {
        divider();
        System.out.print("Your input: ");
        return input.nextLine();
    }

    /**
     * Displays an error message for invalid index inputs.
     */
    public void showInvalidIndexMessage() {
        System.out.println("Enter a valid index");
    }

    /**
     * Displays an error message with details from an illegal argument.
     *
     * @param message the error message
     */
    public void showIllegalArgumentMessage(String message) {
        System.out.println(message);
    }

    /**
     * Displays an error message when the task number is out of range.
     */
    public void showOutOfRangeMessage() {
        System.out.println("billy.task.Task number is out of range");
    }

    /**
     * Displays a message when an unknown error occurs.
     */
    public void showUnknownErrorMessage() {
        System.out.println("An unknown error occurred");
    }
}
