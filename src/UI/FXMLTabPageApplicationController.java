/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import com.sun.javafx.tk.FileChooserType;
import common.ApplicationPropertiesAcc;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

/**
 * FXML Controller class
 *
 * @author kyokuto
 */
public class FXMLTabPageApplicationController implements Initializable {

    @FXML
    private ComboBox<String> cmbExtention;

    @FXML
    private TextField txtFldInvoker;

    @FXML
    private Button btnInvoker;

    @FXML
    private Button btnApply;

    @FXML
    void findInvlkerApp(ActionEvent event) {
        final FileChooser fc = new FileChooser();
        fc.setTitle("SLECT APPLICATION INVOKER");
        txtFldInvoker.setText(fc.showOpenDialog(null).getPath());
    }

    @FXML
    void syncroItem() {
        cmbExtention
                .getSelectionModel()
                .selectedItemProperty()
                .addListener((r, o, newValue) -> {
                    System.out.println("Change");
                    ApplicationPropertiesAcc.loadApplicationProperties();
                    this.txtFldInvoker.setText(
                            ApplicationPropertiesAcc.map.get(
                                    cmbExtention.selectionModelProperty()
                                            .getValue()));
                });
    }

    @FXML
    void apply(ActionEvent event) {
        System.out.println("apply");
        ApplicationPropertiesAcc.storeApplicationProperties(
                this.cmbExtention.getEditor().getText(),
                txtFldInvoker.getText());
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        syncroItem();
    }

}
