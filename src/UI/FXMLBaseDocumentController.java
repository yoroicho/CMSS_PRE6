/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;

/**
 *
 * @author tokyo
 */
public class FXMLBaseDocumentController implements Initializable {

    @FXML
    private TabPane mainTabPane;

    /**
     * @FXML private Label label;
     *
     * @FXML private void handleButtonAction(ActionEvent event) {
     * System.out.println("You clicked me!"); label.setText("Hello World!"); }
     */
    //@FXML
    //Label LabelCentralMassage;
    @FXML
    FXMLTabPageProcessController FXMLTabPageProcessController;

    @FXML
    FXMLTabPageUnitController FXMLTabPageUnitController;

    //@FXML
    //private static void labelCentralMassageSetText(String value){
    //LabelCentralMassage.setText(value);
    //}
    @FXML
    private TextField textFieldBaseMasterId;

    @FXML
    private Label labelCentralMessage;

    @FXML
    private void processTabClear(String txt) {
        //System.out.println(fXMLTabPageProcessController.toString());
        FXMLTabPageProcessController.testCase(txt + "代入する文字");
        FXMLTabPageProcessController.clearIdAndDivDateTime(); //作動しない
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        FXMLTabPageProcessController.setFXMLBaseDocumentController(this);
        FXMLTabPageUnitController.setFXMLBaseDocumentController(this);
        // リスナーは、ChangeListener#changed(ObservableValue<? extends T> observable, T oldValue, T newValue)
        mainTabPane.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends Tab> obs, Tab ot, Tab nt) -> {
            System.out.println("tab selected " + obs + "が発生。" + ot.getId() + "から" + nt.getId());
            if (ot.getId() != null) {
                switch (ot.getId()) {
                    case "tabProcess":
                        System.out.println("clear");
                        processTabClear(ot.getId());
                        break;
                    case "tabUnit":
                        System.out.println("Unit");
                        break;
                    default:
                        System.out.println("その他のタブ");
                }
            }
        });
    }

    /**
     * @return the textFieldBaseMasterId
     */
    public TextField getTextFieldBaseMasterId() {
        return textFieldBaseMasterId;
    }

    public Label getLabelCentralMessage() {
        return labelCentralMessage;
    }

    /**
     * @param textFieldBaseMasterId the textFieldBaseMasterId to set
     */
    public void setTextFieldBaseMasterId(TextField textFieldBaseMasterId) {
        this.textFieldBaseMasterId = textFieldBaseMasterId;
    }

    /**
     * @param labelCentralMessage the labelCentralMessage to set
     */
    public void setLabelCentralMessage(Label labelCentralMessage) {
        this.labelCentralMessage = labelCentralMessage;
    }

}
