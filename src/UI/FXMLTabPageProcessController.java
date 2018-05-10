/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import DB.DatabaseUty;
import DB.ProcessDAO;
import DB.ProcessDTO;
import Slip.StructSheet;
import com.itextpdf.text.DocumentException;
import common.SystemPropertiesItem;
import common.TimestampUtil;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.time.Instant;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author AnyUser
 */
public class FXMLTabPageProcessController implements Initializable {
    
    @FXML
    private TextField textFieldId;
    @FXML
    private ComboBox<ProcessDTO> comboBoxDivTime;
    @FXML
    private TextField textFieldArtifactsId;
    @FXML
    private TextArea textAreaDivName;
    @FXML
    private TextArea textAreaComment;
    //@FXML
    //private Button buttonClear;

    @FXML
    AnchorPane anchorPaneTabPageProcess;
    
    private State state;
    
    private long tempId;
    private long tempDivTime;
    
    private enum State {
        // 下記は確定でない。
        NEW_CREATE, DIV_FORK, UPDATE_RECORD, PEEK, DELETE
    }
    
    private void clearAllProperty() {
        //comboBoxDivTime.setDisable(true);
        //textFieldId.setEditable(false);

        textAreaDivName.clear();
        textAreaComment.clear();
        
        comboBoxDivTime.setDisable(false);
        
        //textFieldId.requestFocus();
        //textFieldId.setEditable(true);
        textFieldId.setDisable(false);
    }
    
    private void syncroItem() {
        this.comboBoxDivTime.getEditor().textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String t, String t1) {
                clearAllProperty();
                System.out.println("Change");
                //System.out.println("ov"+ov);
                System.out.println("t" + t);
                System.out.println("t1" + t1);
                ProcessDTO processDTO = (comboBoxDivTime
                        .getItems()
                        .stream()
                        .filter(pri -> String.valueOf(pri.getDivtime()).equals(t1)))
                        .findFirst()
                        .get();
                textAreaDivName.setText(processDTO.getDivname());
                textAreaComment.setText(processDTO.getComment());
                
            }
        });
    }
    
    private void initFocuseConditionForTask() { // 存在確認をしてから編集不可。
        this.textFieldId.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> arg0,
                    Boolean oldPropertyValue, Boolean newPropertyValue) {
                if (newPropertyValue) {
                    //textFieldId.setEditable(true);
                    System.out.println("Textfield on focus");
                } else {
                    // 入力されているidが存在するか。
                    if (textFieldId.getText().length() > 0) {
                        // 何か入力されている。
                        List<ProcessDTO> processList = ProcessDAO.findById(
                                Long.parseLong(textFieldId.getText()));
                        if (processList.isEmpty()) { // 存在しない。
                            System.out.println("ID ERROR");
                            
                            textFieldId.requestFocus();
                            
                        } else {                       // 存在する。
                            textFieldId.setDisable(true);
                            //textFieldId.setEditable(false);
                            tempId = Long.parseLong(textFieldId.getText());
                            // コンボボックスをクリアしてから入れ込み。
                            comboBoxDivTime.getItems().clear(); //
                            comboBoxDivTime.getItems().addAll(processList);
                            
                        }
                    } else { // なにも入力されていなければ新規
                        //textFieldId.setText("");
                        System.out.println("New process and new divison. ");
                        
                    }
                    ////////textFieldId.setDisable(true); // 編集不可になっていることが明確。ただし文字は見にくい。
                    System.out.println("Textfield out focus");
                }
            }
        });
    }
    
    @FXML
    private void testButtonAction(ActionEvent event) {
        List<ProcessDTO> findAll = ProcessDAO.findAll();
        findAll.forEach(s -> System.out.println(s.getId() + ":" + String.valueOf(s.getDivtime())));
        String TIME_FORMAT = "yyyy/MM/dd HH:mm:ss";
        
        System.out.println(TimestampUtil.formattedTimestamp(TimestampUtil.current(), TIME_FORMAT));
        
    }
    
    @FXML
    private void clearButtonAction(ActionEvent event) {
        clearAllProperty();
        
    }
    
    @FXML
    private void enterButtonAction(ActionEvent event) {
        /*List<ProcessDTO> findAll = ProcessDAO.findAll();
        findAll.forEach(s -> System.out.println(s.getId() + ":" + (s.getDivtime()).toString()));
        String TIME_FORMAT = "yyyy/MM/dd HH:mm:ss";

        System.out.println(TimestampUtil.formattedTimestamp(TimestampUtil.current(), TIME_FORMAT));
         */
        ProcessDTO processDTO = new ProcessDTO();
        if (textFieldId.getText().length() > 0) {
            processDTO.setId(Long.parseLong(textFieldId.getText()));
        } else {
            tempId = Instant.now().getEpochSecond();
            processDTO.setId(tempId);
        }
        System.out.println("getPromptText "
                + comboBoxDivTime.getEditor().getText()
        );
        if (comboBoxDivTime.getEditor().getText().length() > 0) {
            processDTO.setDivtime(Long.parseLong(comboBoxDivTime.getEditor().getText()));
        } else {
            tempDivTime = Instant.now().getEpochSecond();
            if (tempId == tempDivTime) {
                ++tempDivTime; // 二回idをスキャンしてもデータに行当たってしまうのを防止。
            }
            processDTO.setDivtime(tempDivTime);
        }
        processDTO.setDivname(textAreaDivName.getText());
//if(comboBoxDivTime.getSelectionModel().getSelectedItem().getDivtime())
        if (ProcessDAO.create(processDTO)) {
            try {
                if (textFieldId.getText().length() == 0) {
                    textFieldId.setText(String.valueOf(tempId));
                }
                textFieldId.setDisable(true);
                //textFieldId.setEditable(false);
                if (comboBoxDivTime.getEditor().getText().length() == 0) {
                    comboBoxDivTime.getEditor().setText(String.valueOf(tempDivTime));
                }
                comboBoxDivTime.setDisable(true);
                StructSheet.creatSlip(this.textAreaDivName.getText(),
                        "cutDateTime",
                        "compData",
                        "makerName",
                        this.textAreaComment.getText(),
                        this.textFieldId.getText(),
                        this.comboBoxDivTime.getEditor().getText(),
                        SystemPropertiesItem.SHIP_BASE,
                        Boolean.FALSE);
                JOptionPane.showMessageDialog(null, "登録／更新が完了しました");
// 要改良リフレッシュしてから選択するかたちにする。
            } catch (IOException ex) {
                Logger.getLogger(FXMLTabPageProcessController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (DocumentException ex) {
                Logger.getLogger(FXMLTabPageProcessController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (RuntimeException ex) {
                Logger.getLogger(FXMLTabPageProcessController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            JOptionPane.showMessageDialog(null, "データベース記録中に障害が発生しました。");
        };
        
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initFocuseConditionForTask(); // 主キーを保護する為にロックするイベントを登録。       
        syncroItem();
    }
    
}
