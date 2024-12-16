import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

class UltraSetTest {

    @Test
    void testEmptySet() {
        UltraSet<Integer> set = new UltraSet<>(10);

        assertEquals(0, set.size(), "Размер пустого множества должен быть 0.");
        assertFalse(set.contains(1), "Пустое множество не должно содержать элемент.");
    }

    @Test
    void testAddAndContains() {
        UltraSet<Integer> set = new UltraSet<>(10);
        set.add(1);
        set.add(2);

        assertTrue(set.contains(1), "Множество должно содержать элемент 1.");
        assertTrue(set.contains(2), "Множество должно содержать элемент 2.");
        assertFalse(set.contains(3), "Множество не должно содержать элемент 3.");
        assertEquals(2, set.size(), "Размер множества должен быть 2.");
    }

    @Test
    void testAddDuplicate() {
        UltraSet<Integer> set = new UltraSet<>(10);
        set.add(1);
        set.add(1); // Добавляем дубликат

        assertTrue(set.contains(1), "Множество должно содержать элемент 1.");
        assertEquals(1, set.size(), "Добавление дубликата не должно изменять размер множества.");
    }

    @Test
    void testRemove() {
        UltraSet<Integer> set = new UltraSet<>(10);
        set.add(1);
        set.remove(1);

        assertFalse(set.contains(1), "Элемент 1 должен быть удалён из множества.");
        assertEquals(0, set.size(), "Размер множества после удаления должен быть 0.");
    }

    @Test
    void testClear() {
        UltraSet<String> set = new UltraSet<>(10);
        set.add("A");
        set.add("B");
        set.clear();

        assertEquals(0, set.size(), "Размер множества после очистки должен быть 0.");
        assertFalse(set.contains("A"), "Множество не должно содержать элемент A после очистки.");
        assertFalse(set.contains("B"), "Множество не должно содержать элемент B после очистки.");
    }

    @Test
    void testToList() {
        UltraSet<String> set = new UltraSet<>(10);
        set.add("A");
        set.add("B");

        List<String> elements = set.toList();
        assertTrue(elements.contains("A"), "Список должен содержать элемент A.");
        assertTrue(elements.contains("B"), "Список должен содержать элемент B.");
        assertEquals(2, elements.size(), "Размер списка должен быть 2.");
    }

    @Test
    void testUnionWithEmptySet() {
        UltraSet<Integer> set1 = new UltraSet<>(10);
        set1.add(1);
        set1.add(2);

        UltraSet<Integer> set2 = new UltraSet<>(10); // Пустое множество

        UltraSet<Integer> union = set1.union(set2);

        assertEquals(2, union.size(), "Объединение с пустым множеством должно вернуть исходное множество.");
        assertTrue(union.contains(1));
        assertTrue(union.contains(2));
    }

    @Test
    void testUnion() {
        UltraSet<Integer> set1 = new UltraSet<>(10);
        set1.add(1);
        set1.add(2);

        UltraSet<Integer> set2 = new UltraSet<>(10);
        set2.add(2);
        set2.add(3);

        UltraSet<Integer> union = set1.union(set2);

        assertEquals(3, union.size(), "Размер объединения должен быть 3.");
        assertTrue(union.contains(1));
        assertTrue(union.contains(2));
        assertTrue(union.contains(3));
    }

    @Test
    void testIntersectionWithEmptySet() {
        UltraSet<Integer> set1 = new UltraSet<>(10);
        set1.add(1);
        set1.add(2);

        UltraSet<Integer> set2 = new UltraSet<>(10); // Пустое множество

        UltraSet<Integer> intersection = set1.intersection(set2);

        assertEquals(0, intersection.size(), "Пересечение с пустым множеством должно быть пустым.");
    }

    @Test
    void testIntersection() {
        UltraSet<Integer> set1 = new UltraSet<>(10);
        set1.add(1);
        set1.add(2);
        set1.add(3);

        UltraSet<Integer> set2 = new UltraSet<>(10);
        set2.add(2);
        set2.add(3);
        set2.add(4);

        UltraSet<Integer> intersection = set1.intersection(set2);

        assertEquals(2, intersection.size(), "Размер пересечения должен быть 2.");
        assertTrue(intersection.contains(2));
        assertTrue(intersection.contains(3));
        assertFalse(intersection.contains(1));
        assertFalse(intersection.contains(4));
    }

    @Test
    void testDifferenceWithEmptySet() {
        UltraSet<Integer> set1 = new UltraSet<>(10);
        set1.add(1);
        set1.add(2);

        UltraSet<Integer> set2 = new UltraSet<>(10); // Пустое множество

        UltraSet<Integer> difference = set1.difference(set2);

        assertEquals(2, difference.size(), "Разность с пустым множеством должна вернуть исходное множество.");
        assertTrue(difference.contains(1));
        assertTrue(difference.contains(2));
    }

    @Test
    void testDifference() {
        UltraSet<Integer> set1 = new UltraSet<>(10);
        set1.add(1);
        set1.add(2);

        UltraSet<Integer> set2 = new UltraSet<>(10);
        set2.add(2);
        set2.add(3);

        UltraSet<Integer> difference = set1.difference(set2);

        assertEquals(1, difference.size(), "Размер разности должен быть 1.");
        assertTrue(difference.contains(1));
        assertFalse(difference.contains(2));
        assertFalse(difference.contains(3));
    }
}
