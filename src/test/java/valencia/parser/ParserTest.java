package valencia.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import valencia.exception.ValenciaException;
import valencia.task.Deadline;
import valencia.task.Task;

public class ParserTest {

    @Test
    public void parseTaskNumber_validNumber_returnsInt() {
        int n = Parser.parseTaskNumber("delete 3", "delete");
        assertEquals(3, n);
    }

    @Test
    public void parseTaskNumber_nonNumber_throwsException() {
        assertThrows(ValenciaException.class, () -> Parser.parseTaskNumber("delete abc", "delete"));
    }

    @Test
    public void validateTaskNumber_outOfRange_throwsException() {
        // make a tiny fake TaskList just for size
        valencia.task.TaskList list = new valencia.task.TaskList();
        assertThrows(ValenciaException.class, () -> Parser.validateTaskNumber(1, list)); // list size = 0
    }

    @Test
    public void parseDeadline_validInput_returnsDeadlineWithCorrectDate() {
        Task t = Parser.parseDeadline("deadline return book /by 2026-02-01");
        assertTrue(t instanceof Deadline);

        Deadline d = (Deadline) t;
        assertEquals("return book", d.getDescription());
        assertEquals(LocalDate.of(2026, 2, 1), d.getBy());
    }

    @Test
    public void parseDeadline_invalidDate_throwsException() {
        assertThrows(ValenciaException.class,
                () -> Parser.parseDeadline("deadline return book /by not-a-date"));
    }
}
