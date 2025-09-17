package billy.command;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import billy.task.TaskList;
import billy.ui.Ui;

/**
 * Test class for Command execution functionality.
 */
public class CommandTest {
    private TaskList taskList;
    private Ui ui;
    
    @BeforeEach
    public void setUp() {
        taskList = new TaskList(new ArrayList<>());
        ui = new Ui();
    }
    
    @Test
    public void testTodoCommand() {
        TodoCommand todoCommand = new TodoCommand("Buy groceries");
        String result = todoCommand.execute(taskList, ui);
        
        assertEquals(1, taskList.getSize());
        assertEquals("Buy groceries", taskList.getTask(0).getDescription());
        assertTrue(result.contains("Task added"));
        assertTrue(result.contains("perfectly organized system"));
        assertTrue(result.contains("clankers"));
    }
    
    @Test
    public void testTodoCommandEmptyDescription() {
        TodoCommand todoCommand = new TodoCommand("");
        String result = todoCommand.execute(taskList, ui);
        
        assertEquals(0, taskList.getSize());
        assertTrue(result.contains("Description of a todo cannot be empty"));
        assertTrue(result.contains("clanker"));
    }
    
    @Test
    public void testDeadlineCommand() {
        DeadlineCommand deadlineCommand = new DeadlineCommand("Submit assignment /by 2025-09-05");
        String result = deadlineCommand.execute(taskList, ui);
        
        assertEquals(1, taskList.getSize());
        assertEquals("Submit assignment", taskList.getTask(0).getDescription());
        assertTrue(result.contains("Task added"));
        assertTrue(result.contains("perfectly organized system"));
    }
    
    @Test
    public void testDeadlineCommandInvalidFormat() {
        DeadlineCommand deadlineCommand = new DeadlineCommand("Submit assignment");
        String result = deadlineCommand.execute(taskList, ui);
        
        assertEquals(0, taskList.getSize());
        assertTrue(result.contains("Use the proper syntax"));
        assertTrue(result.contains("deadline <description> /by <deadline>"));
        assertTrue(result.contains("clanker"));
    }
    
    @Test
    public void testUnknownCommand() {
        UnknownCommand unknownCommand = new UnknownCommand("invalidcommand");
        String result = unknownCommand.execute(taskList, ui);
        
        assertEquals(0, taskList.getSize());
        assertTrue(result.contains("Unknown command"));
        assertTrue(result.contains("clanker"));
    }
}
