import static org.junit.jupiter.api.Assertions.*;
import org.junit.Test;
import java.util.Iterator;

public class AVLTreeIteratorTest {

    @Test
    public void testIteratorTraversal() {
        AVLTree<Integer> avlTree = new AVLTree<>();

        // Добавляем элементы в дерево
        avlTree.insert(20);
        avlTree.insert(10);
        avlTree.insert(30);
        avlTree.insert(25);
        avlTree.insert(35);

        // Проверяем порядок обхода (должен быть 10, 20, 25, 30, 35)
        Iterator<Integer> iterator = avlTree.iterator();

        assertTrue(iterator.hasNext());
        assertEquals(10, iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals(20, iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals(25, iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals(30, iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals(35, iterator.next());

        // Убедимся, что больше элементов нет
        assertFalse(iterator.hasNext());
    }

    @Test
    public void testEmptyTreeIterator() {
        AVLTree<Integer> avlTree = new AVLTree<>();

        // Итератор для пустого дерева
        Iterator<Integer> iterator = avlTree.iterator();

        assertFalse(iterator.hasNext());
    }

    @Test
    public void testSingleElementTreeIterator() {
        AVLTree<Integer> avlTree = new AVLTree<>();
        avlTree.insert(15);

        // Итератор для дерева с одним элементом
        Iterator<Integer> iterator = avlTree.iterator();

        assertTrue(iterator.hasNext());
        assertEquals(15, iterator.next());
        assertFalse(iterator.hasNext());
        iterator.next();
        iterator.next();
        iterator.next();
        iterator.next();
    }
}
