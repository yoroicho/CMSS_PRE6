/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author kyokuto
 */
public class FXMLTestInvoke extends Application {

    @FXML
    public void invokeWindowOpen(){
        try {
           // FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLTestInvoke.fxml"));
            //BorderPane root = (BorderPane) loader.load();
            Parent root = FXMLLoader.load(getClass().getResource("FXMLTestInvoke.fxml"));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.showAndWait();
        } catch (IOException ex) {
            Logger.getLogger(FXMLTestInvoke.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLTestInvoke.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
