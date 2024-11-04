/**
 * Класс, без чёткого назначения.
 * Описывает лампочки.
 */
public class Bulb implements Comparable<Bulb> {
    public int P;

    Bulb(int k) {
        P = k;
    }

    @Override
    public int compareTo(Bulb other) {
        return Integer.compare(this.P, other.P);
    }
}

