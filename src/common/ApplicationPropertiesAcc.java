/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package common;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.paint.Color;
import javax.swing.JOptionPane;

/**
 *
 * @author tokyo
 */
public class ApplicationPropertiesAcc {
    
    private ApplicationPropertiesAcc(){
        
    }

    public static Map<String, String> map = new HashMap<>();

    private static final String applicationPropertiesXmlFilePath = "APPLICATION_PROPERTIES.xml";

    public static void storeApplicationProperties( // 登録画面から直接ファイルに書込
            String key, String value
    ) {
        map.put(key, value);
        // 書き込み
        Properties properties = new Properties();
        map.forEach(properties::setProperty);
        //properties.setProperty(key,value);
        try {
            Path path = Paths.get(applicationPropertiesXmlFilePath);
            if(Files.exists(path)){
                System.out.println("Exsist");
                //OutputStream ostream = new FileOutputStream(applicationPropertiesXmlFilePath);
            }else{
                System.out.println("nothing File");
                //OutputStream ostream = new FileOutputStream(applicationPropertiesXmlFilePath);
            }
            OutputStream ostream = new FileOutputStream(applicationPropertiesXmlFilePath);
            properties.storeToXML(ostream, "ApplicatioProperties","UTF-8");
            //ostream.flush();
            //ostream.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ApplicationPropertiesAcc.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "設定ファイルが見当たりません。");
        } catch (IOException ex) {
            Logger.getLogger(ApplicationPropertiesAcc.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "設定ファイルの書き込み時に問題が発生しました。");
        }
    }

    static { // これは書き換えても構わないのでstaticイニシャライザーで読まなくてもよかった。
        
        Properties properties = new Properties();
        try {
            InputStream istream = new FileInputStream(applicationPropertiesXmlFilePath);
            properties.loadFromXML(istream);
            properties.forEach((key, value) -> map.put((String) key, (String) value));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ApplicationPropertiesAcc.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "???????????????");

        } catch (IOException ex) {
            Logger.getLogger(ApplicationPropertiesAcc.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "???????????????????????");
            // System.exit(0);  
        }

    }

    public static boolean loadApplicationProperties() {

        Properties properties = new Properties();
        try {
            InputStream istream = new FileInputStream(applicationPropertiesXmlFilePath);
            properties.loadFromXML(istream);
            properties.forEach((key, value) -> map.put((String) key, (String) value));
      

            } catch (FileNotFoundException ex) {
                Logger.getLogger(ApplicationPropertiesAcc.class
                        .getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(null, "設定ファイルが見当たりません。");
                return false;

            } catch (IOException ex) {
                Logger.getLogger(ApplicationPropertiesAcc.class
                        .getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(null, "設定ファイルの読み込み時に問題が発生しました。");
                // System.exit(0); 
                return false;
            }
            return true;
            // Mapに格納
        }
    }

//設定ファイルが存在すればロードしてフラグを立てる。
// 設定ファイルの内容をcommon.SystemPropertisに代入する。
//設定ファイルが存在しない場合はフラグを倒してから警告を出す。
//
//設定ファイルに書き出す。
//設定ファイルに書き出しても読み込みはinitでの一回だけ。
//アプリケーションを再起動しなければ読み込まれない。
//
//

