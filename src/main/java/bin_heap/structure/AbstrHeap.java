package bin_heap.structure;

import bin_heap.queue.AbstrFifo;
import bin_heap.queue.AbstrLifo;

import java.util.*;

public class AbstrHeap<T> implements IAbstrHeap<T> {
    private T[] heap;
    int heapSize;
    private Comparator<T> comparator;

    public AbstrHeap(Comparator<T> comparator) {
        this.comparator = comparator;
        heapSize = 0;
    }

    @Override
    public void vybuduj(T[] array) {
        if (!jePrazdny()) zrus();
        this.heap = array;
        this.heapSize = heap.length;
    }

    /**
     * Všechny listy haldy jsou ve spodní půlce pole, proto začínáme od poloviny směrem nahoru <br>
     * abychom měli jistotu že kontrolujeme hlavně rodiče, pro které se spouští heapDown
     */
    @Override
    public void reorganizace(Comparator<T> comparator) {
        this.comparator = comparator;
        for (int i = heapSize / 2; i >= 0; i--) {
            heapDown(i);
        }
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
        if (jePrazdny()) {
            heap[0] = data;
            heapSize++;
        } else {
            checkHeapSize();

            heap[heapSize++] = data;
            heapUp(heapSize);
        }
    }

    /**
     * Vymění max prvek s nejposlednějším, nový nejposlednější vyNulluje a spustí heapDown
     */
    @Override
    public T odeberMax() {
        if (jePrazdny()) {
            System.err.println("Prázdná halda => nelze odebrat MAX");
            return null;
        }
        T max = heap[0];
        heap[0] = heap[heapSize-1];
        heap[heapSize - 1] = null;
        heapSize--;

        heapDown(0); // zajistí vlastnosti haldy

        checkHeapSize();
        return max;
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
    public List<T> vypis(eTypProhl typProhlidky) {
        List<T> list = new ArrayList<>();
        Iterator<Integer> iterator = vytvorIterator(typProhlidky);
        if (iterator != null) {
//            System.out.println("\nVypis haldy dle prohlidky typu: " + typProhlidky);
            while (iterator.hasNext()) {
                int index = iterator.next();
                list.add(heap[index]);
//                System.out.println("Heap[" + index + "]: " + heap[index]);
            }
        }
        return list;
    }

    private void heapDown(int parrent) {
        int maxChild;
        while ((maxChild = getMaxChild(parrent)) != -1 && jeMensi(parrent, maxChild)) {
            swap(parrent, maxChild);
            parrent = maxChild;
        }
    }

    private void heapUp(int child) {
        int parrent;
        while (child > 0 && jeMensi((parrent = getParent(child)), child)) {
            swap(parrent, child);
            child = parrent;
        }
    }

    private void swap(int i, int j) {
        T temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
    }

    private Iterator<Integer> vytvorIterator(eTypProhl typProhlidky) {
        int actual = 0;

        if (typProhlidky == eTypProhl.HLOUBKA) {    // prohlidka do nejlevejsiho prvku s backtrackingem
            AbstrLifo<Integer> zasobnik = getHeapStack(actual);
            return zasobnik.vytvorIterator();

        } else if (typProhlidky == eTypProhl.SIRKA) {   // prohlidka po vrstvach
            AbstrFifo<Integer> fronta = getHeapQueue(actual);
            return fronta.vytvorIterator();
        }
        return null;
    }

    private AbstrLifo<Integer> getHeapStack(int actual) {
        addHeapArrayLength();
        AbstrLifo<Integer> zasobnik = new AbstrLifo<>();
        while (heap[actual] != null || !zasobnik.jePrazdny()) {
            while (heap[actual] != null) {
                zasobnik.vloz(actual);
                actual = getLeftChild(actual);
            }
            actual = zasobnik.odeber(); // backtrack
            actual = getRightChild(actual);
        }
        return zasobnik;
    }

    private AbstrFifo<Integer> getHeapQueue(int actual) {
        AbstrFifo<Integer> fronta = new AbstrFifo<>();
        fronta.vloz(actual);
        while (!fronta.jePrazdny()) {
            int removed = fronta.odeber();
            int leftChildOfRemoved = getLeftChild(removed);
            int rightChildOfRemoved = getRightChild(removed);

            if (jePrvekVHalde(leftChildOfRemoved)) {
                fronta.vloz(leftChildOfRemoved);
            }
            if (jePrvekVHalde(rightChildOfRemoved)) {
                fronta.vloz(rightChildOfRemoved);
            }
        }
        return fronta;
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

    private int getParent(int child) {
        return (child - 1) / 2;
    }

    /**
     * Pomocná metoda pro získání indexu maximálního potomka
     */
    private int getMaxChild(int parrent) {
        int leftChild = getLeftChild(parrent);
        int rightChild = getRightChild(parrent);

        if (jePrvekVHalde(rightChild) && jeMensi(leftChild, rightChild)) {
            return rightChild;
        } else if (jePrvekVHalde(leftChild)) {
            return leftChild;
        } else { // Neexistuje potomek
            return -1;
        }
    }

    /**
     * @return true => heap[i] je mensi nez heap[j] <br> false => heap[i] je vetsi nez heap[j]
     */
    private boolean jeMensi(int i, int j) {
        if (heap[j] == null) {
            return false;
        }
        return comparator.compare(heap[i], heap[j]) < 0;
    }

    /**
     * @return true => prvek se nachazi v haldě <br> false => prvek se nenachází v haldě
     */
    private boolean jePrvekVHalde(int prvek) {
        return prvek < heapSize;
    }

    private void checkHeapSize() {
        int originalLength = heap.length;
        if (heapSize * 2 < heap.length) { // pokud je aktualni pocet prvku v poli 2x menší než velikost heapu
            reduceHeapArrayLength();
        } else if (heapSize == heap.length) {   // pokud je stejně prvku v poli jako je jeho velikost
            addHeapArrayLength();
        }
        System.out.println("Velikost haldy = " + originalLength + " se změnila na = " + heap.length);
    }

    private void addHeapArrayLength() {
        heap = Arrays.copyOf(heap, (heap.length * 2) + 1);
    }

    private void reduceHeapArrayLength() {
        heap = Arrays.copyOf(heap, (heap.length / 2) + 1);
    }
}
