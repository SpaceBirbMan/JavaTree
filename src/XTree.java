import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
/**
 * Класс бинарного дерева
 */
public class XTree<T> {
    private Joint<T> root;
    private boolean is_sorted = false;
    private Joint<T> active_node;
    public XTree(T d) {
        this.root = new Joint<>(d);
    }

    //конструкторы и операторы копирования и сравнения

    public XTree() {
        this.root = new Joint<>(null);
        this.active_node = this.root;
    }

    public void sort() {
        setIs_sorted(true);
        this.active_node = this.root;
    }

    public boolean isIs_sorted() {
        return is_sorted;
    }

    public void setIs_sorted(boolean is_sorted) {
        this.is_sorted = is_sorted;
    }

    public void add(T data) {

    }

    public void delete(T data) {

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

    // Применение к указанному узлу дерева функции, метод NLR
    public void applyFunction(Joint<T> node, Consumer<T> func) {
        if (node != null) {
            func.accept(node.data);
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

    // Подсчет количества узлов в дереве
    public int countNodes(Joint<T> node) {
        if (node == null) {
            return 0;
        }
        return 1 + countNodes(node.Left) + countNodes(node.Right);
    }

    // Определение глубины дерева
    public int treeDepth(Joint<T> node) {
        if (node == null) {
            return 0;
        }
        int leftDepth = treeDepth(node.Left);
        int rightDepth = treeDepth(node.Right);
        return Math.max(leftDepth, rightDepth) + 1;
    }

    // Печать дерева в произвольном порядке (например, NLR)
    public void printRandomOrder() {
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
