/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import common.ApplicationPropertiesAcc;
import common.StaticSystemPropertiesAcc;
import common.SystemPropertiesAcc;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author tokyo
 */
public class CMSS extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLBaseDocument.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
                StaticSystemPropertiesAcc.loadSystemProperties(); 
//設定ファイルの読み込みSystemPropertiesAccからStaticSystemPropertiesAccに変えた20181029
//ApplicationPropertiesAcc.loadApplicationProperties();
        launch(args);
    }
    
}
