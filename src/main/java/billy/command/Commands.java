package billy.command;

/**
 * Represents the set of valid commands recognized by the application.
 *
 * <p>This enum defines all supported commands that a user can input.
 * Each constant corresponds to a specific action, such as listing tasks,
 * marking tasks as done, or exiting the program.</p>
 */
public enum Commands {
    LIST,
    MARK,
    UNMARK,
    DELETE,
    FIND,
    DEADLINE,
    EVENT,
    TODO,
    BYE,
    UNKNOWN
}
