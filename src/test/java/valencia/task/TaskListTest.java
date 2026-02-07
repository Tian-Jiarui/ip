package valencia.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class TaskListTest {

    @Test
    public void add_increasesSize() {
        TaskList list = new TaskList();
        list.add(new Todo("read book"));
        assertEquals(1, list.size());
    }

    @Test
    public void remove_removesCorrectTask_andDecreasesSize() {
        TaskList list = new TaskList();
        Task t1 = new Todo("A");
        Task t2 = new Todo("B");
        list.add(t1);
        list.add(t2);

        Task removed = list.remove(0);
        assertEquals(t1, removed);
        assertEquals(1, list.size());
        assertEquals(t2, list.get(0));
    }

    @Test
    public void markDone_setsTaskToDone() {
        TaskList list = new TaskList();
        list.add(new Todo("do homework"));

        list.markDone(0);

        assertTrue(list.get(0).isDone());
    }

    @Test
    public void unmarkDone_setsTaskToNotDone() {
        TaskList list = new TaskList();
        list.add(new Todo("do homework"));
        list.markDone(0);

        list.unmarkDone(0);

        assertFalse(list.get(0).isDone());
    }
}

