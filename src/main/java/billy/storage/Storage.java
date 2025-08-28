package billy.storage;

import billy.task.TaskList;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Handles reading from and writing to a file for persistent task storage.
 * <p>
 * The Storage class ensures that the file and its parent directories exist before reading or writing.
 * It provides methods to read the file into a list of strings and to write tasks to the file.
 * </p>
 */
public class Storage {
    private final File file;

    /**
     * Constructs a Storage object for a given file path.
     *
     * @param filePath the path to the file used for storing tasks
     */
    public Storage(String filePath) {
        this.file = new File(filePath);
    }

    /**
     * Ensures that the specified file and its parent directories exist.
     * <p>
     * If the parent directories do not exist, they will be created.
     * If the file does not exist, it will be created.
     * </p>
     *
     * @param file the file to check or create
     * @throws RuntimeException if directories or file cannot be created
     */
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
                throw new RuntimeException(exception);
            }
        }
    }

    /**
     * Reads the contents of the storage file into a list of strings.
     * <p>
     * Each line in the file corresponds to an element in the returned ArrayList.
     * Ensures the file exists before reading.
     * </p>
     *
     * @return an ArrayList of strings representing the lines in the file
     * @throws RuntimeException if the file cannot be found after ensuring existence
     */
    public ArrayList<String> readFile() {
        ensureFileExists(this.file);
        ArrayList<String> lines = new ArrayList<>();

        try (Scanner scanner = new Scanner(this.file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                lines.add(line);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Unexpected error: file not found after ensuring existence", e);
        }

        return lines;
    }

    /**
     * Writes the contents of a {@link TaskList} to the storage file.
     * <p>
     * Each task is written using its {@code getFileString()} representation.
     * Existing content in the file will be overwritten.
     * </p>
     *
     * @param tasks the TaskList to write to the file
     */
    public void writeFile(TaskList tasks) {
        try {
            FileWriter fileWriter = new FileWriter(this.file.getPath());
            for (int i = 0; i < tasks.getSize(); ++i) {
                fileWriter.write(tasks.getTask(i).getFileString());
            }

            fileWriter.close();
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }

    }
}
