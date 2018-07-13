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
import static java.lang.Thread.sleep;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    private Button buttonRegister;

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
        if (unitDTO.size() > 1) {
            FXMLBaseDocumentController.getLabelCentralMessage().setText("重大な障害 DB cmss.unit.id 重複Key発生");
        } else if (unitDTO.size() == 0) {
            FXMLBaseDocumentController.getLabelCentralMessage().setText("レコードがありません。");
        } else if (unitDTO.size() == 1) {
            FXMLBaseDocumentController.getLabelCentralMessage().setText("呼び出しを行います。");
            this.textFieldId.setEditable(false);
            unitDTO.forEach(s -> { // 一件しかないのでループは無意味だがコピペ元用
                System.out.println("ID " + String.valueOf(s.getId()));
                //this.textFieldId.setText(String.valueOf(s.getId()));
                this.datePickerClose.setValue(s.getCut().toLocalDate());
            });
            FXMLBaseDocumentController.getLabelCentralMessage().setText("入力受付中。");
        }
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    /**
     * @return the FXMLBaseDocumentController
     */
    public FXMLBaseDocumentController getFXMLBaseDocumentController() {
        return FXMLBaseDocumentController;
    }

    /**
     * @param FXMLBaseDocumentController the FXMLBaseDocumentController to set
     */
    public void setFXMLBaseDocumentController(FXMLBaseDocumentController FXMLBaseDocumentController) {
        this.FXMLBaseDocumentController = FXMLBaseDocumentController;
    }

}
