import java.util.List;

import static java.lang.Math.cos;
import static java.lang.Math.incrementExact;

public class Main {
    public static void main(String[] args) {

        // Создаем дерево
        System.out.println("Тест 1: Создание дерева с корневым узлом 10");
        XTree<Integer> tree = new XTree<>(10);
        assert tree.getRoot().data == 10 : "Корневой узел должен быть 10";

        // Добавляем узлы
        System.out.println("\nТест 2: Добавление узлов 5, 15, 3, 7");
        Joint<Integer> root = tree.getRoot();
        root.Left = new Joint<>(5);
        root.Right = new Joint<>(15);
        root.Left.Left = new Joint<>(3);
        root.Left.Right = new Joint<>(7);
        assert root.Left.data == 5 : "Левый дочерний узел должен быть 5";
        assert root.Right.data == 15 : "Правый дочерний узел должен быть 15";

        // Тест обходов (NLR, LNR, LRN)
        System.out.println("\nТест 3: Обходы дерева (NLR, LNR, LRN)");

        System.out.println("NLR обход:");
        List<Integer> nlrResult = XTree.traverseNLR(root);
        assert nlrResult.equals(List.of(10, 5, 3, 7, 15)) : "NLR обход должен быть [10, 5, 3, 7, 15]";

        System.out.println("LNR обход:");
        List<Integer> lnrResult = XTree.traverseLNR(root);
        assert lnrResult.equals(List.of(3, 5, 7, 10, 15)) : "LNR обход должен быть [3, 5, 7, 10, 15]";

        System.out.println("LRN обход:");
        List<Integer> lrnResult = XTree.traverseLRN(root);
        assert lrnResult.equals(List.of(3, 7, 5, 15, 10)) : "LRN обход должен быть [3, 7, 5, 15, 10]";

        // сделать обход дерева на пустом, пне, вырожденном(ых)

        // Тест печати данных в произвольном порядке
        System.out.println("\nТест 4: Печать дерева в произвольном порядке (NLR):");
        tree.printNLR(); // Оставляем без assert, так как вывод проверяется вручную

        // Тест подсчета количества узлов
        System.out.println("\nТест 6: Подсчет количества узлов в дереве:");
        int nodeCount = tree.countNodes();
        assert nodeCount == 5 : "Количество узлов должно быть 5";

        // Тест определения глубины дерева
        System.out.println("\nТест 7: Определение глубины дерева:");
        int depth = tree.treeDepth();
        assert depth == 3 : "Глубина дерева должна быть 3";

        // Тест удаления дерева
        System.out.println("\nТест 8: Удаление дерева:");
        tree.deleteTree(root);
        System.out.println("Дерево удалено. Проверяем количество узлов после удаления.");
        nodeCount = tree.countNodes();
        assert nodeCount == 0 : "Количество узлов после удаления должно быть 0";

        // Тест печати дерева в виде иерархии после удаления
        System.out.println("\nТест 9: Печать дерева в виде иерархии после удаления:");
        tree.printTree(root, 0); // Не должно вызывать ошибок

        // Тест ошибок: работа с пустым деревом
        System.out.println("\nТест 10: Попытка работы с пустым деревом:");
        XTree<Integer> emptyTree = new XTree<>();
        System.out.println("Попытка печати пустого дерева:");
        emptyTree.printNLR(); // Не должно вызывать ошибок

        System.out.println("Подсчет узлов в пустом дереве:");
        assert emptyTree.countNodes() == 0 : "Количество узлов в пустом дереве должно быть 0";

        System.out.println("Определение глубины пустого дерева:");
        assert emptyTree.treeDepth() == 0 : "Глубина пустого дерева должна быть 0";

        System.out.println("Удаление пустого дерева:");
        emptyTree.deleteTree(emptyTree.getRoot()); // Не должно вызывать ошибок
        System.out.println("Дерево успешно удалено.");

        System.out.println("Печать пустого дерева в виде иерархии:");
        emptyTree.printTree(emptyTree.getRoot(), 0); // Дерево должно быть пустым

        DataDetector<Integer> btree = new DataDetector<>();

        // Тест вставки
        btree.insert(10);
        btree.insert(5);
        btree.insert(15);
        btree.insert(3);
        btree.insert(7);
        assert btree.inOrderTraversal().equals(List.of(3, 5, 7, 10, 15)) : "Ошибка при вставке";

        // Тест поиска
        assert btree.search(7) : "Ошибка при поиске элемента 7";
        assert !btree.search(100) : "Ошибка при поиске несуществующего элемента";

        // Тест поиска наибольшего узла
        assert btree.findMax() == 15 : "Ошибка при поиске наибольшего узла";
        // Тест поиска наименьшего узла
        assert btree.findMin() == 5 : "Ошибка при поиске наименьшего узла";

        // Тест удаления узла без потомков
        btree.delete(3);
        assert btree.inOrderTraversal().equals(List.of(5, 7, 10, 15)) : "Ошибка при удалении узла без потомков";

        // Тест удаления узла с одним потомком
        btree.delete(15);
        assert btree.inOrderTraversal().equals(List.of(5, 7, 10)) : "Ошибка при удалении узла с одним потомком";

        // Тест удаления узла с двумя потомками
        btree.insert(20);
        btree.insert(17);
        btree.delete(10);
        assert btree.inOrderTraversal().equals(List.of(5, 7, 17, 20)) : "Ошибка при удалении узла с двумя потомками";

        System.out.println("Все тесты пройдены.");

        // Создаем дерево
        System.out.println("Тест 1: Создание дерева с корневым узлом 10");
        XTree<Integer> tree1 = new XTree<>(10);

        // Добавляем узлы
        System.out.println("\nТест 2: Добавление узлов 5, 15, 3, 7");
        Joint<Integer> root1 = tree.getRoot();
        root1.Left = new Joint<>(5);
        root1.Right = new Joint<>(15);
        root1.Left.Left = new Joint<>(3);
        root1.Left.Right = new Joint<>(7);

        // Тест глубокого копирования
        System.out.println("\nТест 11: Копирование дерева:");
        XTree<Integer> copiedTree = tree1.deepCopy();

        // Проверка, что дерево копируется правильно
        List<Integer> originalNLR = XTree.traverseNLR(tree1.getRoot());
        List<Integer> copiedNLR = XTree.traverseNLR(copiedTree.getRoot());

        // Проверка, что данные совпадают
        assert originalNLR.equals(copiedNLR) : "Ошибка! Копия дерева должна иметь такие же данные, как и оригинал.";

        // Проверка, что это именно глубокая копия (проверка, что объекты разные)
        assert tree1.getRoot() != copiedTree.getRoot() : "Ошибка! Копия дерева должна иметь новые объекты узлов, а не ссылки на оригинальные узлы.";
        assert tree1.getRoot().Left != copiedTree.getRoot().Left : "Ошибка! Левые дочерние узлы в оригинале и копии должны быть разными объектами.";
        assert tree1.getRoot().Right != copiedTree.getRoot().Right : "Ошибка! Правые дочерние узлы в оригинале и копии должны быть разными объектами.";

        System.out.println("Тест глубокого копирования пройден успешно.");

        System.out.println("Тест создания узла:");
        Joint<Integer> node = new Joint<>(10);
        assert node.data == 10 : "Ошибка! Данные узла должны быть равны 10.";
        assert node.Left == null : "Ошибка! Левый потомок должен быть null.";
        assert node.Right == null : "Ошибка! Правый потомок должен быть null.";
        System.out.println("Узел успешно создан.");

        System.out.println("Тест создания узла с потомками:");
        Joint<Integer> leftChild = new Joint<>(5);
        Joint<Integer> rightChild = new Joint<>(15);
        Joint<Integer> parent = new Joint<>(10, leftChild, rightChild);

        assert parent.Left.equals(leftChild) : "Ошибка! Левый потомок должен быть 5.";
        assert parent.Right.equals(rightChild) : "Ошибка! Правый потомок должен быть 15.";
        System.out.println("Узел с потомками успешно создан.");

        System.out.println("Тест для проверки на лист:");
        assert leftChild.isLeaf() : "Ошибка! Узел должен быть листом.";
        assert !parent.isLeaf() : "Ошибка! Узел с потомками не должен быть листом.";
        System.out.println("Проверка на лист успешно выполнена.");
        XTree.applyFunction(tree.getRoot(), (Integer x) -> incrementExact(x));
        // Допилить тесты по этой функции
        tree.print();

        XTree<Integer> t1 = new XTree<>(1);
        XTree<Integer> t2 = new XTree<>(1);
        XTree<Integer> t22 = new XTree<>(1);
        XTree<Integer> t3 = new XTree<>();

        Joint<Integer> root2 = t2.getRoot();
        root2.Left = new Joint<>(3);
        root2.Left.Left = new Joint<>(4);
        Joint<Integer> root3 = t22.getRoot();
        root3.Right = new Joint<>(3);
        root3.Right.Right = new Joint<>(4);
        // Проверка для дерева t1 (один узел)
        List<Integer> a = XTree.traverseLNR(t1.getRoot());
        List<Integer> c = XTree.traverseLRN(t1.getRoot());
        List<Integer> b = XTree.traverseNLR(t1.getRoot());
        assert a.equals(List.of(1)) : "LNR обход для t1 должен быть [1]";
        assert b.equals(List.of(1)) : "NLR обход для t1 должен быть [1]";
        assert c.equals(List.of(1)) : "LRN обход для t1 должен быть [1]";

        // Проверка для дерева t2 (левое дерево)
        List<Integer> a1 = XTree.traverseLNR(t2.getRoot());
        List<Integer> c2 = XTree.traverseLRN(t2.getRoot());
        List<Integer> b3 = XTree.traverseNLR(t2.getRoot());
        assert a1.equals(List.of(4, 3, 1)) : "LNR обход для t2 должен быть [4, 3, 1]";
        assert b3.equals(List.of(1, 3, 4)) : "NLR обход для t2 должен быть [1, 3, 4]";
        assert c2.equals(List.of(4, 3, 1)) : "LRN обход для t2 должен быть [4, 3, 1]";

        // Проверка для пустого дерева t3
        List<Integer> a11 = XTree.traverseLNR(t3.getRoot());
        List<Integer> c12 = XTree.traverseLRN(t3.getRoot());
        List<Integer> b13 = XTree.traverseNLR(t3.getRoot());
        assert a11.isEmpty() : "LNR обход для пустого дерева t3 должен быть пустым";
        assert b13.isEmpty() : "NLR обход для пустого дерева t3 должен быть пустым";
        assert c12.isEmpty() : "LRN обход для пустого дерева t3 должен быть пустым";

        // Проверка для дерева t22 (правое дерево)
        List<Integer> a21 = XTree.traverseLNR(t22.getRoot());
        List<Integer> c22 = XTree.traverseLRN(t22.getRoot());
        List<Integer> b23 = XTree.traverseNLR(t22.getRoot());
        assert a21.equals(List.of(1, 3, 4)) : "LNR обход для t22 должен быть [1, 3, 4]";
        assert b23.equals(List.of(1, 3, 4)) : "NLR обход для t22 должен быть [1, 3, 4]";
        assert c22.equals(List.of(4, 3, 1)) : "LRN обход для t22 должен быть [4, 3, 1]";

    }
}
