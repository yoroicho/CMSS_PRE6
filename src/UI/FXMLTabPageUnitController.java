/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import DB.UnitDAO;
import DB.UnitDTO;
import FileDirController.OperationTool;
import common.SystemPropertiesAcc;
import java.net.URL;
import java.sql.Date;
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
import javafx.scene.layout.Region;

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
    private Button buttonMakeAnotherVersion;

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

    private enum UnitAim {
        // PEEK,DELETEは確定でない。
        MAKE_FROM_TEMPLATE, MAKE_ANOTHER_VERSION, REGISTER_CHANGE, REGISTER_NEW, PEEK, DELETE
    }

    // 馬鹿馬鹿しいのでちゃんとやる
    private List<UnitDTO> unitDTO; // そもそも1件しかありえないのでListにする必要はない（コピペ元用）
    // UnitDTO unitDTO;

    private UnitDTO registerUnitDTO; // 登録予定のUnitDTO

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
            this.buttonMakeAnotherVersion.setDisable(true);
        } else { //入力があればDB索引
            // List<UnitDTO> unitDTO; // そもそも1件しかありえないのでListにする必要はない（コピペ元用）
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
                unitDTO.forEach(s -> { // 一件しかないのでループは無意味だがコピペ元用
                    System.out.println("ID " + String.valueOf(s.getId()));
                    //this.textFieldId.setText(String.valueOf(s.getId()));
                    if (s.getClose() != null) {
                        datePickerClose.setValue(s.getClose().toLocalDate());
                    }
                    if (s.getCut() != null) {
                        datePickerCut.setValue(s.getCut().toLocalDate());
                    }
                    if (s.getEtd() != null) {
                        datePickerEtd.setValue(s.getEtd().toLocalDate());
                    }
                    textFieldMainTitleId.setText(s.getMaintitleId());
                    textAreaTitle.setText(s.getTitle());
                    if (s.getMtg() != null) {
                        datePickerMtg.setValue(s.getMtg().toLocalDate());
                    }
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
            // blockRegisterButton();
            FXMLBaseDocumentController.getLabelCentralMessage().setText("新規登録の受付中。");
            // ここでプレビューと可否の入力を受け付け
            Alert alertRegisterNew = new Alert(Alert.AlertType.CONFIRMATION,
                    "新規登録：新しいユニットを作成します。"
                    // + "ID:" + this.textFieldId.getText() // IDは表示されないはず
                    + "TITLE:" + this.textAreaTitle.getText());
            alertRegisterNew.showAndWait()
                    .filter(response -> response == ButtonType.OK)
                    .ifPresent(response -> {
                        registerUnitDTO // 登録予定のUnitDTO
                                = pushDTO(new UnitDTO(), UnitAim.REGISTER_NEW);
                        if (UnitDAO.register(registerUnitDTO)) {
                            FXMLBaseDocumentController.getLabelCentralMessage().setText(
                                    "データベースに新規登録しました。");
                        } else {
                            registerUnitDTO = null;
                            FXMLBaseDocumentController.getLabelCentralMessage().setText(
                                    "データーベース登録に失敗しました。");
                            return; // 失敗したらそこで終了。
                        }
                        if (OperationTool.createUnitDir(registerUnitDTO)) {
                            FXMLBaseDocumentController.getLabelCentralMessage().setText(
                                    "UNIT_BASEに新規登録しました。");
                        } else {
                            FXMLBaseDocumentController.getLabelCentralMessage().setText(
                                    "UNIT_BASEの登録に失敗しました。");
                            // 変更以外はデータベースに遡って削除する。
                            if (UnitDAO.deleteById(registerUnitDTO.getId())) {
                                FXMLBaseDocumentController.getLabelCentralMessage().setText(
                                        "UNIT_BASEの登録に失敗したのでデータベースレコード削除しました。");
                            } else {
                                FXMLBaseDocumentController.getLabelCentralMessage().setText(
                                        "UNIT_BASEの登録に失敗しましたが、データベースレコードの削除はできませんでした。");
                            }
                            return; // 失敗したらそこで終了。
                        }
                        this.lockAllControls(true);
                        clearAllView();
                        textFieldId.clear();
                        textFieldId.setDisable(false);
                        textFieldId.requestFocus();
                    });
            registerUnitDTO = null;
        }
    }

    @FXML
    private void registerChange() { //既存のレコードを変更
        /*
        条件buttonRegisterChangeの押下
        ★おそらく元のUnitDTOとの比較も必要であろう。
            IDが入力されており、CUT日を含みそれ以前で、
            かつCLOSEが本日を含みそれ以前であること
         */
        if (textFieldId.getText().isEmpty() == true) { //IDが空欄
            Alert alert = new Alert(Alert.AlertType.WARNING,
                    "IDが入力されていません。\n"
                    + "既存のレコードでなければ変更上書きはできません。");
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
                    "変更登録を開始します。\n"
            );
            Optional<ButtonType> showAndWait = alert.showAndWait();
            // blockRegisterButton();
            FXMLBaseDocumentController.getLabelCentralMessage().setText("変更登録の受付中。");
            // ここでプレビューと可否の入力を受け付け
            Alert alertRegisterNew = new Alert(Alert.AlertType.CONFIRMATION,
                    "変更\n"
                    + "ID: "
                    + String.valueOf(unitDTO.get(0).getId()
                            + " から "
                            + this.textFieldId.getText())
                    + "\n"
                    + "TITLE: "
                    + "\n"
                    + String.valueOf(unitDTO.get(0).getTitle()
                            + "\n"
                            + " から "
                            + "\n"
                            + this.textAreaTitle.getText()));
            // 大きさの制限をとりはらう(setMinSizeでも横幅は広がらなかった)
            alertRegisterNew.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
            // .setMinSize(Region.USE_PREF_SIZE,Region.USE_PREF_SIZE); 
            alertRegisterNew.showAndWait()
                    .filter(response -> response == ButtonType.OK)
                    .ifPresent(response -> {
                        registerUnitDTO // 登録予定のUnitDTO
                                = pushDTO(new UnitDTO(), UnitAim.REGISTER_CHANGE);
                        if (UnitDAO.register(registerUnitDTO)) {
                            FXMLBaseDocumentController.getLabelCentralMessage().setText(
                                    "変更登録しました。");
                        } else {
                            registerUnitDTO = null;
                            FXMLBaseDocumentController.getLabelCentralMessage().setText(
                                    "データーベース登録に失敗しました。");
                            return; // 失敗したらそこで終了。
                        }
                        if (OperationTool.createUnitDir(registerUnitDTO)) {
                            FXMLBaseDocumentController.getLabelCentralMessage().setText(
                                    "UNIT_BASEに変更登録しました。");
                        } else {
                            Alert alertFileError = new Alert(Alert.AlertType.WARNING,
                                    "ファイルシステムに異常が起こりました。\n"
                                    + "設定を見直した上で、再度画面を呼出し\n"
                                    + "上書き登録をしてください。"
                            );
                            Optional<ButtonType> ans = alertFileError.showAndWait();
                            return; // 失敗したらそこで終了。
                        }
                        this.lockAllControls(true);
                        clearAllView();
                        textFieldId.clear();
                        textFieldId.setDisable(false);
                        textFieldId.requestFocus();
                    }
                    );
            registerUnitDTO = null;
        }
    }

    @FXML

    private void makeAnotherVersion() { // 細部の調整はまだ
        /*
        条件buttonMakeAnotherVersionの押下
        もともと索引されているUnitDTOの内容と登録しようとしている画面の内容を比較
            IDが入力されており、CUT日を含みそれ以前で、
            かつCLOSEが本日を含みそれ以前であること
         */
        if (textFieldId.getText() == String.valueOf(unitDTO.get(0).getId())) { //IDが一致しない。通常は有り得ない。
            Alert alert = new Alert(Alert.AlertType.WARNING,
                    "IDが呼び出し時と一致していません。\n"
                    + "プログラムを見直す必要があります。"
            );
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
                    "変更登録を開始します。\n"
                    + "ID: "
                    + String.valueOf(unitDTO.get(0).getId()
                            + " から "
                            + this.textFieldId.getText())
            );
            Optional<ButtonType> showAndWait = alert.showAndWait();
            // blockRegisterButton();
            FXMLBaseDocumentController.getLabelCentralMessage().setText("変更登録の受付中。");
            // ここでプレビューと可否の入力を受け付け
            Alert alertRegisterNew = new Alert(Alert.AlertType.CONFIRMATION,
                    "変更登録：既存のユニットを変更します。"
                    + "ID:" + this.textFieldId.getText() // IDは表示されないはず
                    + "TITLE:" + this.textAreaTitle.getText());
            alertRegisterNew.showAndWait()
                    .filter(response -> response == ButtonType.OK)
                    .ifPresent(response -> {
                        registerUnitDTO // 登録予定のUnitDTO
                                = pushDTO(new UnitDTO(), UnitAim.MAKE_ANOTHER_VERSION);
                        System.out.println("call version");
                        if (UnitDAO.register(registerUnitDTO)) {
                            FXMLBaseDocumentController.getLabelCentralMessage().setText(
                                    "変更登録しました。");

                        } else {
                            registerUnitDTO = null;
                            FXMLBaseDocumentController.getLabelCentralMessage().setText(
                                    "データーベース登録に失敗しました。");
                            return; // 失敗したらそこで終了。
                        }
                        if (OperationTool.createUnitDir(registerUnitDTO)) {
                            FXMLBaseDocumentController.getLabelCentralMessage().setText(
                                    "UNIT_BASEに新規登録しました。");
                        } else {
                            FXMLBaseDocumentController.getLabelCentralMessage().setText(
                                    "UNIT_BASEの登録に失敗しました。");
                            // 変更以外はデータベースに遡って削除する。
                            if (UnitDAO.deleteById(registerUnitDTO.getId())) {
                                FXMLBaseDocumentController.getLabelCentralMessage().setText(
                                        "UNIT_BASEの登録に失敗したのでデータベースレコード削除しました。");
                            } else {
                                FXMLBaseDocumentController.getLabelCentralMessage().setText(
                                        "UNIT_BASEの登録に失敗しましたが、データベースレコードの削除はできませんでした。");
                            }
                            return; // 失敗したらそこで終了。
                        }
                        this.lockAllControls(true);
                        clearAllView();
                        textFieldId.clear();
                        textFieldId.setDisable(false);
                        textFieldId.requestFocus();
                    });
            registerUnitDTO = null;
        }
    }

    @FXML
    private void makeFromTemplate() {  // 細部の調整はまだ
        /*
        条件buttonRegisterChangeの押下
            IDが入力されており、CUT日を含みそれ以前で、
            かつCLOSEが本日を含みそれ以前であること
         */
        if (textFieldId.getText().isEmpty() == true) { //IDが空欄
            Alert alert = new Alert(Alert.AlertType.WARNING,
                    "IDが入力されていません。\n"
                    + "既存のレコードでなければ変更上書きはできません。");
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
                    "変更登録を開始します。\n"
            );
            Optional<ButtonType> showAndWait = alert.showAndWait();
            // blockRegisterButton();
            FXMLBaseDocumentController.getLabelCentralMessage().setText("変更登録の受付中。");
            // ここでプレビューと可否の入力を受け付け
            Alert alertRegisterNew = new Alert(Alert.AlertType.CONFIRMATION,
                    "変更登録：既存のユニットを変更します。"
                    + "ID:" + this.textFieldId.getText() // IDは表示されないはず
                    + "TITLE:" + this.textAreaTitle.getText());
            alertRegisterNew.showAndWait()
                    .filter(response -> response == ButtonType.OK)
                    .ifPresent(response -> {
                        registerUnitDTO // 登録予定のUnitDTO
                                = pushDTO(new UnitDTO(), UnitAim.MAKE_FROM_TEMPLATE);
                        System.out.println("call template");
                        if (UnitDAO.register(registerUnitDTO)) {
                            FXMLBaseDocumentController.getLabelCentralMessage().setText(
                                    "変更登録しました。");
                        } else {
                            registerUnitDTO = null;
                            FXMLBaseDocumentController.getLabelCentralMessage().setText(
                                    "データーベース登録に失敗しました。");
                            return; // 失敗したらそこで終了。
                        }
                        if (OperationTool.createUnitDir(registerUnitDTO)) {
                            FXMLBaseDocumentController.getLabelCentralMessage().setText(
                                    "UNIT_BASEに新規登録しました。");
                        } else {
                            FXMLBaseDocumentController.getLabelCentralMessage().setText(
                                    "UNIT_BASEの登録に失敗しました。");
                            // 変更以外はデータベースに遡って削除する。
                            if (UnitDAO.deleteById(registerUnitDTO.getId())) {
                                FXMLBaseDocumentController.getLabelCentralMessage().setText(
                                        "UNIT_BASEの登録に失敗したのでデータベースレコード削除しました。");
                            } else {
                                FXMLBaseDocumentController.getLabelCentralMessage().setText(
                                        "UNIT_BASEの登録に失敗しましたが、データベースレコードの削除はできませんでした。");
                            }
                            return; // 失敗したらそこで終了。
                        }
                        this.lockAllControls(true);
                        clearAllView();
                        textFieldId.clear();
                        textFieldId.setDisable(false);
                        textFieldId.requestFocus();
                    });
            registerUnitDTO = null;
        }
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
        this.buttonMakeAnotherVersion.setDisable(value);
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

    // ここからデータベースまわり
    private UnitDTO pushDTO(UnitDTO unitDTO, UnitAim unitAim) {
        /**
         * 画面表示からDTOに詰め込み用(上書きする) 内容の検査は各モードで共通のもの以外原則しない
         */
        switch (unitAim) {
            case REGISTER_NEW:
                unitDTO.setId(Instant.now().getEpochSecond());
                break;
            case REGISTER_CHANGE:
                unitDTO.setId(Long.parseLong(this.textFieldId.getText()));
                break;
            case MAKE_ANOTHER_VERSION:
                unitDTO.setId(Instant.now().getEpochSecond());
                unitDTO.setVersionId(Long.parseLong(this.textFieldId.getText()));
                System.out.println("version");
                break;
            case MAKE_FROM_TEMPLATE:
                unitDTO.setId(Instant.now().getEpochSecond());
                unitDTO.setTemplateId(Long.parseLong(this.textFieldId.getText()));
                System.out.println("template");
                break;
        }

        if (datePickerClose.getValue() != null) {
            unitDTO.setClose(Date.valueOf(datePickerClose.getValue()));
        }
        unitDTO.setMaintitleId(this.textFieldMainTitleId.getText());
        unitDTO.setTitle(this.textAreaTitle.getText());
        if (datePickerMtg.getValue() != null) {
            unitDTO.setMtg(Date.valueOf(this.datePickerMtg.getValue()));
        }
        if (datePickerCut.getValue() != null) {
            unitDTO.setCut(Date.valueOf(this.datePickerCut.getValue()));
        }
        if (datePickerEtd.getValue() != null) {
            unitDTO.setEtd(Date.valueOf(this.datePickerEtd.getValue()));
        }
        unitDTO.setRemark(this.textAreaRemark.getText());

        return unitDTO;
    }
}
