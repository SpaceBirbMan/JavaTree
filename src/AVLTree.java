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



    /**
     * Вставляет элемент в AVL дерево
     * @param data значение для вставки
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
     * Удаляет элемент из AVL дерева
     * @param data значение для удаления
     */
    public void delete(T data) {
        root = delete(root, data);
    }

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

    private AVLNode<T> findMin(AVLNode<T> node) {
        while (node.Left != null) {
            node = (AVLNode<T>) node.Left;
        }
        return node;
    }

    private AVLNode<T> balance(AVLNode<T> node) {
        updateHeight(node);
        int balanceFactor = getBalanceFactor(node);

        if (balanceFactor > 1) {
            if (getBalanceFactor((AVLNode<T>) node.Left) < 0) {
                node.Left = rotateLeft((AVLNode<T>) node.Left);
            }
            node = rotateRight(node);
        } else if (balanceFactor < -1) {
            if (getBalanceFactor((AVLNode<T>) node.Right) > 0) {
                node.Right = rotateRight((AVLNode<T>) node.Right);
            }
            node = rotateLeft(node);
        }

        return node;
    }

    private void updateHeight(AVLNode<T> node) {
        node.setHeight(1 + Math.max(getHeight((AVLNode<T>) node.Left), getHeight((AVLNode<T>) node.Right)));
    }

    private int getHeight(AVLNode<T> node) {
        return node == null ? -1 : node.getHeight();
    }

    private int getBalanceFactor(AVLNode<T> node) {
        return node == null ? 0 : getHeight((AVLNode<T>) node.Left) - getHeight((AVLNode<T>) node.Right);
    }

    private AVLNode<T> rotateRight(AVLNode<T> y) {
        AVLNode<T> x = (AVLNode<T>) y.Left;
        y.Left = x.Right;
        x.Right = y;
        updateHeight(y);
        updateHeight(x);
        return x;
    }

    private AVLNode<T> rotateLeft(AVLNode<T> x) {
        AVLNode<T> y = (AVLNode<T>) x.Right;
        x.Right = y.Left;
        y.Left = x;
        updateHeight(x);
        updateHeight(y);
        return y;
    }

    /**
     * Итератор по элементам AVL дерева в порядке возрастания
     */
    @Override
    public Iterator<T> iterator() {
        return new AVLIterator<>(root);
    }

    /**
     * Итератор для AVL дерева
     */
    private static class AVLIterator<T extends Comparable<T>> implements Iterator<T> {
        private Stack<Joint<T>> stack = new Stack<>();

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

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            Joint<T> node = stack.pop();
            pushLeft(node.Right);
            return node.data;
        }
    }
}
