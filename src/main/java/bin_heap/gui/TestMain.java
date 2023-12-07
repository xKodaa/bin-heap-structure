package bin_heap.gui;

import bin_heap.manager.AgendaKraj;
import bin_heap.structure.eTypPriority;
import bin_heap.structure.eTypProhl;

public class TestMain {
    public static void main(String[] args) {
        AgendaKraj agendaKraj = new AgendaKraj();
        agendaKraj.importDat();

        agendaKraj.createHeapFromTable(eTypPriority.NAZEV_OBCE);
        System.out.println("---| Heap sirka: |---");
        agendaKraj.vypisHeap(eTypProhl.SIRKA);
        System.out.println("---| Heap hloubka: |---");
        agendaKraj.vypisHeap(eTypProhl.HLOUBKA);

        agendaKraj.createHeapFromTable(eTypPriority.POCET_OBYVATEL);
        System.out.println("---| Heap sirka: |---");
        agendaKraj.vypisHeap(eTypProhl.SIRKA);
        System.out.println("---| Heap hloubka: |---");
        agendaKraj.vypisHeap(eTypProhl.HLOUBKA);



    }
}
