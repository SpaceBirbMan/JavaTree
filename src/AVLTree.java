import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Stack;

/**
 * Класс AVLTree - реализация самобалансирующегося AVL дерева.
 *
 * Основные методы:
 * <ul>
 *     <li>{@link #insert(Comparable)} - Вставка элемента в дерево</li>
 *     <li>{@link #delete(Comparable)} - Удаление элемента из дерева</li>
 *     <li>{@link #balance(AVLNode)} - Балансировка узла дерева</li>
 *     <li>{@link #rotateLeft(AVLNode)} - Левый поворот для балансировки</li>
 *     <li>{@link #rotateRight(AVLNode)} - Правый поворот для балансировки</li>
 *     <li>{@link #iterator()} - Итератор для обхода дерева</li>
 * </ul>
 *
 * @param <T> Тип данных узла дерева, должен быть Comparable.
 */
public class AVLTree<T extends Comparable<T>> implements Iterable<T> {
    private AVLNode<T> root;


// nvrmnd

    /**
     * Вставляет элемент в AVL дерево
     * @param data значение для вставки (O(logN)/O(n))
     */
    public void insert(T data) {
        root = insert(root, data);
    }

    private AVLNode<T> insert(AVLNode<T> node, T data) {
        if (node == null) {
            return new AVLNode<>(data);
        }

        int cmp = data.compareTo(node.data);
        if (cmp < 0) {
            node.Left = insert((AVLNode<T>) node.Left, data);
        } else if (cmp > 0) {
            node.Right = insert((AVLNode<T>) node.Right, data);
        } else {
            return node; // Дубликаты не добавляем
        }

        return balance(node);
    }

    /**
     * Delete element(tree/subtree) - data from AVL
     * (O(logN)/O(n))
     */
    public void delete(T data) {
        root = delete(root, data);
    }

    /**
     * Delete element(tree/subtree) - data from AVL
     *      * (O(logN)/O(n))
     * @param node
     * @param data
     * @return
     */
    private AVLNode<T> delete(AVLNode<T> node, T data) {
        if (node == null) {
            return null;
        }

        int cmp = data.compareTo(node.data);
        if (cmp < 0) {
            node.Left = delete((AVLNode<T>) node.Left, data);
        } else if (cmp > 0) {
            node.Right = delete((AVLNode<T>) node.Right, data);
        } else {
            if (node.Left == null) {
                return (AVLNode<T>) node.Right;
            } else if (node.Right == null) {
                return (AVLNode<T>) node.Left;
            } else {
                AVLNode<T> min = findMin((AVLNode<T>) node.Right);
                node.data = min.data;
                node.Right = delete((AVLNode<T>) node.Right, node.data);
            }
        }

        return balance(node);
    }

    /**
     * Find minimal element
     * @param node
     * @return
     */
    private AVLNode<T> findMin(AVLNode<T> node) {
        while (node.Left != null) {
            node = (AVLNode<T>) node.Left;
        }
        return node;
    }

    /**
     * Returns root of tree
     * @return
     */
    public AVLNode<T> getRoot() {
        return root;
    }

    /**
     * Balance leafs and subtrees in tree. O(1)
     */
    private AVLNode<T> balance(AVLNode<T> node) {
        updateHeight(node);
        int balanceFactor = getBalanceFactor(node);

        if (balanceFactor > 1) { // BF > 1 -> Поворот влево
            if (getBalanceFactor((AVLNode<T>) node.Left) < 0) {
                node.Left = rotateLeft((AVLNode<T>) node.Left);
            }
            node = rotateRight(node);
        } else if (balanceFactor < -1) { // BF < -1 -> Поворот вправо
            if (getBalanceFactor((AVLNode<T>) node.Right) > 0) {
                node.Right = rotateRight((AVLNode<T>) node.Right);
            }
            node = rotateLeft(node);
        }

        return node;
    }

    /**
     * Update height in node, using left 'n right heights
     */
    private void updateHeight(AVLNode<T> node) {
        node.setHeight(1 + Math.max(getHeight((AVLNode<T>) node.Left), getHeight((AVLNode<T>) node.Right)));
    }

    /**
     * Return height if it not null, else return -1
     */
    private int getHeight(AVLNode<T> node) {
        return node == null ? -1 : node.getHeight();
    }

    /**
     * return zero (if node is null) or node balance factor
     */
    private int getBalanceFactor(AVLNode<T> node) {
        return node == null ? 0 : getHeight((AVLNode<T>) node.Left) - getHeight((AVLNode<T>) node.Right);
    }

    /**
     * Rotate right in tree, returns node, replaced on right place
     * O(1)
     */
    private AVLNode<T> rotateRight(AVLNode<T> y) {
        AVLNode<T> x = (AVLNode<T>) y.Left;
        y.Left = x.Right;
        x.Right = y;
        updateHeight(y);
        updateHeight(x);
        return x;
    }

    /**
     * Rotate left in tree, returns node, replaced on right place
     * O(1)
     */
    private AVLNode<T> rotateLeft(AVLNode<T> x) {
        AVLNode<T> y = (AVLNode<T>) x.Right;
        x.Right = y.Left;
        y.Left = x;
        updateHeight(x);
        updateHeight(y);
        return y;
    }

    /**
     * AVL Iterator
     */
    @Override
    public Iterator<T> iterator() {
        return new AVLIterator<>(root);
    }


    /**
     * AVL tree iterator (NLR traversal)
     */
    private static class AVLIterator<T extends Comparable<T>> implements Iterator<T> {
        private final Stack<Joint<T>> stack = new Stack<>(); // stack using for remember, what joints already visited, and be able to move back
        // 'cause we haven't pointer on parent in joint

        public AVLIterator(Joint<T> root) {
            pushLeft(root);
        }

        private void pushLeft(Joint<T> node) {
            while (node != null) {
                stack.push(node);
                node = node.Left;
            }
        }

        @Override
        public boolean hasNext() {
            return !stack.isEmpty();
        }

        /**
         * Возвращает данные узла, следующего в стеке, или исключение, если больше элементов нет
         * Returns node data, next in the stack, or error message and null, if no more elements
         */
        @Override
        public T next() {
            if (!hasNext()) {
                System.err.println("Attempt to access element from an empty iterator");
                return null;
            }
                Joint<T> node = stack.pop();
                pushLeft(node.Right);
                return node.data;
        }
    }
}
