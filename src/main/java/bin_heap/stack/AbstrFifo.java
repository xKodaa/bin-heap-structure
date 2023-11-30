package bin_heap.stack;

import java.util.Iterator;

// fronta
public class AbstrFifo<T> implements IQueueStack<T> {   // first in, first out = prvni vlozeny jde ven jako prvni
    private final AbstrDoubleList<T> queue;
    private final AbstrDoubleList<T> list;

    public AbstrFifo() {
        this.queue = new AbstrDoubleList<>();
        this.list = new AbstrDoubleList<>();
    }

    @Override
    public void zrus() {
        queue.zrus();
    }
    @Override
    public boolean jePrazdny() {
        return queue.jePrazdny();
    }
    @Override
    public void vloz(T data) {
        queue.vlozPrvni(data);
        list.vlozNaslednika(data);
    }
    @Override
    public T odeber() {
        return queue.odeberPosledni();
    }
    @Override
    public Iterator<T> vytvorIterator() {
        return list.iterator();
    }
}
