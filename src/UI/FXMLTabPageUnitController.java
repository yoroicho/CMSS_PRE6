/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import DB.ProcessDAO;
import DB.ProcessDTO;
import DB.UnitDAO;
import DB.UnitDTO;
import common.TimestampUtil;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author kyokuto
 */
public class FXMLTabPageUnitController implements Initializable {

    @FXML
    private FXMLBaseDocumentController FXMLBaseDocumentController;

    @FXML
    private TextField textFieldId;

    @FXML
    private Button buttonClear;

    @FXML
    private DatePicker datePickerClose;

    @FXML
    private Button buttonCloseToday;

    @FXML
    private DatePicker datePickerCut;

    @FXML
    private DatePicker datePickerEtd;

    @FXML
    private Button buttonMakeFromTemplate;

    @FXML
    private Button buttonMakeFromVersion;

    @FXML
    private Button buttonReRegister;

    @FXML
    private TextField textFieldMaintitleid;

    @FXML
    private TextArea textAreaTitle;

    @FXML
    private DatePicker datePickerMtg;

    @FXML
    private TextArea textAreaRemark;

    @FXML
    private void textFieldIdOnAction(ActionEvent event) {
        System.out.println("textFieldIdOnAction called." + textFieldId.getText());
        List<UnitDTO> unitDTO;
        unitDTO = UnitDAO.findById(Long.parseLong(textFieldId.getText()));
        System.out.println("textFieldIdOnAction size " + unitDTO.size());
        if (unitDTO.size() != 1) {
            FXMLBaseDocumentController.getLabelCentralMessage().setText("Key 重複");
        }else{
            FXMLBaseDocumentController.getLabelCentralMessage().setText("呼び出しを行います。");
        }
        unitDTO.forEach(s -> {
            System.out.println("ID " + String.valueOf(s.getId()));
        });
        
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
