package billy.command;

import billy.task.TaskList;
import billy.task.Task;
import billy.ui.Ui;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class CommandTest {

    private TaskList taskList;
    private Ui ui;

    @BeforeEach
    void setUp() {
        taskList = new TaskList(new ArrayList<>());
        ui = new Ui(); // if Ui only prints, you might later mock it
    }

    @Test
    void testListCommand() {
        Command command = new Command(Commands.LIST);
        assertDoesNotThrow(() -> command.execute(taskList, ui));
    }

    @Test
    void testAddTodo() {
        Command command = new Command(Commands.TODO, "read book");
        command.execute(taskList, ui);
        assertEquals(1, taskList.getSize());
        assertEquals("read book", taskList.getTask(0).getDescription());
    }

    @Test
    void testAddDeadlineValid() {
        Command command = new Command(Commands.DEADLINE, "submit homework /by tomorrow");
        command.execute(taskList, ui);
        assertEquals(1, taskList.getSize());
        Task t = taskList.getTask(0);
        assertTrue(t instanceof billy.task.Deadlines);
        assertEquals("submit homework", t.getDescription());
    }

    @Test
    void testAddDeadlineMissingBy() {
        Command command = new Command(Commands.DEADLINE, "submit homework");
        assertDoesNotThrow(() -> command.execute(taskList, ui)); // shouldn't crash
        assertEquals(0, taskList.getSize()); // should not add anything
    }

    @Test
    void testAddEventValid() {
        Command command = new Command(Commands.EVENT, "project meeting /from Monday /to Tuesday");
        command.execute(taskList, ui);
        assertEquals(1, taskList.getSize());
        Task t = taskList.getTask(0);
        assertTrue(t instanceof billy.task.Events);
        assertEquals("project meeting", t.getDescription());
    }

    @Test
    void testAddEventMissingParts() {
        Command command = new Command(Commands.EVENT, "project meeting /from Monday");
        assertDoesNotThrow(() -> command.execute(taskList, ui));
        assertEquals(0, taskList.getSize());
    }

    @Test
    void testMarkAndUnmarkValid() {
        // add a todo first
        new Command(Commands.TODO, "read book").execute(taskList, ui);

        Command markCommand = new Command(Commands.MARK, "1");
        markCommand.execute(taskList, ui);
        assertTrue(taskList.getTask(0).isDone());

        Command unmarkCommand = new Command(Commands.UNMARK, "1");
        unmarkCommand.execute(taskList, ui);
        assertFalse(taskList.getTask(0).isDone());
    }

    @Test
    void testDeleteValid() {
        new Command(Commands.TODO, "task1").execute(taskList, ui);
        assertEquals(1, taskList.getSize());

        Command deleteCommand = new Command(Commands.DELETE, "1");
        deleteCommand.execute(taskList, ui);
        assertEquals(0, taskList.getSize());
    }

    @Test
    void testDeleteOutOfRange() {
        Command deleteCommand = new Command(Commands.DELETE, "1");
        assertDoesNotThrow(() -> deleteCommand.execute(taskList, ui));
    }

    @Test
    void testByeCommand() {
        Command byeCommand = new Command(Commands.BYE);
        byeCommand.execute(taskList, ui);
        assertTrue(byeCommand.isExit());
    }

    @Test
    void testUnknownCommand() {
        Command unknown = new Command(Commands.UNKNOWN);
        assertDoesNotThrow(() -> unknown.execute(taskList, ui));
    }
}
