import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Создаем дерево
        System.out.println("Тест 1: Создание дерева с корневым узлом 10");
        XTree<Integer> tree = new XTree<>(10);

        // Добавляем узлы
        System.out.println("\nТест 2: Добавление узлов 5, 15, 3, 7");
        Joint<Integer> root = tree.getRoot();
        root.Left = new Joint<>(5);
        root.Right = new Joint<>(15);
        root.Left.Left = new Joint<>(3);
        root.Left.Right = new Joint<>(7);

        // Тест обходов (NLR, LNR, LRN)
        System.out.println("\nТест 3: Обходы дерева (NLR, LNR, LRN)");

        System.out.println("NLR обход:");
        List<Integer> nlrResult = XTree.traverseNLR(root);
        System.out.println(nlrResult);

        System.out.println("LNR обход:");
        List<Integer> lnrResult = XTree.traverseLNR(root);
        System.out.println(lnrResult);

        System.out.println("LRN обход:");
        List<Integer> lrnResult = XTree.traverseLRN(root);
        System.out.println(lrnResult);

        // Тест печати данных в произвольном порядке
        System.out.println("\nТест 4: Печать дерева в произвольном порядке (NLR):");
        tree.printRandomOrder();

        // Тест применения функции к узлам
        System.out.println("\nТест 5: Применение функции ко всем узлам (вывод значений узлов):");
        tree.applyFunction(root, data -> System.out.println("Значение узла: " + data));

        // Тест подсчета количества узлов
        System.out.println("\nТест 6: Подсчет количества узлов в дереве:");
        int nodeCount = tree.countNodes(root);
        System.out.println("Количество узлов: " + nodeCount);

        // Тест определения глубины дерева
        System.out.println("\nТест 7: Определение глубины дерева:");
        int depth = tree.treeDepth(root);
        System.out.println("Глубина дерева: " + depth);

        // Тест удаления дерева
        System.out.println("\nТест 8: Удаление дерева:");
        tree.deleteTree(root);
        System.out.println("Дерево удалено. Проверяем количество узлов после удаления.");
        nodeCount = tree.countNodes(root);
        System.out.println("Количество узлов после удаления: " + nodeCount); // Должно быть 0

        // Тест печати дерева в виде иерархии
        System.out.println("\nТест 9: Печать дерева в виде иерархии после удаления:");
        tree.printTree(root, 0); // Дерево должно быть пустым

        // Тест ошибок: работа с пустым деревом
        System.out.println("\nТест 10: Попытка работы с пустым деревом:");
        XTree<Integer> emptyTree = new XTree<>();
        System.out.println("Попытка печати пустого дерева:");
        emptyTree.printRandomOrder(); // Не должно вызывать ошибок

        System.out.println("Подсчет узлов в пустом дереве:");
        System.out.println("Количество узлов: " + emptyTree.countNodes(emptyTree.getRoot()));

        System.out.println("Определение глубины пустого дерева:");
        System.out.println("Глубина: " + emptyTree.treeDepth(emptyTree.getRoot()));

        System.out.println("Удаление пустого дерева:");
        emptyTree.deleteTree(emptyTree.getRoot()); // Не должно вызывать ошибок
        System.out.println("Дерево успешно удалено.");

        System.out.println("Печать пустого дерева в виде иерархии:");
        emptyTree.printTree(emptyTree.getRoot(), 0); // Дерево должно быть пустым
    }
}
