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
