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
        assert time != null && !time.trim().isEmpty();
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

    /**
     * Parses storage lines from a file into an {@link ArrayList} of {@link Task} objects.
     * <p>
     * Each line should follow the format:
     * <pre>
     *     command|doneFlag|description|[additional fields for deadline/event]
     * </pre>
     * If the line is invalid, an error message is displayed using {@link Ui},
     * and an empty list is returned.
     * </p>
     *
     * @param lines the list of lines from storage
     * @param ui    the user interface used to display messages
     * @return the list of parsed Task objects
     */
    public static ParseResult parseStorageLines(ArrayList<String> lines, Ui ui) {
        assert lines != null && ui != null;
        ArrayList<Task> tasks = new ArrayList<>();
        try {
            for (int lineCount = 0; lineCount < lines.size(); lineCount++) {
                String[] parts = lines.get(lineCount).split("\\|");
                for (int i = 0; i < parts.length; i++) {
                    parts[i] = parts[i].trim();
                }
                Commands command = parseStorageCommand(parts[0]);
                if (parts.length < 3) {
                    throw new IllegalArgumentException("Line " + lineCount + " invalid command format");
                }

                boolean done = Integer.parseInt(parts[1]) != 0;

                switch (command) {
                case DEADLINE: {
                    if (parts.length < 4) {
                        throw new IllegalArgumentException("Line " + lineCount + " invalid task format");
                    }
                    tasks.add(new Deadlines(parts[2], done, parts[3]));
                    break;
                }
                case EVENT: {
                    if (parts.length < 5) {
                        throw new IllegalArgumentException("Line " + lineCount + " invalid task format");
                    }
                    tasks.add(new Events(parts[2], done, parts[3], parts[4]));
                    break;
                }
                case TODO: {
                    tasks.add(new ToDos(parts[2], done));
                    break;
                }
                default: {
                    throw new IllegalArgumentException("File contains invalid commands");
                }
                }
            }
            return new ParseResult(tasks, ui.getListLoaded(tasks));
        } catch (IllegalArgumentException exception) {
            return new ParseResult(new ArrayList<>(), ui.getIllegalArgumentMessage(exception.getMessage()));
        }

    }

}
