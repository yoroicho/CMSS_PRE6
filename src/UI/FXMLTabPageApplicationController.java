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
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
    void loadDataCmbExtention(){
        cmbExtention.focusedProperty().addListener(new ChangeListener<Boolean>(){
            @Override
            public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue) {
                //throw new UnsupportedOperationException("Not supported yet.");
                if(newPropertyValue){
                    cmbExtention.getItems().clear();
                    ApplicationPropertiesAcc.map.forEach((k,v)->
                    cmbExtention.getItems().add(k));
                }
            }
        
    });
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
                            ApplicationPropertiesAcc.map.get(newValue));
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
        loadDataCmbExtention();
    }

}
