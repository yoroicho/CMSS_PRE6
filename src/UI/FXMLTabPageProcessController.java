/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import DB.DatabaseUty;
import DB.ProcessDAO;
import DB.ProcessDTO;
import common.TimestampUtil;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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

    @FXML
     AnchorPane anchorPaneTabPageProcess;

    private State state;

    private enum State {
        // 下記は確定でない。
        NEW_CREATE, DIV_FORK, UPDATE_RECORD, PEEK, DELETE
    }

    private void initFocuseConditionForTask() { // 要改善最初に存在確認をしてから編集不可とすべき。
        this.textFieldId.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> arg0,
                    Boolean oldPropertyValue, Boolean newPropertyValue) {
                if (newPropertyValue) {
                    textFieldId.setEditable(true);
                    System.out.println("Textfield on focus");
                } else {
                    // 入力されているidが存在するか。
                    if (textFieldId.getText() != null) {
                        try {
                            // 何か入力されている。
                            List<ProcessDTO> processList = ProcessDAO.findById(
                                    TimestampUtil.parseToTimestamp(textFieldId.getText()));
                            if (processList.isEmpty()) { // 存在しない。
                                System.out.println("ID ERROR");
                                textFieldId.requestFocus();
                            } else {                       // 存在する。
                                // コンボボックスに入れ込み。                                     
                                comboBoxDivTime.getItems().addAll(processList);

                            }
                        } catch (ParseException ex) {
                            Logger.getLogger(FXMLTabPageProcessController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    textFieldId.setDisable(true); // 編集不可になっていることが明確。ただし文字は見にくい。
                    System.out.println("Textfield out focus");
                }
            }
        });
    }

    @FXML
    private void testButtonAction(ActionEvent event) {
        List<ProcessDTO> findAll = ProcessDAO.findAll();
        findAll.forEach(s -> System.out.println(s.getId() + ":" + TimestampUtil.formattedTimestamp(s.getDivtime())));
        String TIME_FORMAT = "yyyy/MM/dd HH:mm:ss";

        System.out.println(TimestampUtil.formattedTimestamp(TimestampUtil.current(), TIME_FORMAT));

    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initFocuseConditionForTask(); // 主キーを保護する為にロックするイベントを登録。       
    }

}
