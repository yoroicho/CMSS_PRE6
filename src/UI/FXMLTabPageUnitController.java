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
            FXMLBaseDocumentController.getLabelCentralMessage().setText("新規入力");
             // スキップ後は主キー欄をブロック
             //this.textFieldId.setEditable(false); ではエンターイベントが発生してしまう
            this.textFieldId.setDisable(true); // 
            //this.textAreaTitle.setEditable(true); // タイトル入力欄を解放
            //this.buttonRegisterChange.setDisable(false);
            //this.buttonRegisterNew.setDisable(false);
            this.lockAllControls(false);
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
                FXMLBaseDocumentController.getLabelCentralMessage().setText("入力受付中。");
                this.textFieldId.setEditable(false); // スキップ後は主キー欄をブロック
                this.textAreaTitle.setEditable(true); // タイトル入力欄を解放
                this.buttonRegisterChange.setDisable(false);
                this.buttonRegisterNew.setDisable(false);
            }
        }
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
            FXMLBaseDocumentController.getLabelCentralMessage().setText("新規登録中。");
            this.textFieldId.setText(String.valueOf(Instant.now().getEpochSecond()));
        }
    }

    private void registerChange() { //既存のレコードを変更
        /*
        条件
            IDが入力されており、CUT日を含みそれ以前で、
            かつCLOSEが本日を含みそれ以前であること
         */
        blockRegisterButton();
    }

    @FXML
    private void initializeAllItems() {
        this.textFieldId.clear();
        this.textFieldId.setDisable(false);
        this.lockAllControls(true);
    }

    private void blockRegisterButton() {
        this.buttonRegisterChange.setDisable(true);
        this.buttonRegisterNew.setDisable(true);
    }

    /**
     * LockAllControls ID以外すべての制御をロックする。 ボタンは無効化するが、テキストなどの表示はさせる。
     */
    private void lockAllControls(boolean value) { //このスペルで正しい。
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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        lockAllControls(true);
        /*
         *ID欄でエンターが押されないと、登録キー4つとも入力不可とする。
         *ID欄はエンターを押さずにフォーカス遷移させると、後で戻って入力できてしまう。
         *タイトルは必須項目なのでとりあえずここをブロックしておき、ID欄でエンターが
         *押されたタイミングに解放する事でID検査を回避して入力が先行するのを回避する。
         *（本来はすべての入力項目に対してブロックを行った方がよい）
         */
        this.lockAllControls(true);

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
