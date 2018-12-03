/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;
// git yoroi
import UI.FXMLTabPageProcessController;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author kyokuto
 */
public class NewMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
      JOptionPane.showMessageDialog(null, "登録／更新が完了しました");
                    if (Desktop.isDesktopSupported()) {
                        new Thread(() -> {
                            try {
                                System.out.println("tyr open file");
                                /*
                                File file = new File(
                                newParentDirString
                                + FILE_SEPARATOR
                                + this.tempDivTime
                                + FILE_SEPARATOR
                                + this.tempId
                                + "-"
                                + this.tempDivTime
                                + ".pdf");
                                System.out.println("opend -D file: " + file.getPath());
                                */
                                // test
                                File file = new File("/home/kyokuto/デスクトップ/pdfTest/1528156654/1528156655/1528156654-1528156655.pdf");
                                System.out.println("リテラルで設定した"+file.getPath());
                                Desktop.getDesktop().open(file);
                                
                                //this.textFieldId.setText(String.valueOf(this.tempId));
                                // this.comboBoxDivTime.getEditor().setText(String.valueOf(this.tempDivTime));
                            } catch (IOException ex) {
                                Logger.getLogger(FXMLTabPageProcessController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }).start();
                    }
                
    }
    
}
