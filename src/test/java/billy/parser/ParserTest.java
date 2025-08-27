package billy.parser;

import billy.command.Command;
import billy.command.Commands;
import billy.task.Deadlines;
import billy.task.Events;
import billy.task.Task;
import billy.task.ToDos;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class   ParserTest {

    @Test
    void testGetTime() {
        LocalDateTime dt = LocalDateTime.of(2025, 8, 27, 14, 30, 15);
        String formatted = Parser.getTime(dt);
        assertEquals("2025-08-27 14:30:15", formatted);
    }

    @Test
    void testGetIsoTime() {
        LocalDateTime dt = LocalDateTime.of(2025, 8, 27, 14, 30);
        String formatted = Parser.getIsoTime(dt);
        assertTrue(formatted.startsWith("2025-08-27T14:30"));
    }

    @Test
    void testTryParseValidDateTime() {
        Optional<LocalDateTime> result = Parser.tryParse("2025-08-27T10:15:30", false);
        assertTrue(result.isPresent());
        assertEquals(2025, result.get().getYear());
        assertEquals(10, result.get().getHour());
    }

    @Test
    void testTryParseValidDateOnly() {
        Optional<LocalDateTime> result = Parser.tryParse("2025-08-27", false);
        assertTrue(result.isPresent());
        assertEquals(0, result.get().getHour()); // start of day
    }

    @Test
    void testTryParseEndOfDay() {
        Optional<LocalDateTime> result = Parser.tryParse("2025-08-27", true);
        assertTrue(result.isPresent());
        assertEquals(23, result.get().getHour()); // end of day
    }

    @Test
    void testTryParseInvalid() {
        Optional<LocalDateTime> result = Parser.tryParse("invalid-date", false);
        assertFalse(result.isPresent());
    }

    @Test
    void testParseFullCommandKnown() {
        Command cmd = Parser.parseFullCommand("mark 3");
        Assertions.assertEquals(Commands.MARK, cmd.getCommand());
        assertEquals("3", cmd.getRemainingCommand());
    }

    @Test
    void testParseFullCommandUnknown() {
        Command cmd = Parser.parseFullCommand("foobar something");
        assertEquals(Commands.UNKNOWN, cmd.getCommand());
    }

    @Test
    void testParseCommandKnown() {
        assertEquals(Commands.EVENT, Parser.parseCommand("event"));
        assertEquals(Commands.BYE, Parser.parseCommand("bye"));
    }

    @Test
    void testParseCommandUnknown() {
        assertEquals(Commands.UNKNOWN, Parser.parseCommand("doesnotexist"));
    }

    @Test
    void testParseLinesValidTasks() {
        ArrayList<String> lines = new ArrayList<>();
        lines.add("deadline | 0 | finish project | 2025-08-30");
        lines.add("event | 1 | meeting | 2025-08-27 | 2025-08-28");
        lines.add("todo | 0 | buy milk");

        ArrayList<Task> tasks = Parser.parseLines(lines);

        assertEquals(3, tasks.size());
        assertTrue(tasks.get(0) instanceof Deadlines);
        assertTrue(tasks.get(1) instanceof Events);
        assertTrue(tasks.get(2) instanceof ToDos);
    }

    @Test
    void testParseLinesInvalidCommand() {
        ArrayList<String> lines = new ArrayList<>();
        lines.add("invalid | 0 | test");

        assertThrows(IllegalArgumentException.class, () -> Parser.parseLines(lines));
    }

    @Test
    void testParseLinesInvalidFormat() {
        ArrayList<String> lines = new ArrayList<>();
        lines.add("deadline | 0 | missing date");

        assertThrows(IllegalArgumentException.class, () -> Parser.parseLines(lines));
    }
}
