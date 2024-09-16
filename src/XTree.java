import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Класс бинарного дерева
 */
public class XTree<T> {
    private Joint<T> root;
    private Joint<T> active_node;
    public XTree(T d) {
        this.root = new Joint<>(d);
    }

    // Конструктор копирования для узлов
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

    // Конструктор копирования для дерева
    public XTree(XTree<T> other) {
        this.root = copyNodes(other.root); // Копируем дерево
        this.active_node = this.root;      // Ставим активный узел на корень нового дерева
    }

    // Метод для глубокого копирования дерева
    public XTree<T> deepCopy() {
        return new XTree<>(this);
    }

    public XTree() {
    }


    public Joint<T> getRoot() {
        return root;
    }

    public void setRoot(Joint<T> root) {
        this.root = root;
    }

    // NLR (Pre-order)
    public static <T> List<T> traverseNLR(Joint<T> node) {
        List<T> result = new ArrayList<>();
        if (node != null) {
            result.add(node.data);
            result.addAll(traverseNLR(node.Left));
            result.addAll(traverseNLR(node.Right));
        }
        return result;
    }

    // LNR (In-order)
    public static <T> List<T> traverseLNR(Joint<T> node) {
        List<T> result = new ArrayList<>();
        if (node != null) {
            result.addAll(traverseLNR(node.Left));
            result.add(node.data);
            result.addAll(traverseLNR(node.Right));
        }
        return result;
    }

    // LRN (Post-order)
    public static <T> List<T> traverseLRN(Joint<T> node) {
        List<T> result = new ArrayList<>();
        if (node != null) {
            result.addAll(traverseLRN(node.Left));
            result.addAll(traverseLRN(node.Right));
            result.add(node.data);
        }
        return result;
    }

    public void print() {
        List<T> tRes = traverseLRN(this.root);
        for (T tRe : tRes) {
            System.out.println(tRe);
        }
    }

    // Применение к указанному узлу дерева функции, метод NLR, можно для печати оставить
   public static <T> void applyFunction(Joint<T> node, Consumer<T> func) {
        if (node != null) {
            func.accept(node.data);
            applyFunction(node.Left, func);
            applyFunction(node.Right, func);
        }
    }
    // Альтернативное применение функции с большими возможностями в плане используемой функции
    public static <T> void applyFunction(Joint<T> node, Function<T, T> func) {
        if (node != null) {
            node.data = func.apply(node.data);
            applyFunction(node.Left, func);
            applyFunction(node.Right, func);
        }
    }

    // Удаление дерева
    public void deleteTree(Joint<T> node) {
        if (node != null) {
            deleteTree(node.Left);  // Удаляем левое поддерево
            deleteTree(node.Right); // Удаляем правое поддерево
            node.Left = null;
            node.Right = null;
        }
    }
    // после вызова корню присваивать null

    // Подсчет количества узлов в дереве
    private static <T> int pcountNodes(Joint <T> node) {
        if (node == null) {
            return 0;
        }
        return 1 + pcountNodes(node.Left) + pcountNodes(node.Right);
    }

    public int countNodes() {
        return pcountNodes(this.root);
    }

    // Определение глубины дерева (сломал)
    private static <T> int ptreeDepth(Joint<T> node) {
        if (node == null) return -1;
        int leftDepth = ptreeDepth(node.Left);
        int rightDepth = ptreeDepth(node.Right);
        return Math.max(leftDepth, rightDepth) + 1;
    }

   public int treeDepth() {
        return ptreeDepth(this.root);
   }

    public boolean isEmpty() {
        return root == null;
    }

    // Печать дерева в произвольном порядке (например, NLR)
    public void printNLR() {
        List<T> result = traverseNLR(this.root);
        for (T data : result) {
            System.out.println(data);
        }
    }

    // Печать дерева с отступами, показывающими иерархию
    public void printTree(Joint<T> node, int level) {
        if (node != null) {
            printTree(node.Right, level + 1);  // Сначала правое поддерево
            System.out.println(" ".repeat(level * 4) + node.data);  // Печать с отступом
            printTree(node.Left, level + 1);   // Затем левое поддерево
        }
    }

}
