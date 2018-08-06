/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import DB.UnitDAO;
import DB.UnitDTO;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
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
    private Button buttonRegisterChange;

    @FXML
    private Button buttonRegisterNew;

    @FXML
    private TextField textFieldMainTitleId;

    @FXML
    private TextArea textAreaTitle;

    @FXML
    private DatePicker datePickerMtg;

    @FXML
    private TextArea textAreaRemark;

    @FXML
    private Button buttonStartUp;

    @FXML
    private TextArea textAreaSeriesName;

    @FXML
    private TextArea textAreaOverallSeriesName;

    @FXML
    private TextField textFieldOverallSeriesId;

    @FXML
    private TextField textFieldSeriesId;

    @FXML
    private TextArea textAreaMainTitleName;

    @FXML
    private void textFieldIdOnAction(ActionEvent event) {
        System.out.println("textFieldIdOnAction called." + textFieldId.getText());
        if (textFieldId.getText().trim().length() == 0) { //空欄は新規入力扱い
            FXMLBaseDocumentController.getLabelCentralMessage().setText("新規入力モード");
            // スキップ後は主キー欄をブロック
            this.textFieldId.setDisable(true); // setEdiatable(false)は不可
            this.lockAllControls(false);
            this.buttonRegisterChange.setDisable(true);
            this.buttonMakeFromTemplate.setDisable(true);
            this.buttonMakeFromVersion.setDisable(true);
        } else { //入力があればDB索引
            List<UnitDTO> unitDTO;
            unitDTO = UnitDAO.findById(Long.parseLong(textFieldId.getText()));
            System.out.println("textFieldIdOnAction size " + unitDTO.size());
            if (unitDTO.size() > 1) {
                FXMLBaseDocumentController.getLabelCentralMessage().setText(
                        "重大な障害 DB cmss.unit.id 重複Key発生");
            } else if (unitDTO.isEmpty()) {
                FXMLBaseDocumentController.getLabelCentralMessage().setText(
                        "レコードがありません。");
            } else if (unitDTO.size() == 1) {
                FXMLBaseDocumentController.getLabelCentralMessage().setText(
                        "呼び出しを行います。");
                this.textFieldId.setEditable(false); // 索引後は主キーをブロック
                unitDTO.forEach(s -> { // 一件しかないのでループは無意味だがコピペ元用
                    System.out.println("ID " + String.valueOf(s.getId()));
                    //this.textFieldId.setText(String.valueOf(s.getId()));
                    datePickerClose.setValue(s.getClose().toLocalDate());
                    datePickerCut.setValue(s.getCut().toLocalDate());
                    datePickerEtd.setValue(s.getEtd().toLocalDate());
                    textFieldMainTitleId.setText(s.getMaintitleid());
                    textAreaTitle.setText(s.getTitle());
                    datePickerMtg.setValue(s.getMtg().toLocalDate());
                    textAreaRemark.setText(s.getRemark());
                });
                FXMLBaseDocumentController.getLabelCentralMessage().setText("既存分の操作を受付中。");
                this.textFieldId.setDisable(true); // スキップ後は主キー欄をブロック
                this.lockAllControls(false); // 画面解放
                this.buttonRegisterNew.setDisable(true); // 新規はありえないのでブロック
            }
        }
    }

    @FXML
    private void buttonClearOnAction(ActionEvent event) {
        initializeAllItems();
    }

    @FXML
    private void registerNew() { //新規にレコードを追加
        /*
        条件
            IDが空欄で、CUT日を含みそれ以前で、かつCLOSEに日が入っていないこと
         */
        if (textFieldId.getText().isEmpty() == false) { //IDが空欄ではない
            Alert alert = new Alert(Alert.AlertType.WARNING,
                    "IDが入力されています。\n"
                    + "既存のレコードを変更しての新規作成はできません。");
            Optional<ButtonType> showAndWait = alert.showAndWait();
        } else if (datePickerCut.getValue() != null
                // CUTが空欄でない
                && LocalDate.now().isAfter(datePickerCut.getValue())) {
            Alert alert = new Alert(Alert.AlertType.WARNING,
                    "設定されているCUT日以降に登録はできません。\n"
            );
            Optional<ButtonType> showAndWait = alert.showAndWait();
        } else if (this.datePickerClose.getValue() != null) {
            //CLOSE(閉鎖日が入力されている)
            Alert alert = new Alert(Alert.AlertType.WARNING,
                    "CLOSE（閉鎖）が宣言された登録はできません。\n"
                    + "すでに閉鎖済みである場合は一旦登録後に閉鎖処理をして下さい"
            );
            Optional<ButtonType> showAndWait = alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING,
                    "新規登録を開始します。\n"
            );
            Optional<ButtonType> showAndWait = alert.showAndWait();
            blockRegisterButton();
            FXMLBaseDocumentController.getLabelCentralMessage().setText("新規登録の受付中。");
            // ここでプレビューと可否の入力を受け付け
            textFieldId.setText(String.valueOf(Instant.now().getEpochSecond()));
            this.lockAllControls(true);
            FXMLBaseDocumentController.getLabelCentralMessage().setText(
                    "ID "
                    + textFieldId.getText()
                    + " を登録しました。");
            clearAllView();
            this.textFieldId.clear();
            this.textFieldId.setDisable(false);
        }
    }

    private void registerChange() { //既存のレコードを変更
        /*
        条件
            IDが入力されており、CUT日を含みそれ以前で、
            かつCLOSEが本日を含みそれ以前であること
         */
        blockRegisterButton();
        // 登録内容を提示し可否を確認して登録したら画面は抹消する。
        initializeAllItems();
    }

    @FXML
    private void initializeAllItems() {
        textFieldId.clear();
        textFieldId.setDisable(false);
        lockAllControls(true);
        clearAllView();
    }

    private void blockRegisterButton() {
        this.buttonRegisterChange.setDisable(true);
        this.buttonRegisterNew.setDisable(true);
    }

    /**
     * LockAllControls ID以外すべての制御をロックする。 ボタンは無効化するが、テキストなどの表示はさせる。
     */
    private void lockAllControls(boolean value) {
        this.buttonClear.setDisable(value);
        this.buttonCloseToday.setDisable(value);
        this.buttonMakeFromTemplate.setDisable(value);
        this.buttonMakeFromVersion.setDisable(value);
        this.buttonRegisterChange.setDisable(value);
        this.buttonRegisterNew.setDisable(value);
        this.buttonStartUp.setDisable(value);
        this.datePickerClose.setDisable(value);
        this.datePickerCut.setDisable(value);
        this.datePickerEtd.setDisable(value);
        this.datePickerMtg.setDisable(value);
        this.textAreaMainTitleName.setDisable(value);
        this.textAreaOverallSeriesName.setDisable(value);
        this.textAreaRemark.setDisable(value);
        this.textAreaSeriesName.setDisable(value);
        this.textAreaTitle.setDisable(value);
        this.textFieldMainTitleId.setDisable(value);
        this.textFieldOverallSeriesId.setDisable(value);
        this.textFieldSeriesId.setDisable(value);
    }

    private void clearAllView() {
        this.datePickerClose.setValue(null);
        this.datePickerCut.setValue(null);
        this.datePickerEtd.setValue(null);
        this.datePickerMtg.setValue(null);
        this.textAreaMainTitleName.setText(null);
        this.textAreaOverallSeriesName.setText(null);
        this.textAreaRemark.setText(null);
        this.textAreaSeriesName.setText(null);
        this.textAreaTitle.setText(null);
        this.textFieldMainTitleId.setText(null);
        this.textFieldOverallSeriesId.setText(null);
        this.textFieldSeriesId.setText(null);
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        clearAllView();
        lockAllControls(true);

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
