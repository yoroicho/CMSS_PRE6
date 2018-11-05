/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.LongStream;
import java.util.stream.Stream;

/**
 *
 * @author kyokuto
 */
public class LastModifiedTest {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        try {
            Path dir= Paths.get("/home/kyokuto/デスクトップ/pdfTest");
            
            Optional<Path> lastFilePath = Files.list(dir)
                    .filter(f-> !Files.isDirectory(f))
                    .max(Comparator.comparingLong(f -> f.toFile().lastModified()));
            
            if(lastFilePath.isPresent()){
               System.out.println(lastFilePath.get().toString());
            }
                    
      
        } catch (IOException ex) {
            Logger.getLogger(LastModifiedTest.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
}
