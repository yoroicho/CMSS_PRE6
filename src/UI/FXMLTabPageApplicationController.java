/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import com.sun.javafx.tk.FileChooserType;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

/**
 * FXML Controller class
 *
 * @author kyokuto
 */
public class FXMLTabPageApplicationController implements Initializable {

    @FXML
    private TextField txtFldInvoker;

    @FXML
    private Button btnInvoker;

    @FXML
    void findInvlkerApp(ActionEvent event) {
        System.out.println("BC");

        final FileChooser fc = new FileChooser();
        fc.setTitle("SLECT APPLICATION INVOKER");
        txtFldInvoker.setText(fc.showOpenDialog(null).getPath());

    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
