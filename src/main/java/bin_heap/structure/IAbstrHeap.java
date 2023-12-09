package bin_heap.structure;

import java.util.Comparator;
import java.util.List;

public interface IAbstrHeap<T> {
    void vybuduj(T[] array);
    void reorganizace(Comparator<T> comparator);
    void zrus();
    boolean jePrazdny();
    void vloz(T data);
    T odeberMax();
    T zpristupniMax();
    List<T> vypis(eTypProhl typProhlidky);
}
