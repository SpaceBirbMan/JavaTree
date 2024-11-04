import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Класс бинарного дерева
 */
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Stack;

public class XTree<T> implements Iterable<T> {
    private Joint<T> root;

    public XTree(T d) {
        this.root = new Joint<>(d);
    }

    /// Конструктор копирования для узлов (NLR)
    private Joint<T> copyNodes(Joint<T> node) {
        if (node == null) {
            return null;
        }
        // Создаем новый узел с копией данных
        Joint<T> newNode = new Joint<>(node.data);
        newNode.Left = copyNodes(node.Left);   // Копируем левое поддерево
        newNode.Right = copyNodes(node.Right); // Копируем правое поддерево
        return newNode;
    }

    //написать комент какой итератор возвращается
    @Override
    public Iterator<T> iterator() {
        return new XTreeIterator<>(root);
    }

    /**
     * Вложенный класс для итератора.
     * Итератор для бинарного дерева, реализующий обход дерева в глубину (in-order traversal).
     * То есть сначала обходятся все левые узлы, затем текущий узел, и в конце - правые узлы.
     */
    private static class XTreeIterator<T> implements Iterator<T> {
        // Стек для хранения узлов дерева, которые нужно обойти
        private Stack<Joint<T>> stack;

        /**
         * Конструктор итератора.
         * Инициализирует стек и добавляет в него все левые узлы начиная с корневого.
         *
         * @param root Корневой узел дерева
         */
        public XTreeIterator(Joint<T> root) {
            stack = new Stack<>(); // Создаем новый стек
            pushLeft(root);        // Добавляем в стек все левые узлы от корня до самого нижнего
        }

        /**
         * Метод для добавления узлов в стек.
         *
         * @param node Узел, с которого начинается добавление
         */
        private void pushLeft(Joint<T> node) {
            while (node != null) {
                stack.push(node);
                node = node.Left;
            }
        }

        /**
         * Проверка, есть ли в дереве еще не обработанные узлы.
         *
         * @return true, если есть еще узлы для обхода, false — если нет
         */
        @Override
        public boolean hasNext() {
            return !stack.isEmpty();
        }

        /**
         * Возвращает следующий элемент в обходе дерева.
         *
         * @return Следующий элемент дерева типа T
         * @throws NoSuchElementException если больше нет элементов
         */
        @Override
        public T next() {
            if (!hasNext()) { // Если больше нет элементов, кидаем исключение
                throw new NoSuchElementException();
            }

            // Получаем узел, находящийся на вершине стека
            Joint<T> node = stack.pop();
            T result = node.data;

            // Если у узла есть правый потомок, добавляем его и все его левые узлы в стек
            if (node.Right != null) {
                pushLeft(node.Right);
            }

            return result;
        }
    }


    /**
     * Конструктор копирования дерева
     * @param other
     */
    public XTree(XTree<T> other) {
        this.root = copyNodes(other.root); // O(n) — копируем все узлы
    }

    /**
     * Глубокое копирование
     * @return
     */
    public XTree<T> deepCopy() {
        return new XTree<>(this); // O(n)
    }

    public XTree() {}

    /**
     * Получить корень дерева
     * @return
     */
    public Joint<T> getRoot() {
        return root;
    }

    /**
     * Установить корень дерева
     * @param root
     */
    public void setRoot(Joint<T> root) {
        this.root = root;
    }

    /**
     * Обход и запись листов в список по методу NLR
     * @param node
     * @return
     * @param <T>
     */
    public static <T> List<T> traverseNLR(Joint<T> node) {
        List<T> result = new ArrayList<>();
        if (node != null) {
            result.add(node.data);
            result.addAll(traverseNLR(node.Left));
            result.addAll(traverseNLR(node.Right));
        }
        return result; // O(n) — нужно обойти каждый узел
    }

    /**
     * Обход и запись листов в список по методу LNR
     * @param node
     * @return
     * @param <T>
     */
    public static <T> List<T> traverseLNR(Joint<T> node) {
        List<T> result = new ArrayList<>();
        if (node != null) {
            result.addAll(traverseLNR(node.Left));
            result.add(node.data);
            result.addAll(traverseLNR(node.Right));
        }
        return result; // O(n) — нужно обойти каждый узел
    }

    /**
     * Обход и запись листов в список по методу LRN
     * @param node
     * @return
     * @param <T>
     */
    public static <T> List<T> traverseLRN(Joint<T> node) {
        List<T> result = new ArrayList<>();
        if (node != null) {
            result.addAll(traverseLRN(node.Left));
            result.addAll(traverseLRN(node.Right));
            result.add(node.data);
        }
        return result; // O(n) — нужно обойти каждый узел
    }

    /**
     * Вывод дерева в консоль
     */
    public void print() {
        List<T> tRes = traverseLRN(this.root);
        for (T tRe : tRes) {
            System.out.println(tRe); // O(n)
        }
    }

    /**
     * Применение функции без возврата с одним параметром к листьям
     * @param node
     * @param func
     * @param <T>
     */
    public static <T> void applyFunction(Joint<T> node, Consumer<T> func) {
        if (node != null) {
            func.accept(node.data);
            applyFunction(node.Left, func);
            applyFunction(node.Right, func);
        }
        // O(n) — обрабатываем каждый узел
    }

    /**
     * Применение функции к листьям с параметрами и возвращаемым значением (data = func(data))
     * @param node
     * @param func
     * @param <T>
     */
    public static <T> void applyFunction(Joint<T> node, Function<T, T> func) {
        if (node != null) {
            node.data = func.apply(node.data);
            applyFunction(node.Left, func);
            applyFunction(node.Right, func);
        }
        // O(n) — обрабатываем каждый узел
    }

    /**
     * Удаление дерева
     * Удаление корня дерева автоматически не происходит, его нужно отвязывать вручную.
     * @param node
     */
    public void deleteTree(Joint<T> node) {
        if (node != null) {
            deleteTree(node.Left);  // Удаляем левое поддерево
            deleteTree(node.Right); // Удаляем правое поддерево
            node.Left = null;
            node.Right = null;
        }
        // O(n) — удаление всех узлов
    }

    /**
     * Подсчёт количества листьев в дереве
     * @param node
     * @return
     * @param <T>
     */
    private static <T> int pcountNodes(Joint <T> node) {
        if (node == null) {
            return 0;
        }
        return 1 + pcountNodes(node.Left) + pcountNodes(node.Right); // O(n) — считаем каждый узел
    }

    public int countNodes() {
        return pcountNodes(this.root); // O(n)
    }

    /**
     * Определение глубины дерева
     * @param node
     * @return
     * @param <T>
     */
    private static <T> int ptreeDepth(Joint<T> node) {
        if (node == null) return -1;
        int leftDepth = ptreeDepth(node.Left);
        int rightDepth = ptreeDepth(node.Right);
        return Math.max(leftDepth, rightDepth) + 1; // O(n) — нужно посетить каждый узел
    }

    public int treeDepth() {
        return ptreeDepth(this.root); // O(n)
    }

    public boolean isEmpty() {
        return root == null; // O(1)
    }

    /**
     * Печать дерева с иерархией
     * @param node
     * @param level
     */
    public void printTree(Joint<T> node, int level) {
        if (node != null) {
            printTree(node.Right, level + 1);  // Сначала правое поддерево
            System.out.println(" ".repeat(level * 4) + node.data);  // Печать с отступом
            printTree(node.Left, level + 1);   // Затем левое поддерево
        }
        // O(n) — обходим все узлы
    }

}
