/**
 * Класс узла AVL дерева с полем высоты
 * @param <T> тип данных узла
 */
class AVLNode<T> extends Joint<T> {
    private int height;

    /**
     * Конструктор листа с данными и высотой
     * @param data данные узла
     */
    public AVLNode(T data) {
        super(data);
        this.height = 0; // Высота листа равна 0
    }

    /**
     * Конструктор узла с потомками и высотой
     * @param data данные узла
     * @param left левый потомок
     * @param right правый потомок
     */
    public AVLNode(T data, AVLNode<T> left, AVLNode<T> right) {
        super(data, left, right);
        this.height = 1 + Math.max(left.getHeight(), right.getHeight());
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}