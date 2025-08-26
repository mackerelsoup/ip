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



public class Parser {

    private static String getTime(String time, boolean endOfDay) {
        try {
            LocalDateTime dateTime = parseDateTime(time, endOfDay);
            return dateTime.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM));
        } catch (DateTimeParseException exception) {
            return time;
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

    public static void ensureFileExists(File file) {
        File parentDir = file.getParentFile();

        if (!parentDir.exists()) {
            System.out.println("Parent directory does not exists, creating directory");
            boolean created = parentDir.mkdirs();
            if (!created) {
                throw new RuntimeException("Couldn't create parent directory " + parentDir);
            }
            System.out.println("Parent directory created at: " + parentDir.getAbsoluteFile());
        }

        if (!file.exists()) {
            System.out.println("Input file does not exist, attempting to create file");
            try {
                boolean created = file.createNewFile();
                if (!created) {
                    throw new IOException("Could not create file " + file.getAbsolutePath());
                }
                System.out.println("Created file at: " + file.getAbsolutePath());
            } catch (IOException exception) {
                System.out.println(exception.getMessage());
            }
        }
    }

    public static void parseFile(File file, ArrayList<Task> tasks) throws FileNotFoundException, IllegalArgumentException  {
        ensureFileExists(file);

        Scanner scanner = new Scanner(file);
        int lineCount = 0;
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] parts = line.split("\\|");
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
                tasks.add(new Deadlines(parts[2], done, getTime(parts[3], true)));
                break;
            }
            case EVENT: {
                if (parts.length < 5) {
                    throw new IllegalArgumentException("Line " + lineCount + " invalid task format");
                }
                tasks.add(new Events(parts[2], done, getTime(parts[3], false), getTime(parts[4], true)));
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
            lineCount++;
        }
        System.out.println("List loaded: ");
        for (Task task : tasks) {
            task.printStatus();
        }
    }
}
