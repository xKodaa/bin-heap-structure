package bin_heap.data;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Obec {
    private final int cisloKraje;
    private final String nazevKraje;
    private final int psc;
    private final String mesto;
    private final int pocetMuzu;
    private final int pocetZen;
    private final int celkem;

    public Obec(int cisloKraje, String nazevKraje, int psc, String mesto, int pocetMuzu, int pocetZen) {
        this.cisloKraje = cisloKraje;
        this.nazevKraje = nazevKraje;
        this.psc = psc;
        this.mesto = mesto;
        this.pocetMuzu = pocetMuzu;
        this.pocetZen = pocetZen;
        this.celkem = pocetMuzu + pocetZen;
    }

    @Override
    public String toString() {
        return "Obec {" +
                "mesto='" + mesto + '\'' +
                ", cisloKraje=" + cisloKraje +
                ", nazevKraje='" + nazevKraje + '\'' +
                ", psc=" + psc +
                ", pocetMuzu=" + pocetMuzu +
                ", pocetZen=" + pocetZen +
                ", celkem=" + celkem +
                '}';
    }
}