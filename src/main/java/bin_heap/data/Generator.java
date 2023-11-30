package bin_heap.data;

import bin_heap.structure.AbstrTable;

import java.util.Random;

public class Generator {
    private final Random random;
    private final AbstrTable<String, Obec> table;

    public Generator(AbstrTable<String, Obec> table) {
        this.random = new Random();
        this.table = table;
    }

    public AbstrTable<String, Obec> generate(int num) {
        table.zrus();
        for (int i = 0; i < num; i++) {
            table.vloz(generujNahodneMesto(), generujNahodnouObec());
        }
        return table;
    }

    public Obec generujNahodnouObec() {
        int cisloKraje = generujNahodneCislo(1, 20);
        String nazevKraje = "Kraj" + generujNahodneCislo(1, 10);
        int psc = generujNahodneCislo(10000, 99999);
        String mesto = generujNahodneMesto();
        int pocetMuzu = generujNahodneCislo(1, 1000);
        int pocetZen = generujNahodneCislo(1, 1000);

        return new Obec(cisloKraje, nazevKraje, psc, mesto, pocetMuzu, pocetZen);
    }

    private int generujNahodneCislo(int min, int max) {
        return random.nextInt(max - min + 1) + min;
    }
    private String generujNahodneMesto() {
        return "Mesto " + generujNahodneCislo(1, 10000);
    }
}