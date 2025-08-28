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
import billy.task.Deadlines;
import billy.task.Events;
import billy.task.ToDos;
import billy.task.Task;




/**
 * Utility class for parsing user input commands and task data.
 * <p>
 * Provides methods to parse commands into {@link Command} objects, convert date and time strings
 * into {@link LocalDateTime} objects, and parse saved task lines from storage into {@link Task} objects.
 * </p>
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
        try {
            return LocalDateTime.parse(time);
        } catch (DateTimeParseException exception) {
            try {
                LocalDate date = LocalDate.parse(time);
                return endOfDay? date.atTime(LocalTime.MAX) : date.atStartOfDay();
            } catch (DateTimeParseException exception2) {
                throw new DateTimeParseException("Not a valid date or date time",
                        exception2.getParsedString(), exception2.getErrorIndex());
            }
        }
    }


    /**
     * Parses a full user command string into a {@link Command} object.
     *
     * @param command the full user command string
     * @return Command object corresponding to the input
     */
    public static Command parseFullCommand(String command) {
        String[] parts = command.split("\\s+", 2);
        String arguments = parts.length > 1 ? parts[1] : "";

        switch (parts[0].toLowerCase()) {
        case "list":
            return new Command(Commands.LIST);
        case "mark":
            return new Command(Commands.MARK, arguments);
        case "unmark":
            return new Command(Commands.UNMARK, arguments);
        case "delete":
            return new Command(Commands.DELETE, arguments);
        case "deadline":
            return new Command(Commands.DEADLINE, arguments);
        case "event":
            return new Command(Commands.EVENT, arguments);
        case "todo":
            return new Command(Commands.TODO, arguments);
        case "bye":
            return new Command(Commands.BYE);
        default:
            return new Command(Commands.UNKNOWN);
        }
    }

    /**
     * Parses a single command string into a {@link Commands} enum.
     *
     * @param command the command string
     * @return Commands enum corresponding to the input
     */
    public static Commands parseCommand(String command) {
        switch (command) {
        case "list":
            return Commands.LIST;
        case "mark":
            return Commands.MARK;
        case "unmark":
            return Commands.UNMARK;
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
     * Parses a list of saved task lines from storage into {@link Task} objects.
     * <p>
     * Each line is expected to have the format: {@code type|done|description|[additional info]}
     * where {@code type} is one of todo, deadline, or event.
     * </p>
     *
     * @param lines the lines to parse
     * @return ArrayList of tasks
     * @throws IllegalArgumentException if a line is malformed or contains invalid commands
     */
    public static ArrayList<Task> parseLines(ArrayList<String> lines) throws IllegalArgumentException  {
        ArrayList<Task> tasks = new ArrayList<>();
        for (int lineCount = 0; lineCount < lines.size(); lineCount++) {
            String[] parts = lines.get(lineCount).split("\\|");
            for (int i = 0; i < parts.length; i++) {
                parts[i] = parts[i].trim();
            }

            Commands command = parseCommand(parts[0]);
            if (parts.length < 3) {
                throw new IllegalArgumentException("Line " + lineCount + " invalid command format");
            }

            boolean done = Integer.parseInt(parts[1]) != 0 ;

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

        System.out.println("List loaded: ");
        for (Task task : tasks) {
            task.printStatus();
        }

        return tasks;
    }

}
