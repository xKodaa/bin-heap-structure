package bin_heap.gui;

import bin_heap.data.Obec;
import bin_heap.manager.AgendaKraj;
import bin_heap.structure.AbstrTable;
import bin_heap.structure.eTypProhl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lombok.Getter;

import java.io.IOException;
import java.util.Iterator;

public class AgendaController {
    @FXML
    private ListView<Obec> list;
    @FXML
    private ChoiceBox<eTypProhl> cbIterator;
    @FXML
    private TextField tfCityName;
    @Getter
    private AgendaKraj agenda;
    private final ObservableList<Obec> oList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        agenda = new AgendaKraj();
        cbIterator.getItems().addAll(eTypProhl.values());
        cbIterator.getSelectionModel().select(0);
        list.setItems(oList);
        loadData();
    }

    @FXML
    public void loadData() {
        agenda.zrusTable();
        agenda.importDat();
        applyIterator();
    }

    @FXML
    public void saveData() {
        agenda.ulozeniDat();
    }

    @FXML
    public void clearList() {
        clearListItems();
    }

    @FXML
    public void generateData() {
        agenda.generuj(50);
        refreshList();
    }

    @FXML
    public void removeCity() {
        Obec obec = list.getSelectionModel().getSelectedItem();
        agenda.odeber(obec.getMesto());
        refreshList();
    }

    @FXML
    public void applyIterator() {
        refreshList();
    }

    private Iterator<AbstrTable<String, Obec>.TreeNode> createIterator() {
        eTypProhl type = cbIterator.getValue();
        return agenda.vytvorIterator(type);
    }

    @FXML
    public void insertCity() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ObecDialog.fxml"));
            Parent root = loader.load();

            DialogController dialogController = loader.getController();
            dialogController.setAgenda(this.agenda);

            Stage dialogStage = new Stage();
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            dialogStage.setTitle("VlozMesto");
            dialogStage.setScene(new Scene(root));
            dialogStage.showAndWait();
            refreshList();
        } catch (IOException ex) {
            System.err.println("Chyba při spouštění ObecDialog.fxml: " + ex);
        }
    }

    @FXML
    public void findCityByName() {
        String city = tfCityName.getText();
        if (city != null) {
            Obec obec = agenda.najdi(city);
            if (obec != null) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Informace o městě");
                alert.setHeaderText(null);

                String contentText = String.format("""
                                Číslo kraje: %d
                                Název kraje: %s
                                PSČ: %d
                                Město: %s
                                Počet mužů: %d
                                Počet žen: %d
                                Celkem: %d""",
                        obec.getCisloKraje(), obec.getNazevKraje(), obec.getPsc(),
                        obec.getMesto(), obec.getPocetMuzu(), obec.getPocetZen(), obec.getCelkem());

                alert.setContentText(contentText);
                alert.showAndWait();
            } else {
                showAlert("Obec nenalezena");
            }
        }
    }

    private static void showAlert(String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Agenda - error");
        alert.setHeaderText("Něco se pokazilo...");
        alert.setContentText(content);
        alert.showAndWait();
    }

    @FXML
    public void refreshList() {
        refreshListWithIterator();
    }

    private void refreshListWithIterator() {
        clearListItems();
        Iterator<AbstrTable<String, Obec>.TreeNode> iterator = createIterator();

        while (iterator.hasNext()) {
            AbstrTable<String, Obec>.TreeNode treeNode = iterator.next();
            oList.add(treeNode.getValue());
        }
    }

    private void clearListItems() {
        oList.clear();
    }


}