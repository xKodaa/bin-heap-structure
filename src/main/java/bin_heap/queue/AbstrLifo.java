package bin_heap.queue;

import java.util.Iterator;

// zasobnik
public class AbstrLifo<T> implements IQueueStack<T> {   // last in, first out = posledni vlozeny jde ven jako prvni

    private final AbstrDoubleList<T> stack;
    private final AbstrDoubleList<T> list;
    public AbstrLifo() {
        this.list = new AbstrDoubleList<>();
        this.stack = new AbstrDoubleList<>();
    }

    @Override
    public void zrus() {
        stack.zrus();
    }

    @Override
    public boolean jePrazdny() {
        return stack.jePrazdny();
    }

    @Override
    public void vloz(T data) {
        stack.vlozPrvni(data);
        list.vlozPosledni(data);
    }

    @Override
    public T odeber() {
        return stack.odeberPrvni();
    }

    @Override
    public Iterator<T> vytvorIterator() {
        return list.iterator();
    }
}