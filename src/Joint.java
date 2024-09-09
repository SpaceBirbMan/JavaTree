public class Joint<T> {
    public T data;
    public Joint<T> Left;
    public Joint<T> Right;

    public Joint(T d) {
        this.data = d;
    }

    //конструкторы и операторы копирования и сравнения
}
