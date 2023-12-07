package bin_heap.structure;

import bin_heap.queue.AbstrFifo;
import bin_heap.queue.AbstrLifo;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;

public class AbstrHeap<T> implements IAbstrHeap<T> {
    private T[] heap;
    int heapSize;
    private final Comparator<T> comparator;

    public AbstrHeap(Comparator<T> comparator) {
        this.comparator = comparator;
        heapSize = 0;
    }

    @Override
    public void vybuduj(T[] array) {
        if (!jePrazdny()) zrus();
        this.heap = array;
        this.heapSize = heap.length;
        reorganizace();
    }

    @Override
    public void reorganizace() {
        for (int i = heapSize / 2; i >= 0; i--) {
            heapDown(i);
        }
    }

    private void heapDown(int i) {
        int maxChild;
        while ((maxChild = getMaxChild(i)) != -1 && jeMensi(i, maxChild)) {
            swap(i, maxChild);
            i = maxChild;
        }
    }

    private void swap(int i, int j) {
        T temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
    }

    /**
     * @return true => heap[i] je vetsi nez heap[j] <br> false => heap[i] je mensi nez heap[j]
     */
    private boolean jeMensi(int i, int j) {
        return comparator.compare(heap[i], heap[j]) > 0;
    }

    @Override
    public void zrus() {
        this.heap = (T[]) new Object[10];
        heapSize = 0;
    }

    @Override
    public boolean jePrazdny() {
        return heapSize == 0;
    }

    @Override
    public void vloz(T data) {
        checkHeapSize();
    }

    @Override
    public T odeberMax() {
        checkHeapSize();
        return null;
    }

    @Override
    public T zpristupniMax() {
        if (!jePrazdny()) {
            return heap[0];
        } else {
            System.err.println("Prázdná halda => nelze zpřístupnit MAX");
            return null;
        }
    }

    @Override
    public void vypis(eTypProhl typProhlidky) {
        Iterator<Integer> iterator = vytvorIterator(typProhlidky);
        if (iterator != null) {
            while (iterator.hasNext()) {
                int index = iterator.next();
                System.out.println("Heap index = " + index + ", data: " + heap[index]);
            }
        }
    }

    private Iterator<Integer> vytvorIterator(eTypProhl typProhlidky) {
        int actual = 0;

        if (typProhlidky == eTypProhl.HLOUBKA) {
            addHeapArrayLength();
            AbstrLifo<Integer> zasobnik = new AbstrLifo<>();
            while (heap[actual] != null || !zasobnik.jePrazdny()) {
                while (heap[actual] != null) {
                    zasobnik.vloz(actual);
                    actual = getLeftChild(actual);
                }
                actual = zasobnik.odeber();
                actual = getRightChild(actual);
            }
            return zasobnik.vytvorIterator();

        } else if (typProhlidky == eTypProhl.SIRKA) {
            AbstrFifo<Integer> fronta = new AbstrFifo<>();
            fronta.vloz(actual);
            while(!fronta.jePrazdny()) {
                int removed = fronta.odeber();
                // prohlidka "po vrstvach"
                if (getLeftChild(removed) < heapSize) {
                    fronta.vloz(getLeftChild(removed));
                }
                if (getRightChild(removed) < heapSize) {
                    fronta.vloz(getRightChild(removed));
                }
            }
            return fronta.vytvorIterator();
        }
        return null;
    }

    private void checkHeapSize() {
        if (heapSize * 2 < heap.length) { // pokud je aktualni size 2x menší než velikost heapu
            reduceHeapArrayLength();
        } else if (heapSize == heap.length) {
            addHeapArrayLength();
        }
    }

    private void addHeapArrayLength() {
        heap = Arrays.copyOf(heap, (heap.length * 2) + 1);
    }

    private void reduceHeapArrayLength() {
        heap = Arrays.copyOf(heap, (heap.length / 2) + 1);
    }


    /**
     *  Metody pro získání potomků v haldě, platí pro haldu indexovanou od 0
     */
    private int getLeftChild(int parrent) {
        return (parrent * 2) + 1;
    }

    private int getRightChild(int parrent) {
        return (parrent * 2) + 2;
    }

    private int getMaxChild(int parrent) {
        int leftChild = getLeftChild(parrent);
        int rightChild = getRightChild(parrent);

        if (rightChild < heapSize && jeMensi(leftChild, rightChild)) {  // righChild < heapSize = righChild se nachazi v heapu
            return rightChild;
        } else if (leftChild < heapSize) {  // leftChild < heapSize = leftChild se nachazi v heapu
            return leftChild;
        } else { // Neexistuje potomek
            return -1;
        }
    }
}
