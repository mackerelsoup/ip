package billy.parser;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Optional;

import billy.command.Command;
import billy.command.Commands;
import billy.command.DeadlineCommand;
import billy.command.DeleteCommand;
import billy.command.EventCommand;
import billy.command.ExitCommand;
import billy.command.FindCommand;
import billy.command.ListCommand;
import billy.command.MarkCommand;
import billy.command.TodoCommand;
import billy.command.UnknownCommand;
import billy.command.UnmarkCommand;
import billy.task.Deadlines;
import billy.task.Events;
import billy.task.Task;
import billy.task.ToDos;
import billy.ui.Ui;


/**
 * Utility class for parsing user input and storage data into commands and tasks.
 * <p>
 * Provides methods for:
 * <ul>
 *   <li>Parsing raw user commands into {@link Command} objects.</li>
 *   <li>Parsing stored data lines into {@link Task} objects.</li>
 *   <li>Converting {@link LocalDateTime} objects to formatted strings.</li>
 *   <li>Parsing dates and times from strings, including handling end-of-day conversions.</li>
 * </ul>
 */
public class Parser {
    private static final int MINIMUM_TASK_PARTS = 3;
    private static final int DEADLINE_PARTS = 4;
    private static final int EVENT_PARTS = 5;

    /**
     * Formats a {@link LocalDateTime} to the pattern "yyyy-MM-dd HH:mm:ss".
     *
     * @param dateTime the LocalDateTime object
     * @return formatted date-time string
     */
    public static String getTime(LocalDateTime dateTime) {
        return dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    /**
     * Formats a {@link LocalDateTime} to ISO date-time string.
     *
     * @param dateTime the LocalDateTime object
     * @return ISO formatted date-time string
     */
    public static String getIsoTime(LocalDateTime dateTime) {

        return dateTime.format(DateTimeFormatter.ISO_DATE_TIME);
    }

    /**
     * Tries to parse a string into a {@link LocalDateTime}.
     *
     * @param input the string input to parse
     * @param isEnd whether to parse as end-of-day if only a date is provided
     * @return Optional containing the parsed LocalDateTime if successful, or empty if parsing fails
     */
    public static Optional<LocalDateTime> tryParse(String input, boolean isEnd) {
        try {
            return Optional.of(Parser.parseDateTime(input, isEnd));
        } catch (DateTimeParseException e) {
            return Optional.empty();
        }
    }

    /**
     * Parses a string into a {@link LocalDateTime}.
     * <p>
     * If the string contains only a date, converts it to start or end of the day based on {@code endOfDay}.
     * </p>
     *
     * @param time     the string to parse
     * @param endOfDay whether to use end-of-day for date-only input
     * @return parsed LocalDateTime
     * @throws DateTimeParseException if parsing fails
     */
    public static LocalDateTime parseDateTime(String time, boolean endOfDay) throws DateTimeParseException {
        try {
            return LocalDateTime.parse(time);
        } catch (DateTimeParseException exception) {
            try {
                LocalDate date = LocalDate.parse(time);
                return endOfDay ? date.atTime(LocalTime.MAX) : date.atStartOfDay();
            } catch (DateTimeParseException exception2) {
                throw new DateTimeParseException("Not a valid date or date time",
                        exception2.getParsedString(), exception2.getErrorIndex());
            }
        }
    }


    /**
     * Parses a raw user input string into the corresponding {@link Command} object.
     *
     * @param command the raw user input string
     * @return the corresponding Command object, or {@link UnknownCommand} if unrecognized
     */
    public static Command parseCommand(String command) {
        String[] parts = command.split("\\s+", 2);
        String arguments = parts.length > 1 ? parts[1] : "";

        switch (parts[0].toLowerCase()) {
        case "list":
            return new ListCommand(arguments);
        case "mark":
            return new MarkCommand(arguments);
        case "unmark":
            return new UnmarkCommand(arguments);
        case "find":
            return new FindCommand(arguments);
        case "delete":
            return new DeleteCommand(arguments);
        case "deadline":
            return new DeadlineCommand(arguments);
        case "event":
            return new EventCommand(arguments);
        case "todo":
            return new TodoCommand(arguments);
        case "bye":
            return new ExitCommand(arguments);
        default:
            return new UnknownCommand(arguments);
        }
    }

    /**
     * Parses a storage string into a {@link Commands} enum.
     *
     * @param command the storage string representing a command
     * @return the corresponding Commands enum, or {@link Commands#UNKNOWN} if unrecognized
     */
    public static Commands parseStorageCommand(String command) {
        switch (command) {
        case "list":
            return Commands.LIST;
        case "mark":
            return Commands.MARK;
        case "unmark":
            return Commands.UNMARK;
        case "find":
            return Commands.FIND;
        case "delete":
            return Commands.DELETE;
        case "deadline":
            return Commands.DEADLINE;
        case "event":
            return Commands.EVENT;
        case "todo":
            return Commands.TODO;
        case "bye":
            return Commands.BYE;
        default:
            return Commands.UNKNOWN;
        }
    }

    public static ParseResult parseStorageLines(ArrayList<String> lines, Ui ui) {
        ArrayList<Task> tasks = new ArrayList<>();

        try {
            for (int lineCount = 0; lineCount < lines.size(); lineCount++) {
                Task task = parseTaskFromLine(lines.get(lineCount), lineCount);
                tasks.add(task);
            }
            return new ParseResult(tasks, ui.getListLoaded(tasks));
        } catch (IllegalArgumentException exception) {
            return new ParseResult(new ArrayList<>(), ui.getIllegalArgumentMessage(exception.getMessage()));
        }
    }

    private static Task parseTaskFromLine(String line, int lineCount) throws IllegalArgumentException {
        String[] parts = splitAndTrimLine(line);
        validateBasicFormat(parts, lineCount);

        Commands command = parseStorageCommand(parts[0]);
        boolean done = Integer.parseInt(parts[1]) != 0;

        return createTaskFromParts(command, parts, done, lineCount);
    }

    private static String[] splitAndTrimLine(String line) {
        String[] parts = line.split("\\|");
        for (int i = 0; i < parts.length; i++) {
            parts[i] = parts[i].trim();
        }
        return parts;
    }

    private static void validateBasicFormat(String[] parts, int lineCount) throws IllegalArgumentException {
        if (parts.length < MINIMUM_TASK_PARTS) {
            throw new IllegalArgumentException("Line " + lineCount + " invalid command format");
        }
    }

    private static Task createTaskFromParts(Commands command, String[] parts, boolean done, int lineCount)
            throws IllegalArgumentException {
        switch (command) {
        case DEADLINE:
            validatePartsLength(parts, DEADLINE_PARTS, lineCount);
            return new Deadlines(parts[2], done, parts[3]);

        case EVENT:
            validatePartsLength(parts, EVENT_PARTS, lineCount);
            return new Events(parts[2], done, parts[3], parts[4]);

        case TODO:
            return new ToDos(parts[2], done);

        default:
            throw new IllegalArgumentException("File contains invalid commands");
        }
    }

    private static void validatePartsLength(String[] parts, int expectedLength, int lineCount)
            throws IllegalArgumentException {
        if (parts.length < expectedLength) {
            throw new IllegalArgumentException("Line " + lineCount + " invalid task format");
        }
    }
}
