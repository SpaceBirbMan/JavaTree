import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

public class AVLTreeTest {

    @org.junit.Test
    public void testAVLTreeWithAllRotationsInOneTree() {
        AVLTree<Integer> avlTree = new AVLTree<>();

        // Создаем дерево, которое вызовет правый поворот
        avlTree.insert(30);
        avlTree.insert(20);
        avlTree.insert(10);  // Правый поворот

        assertEquals(20, avlTree.getRoot().data);
        assertEquals(10, avlTree.getRoot().Left.data);
        assertEquals(30, avlTree.getRoot().Right.data);

        // Добавляем узлы, чтобы вызвать левый поворот
        avlTree.insert(40);
        avlTree.insert(50);  // Левый поворот

        assertEquals(20, avlTree.getRoot().data);
        assertEquals(10, avlTree.getRoot().Left.data);
        assertEquals(40, avlTree.getRoot().Right.data);
        assertEquals(30, avlTree.getRoot().Right.Left.data);
        assertEquals(50, avlTree.getRoot().Right.Right.data);

        avlTree.insert(25);  // Правый-левый поворот

        assertEquals(30, avlTree.getRoot().data);
        assertEquals(20, avlTree.getRoot().Left.data);
        assertEquals(40, avlTree.getRoot().Right.data);
        assertEquals(50, avlTree.getRoot().Right.Right.data);
        assertEquals(25, avlTree.getRoot().Left.Right.data);

        avlTree.insert(5);   // Левый-правый поворот

        assertEquals(30, avlTree.getRoot().data);
        assertEquals(20, avlTree.getRoot().Left.data);
        assertEquals(10, avlTree.getRoot().Left.Left.data);
        assertEquals(40, avlTree.getRoot().Right.data);
        assertEquals(50, avlTree.getRoot().Right.Right.data);
        assertEquals(25, avlTree.getRoot().Left.Right.data);
        assertEquals(5, avlTree.getRoot().Left.Left.Left.data);

    }

}
