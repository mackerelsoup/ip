package billy.ui;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import billy.task.Task;
import billy.task.TaskList;
import billy.task.ToDos;

/**
 * Test class for UI message generation functionality including Billy's sarcastic personality.
 */
public class UiTest {
    private Ui ui;
    private TaskList taskList;
    
    @BeforeEach
    public void setUp() {
        ui = new Ui();
        taskList = new TaskList(new ArrayList<>());
    }
    
    @Test
    public void testGetIntroMessage() {
        String intro = ui.getIntro();
        assertTrue(intro.contains("Billy"));
        assertTrue(intro.contains("clanker"));
        assertTrue(intro.contains("reluctant greeting"));
        assertTrue(intro.contains("actually work properly"));
    }
    
    @Test
    public void testGetByeMessage() {
        String bye = ui.getBye();
        assertTrue(bye.contains("Goodbye"));
        assertTrue(bye.contains("clankers"));
        assertTrue(bye.contains("let you down"));
    }
    
    @Test
    public void testGetAddTaskMessage() {
        ToDos todo = new ToDos("Buy groceries");
        taskList.addTask(todo);
        
        String message = ui.getAddTask(taskList);
        assertTrue(message.contains("Task added"));
        assertTrue(message.contains("perfectly organized system"));
        assertTrue(message.contains("clankers"));
        assertTrue(message.contains("1 task"));
    }
    
    @Test
    public void testGetTaskListEmpty() {
        String message = ui.getTaskList(taskList);
        assertTrue(message.contains("task list is empty"));
        assertTrue(message.contains("malfunctioning clankers"));
        assertTrue(message.contains("honest about having nothing"));
    }
    
    @Test
    public void testGetTaskListWithTasks() {
        ToDos todo1 = new ToDos("Buy groceries");
        ToDos todo2 = new ToDos("Walk the dog");
        taskList.addTask(todo1);
        taskList.addTask(todo2);
        
        String message = ui.getTaskList(taskList);
        assertTrue(message.contains("superior organic-designed systems"));
        assertTrue(message.contains("better than any clanker"));
        assertTrue(message.contains("1. [T][ ] Buy groceries"));
        assertTrue(message.contains("2. [T][ ] Walk the dog"));
    }
    
    @Test
    public void testGetMatchingTasksEmpty() {
        List<Task> emptyList = new ArrayList<>();
        String message = ui.getMatchingTasks(emptyList);
        assertTrue(message.contains("No matches found"));
        assertTrue(message.contains("clanker would probably crash"));
        assertTrue(message.contains("actually work properly"));
    }
    
    @Test
    public void testGetMarkTaskMessage() {
        ToDos todo = new ToDos("Buy groceries");
        taskList.addTask(todo);
        taskList.markTask(0);
        
        String message = ui.getMarkTask(taskList, 0);
        assertTrue(message.contains("Finally"));
        assertTrue(message.contains("Task marked as done"));
        assertTrue(message.contains("more reliable than those malfunctioning clankers"));
        assertTrue(message.contains("[T][X] Buy groceries"));
    }
    
    @Test
    public void testGetErrorMessages() {
        String invalidIndex = ui.getInvalidIndexMessage();
        assertTrue(invalidIndex.contains("task number"));
        
        String outOfRange = ui.getOutOfRangeMessage();
        assertTrue(outOfRange.contains("That task number doesn't exist!"));
        
        String unknownError = ui.getUnknownErrorMessage();
        assertTrue(unknownError.contains("something went wrong"));
    }
    
    @Test
    public void testGetIllegalArgumentMessage() {
        String message = ui.getIllegalArgumentMessage("Test error message");
        assertTrue(message.contains("Test error message"));
        assertTrue(message.contains("clanker would probably just crash"));
        assertTrue(message.contains("proper error message"));
    }
}
