package billy.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import billy.command.Command;
import billy.command.DeadlineCommand;
import billy.command.DeleteCommand;
import billy.command.EventCommand;
import billy.command.ExitCommand;
import billy.command.FindCommand;
import billy.command.FreeCommand;
import billy.command.ListCommand;
import billy.command.MarkCommand;
import billy.command.TodoCommand;
import billy.command.UnknownCommand;
import billy.command.UnmarkCommand;

/**
 * Test class for Parser functionality including command parsing and validation.
 */
public class ParserTest {
    
    @Test
    public void testParseTodoCommand() {
        Command command = Parser.parseCommand("todo Buy groceries");
        assertTrue(command instanceof TodoCommand);
        assertEquals("Buy groceries", command.getInput());
    }
    
    @Test
    public void testParseDeadlineCommand() {
        Command command = Parser.parseCommand("deadline Submit assignment /by 2025-09-05");
        assertTrue(command instanceof DeadlineCommand);
        assertEquals("Submit assignment /by 2025-09-05", command.getInput());
    }
    
    @Test
    public void testParseEventCommand() {
        Command command = Parser.parseCommand("event Team meeting /from 2025-09-05 10:00 /to 2025-09-05 12:00");
        assertTrue(command instanceof EventCommand);
        assertEquals("Team meeting /from 2025-09-05 10:00 /to 2025-09-05 12:00", command.getInput());
    }
    
    @Test
    public void testParseListCommand() {
        Command command = Parser.parseCommand("list");
        assertTrue(command instanceof ListCommand);
    }
    
    @Test
    public void testParseMarkCommand() {
        Command command = Parser.parseCommand("mark 1");
        assertTrue(command instanceof MarkCommand);
        assertEquals("1", command.getInput());
    }
    
    @Test
    public void testParseUnmarkCommand() {
        Command command = Parser.parseCommand("unmark 2");
        assertTrue(command instanceof UnmarkCommand);
        assertEquals("2", command.getInput());
    }
    
    @Test
    public void testParseDeleteCommand() {
        Command command = Parser.parseCommand("delete 3");
        assertTrue(command instanceof DeleteCommand);
        assertEquals("3", command.getInput());
    }
    
    @Test
    public void testParseFindCommand() {
        Command command = Parser.parseCommand("find meeting");
        assertTrue(command instanceof FindCommand);
        assertEquals("meeting", command.getInput());
    }
    
    @Test
    public void testParseFreeCommand() {
        Command command = Parser.parseCommand("free 2");
        assertTrue(command instanceof FreeCommand);
        assertEquals("2", command.getInput());
    }
    
    @Test
    public void testParseByeCommand() {
        Command command = Parser.parseCommand("bye");
        assertTrue(command instanceof ExitCommand);
    }
    
    @Test
    public void testParseUnknownCommand() {
        Command command = Parser.parseCommand("invalidcommand");
        assertTrue(command instanceof UnknownCommand);
        assertEquals("", command.getInput()); // Arguments are empty for single word unknown command
    }
    
    @Test
    public void testParseEmptyCommand() {
        Command command = Parser.parseCommand("");
        assertTrue(command instanceof UnknownCommand);
        assertEquals("", command.getInput());
    }
    
    @Test
    public void testParseCaseInsensitive() {
        Command command1 = Parser.parseCommand("TODO Buy groceries");
        Command command2 = Parser.parseCommand("List");
        Command command3 = Parser.parseCommand("BYE");
        
        assertTrue(command1 instanceof TodoCommand);
        assertTrue(command2 instanceof ListCommand);
        assertTrue(command3 instanceof ExitCommand);
    }
}
