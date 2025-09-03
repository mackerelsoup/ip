package billy.command;

import billy.task.TaskList;
import billy.task.Task;
import billy.ui.Ui;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class pingpongTest {

    private TaskList taskList;
    private Ui ui;

    @BeforeEach
    void setUp() {
        taskList = new TaskList(new ArrayList<>());
        ui = new Ui(); // if Ui only prints, you might later mock it
    }

    @Test
    void testListCommand() {
        pingpong pingpong = new pingpong(Commands.LIST);
        assertDoesNotThrow(() -> pingpong.execute(taskList, ui));
    }

    @Test
    void testAddTodo() {
        pingpong pingpong = new pingpong(Commands.TODO, "read book");
        pingpong.execute(taskList, ui);
        assertEquals(1, taskList.getSize());
        assertEquals("read book", taskList.getTask(0).getDescription());
    }

    @Test
    void testAddDeadlineValid() {
        pingpong pingpong = new pingpong(Commands.DEADLINE, "submit homework /by tomorrow");
        pingpong.execute(taskList, ui);
        assertEquals(1, taskList.getSize());
        Task t = taskList.getTask(0);
        assertTrue(t instanceof billy.task.Deadlines);
        assertEquals("submit homework", t.getDescription());
    }

    @Test
    void testAddDeadlineMissingBy() {
        pingpong pingpong = new pingpong(Commands.DEADLINE, "submit homework");
        assertDoesNotThrow(() -> pingpong.execute(taskList, ui)); // shouldn't crash
        assertEquals(0, taskList.getSize()); // should not add anything
    }

    @Test
    void testAddEventValid() {
        pingpong pingpong = new pingpong(Commands.EVENT, "project meeting /from Monday /to Tuesday");
        pingpong.execute(taskList, ui);
        assertEquals(1, taskList.getSize());
        Task t = taskList.getTask(0);
        assertTrue(t instanceof billy.task.Events);
        assertEquals("project meeting", t.getDescription());
    }

    @Test
    void testAddEventMissingParts() {
        pingpong pingpong = new pingpong(Commands.EVENT, "project meeting /from Monday");
        assertDoesNotThrow(() -> pingpong.execute(taskList, ui));
        assertEquals(0, taskList.getSize());
    }

    @Test
    void testMarkAndUnmarkValid() {
        // add a todo first
        new pingpong(Commands.TODO, "read book").execute(taskList, ui);

        pingpong markPingpong = new pingpong(Commands.MARK, "1");
        markPingpong.execute(taskList, ui);
        assertTrue(taskList.getTask(0).isDone());

        pingpong unmarkPingpong = new pingpong(Commands.UNMARK, "1");
        unmarkPingpong.execute(taskList, ui);
        assertFalse(taskList.getTask(0).isDone());
    }

    @Test
    void testDeleteValid() {
        new pingpong(Commands.TODO, "task1").execute(taskList, ui);
        assertEquals(1, taskList.getSize());

        pingpong deletePingpong = new pingpong(Commands.DELETE, "1");
        deletePingpong.execute(taskList, ui);
        assertEquals(0, taskList.getSize());
    }

    @Test
    void testDeleteOutOfRange() {
        pingpong deletePingpong = new pingpong(Commands.DELETE, "1");
        assertDoesNotThrow(() -> deletePingpong.execute(taskList, ui));
    }

    @Test
    void testByeCommand() {
        pingpong byePingpong = new pingpong(Commands.BYE);
        byePingpong.execute(taskList, ui);
        assertTrue(byePingpong.isExit());
    }

    @Test
    void testUnknownCommand() {
        pingpong unknown = new pingpong(Commands.UNKNOWN);
        assertDoesNotThrow(() -> unknown.execute(taskList, ui));
    }
}
