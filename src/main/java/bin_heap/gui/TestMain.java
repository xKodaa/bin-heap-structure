package bin_heap.gui;

import bin_heap.data.Obec;
import bin_heap.manager.AgendaKraj;
import bin_heap.structure.AbstrTable;
import bin_heap.structure.eTypProhl;

import java.util.Iterator;

public class TestMain {
    public static void main(String[] args) {
        AgendaKraj agendaKraj = new AgendaKraj();
        agendaKraj.vybudujTable();

        System.out.println("----| Original bin-tree: prohl HLOUBKA |----");
        Iterator<AbstrTable<String, Obec>.TreeNode> iterator = agendaKraj.vytvorIteratorTable(eTypProhl.HLOUBKA);
        while (iterator.hasNext()) {
            System.out.println(iterator.next().getValue().getMesto());
        }

//        System.out.println("----| Original halda: prohl HLOUBKA |----");
//        agendaKraj.vybudujHaldu();
//        agendaKraj.vypisHaldu(eTypProhl.HLOUBKA);
//

//        agendaKraj.reorganizujHaldu(eTypPriority.NAZEV_OBCE);
//        System.out.println("---| Heap sirka: prio nazev_obce |---");
//        agendaKraj.vypisHeap(eTypProhl.SIRKA);
//        System.out.println("---| Heap hloubka: prio nazev_obce |---");
//        agendaKraj.vypisHeap(eTypProhl.HLOUBKA);
//
//        agendaKraj.createHeapFromTable(eTypPriority.POCET_OBYVATEL);
//        System.out.println("---| Heap sirka: prio pocet_obyvatel |---");
//        agendaKraj.vypisHeap(eTypProhl.SIRKA);
//        System.out.println("---| Heap hloubka: prio pocet_obyvatel |---");
//        agendaKraj.vypisHeap(eTypProhl.HLOUBKA);



    }
}
