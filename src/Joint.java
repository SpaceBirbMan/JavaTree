/**
 * Класс узла бинарного дерева
 * @param <T> тип данных узла
 */
public class Joint<T> {
    public T data;
    public Joint<T> Left;
    public Joint<T> Right;

    // Конструктор для создания узла только с данными
    public Joint(T data) {
        this.data = data;
        this.Left = null;
        this.Right = null;
    }

    // Конструктор для создания узла с потомками
    public Joint(T data, Joint<T> left, Joint<T> right) {
        this.data = data;
        this.Left = left;
        this.Right = right;
    }

    // Проверка, является ли узел листом (без потомков)
    public boolean isLeaf() {
        return Left == null && Right == null;
    }

    // Сравнение узлов по данным
    public boolean equals(Joint<T> other) {
        if (other == null) return false;
        return this.data.equals(other.data);
    }
}
