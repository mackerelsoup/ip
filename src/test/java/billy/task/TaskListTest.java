package billy.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test class for TaskList functionality including adding, removing, and managing tasks.
 */
public class TaskListTest {
    private TaskList taskList;

    @BeforeEach
    public void setUp() {
        taskList = new TaskList(new ArrayList<>());
    }

    @Test
    public void testAddTask() {
        ToDos todo = new ToDos("Buy groceries");
        taskList.addTask(todo);

        assertEquals(1, taskList.getSize());
        assertEquals(todo, taskList.getTask(0));
        assertEquals(todo, taskList.getLatestTask());
    }

    @Test
    public void testAddMultipleTasks() {
        ToDos todo1 = new ToDos("Buy groceries");
        Deadlines deadline = new Deadlines("Submit assignment", false, "2025-09-05");
        Events event = new Events("Team meeting", false, "2025-09-05 10:00", "2025-09-05 12:00");

        taskList.addTask(todo1);
        taskList.addTask(deadline);
        taskList.addTask(event);

        assertEquals(3, taskList.getSize());
        assertEquals(todo1, taskList.getTask(0));
        assertEquals(deadline, taskList.getTask(1));
        assertEquals(event, taskList.getTask(2));
        assertEquals(event, taskList.getLatestTask());
    }

    @Test
    public void testRemoveTask() {
        ToDos todo1 = new ToDos("Buy groceries");
        ToDos todo2 = new ToDos("Walk the dog");

        taskList.addTask(todo1);
        taskList.addTask(todo2);
        assertEquals(2, taskList.getSize());

        Task removedTask = taskList.removeTask(0);
        assertEquals(todo1, removedTask);
        assertEquals(1, taskList.getSize());
        assertEquals(todo2, taskList.getTask(0));
    }

    @Test
    public void testMarkAndUnmarkTask() {
        ToDos todo = new ToDos("Buy groceries");
        taskList.addTask(todo);

        assertFalse(todo.isDone());

        taskList.markTask(0);
        assertTrue(taskList.getTask(0).isDone());

        taskList.unmarkTask(0);
        assertFalse(taskList.getTask(0).isDone());
    }

    @Test
    public void testTaskListFromArrayList() {
        ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(new ToDos("Task 1"));
        tasks.add(new Deadlines("Task 2", false, "2025-09-05"));

        TaskList taskListFromArray = new TaskList(tasks);

        assertEquals(2, taskListFromArray.getSize());
        assertEquals("Task 1", taskListFromArray.getTask(0).getDescription());
        assertEquals("Task 2", taskListFromArray.getTask(1).getDescription());
    }
}
