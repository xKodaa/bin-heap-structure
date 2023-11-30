package bin_heap.stack;

import java.util.Iterator;

public interface IQueueStack<T> {

    void zrus();
    boolean jePrazdny();
    void vloz (T data);
    T odeber();
    Iterator<T> vytvorIterator();
}
