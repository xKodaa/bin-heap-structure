package data;

import structure.AbstrTable;
import structure.eTypProhl;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;

public class DataLoader {
    private final AbstrTable<String, Obec> table;
    private static final String FILENAME = "vzor.csv";
//    private static final String FILENAME = "kraje.csv";

    public DataLoader(AbstrTable<String, Obec> table) {
        this.table = table;
    }

    public AbstrTable<String, Obec> loadFromCsv() {
        try {
            List<String> lines = Files.readAllLines(Paths.get(FILENAME));
            initObec(lines);
            System.out.println("Data byla načtena ze souboru: " + FILENAME);
            return table;
        } catch (IOException e) {
            System.err.println("Chyba při načítání dat ze souboru " + FILENAME);
        }
        return null;
    }

    private void initObec(List<String> lines) {
        for (String item: lines) {
            String[] data = item.split(";");
            try {
                int cisloKraje = Integer.parseInt(data[0]);
                String nazevKraje = data[1];
                int psc = Integer.parseInt(data[2]);
                String mesto = data[3];
                int pocetMuzu = Integer.parseInt(data[4]);
                int pocetZen = Integer.parseInt(data[5]);

                Obec obec = new Obec(cisloKraje, nazevKraje, psc, mesto, pocetMuzu, pocetZen);
                table.vloz(mesto, obec);
            } catch (Exception ex) {
                System.err.println(ex);
            }
        }
    }

    public void saveIntoCsv() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILENAME))) {

            Iterator<AbstrTable<String, Obec>.TreeNode> iterator = table.vytvorIterator(eTypProhl.SIRKA);
            while (iterator.hasNext()) {
                AbstrTable<String, Obec>.TreeNode treeNode = iterator.next();
                Obec obec = treeNode.getValue();
                writer.write(String.format("%d;%s;%d;%s;%d;%d;%d",
                        obec.getCisloKraje(), obec.getNazevKraje(), obec.getPsc(),
                        obec.getMesto(), obec.getPocetMuzu(), obec.getPocetZen(), obec.getCelkem()));
                writer.newLine();
            }
            System.out.println("Data byla úspěšně uložena do souboru: " + FILENAME);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
