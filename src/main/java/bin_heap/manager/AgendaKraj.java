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
    private AbstrHeap<Obec> heap;

    public AgendaKraj() {
        table = new AbstrTable<>();
        generator = new Generator(table);
        dataLoader = new DataLoader(table);
    }

    public void importDat() {
        table = dataLoader.loadFromCsv();
    }

    public void vypisHeap(eTypProhl typProhlidky) {
        heap.vypis(typProhlidky);
    }

    public void createHeapFromTable(eTypPriority typPriority) {
        List<Obec> list = new ArrayList<>();
        Iterator<AbstrTable<String, Obec>.TreeNode> iterator = vytvorIterator(eTypProhl.HLOUBKA);
        while (iterator.hasNext()) {
            list.add(iterator.next().getValue());
        }
        Obec[] array = list.toArray(new Obec[0]);
        heap = new AbstrHeap<>(createComparator(typPriority));
        heap.vybuduj(array);
    }

    private Comparator<Obec> createComparator(eTypPriority typPriority) {
        if (typPriority == eTypPriority.NAZEV_OBCE) {
            return Comparator.comparing(Obec::getMesto);
        } else if (typPriority == eTypPriority.POCET_OBYVATEL) {
            return Comparator.comparing(Obec::getCelkem);
        }
        return null;
    }

    public void ulozeniDat() {
        dataLoader.saveIntoCsv();
    }

    public void zrusTable() {
        table.zrus();
    }

    public Obec najdi(String key) {
        Obec found = table.najdi(key);
        System.out.println("Nalezeno: " + found.toString());
        return found;
    }

    public void vloz(String key, Obec obec) {
        table.vloz(key, obec);
        System.out.println("Vloženo: " + obec.toString());
    }

    public Obec odeber(String key) {
        Obec removed = table.odeber(key);
        System.out.println("Odebráno: " + removed.toString());
        return removed;
    }

    public Iterator<AbstrTable<String, Obec>.TreeNode> vytvorIterator(eTypProhl typ) {
        return table.vytvorIterator(typ);
    }

    public void generuj(int num) {
        table = generator.generate(num);
    }

    public Obec generujObec() {
        return generator.generujNahodnouObec();
    }
}