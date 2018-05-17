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
    FXMLTabPageProcessController fXMLTabPageProcessController;
    //@FXML
    //private static void labelCentralMassageSetText(String value){
    //LabelCentralMassage.setText(value);
    //}

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // リスナーは、ChangeListener#changed(ObservableValue<? extends T> observable, T oldValue, T newValue)
        mainTabPane.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends Tab> obs, Tab ot, Tab nt) -> {
            System.out.println("tab selected " + obs + "が発生。" + ot.getId() + "から" + nt.getId());
            switch (ot.getId()) {
                case "tabProcess":
                    System.out.println("clear");
                    // fXMLTabPageProcessController.clearIdAndDivDateTime(); 作動しない
                    break;
            }
        });
    }

}
