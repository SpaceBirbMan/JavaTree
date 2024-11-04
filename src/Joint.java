import static java.lang.Math.random;

/**
 * Класс узла бинарного дерева
 * @param <T> тип данных узла
 */
public class Joint<T> {
    public T data;
    public Joint<T> Left;
    public Joint<T> Right;

    /**
     * Конструктор узла только с данными (лист)
     * @param data
     */
    public Joint(T data) {
        this.data = data;
        this.Left = null;
        this.Right = null;
    }

    /**
     * Конструктор узла с потомками
     * @param data
     * @param left
     * @param right
     */
    public Joint(T data, Joint<T> left, Joint<T> right) {
        this.data = data;
        this.Left = left;
        this.Right = right;
    }

    /**
     * Проверка, есть ли у узла потомки (лист ли он)
     * @return
     */
    public boolean isLeaf() {
        return Left == null && Right == null;
    }
}
