import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Optional;



public class Parser {

    public static String getTime(LocalDateTime dateTime) {
        return dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public static String getIsoTime(LocalDateTime dateTime) {
        return dateTime.format(DateTimeFormatter.ISO_DATE_TIME);
    }

    public static Optional<LocalDateTime> tryParse(String input, boolean isEnd) {
        try {
            return Optional.of(Parser.parseDateTime(input, isEnd));
        } catch (DateTimeParseException e) {
            return Optional.empty();
        }
    }

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

    public static void parseLines(ArrayList<String> lines, ArrayList<Task> tasks) throws IllegalArgumentException  {

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
    }

}
