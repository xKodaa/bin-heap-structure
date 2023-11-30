package bin_heap.gui;

import bin_heap.data.Obec;
import bin_heap.manager.AgendaKraj;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lombok.Setter;

public class DialogController {
    @FXML
    private TextField tfRegionNum;
    @FXML
    private TextField tfWomenCount;
    @FXML
    private TextField tfMenCount;
    @FXML
    private TextField tfCity;
    @FXML
    private Button btnOk;
    @FXML
    private Button btnCancel;
    @FXML
    private TextField tfRegionName;
    @FXML
    private TextField tfPsc;

    @Setter
    private AgendaKraj agenda;
    private Obec randomObec;
    private boolean randomlyGeneratedObec;

    @FXML
    public void initialize() {
        this.randomlyGeneratedObec = false;
    }

    @FXML
    public void generateRandomData() {
        randomObec = agenda.generujObec();
        tfRegionNum.setText(String.valueOf(randomObec.getCisloKraje()));
        tfRegionName.setText(randomObec.getNazevKraje());
        tfPsc.setText(String.valueOf(randomObec.getPsc()));
        tfCity.setText(randomObec.getMesto());
        tfMenCount.setText(String.valueOf(randomObec.getPocetMuzu()));
        tfWomenCount.setText(String.valueOf(randomObec.getPocetZen()));
        randomlyGeneratedObec = true;
    }

    @FXML
    public void submitObec() {
        if (randomlyGeneratedObec) {
            agenda.vloz(randomObec.getMesto(), randomObec);
            randomlyGeneratedObec = false;
        } else {
            int regNum = Integer.parseInt(tfRegionNum.getText());
            String regName = tfRegionName.getText();
            int psc = Integer.parseInt(tfPsc.getText());
            String city = tfCity.getText();
            int menCount = Integer.parseInt(tfMenCount.getText());
            int womenCount = Integer.parseInt(tfWomenCount.getText());

            agenda.vloz(city, new Obec(regNum, regName, psc, city, menCount, womenCount));
        }
        turnOff();
    }

    @FXML
    public void cancelObec() {
        turnOff();
    }

    private void turnOff() {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }
}
