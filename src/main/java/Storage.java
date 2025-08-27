import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Storage {
    private final File file;

    public Storage(String filePath) {
        this.file = new File(filePath);
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
                throw new RuntimeException(exception);
            }
        }
    }

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
