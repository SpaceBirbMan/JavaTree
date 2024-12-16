import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Iterator;

class UltraHashIteratorTest {

    @Test
    void testIterator() {
        UltraHash<Integer, String> hashTable = new UltraHash<>();
        hashTable.put(1, "One");
        hashTable.put(2, "Two");
        hashTable.put(3, "Three");

        Iterator<Integer> iterator = hashTable.iterator();
        assertTrue(iterator.hasNext());
        assertEquals(1, iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals(2, iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals(3, iterator.next());
        assertFalse(iterator.hasNext());
    }

    @Test
    void testIteratorWithRemovedElement() {
        UltraHash<Integer, String> hashTable = new UltraHash<>();
        hashTable.put(1, "One");
        hashTable.put(2, "Two");
        hashTable.put(3, "Three");

        hashTable.remove(2);

        Iterator<Integer> iterator = hashTable.iterator();
        assertTrue(iterator.hasNext());
        assertEquals(1, iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals(3, iterator.next());
        assertFalse(iterator.hasNext());
    }

    @Test
    void testEmptyIterator() {
        UltraHash<Integer, String> hashTable = new UltraHash<>();
        Iterator<Integer> iterator = hashTable.iterator();
        assertFalse(iterator.hasNext());
    }

    @Test
    void testIteratorAfterClear() {
        UltraHash<Integer, String> hashTable = new UltraHash<>();
        hashTable.put(1, "One");
        hashTable.put(2, "Two");
        hashTable.clear();

        Iterator<Integer> iterator = hashTable.iterator();
        assertFalse(iterator.hasNext());
    }

    @Test
    void testIteratorWithDuplicateKeys() {
        UltraHash<Integer, String> hashTable = new UltraHash<>();
        hashTable.put(1, "One");
        hashTable.put(1, "Updated"); // Дубликат ключа

        Iterator<Integer> iterator = hashTable.iterator();
        assertTrue(iterator.hasNext());
        assertEquals(1, iterator.next());
        assertFalse(iterator.hasNext());
    }
}
