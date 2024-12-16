import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UltraHashTest {
    @Test
    void testEmptyHashTable() {
        UltraHash<String, Integer> hashTable = new UltraHash<>(10);
        assertEquals(0, hashTable.size());
        assertNull(hashTable.find("A"));
    }

    @Test
    void testPutAndGet() {
        UltraHash<String, Integer> hashTable = new UltraHash<>(10);
        hashTable.put("A", 1);
        hashTable.put("B", 2);

        assertEquals(1, hashTable.find("A"));
        assertEquals(2, hashTable.find("B"));
        assertNull(hashTable.find("C"));
    }

    @Test
    void testContainsKey() {
        UltraHash<String, Integer> hashTable = new UltraHash<>(10);
        hashTable.put("A", 1);

        assertNotNull(hashTable.find("A"));
        assertNull(hashTable.find("B"));
    }

    @Test
    void testRemove() {
        UltraHash<String, Integer> hashTable = new UltraHash<>(10);
        hashTable.put("A", 1);
        hashTable.remove("A");

        assertNull(hashTable.find("A"));
    }

    @Test
    void testClear() {
        UltraHash<String, Integer> hashTable = new UltraHash<>(10);
        hashTable.put("A", 1);
        hashTable.put("B", 2);
        hashTable.clear();

        assertEquals(0, hashTable.size());
        assertNull(hashTable.find("A"));
        assertNull(hashTable.find("B"));
    }

    @Test
    void testSize() {
        UltraHash<String, Integer> hashTable = new UltraHash<>(10);
        assertEquals(0, hashTable.size());

        hashTable.put("A", 1);
        assertEquals(1, hashTable.size());

        hashTable.put("B", 2);
        assertEquals(2, hashTable.size());

        hashTable.remove("A");
        assertEquals(1, hashTable.size());
    }
}
