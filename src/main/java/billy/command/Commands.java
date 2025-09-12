package billy.command;

/**
 * Represents the set of valid commands recognized by the application.
 *
 * <p>This enum defines all supported commands that a user can input.
 * Each constant corresponds to a specific action, such as listing tasks,
 * marking tasks as done, finding free time, or exiting the program.</p>
 */
public enum Commands {
    /** Command to list all tasks */
    LIST,
    /** Command to mark a task as completed */
    MARK,
    /** Command to unmark a task (mark as not completed) */
    UNMARK,
    /** Command to delete a task */
    DELETE,
    /** Command to find tasks by keyword */
    FIND,
    /** Command to find free time slots */
    FREE,
    /** Command to create a deadline task */
    DEADLINE,
    /** Command to create an event task */
    EVENT,
    /** Command to create a todo task */
    TODO,
    /** Command to exit the application */
    BYE,
    /** Represents an unrecognized command */
    UNKNOWN
}
