import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import java.util.Map;

class UltraDictTest {
    @Test
    void testPutAndGet() {
        UltraDict<String, Integer> dict = new UltraDict<>();
        dict.put("A", 1);
        dict.put("B", 2);

        assertEquals(1, dict.get("A"));
        assertEquals(2, dict.get("B"));
        assertNull(dict.get("C"));
    }

    @Test
    void testOrder() {
        UltraDict<String, Integer> dict = new UltraDict<>();
        dict.put("A", 1);
        dict.put("B", 2);
        dict.put("A", 3); // Обновление значения не должно менять порядок

        assertEquals(List.of("A", "B"), dict.keys());
    }

    @Test
    void testKeysAndValues() {
        UltraDict<String, Integer> dict = new UltraDict<>();
        dict.put("A", 1);
        dict.put("B", 2);

        assertEquals(List.of("A", "B"), dict.keys());
        assertEquals(List.of(1, 2), dict.values());
    }

    @Test
    void testEntries() {
        UltraDict<String, Integer> dict = new UltraDict<>();
        dict.put("A", 1);
        dict.put("B", 2);

        List<Map.Entry<String, Integer>> entries = dict.entries();
        assertEquals(2, entries.size());
        assertEquals("A", entries.get(0).getKey());
        assertEquals(1, entries.get(0).getValue());
        assertEquals("B", entries.get(1).getKey());
        assertEquals(2, entries.get(1).getValue());
    }

    @Test
    void testMerge() {
        UltraDict<String, Integer> dict1 = new UltraDict<>();
        dict1.put("A", 1);
        dict1.put("B", 2);

        UltraDict<String, Integer> dict2 = new UltraDict<>();
        dict2.put("B", 20); // Обновление значения
        dict2.put("C", 3);

        dict1.merge(dict2);

        assertEquals(List.of("A", "B", "C"), dict1.keys());
        assertEquals(List.of(1, 20, 3), dict1.values());
    }

    @Test
    void testClear() {
        UltraDict<String, Integer> dict = new UltraDict<>();
        dict.put("A", 1);
        dict.put("B", 2);
        dict.clear();

        assertEquals(0, dict.size());
        assertTrue(dict.keys().isEmpty());
        assertTrue(dict.values().isEmpty());
    }
}
