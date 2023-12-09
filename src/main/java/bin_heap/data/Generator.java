package bin_heap.data;

import bin_heap.structure.AbstrTable;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Generator {
    private final Random random;
    private final AbstrTable<String, Obec> table;
    private final List<String> seznamMest;

    public Generator(AbstrTable<String, Obec> table) {
        seznamMest = initMesta();
        this.random = new Random();
        this.table = table;
    }


    public AbstrTable<String, Obec> generate(int num) {
        table.zrus();
        for (int i = 0; i < num; i++) {
            Obec obec = generujNahodnouObec();
            table.vloz(obec.getMesto(), obec);
        }
        return table;
    }

    public Obec generujNahodnouObec() {
        int cisloKraje = generujNahodneCislo(1, 20);
        String nazevKraje = "Kraj" + generujNahodneCislo(1, 10);
        int psc = generujNahodneCislo(10000, 99999);
        String mesto = generujNahodneMesto();
        int pocetMuzu = generujNahodneCislo(1, 50000);
        int pocetZen = generujNahodneCislo(1, 50000);

        return new Obec(cisloKraje, nazevKraje, psc, mesto, pocetMuzu, pocetZen);
    }

    private int generujNahodneCislo(int min, int max) {
        return random.nextInt(max - min + 1) + min;
    }
    private String generujNahodneMesto() {
        return seznamMest.get(random.nextInt(seznamMest.size()));
    }

    private List<String> initMesta() {
        return Arrays.asList("Praha", "Brno", "Ostrava", "Plzeň", "Liberec", "Olomouc", "Ústí nad Labem",
                "Hradec Králové", "České Budějovice", "Pardubice", "Zlín", "Jihlava", "Tábor",
                "Karlovy Vary", "Mladá Boleslav", "Havířov", "Kladno", "Frýdek-Místek", "Opava",
                "Děčín", "Karviná", "Jablonec nad Nisou", "Mělník", "Kroměříž", "Prostějov",
                "Teplice", "Český Těšín", "Chomutov", "Cheb",
                "Plauen", "Bautzen", "Zwickau", "Görlitz", "Meißen", "Riesa", "Pirna",
                "Döbeln", "Freiberg", "Radebeul", "Kamenz", "Coswig", "Annaberg-Buchholz",
                "Glauchau", "Löbau", "Markkleeberg", "Grimma", "Geithain", "Werdau",
                "Radeberg", "Mittweida", "Zittau", "Schkeuditz", "Limbach-Oberfrohna", "Delitzsch",
                "Leipzig", "Chemnitz", "Dresden", "Halle (Saale)", "Magdeburg", "Erfurt",
                "Jena", "Gera", "Eisenach", "Weimar", "Cottbus", "Brandenburg an der Havel",
                "Frankfurt (Oder)", "Potsdam", "Oranienburg", "Neubrandenburg", "Stralsund", "Greifswald",
                "Rostock", "Schwerin", "Wismar", "Lübeck", "Kiel", "Flensburg", "Neumünster",
                "Hamburg", "Bremen", "Oldenburg", "Bremerhaven", "Hannover", "Braunschweig", "Osnabrück",
                "Göttingen", "Wolfsburg", "Hildesheim", "Salzgitter", "Kassel", "Marburg", "Gießen",
                "Fulda", "Wiesbaden", "Mainz", "Koblenz", "Trier", "Saarbrücken", "Düsseldorf",
                "Köln", "Bonn", "Aachen", "Dortmund", "Essen", "Duisburg", "Bochum",
                "Wuppertal", "Bielefeld", "Münster", "Mönchengladbach", "Krefeld", "Augsburg", "Ingolstadt",
                "München", "Nürnberg", "Regensburg", "Würzburg", "Augsburg", "Ulm", "Erlangen",
                "Fürth", "Bayreuth", "Bamberg", "Aschaffenburg", "Wiesbaden", "Darmstadt", "Heidelberg",
                "Mannheim", "Ludwigshafen", "Karlsruhe", "Freiburg im Breisgau", "Stuttgart", "Mannheim",
                "Heilbronn", "Pforzheim", "Reutlingen", "Ulm", "Esslingen am Neckar", "Tübingen",
                "Konstanz", "Friedrichshafen", "Ravensburg", "Rottweil", "Offenburg", "Baden-Baden", "Göppingen"
        );
    }
}