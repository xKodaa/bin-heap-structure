package manager;

import data.DataLoader;
import data.Generator;
import data.Obec;
import structure.AbstrTable;
import structure.eTypProhl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AgendaKraj {
    private AbstrTable<String, Obec> table;
    private final Generator generator;
    private final DataLoader dataLoader;

    public AgendaKraj() {
        table = new AbstrTable<>();
        generator = new Generator(table);
        dataLoader = new DataLoader(table);
    }

    public void importDat() {
        table = dataLoader.loadFromCsv();
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