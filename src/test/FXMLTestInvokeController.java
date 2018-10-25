/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;

/**
 * FXML Controller class
 *
 * @author kyokuto
 */
public class FXMLTestInvokeController implements Initializable {

    @FXML
    private Button btnInvoke;

    @FXML
    private void invokeSystem(ActionEvent event) {
        System.out.println("Invoke");
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("FileChooser");
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("prog file", "*.*", "*.*"));
            File file = fileChooser.showOpenDialog(null);

            Process proc = new ProcessBuilder(file.getPath()).start();
            proc.waitFor();
        } catch (IOException ex) {
            Logger.getLogger(FXMLTestInvokeController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(FXMLTestInvokeController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
