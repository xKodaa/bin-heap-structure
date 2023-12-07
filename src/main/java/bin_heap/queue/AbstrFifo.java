package bin_heap.queue;

import java.util.Iterator;

// fronta
public class AbstrFifo<T> implements IQueueStack<T> {   // first in, first out = prvni vlozeny jde ven jako prvni
    private final AbstrDoubleList<T> queue;
    private final AbstrDoubleList<T> list;

    public AbstrFifo() {
        this.list = new AbstrDoubleList<>();
        this.queue = new AbstrDoubleList<>();
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
        list.vlozPosledni(data);
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
