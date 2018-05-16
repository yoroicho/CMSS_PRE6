/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import FileDirController.FileIO;
import common.SystemPropertiesAcc;
import common.SystemPropertiesItem;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author kyokuto
 */
public class NewMainMakeDir {

    private static final String FILE_SEPARATOR = System.getProperty("file.separator");

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        SystemPropertiesAcc.loadSystemProperties(); //設定ファイルの読み込み

        FileIO.makeUnderDirNamed(SystemPropertiesItem.SHIP_BASE + FILE_SEPARATOR, "parent");
    
    Path targetPaths = Paths.get(SystemPropertiesItem.SHIP_BASE + FILE_SEPARATOR+"parent"+FILE_SEPARATOR+"child");
                
        try {
            Files.createDirectory(targetPaths);
        } catch (IOException ex) {
            Logger.getLogger(NewMainMakeDir.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    
    }

}
