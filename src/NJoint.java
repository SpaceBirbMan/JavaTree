import java.util.ArrayList;

/**
 * Класс узла n-арного дерева
 * @param <T>
 */
public class NJoint<T> {
    public T data;
    public ArrayList<NJoint<T>> Links = new ArrayList<>(0); //указатели на другие узлы

    public NJoint(T d) {
        this.data = d;
    }

    public void AddLink(NJoint<T> L) {
        Links.addLast(L);
    }

    public void DelLink() {
        Links.removeLast();
    }

}
