package bin_heap.gui;

import bin_heap.data.Obec;
import bin_heap.manager.AgendaKraj;
import bin_heap.structure.AbstrTable;
import bin_heap.structure.eTypPriority;
import bin_heap.structure.eTypProhl;
import javafx.beans.binding.Bindings;
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
import java.util.List;

public class AgendaController {
    @FXML
    private ListView<Obec> list;
    @FXML
    private ChoiceBox<eTypProhl> cbIterator;
    @FXML
    private ChoiceBox<eTypPriority> cbPriority;
    @FXML
    private ChoiceBox<eTypStruktury> cbStructure;
    @FXML
    private TextField tfCityName;
    @Getter
    private AgendaKraj agenda;
    @FXML
    private Label lblItemCount;
    private final ObservableList<Obec> oList = FXCollections.observableArrayList();
    private eTypStruktury typStruktury;
    @FXML
    private Button btnShowCity;
    @FXML
    private Button btnRemoveCity;
    @FXML
    private Button btnChangePriority;

    @FXML
    public void initialize() {
        initComboBoxes();
        agenda = new AgendaKraj();  // naplni 1. table daty z .csv, 2. haldu daty z table
        lblItemCount.textProperty().bind(Bindings.size(oList).asString("Počet položek: %d"));
        list.setItems(oList);
        changeStructure();
        refreshList();
        applyPriority();
    }

    private void initComboBoxes() {
        cbIterator.getItems().addAll(eTypProhl.values());
        cbIterator.getSelectionModel().select(0);
        cbPriority.getItems().addAll(eTypPriority.values());
        cbPriority.getSelectionModel().select(0);
        cbStructure.getItems().addAll(eTypStruktury.values());
        cbStructure.getSelectionModel().select(0);
    }

    @FXML
    public void loadData() {
        agenda.zrusTable();
        agenda.vybudujTable();
        agenda.vybudujHaldu();
        applyIterator();
        applyPriority();
    }

    @FXML
    public void saveData() {
        agenda.ulozTable();
    }

    @FXML
    public void clearList() {
        clearListItems();
    }

    @FXML
    public void generateData() {
        agenda.generujDoTable(30);
        agenda.vybudujHaldu();
        if (typStruktury == eTypStruktury.HALDA) {
            applyPriority();
        } else {
            refreshList();
        }
    }

    @FXML
    public void removeCity() {
        if (typStruktury == eTypStruktury.BINTREE) {
            Obec obec = list.getSelectionModel().getSelectedItem();
            showCityInAlert(agenda.odeberZTable(obec.getMesto()));
        } else if (typStruktury == eTypStruktury.HALDA) {
            showCityInAlert(agenda.odeberMaxZHaldy());
        }
        refreshList();
    }

    @FXML
    public void applyIterator() {
        refreshList();
    }

    @FXML
    public void applyPriority() {
        sortList();
    }

    private void sortList() {
        agenda.reorganizujHaldu(cbPriority.getSelectionModel().getSelectedItem());
        refreshList();
    }

    private Iterator<AbstrTable<String, Obec>.TreeNode> createIterator() {
        eTypProhl type = cbIterator.getValue();
        return agenda.vytvorIteratorTable(type);
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
            if (typStruktury == eTypStruktury.HALDA) {
                applyPriority();
            } else {
                refreshList();
            }
        } catch (IOException ex) {
            System.err.println("Chyba při spouštění ObecDialog.fxml: " + ex);
        }

    }

    @FXML
    public void findCityByName() {
        if (typStruktury == eTypStruktury.BINTREE) {
            String city = tfCityName.getText();
            if (city != null) {
                Obec obec = agenda.najdiVTable(city);
                if (obec != null) {
                    showCityInAlert(obec);
                } else {
                    showErrorAlert("Obec v BinTree nenalezena");
                }
            }
        } else if (typStruktury == eTypStruktury.HALDA){
            Obec obec = agenda.zpristupniMaxZHaldy();
            if (obec != null) {
                showCityInAlert(obec);
            } else {
                showErrorAlert("Obec v Haldě nenalezena");
            }
        }
    }

    private static void showCityInAlert(Obec obec) {
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
    }

    private static void showErrorAlert(String content) {
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
        if (typStruktury == eTypStruktury.BINTREE) {
            fillListViewByFromAbstrTable();
        } else {
            fillListViewFromHeap();
        }
    }

    private void fillListViewFromHeap() {
        List<Obec> listObci = agenda.vypisHaldu(cbIterator.getSelectionModel().getSelectedItem());
        oList.addAll(listObci);
    }

    private void fillListViewByFromAbstrTable() {
        Iterator<AbstrTable<String, Obec>.TreeNode> iterator = createIterator();

        while (iterator.hasNext()) {
            AbstrTable<String, Obec>.TreeNode treeNode = iterator.next();
            oList.add(treeNode.getValue());
        }
    }

    private void clearListItems() {
        oList.clear();
        list.getItems().clear();
    }

    @FXML
    public void changeStructure() {
        typStruktury = cbStructure.getSelectionModel().getSelectedItem();
        if (typStruktury == eTypStruktury.HALDA) {  // v haldě tyto operace nejsou
            btnShowCity.setDisable(false);
            tfCityName.setDisable(true);
            btnChangePriority.setDisable(false);
            cbPriority.setDisable(false);
            btnShowCity.setText("Zpřístupni max město");
            btnRemoveCity.setText("Odeber max město");
            applyPriority();
        } else {
            btnShowCity.setDisable(false);
            tfCityName.setDisable(false);
            btnChangePriority.setDisable(true);
            cbPriority.setDisable(true);
            btnShowCity.setText("Zpřístupni město");
            btnRemoveCity.setText("Odeber město");
            refreshList();
        }
    }
}