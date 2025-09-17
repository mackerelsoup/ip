package billy.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import billy.parser.ParseResult;
import billy.parser.Parser;
import billy.task.TaskList;
import billy.task.ToDos;
import billy.ui.Ui;

/**
 * Test class for Storage functionality including file reading and writing operations.
 */
public class StorageTest {
    private Storage storage;
    private TaskList taskList;
    private Ui ui;
    private String testFilePath;
    
    @BeforeEach
    public void setUp() {
        testFilePath = "./test_data.txt";
        storage = new Storage(testFilePath);
        taskList = new TaskList(new ArrayList<>());
        ui = new Ui();
        
        // Ensure test directory exists
        File testFile = new File(testFilePath);
        testFile.getParentFile().mkdirs();
    }
    
    @AfterEach
    public void tearDown() {
        // Clean up test file
        try {
            Files.deleteIfExists(Paths.get(testFilePath));
        } catch (IOException e) {
            // Ignore cleanup errors
        }
    }
    
    @Test
    public void testWriteAndReadEmptyTaskList() {
        storage.writeFile(taskList);
        
        List<String> lines = storage.readFile();
        assertTrue(lines.isEmpty());
    }
    
    @Test
    public void testWriteAndReadTaskList() {
        // Add tasks to task list
        ToDos todo1 = new ToDos("Buy groceries");
        ToDos todo2 = new ToDos("Walk the dog");
        todo2.setDone();
        
        taskList.addTask(todo1);
        taskList.addTask(todo2);
        
        // Write to storage
        storage.writeFile(taskList);
        
        // Read from storage
        List<String> lines = storage.readFile();
        assertEquals(2, lines.size());
        
        // Verify format
        assertTrue(lines.get(0).contains("todo") && lines.get(0).contains("0") && lines.get(0).contains("Buy groceries"));
        assertTrue(lines.get(1).contains("todo") && lines.get(1).contains("1") && lines.get(1).contains("Walk the dog"));
    }
    
    @Test
    public void testParseStorageLinesEmpty() {
        ArrayList<String> emptyLines = new ArrayList<>();
        ParseResult result = Parser.parseStorageLines(emptyLines, ui);
        
        assertTrue(result.getTaskList().isEmpty());
        assertTrue(result.getMessage().contains("task list is empty"));
        assertTrue(result.getMessage().contains("storage didn't corrupt"));
        assertTrue(result.getMessage().contains("clanker"));
    }
    
    @Test
    public void testParseStorageLinesWithTasks() {
        ArrayList<String> lines = new ArrayList<>();
        lines.add("todo | 0 | Buy groceries");
        lines.add("todo | 1 | Walk the dog");
        
        ParseResult result = Parser.parseStorageLines(lines, ui);
        
        assertEquals(2, result.getTaskList().size());
        assertEquals("Buy groceries", result.getTaskList().get(0).getDescription());
        assertEquals("Walk the dog", result.getTaskList().get(1).getDescription());
        assertTrue(result.getTaskList().get(1).isDone());
        
        assertTrue(result.getMessage().contains("Successfully loaded"));
        assertTrue(result.getMessage().contains("2 tasks"));
        assertTrue(result.getMessage().contains("superior storage system"));
        assertTrue(result.getMessage().contains("clankers"));
    }
    
    @Test
    public void testStorageFileCreation() throws IOException {
        // Ensure file doesn't exist initially
        Path testPath = Paths.get(testFilePath);
        Files.deleteIfExists(testPath);
        
        // Write to storage should create the file
        storage.writeFile(taskList);
        
        assertTrue(Files.exists(testPath));
    }
    
    @Test
    public void testStorageReadNonExistentFile() {
        Storage nonExistentStorage = new Storage("./non_existent_file.txt");
        List<String> lines = nonExistentStorage.readFile();
        
        assertTrue(lines.isEmpty());
    }
    
    @Test
    public void testStoragePersistence() {
        // Add a task and save
        ToDos todo = new ToDos("Persistent task");
        taskList.addTask(todo);
        storage.writeFile(taskList);
        
        // Create new storage instance and read
        Storage newStorage = new Storage(testFilePath);
        List<String> lines = newStorage.readFile();
        ArrayList<String> linesList = new ArrayList<>(lines);
        ParseResult result = Parser.parseStorageLines(linesList, ui);
        
        assertEquals(1, result.getTaskList().size());
        assertEquals("Persistent task", result.getTaskList().get(0).getDescription());
    }
}
