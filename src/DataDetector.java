import java.util.List;

/**
 * Класс бинарного дерева поиска, основанный на XTree
 * @param <T> тип данных узла, который должен быть Comparable
 */
public class DataDetector<T extends Comparable<T>> extends XTree<T> {
    /**
     * Extends Comparable<T> — это ограничение, указывающее, что тип T должен реализовывать интерфейс Comparable<T>.
     * То есть любой класс, который будет использоваться в качестве типа T, обязан иметь метод сравнения для корректной работы с элементами.
     * В бинарном дереве поиска это необходимо для сравнения элементов (меньше, больше или равно).
     */
    public DataDetector() {
        super(); // Вызов конструктора родительского класса
    }

    /**
     * Вставка элемента в бинарное дерево поиска
     * @param data данные, которые нужно вставить
     */
    public void insert(T data) {
        setRoot(insertRec(getRoot(), data));
    }

    // Рекурсивный метод для вставки
    private Joint<T> insertRec(Joint<T> node, T data) {
        if (node == null) {
            return new Joint<>(data);
        }

        if (data.compareTo(node.data) < 0) {
            node.Left = insertRec(node.Left, data);
        } else if (data.compareTo(node.data) > 0) {
            node.Right = insertRec(node.Right, data);
        }

        return node;
    }

    /**
     * Поиск элемента в бинарном дереве
     * @param data данные для поиска
     * @return true если элемент найден, иначе false
     */
    public boolean search(T data) {
        return searchRec(getRoot(), data);
    }

    // Рекурсивный метод для поиска
    private boolean searchRec(Joint<T> node, T data) {
        if (node == null) {
            return false;
        }

        if (data.compareTo(node.data) == 0) {
            return true;
        }

        if (data.compareTo(node.data) < 0) {
            return searchRec(node.Left, data);
        } else {
            return searchRec(node.Right, data);
        }
    }

    /**
     * Удаление элемента из дерева
     * @param data данные для удаления
     */
    public void delete(T data) {
        setRoot(deleteRec(getRoot(), data));
    }

    // Рекурсивный метод для удаления
    private Joint<T> deleteRec(Joint<T> node, T data) {
        if (node == null) {
            return null;
        }

        if (data.compareTo(node.data) < 0) {
            node.Left = deleteRec(node.Left, data);
        } else if (data.compareTo(node.data) > 0) {
            node.Right = deleteRec(node.Right, data);
        } else {
            // Узел найден, есть три случая
            if (node.Left == null && node.Right == null) {
                return null;
            }
            if (node.Left == null) {
                return node.Right;
            } else if (node.Right == null) {
                return node.Left;
            }

            // Найти минимальный элемент в правом поддереве
            node.data = findMinRec(node.Right).data;
            node.Right = deleteRec(node.Right, node.data);
        }

        return node;
    }

    /**
     * Поиск наименьшего узла в дереве
     * @return данные наименьшего узла
     */
    public T findMin() {
        Joint<T> root = getRoot();
        if (root == null) {
            return null;
        }
        return findMinRec(root).data;
    }

    private Joint<T> findMinRec(Joint<T> node) {
        if (node.Left != null) {
            return findMinRec(node.Left);
        }
        return node;
    }

    /**
     * Поиск наибольшего узла в дереве
     * @return данные наибольшего узла
     */
    public T findMax() {
        Joint<T> root = getRoot();
        if (root == null) {
            return null;
        }
        return findMaxRec(root).data;
    }

    private Joint<T> findMaxRec(Joint<T> node) {
        if (node.Right != null) {
            return findMaxRec(node.Right);
        }
        return node;
    }

    /**
     * Обход дерева (LNR — in-order)
     * @return список элементов дерева в отсортированном порядке
     */
    public List<T> inOrderTraversal() {
        return traverseLNR(getRoot());
    }
}
