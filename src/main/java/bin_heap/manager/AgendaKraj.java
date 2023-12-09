package bin_heap.manager;

import bin_heap.data.DataLoader;
import bin_heap.data.Generator;
import bin_heap.data.Obec;
import bin_heap.structure.AbstrHeap;
import bin_heap.structure.AbstrTable;
import bin_heap.structure.eTypPriority;
import bin_heap.structure.eTypProhl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class AgendaKraj {
    private AbstrTable<String, Obec> table;
    private final Generator generator;
    private final DataLoader dataLoader;
    private final AbstrHeap<Obec> heap;

    public AgendaKraj() {
        table = new AbstrTable<>();
        heap = new AbstrHeap<>(vytvorComparator(eTypPriority.POCET_OBYVATEL));
        generator = new Generator(table);
        dataLoader = new DataLoader(table);
        vybudujTable();
        vybudujHaldu();
    }

    // --- | ABSTR-HEAP | ---

    public List<Obec> vypisHaldu(eTypProhl typProhlidky) {
        return heap.vypis(typProhlidky);
    }

    public Comparator<Obec> vytvorComparator(eTypPriority typPriority) {
        if (typPriority == eTypPriority.NAZEV_OBCE) {
            return Comparator.comparing(Obec::getMesto);
        } else if (typPriority == eTypPriority.POCET_OBYVATEL) {
            return Comparator.comparing(Obec::getCelkem);
        }
        return null;
    }

    public void vybudujHaldu() {
        heap.zrus();
        List<Obec> list = new ArrayList<>();
        Iterator<AbstrTable<String, Obec>.TreeNode> iterator = vytvorIteratorTable(eTypProhl.HLOUBKA);
        while (iterator.hasNext()) {
            list.add(iterator.next().getValue());
        }
        Obec[] array = list.toArray(new Obec[0]);
        heap.vybuduj(array);
    }

    public void reorganizujHaldu(eTypPriority typPriority) {
        heap.reorganizace(vytvorComparator(typPriority));
    }

    public void zrusHaldu() { heap.zrus(); }

    public void vlozDoHaldy(Obec data) {
        heap.vloz(data);
    }

    public Obec odeberMaxZHaldy() {
        Obec obec = heap.odeberMax();
        if (obec != null)
            System.out.println("\nOdebraný max: " + obec.toString());
        return obec;
    }

    public Obec zpristupniMaxZHaldy() {
        Obec obec = heap.zpristupniMax();
        System.out.println("Max: " + obec.toString());
        return obec;
    }

    // --- | ABSTR-TABLE | ---

    public void vybudujTable() {
        table = dataLoader.loadFromCsv();
    }

    public void ulozTable() {
        dataLoader.saveIntoCsv();
    }

    public void zrusTable() {
        table.zrus();
    }

    public Obec najdiVTable(String key) {
        Obec found = table.najdi(key);
        if (found != null)
            System.out.println("Nalezeno: " + found);
        return found;
    }

    public void vlozDoTable(String key, Obec obec) {
        table.vloz(key, obec);
        System.out.println("Vloženo: " + obec.toString());
    }

    public Obec odeberZTable(String key) {
        Obec removed = table.odeber(key);
        if (removed != null)
            System.out.println("Odebráno: " + removed);
        return removed;
    }

    public Iterator<AbstrTable<String, Obec>.TreeNode> vytvorIteratorTable(eTypProhl typ) {
        return table.vytvorIterator(typ);
    }

    public void generujDoTable(int num) {
        table = generator.generate(num);
    }

    public Obec generujObec() {
        return generator.generujNahodnouObec();
    }
}