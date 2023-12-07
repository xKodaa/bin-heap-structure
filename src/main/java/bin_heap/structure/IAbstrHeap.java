package bin_heap.structure;

public interface IAbstrHeap<T> {
    void vybuduj(T[] array);
    void reorganizace();
    void zrus();
    boolean jePrazdny();
    void vloz(T data);
    T odeberMax();
    T zpristupniMax();
    void vypis(eTypProhl typProhlidky);
}
