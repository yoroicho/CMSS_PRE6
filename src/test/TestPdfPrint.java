/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author kyokuto
 */
public class TestPdfPrint {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            Desktop desktop = Desktop.getDesktop();
            File file = new File("temp.pdf");
            desktop.open(file) ;
        } catch (IOException ex) {
            Logger.getLogger(TestPdfPrint.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
