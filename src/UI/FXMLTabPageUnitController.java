/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import DB.MainTitleDAO;
import DB.MainTitleDTO;
import com.sun.javafx.scene.control.behavior.*;

import DB.UnitDAO;
import DB.UnitDTO;
import FileDirController.OperationTool;
import Slip.StructSheet;
import Slip.StructUnitSlip;
import com.itextpdf.text.DocumentException;
import com.sun.javafx.runtime.SystemProperties;
import com.sun.javafx.scene.control.behavior.BehaviorBase;
import com.sun.javafx.scene.control.behavior.TextAreaBehavior;
import com.sun.javafx.scene.control.skin.TextAreaSkin;
import common.SystemPropertiesAcc;
import common.SystemPropertiesItem;
import common.TimestampUtil;
import java.awt.Desktop;
import java.awt.event.KeyAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.SkinBase;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.InputEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Region;
import javax.swing.JOptionPane;
import javax.xml.stream.EventFilter;
import javax.xml.stream.events.XMLEvent;

import com.sun.javafx.scene.control.behavior.*;
import com.sun.javafx.scene.control.skin.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import static javafx.scene.input.KeyCode.T;

import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import static sun.misc.Signal.handle;
import test.FXMLTestInvoke;

/**
 * FXML Controller class
 *
 * @author kyokuto
 */
public class FXMLTabPageUnitController implements Initializable {

    /**
     * @return the overallSeriesId
     */
    public String getOverallSeriesId() {
        return overallSeriesId;
    }

    /**
     * @return the overallSeriesName
     */
    public String getOverallSeriesName() {
        return overallSeriesName;
    }

    /**
     * @return the seriesId
     */
    public String getSeriesId() {
        return seriesId;
    }

    /**
     * @return the seriesName
     */
    public String getSeriesName() {
        return seriesName;
    }

    /**
     * @return the mainTitleId
     */
    public String getMainTitleId() {
        return mainTitleId;
    }

    /**
     * @return the mainTitleName
     */
    public String getMainTitleName() {
        return mainTitleName;
    }

    /**
     * @param overallSeriesId the overallSeriesId to set
     */
    public void setOverallSeriesId(String overallSeriesId) {
        this.overallSeriesId = overallSeriesId;
    }

    /**
     * @param overallSeriesName the overallSeriesName to set
     */
    public void setOverallSeriesName(String overallSeriesName) {
        this.overallSeriesName = overallSeriesName;
    }

    /**
     * @param seriesId the seriesId to set
     */
    public void setSeriesId(String seriesId) {
        this.seriesId = seriesId;
    }

    /**
     * @param seriesName the seriesName to set
     */
    public void setSeriesName(String seriesName) {
        this.seriesName = seriesName;
    }

    /**
     * @param mainTitleId the mainTitleId to set
     */
    public void setMainTitleId(String mainTitleId) {
        this.mainTitleId = mainTitleId;
    }

    /**
     * @param mainTitleName the mainTitleName to set
     */
    public void setMainTitleName(String mainTitleName) {
        this.mainTitleName = mainTitleName;
    }

    private static final String FILE_SEPARATOR = System.getProperty("file.separator");
    private static final String UNIT_BASE = SystemPropertiesItem.UNIT_BASE;

    // データ受け渡し用の構造体
    private String overallSeriesId;
    private String overallSeriesName;
    private String seriesId;
    private String seriesName;
    private String mainTitleId;
    private String mainTitleName;

    @FXML
    private ScrollPane scrollPaneUnit;

    @FXML
    private VBox vBoxLayout;

    @FXML
    private AnchorPane unitAnchorPane;

    @FXML
    private FXMLBaseDocumentController FXMLBaseDocumentController;

    @FXML
    private TextField textFieldId;

    @FXML
    private Button buttonClear;

    @FXML
    private DatePicker datePickerClose;

    @FXML
    private TextField textFieldMainTitleId;

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
    private TextArea textAreaTitle;
    //private final TextArea textAreaTitle = new TabAndEnterIgnoringTextArea();

    @FXML
    private TextArea textAreaCreator;
    //private final TextArea textAreaCreator = new TabAndEnterIgnoringTextArea();

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
    private FXMLTestInvoke FXMLTestInvoke;

    String ls = SystemProperties.getProperty("line.separator");

    private enum UnitAim {
        // PEEK,DELETEは確定でない。
        MAKE_FROM_TEMPLATE, MAKE_ANOTHER_VERSION, REGISTER_CHANGE, REGISTER_NEW, PEEK, DELETE
    }

    // 馬鹿馬鹿しいのでちゃんとやる
    private List<UnitDTO> unitDTO; // そもそも1件しかありえないのでListにする必要はない（コピペ元用）
    // UnitDTO unitDTO;

    private UnitDTO registerUnitDTO; // 登録予定のUnitDTO

    private List<MainTitleDTO> mainTitleDTO; //ひとつ上の階層情報

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
                    textAreaCreator.setText(s.getCreator());
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

    private void mainTitleIdChangeListener() {
        textFieldMainTitleId.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(
                    ObservableValue<? extends String> observable, String oldValue, String newValue) {
                System.out.println("TextField mainTitleId Changed (newValue: " + newValue + ")");
                pickMainTitleOuterData(textFieldMainTitleId.getText()); //tlim()?
            }
        ;
    }

    );
    }
/*
    private class MainTitleOuterData {
        // 構造体は外側のクラスに持たせることにした。
        // UnitDTO unitDTO, String overallSeriesId, String overallSeriesName, String seriesId, String seriesName, String mainTitleName
    }
*/
    private void pickMainTitleOuterData(String id) {
        //登録直前に無効なIDが入力されていないかチェックするために直接呼ぶ場合は別メソッドにする。

        mainTitleDTO = MainTitleDAO.findById(id);

        if (mainTitleDTO.size() > 1) {
            FXMLBaseDocumentController.getLabelCentralMessage().setText("????? DB cmss.unit.id ??Key??");
        } else if (mainTitleDTO.isEmpty()) {
            FXMLBaseDocumentController.getLabelCentralMessage().setText("???????????");
        } else if (mainTitleDTO.size() == 1) {
            FXMLBaseDocumentController.getLabelCentralMessage().setText("??????????");
            mainTitleDTO.forEach(s -> {
                // ??????????????????????
                System.out.println("MAIN TITLE ID " + String.valueOf(s.getId()));
//this.textFieldId.setText(String.valueOf(s.getId())); 
                textFieldMainTitleId.setText(s.getId());
                textAreaMainTitleName.setText(s.getMaintitle());

            });
            FXMLBaseDocumentController.getLabelCentralMessage().setText("Looked up mainttle.");

        }
        //return fXMLTabPageUnitController;
    }

    @FXML

    private void buttonClearOnAction(ActionEvent event
    ) {
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
        } else if (textAreaTitle.getText() == null
                || textAreaTitle.getText().trim().length() == 0) {
            //タイトルが空白文字だけかゼロ文字
            Alert alert = new Alert(Alert.AlertType.WARNING,
                    "タイトル名が不正です。\n"
            );
            Optional<ButtonType> showAndWait = alert.showAndWait();
        } else if (textAreaCreator.getText() == null
                || textAreaCreator.getText().trim().length() == 0) {
            //製作者が空白文字だけかゼロ文字
            Alert alert = new Alert(Alert.AlertType.WARNING,
                    "製作者名が不正です。\n"
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
            registerUnitDTO // 登録予定のUnitDTO
                    = pushDTO(new UnitDTO(), UnitAim.REGISTER_NEW);
            Alert alertRegisterNew = new Alert(Alert.AlertType.CONFIRMATION,
                    "新規登録：新しいユニットを作成します。"
                    + registerUnitDTO.toString());
            // + "ID:" + this.textFieldId.getText() // IDは表示されないはず
            //+ "TITLE:" + this.textAreaTitle.getText());
            alertRegisterNew.showAndWait()
                    .filter(response -> response == ButtonType.OK)
                    .ifPresent((ButtonType response) -> {
                        unitProcess();
                        /*  
                        // registerUnitDTO // 登録予定のUnitDTO
                        //       = pushDTO(new UnitDTO(), UnitAim.REGISTER_NEW);
                        if (UnitDAO.register(registerUnitDTO)) {
                            FXMLBaseDocumentController.getLabelCentralMessage().setText(
                                    "データベースに新規登録しました。"
                            );
                            System.out.println(registerUnitDTO.toString());
                        } else {
                            registerUnitDTO = null;
                            FXMLBaseDocumentController.getLabelCentralMessage().setText(
                                    "データーベース登録に失敗しました。");
                            return; // 失敗したらそこで終了。
                        }
                        if (OperationTool.createUnitDir(registerUnitDTO)) {
                            FXMLBaseDocumentController.getLabelCentralMessage().setText(
                                    "UNIT_BASEに新規登録しました。");
                            try {
                                StructUnitSlip.creatSlip( // 伝票の作成
                                        registerUnitDTO,
                                        this.textFieldOverallSeriesId.getText(),
                                        this.textAreaOverallSeriesName.getText(),
                                        this.textFieldSeriesId.getText(),
                                        this.textAreaSeriesName.getText(),
                                        this.textAreaMainTitleName.getText()
                                );
                                // 伝票の出力
                                JOptionPane.showMessageDialog(null, "伝票印刷します。");
                                StructUnitSlip.printSlip(registerUnitDTO.getId(), registerUnitDTO.getTimestamp());
                            } catch (IOException | DocumentException | RuntimeException ex) {
                                Logger.getLogger(FXMLTabPageUnitController.class.getName()).log(Level.SEVERE, null, ex);
                            }
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
                         */
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
        } else if (textAreaTitle.getText() == null
                || textAreaTitle.getText().trim().length() == 0) {
            //タイトルが空白文字だけかゼロ文字
            Alert alert = new Alert(Alert.AlertType.WARNING,
                    "タイトル名が不正です。\n"
            );
            Optional<ButtonType> showAndWait = alert.showAndWait();
        } else if (textAreaCreator.getText() == null
                || textAreaCreator.getText().trim().length() == 0) {
            //製作者が空白文字だけかゼロ文字
            Alert alert = new Alert(Alert.AlertType.WARNING,
                    "製作者名が不正です。\n"
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
            registerUnitDTO // UnitDTO
                    = pushDTO(new UnitDTO(), UnitAim.REGISTER_CHANGE);
            Alert alertRegisterNew = new Alert(Alert.AlertType.CONFIRMATION,
                    "変更" + ls
                    + "ID: "
                    + String.valueOf(unitDTO.get(0).getId()
                            + " から "
                            + String.valueOf(registerUnitDTO.getId())
                            + ls
                            + "TITLE: "
                            + ls
                            + String.valueOf(unitDTO.get(0).getTitle()
                                    + ls
                                    + " から "
                                    + ls
                                    + String.valueOf(registerUnitDTO.getTitle()))));
            // 大きさの制限をとりはらう(setMinSizeでも横幅は広がらなかった)
            alertRegisterNew.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
            // .setMinSize(Region.USE_PREF_SIZE,Region.USE_PREF_SIZE); 
            alertRegisterNew.showAndWait()
                    .filter(response -> response == ButtonType.OK)
                    .ifPresent(response -> {
                        this.unitProcess();
                        /*
                        //registerUnitDTO // 登録予定のUnitDTO
                        //      = pushDTO(new UnitDTO(), UnitAim.REGISTER_CHANGE);
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
                            try {
                                StructUnitSlip.creatSlip( // pdf???????
                                        registerUnitDTO,
                                        this.textFieldOverallSeriesId.getText(),
                                        this.textAreaOverallSeriesName.getText(),
                                        this.textFieldSeriesId.getText(),
                                        this.textAreaSeriesName.getText(),
                                        this.textAreaMainTitleName.getText()
                                );
                            } catch (IOException | DocumentException | RuntimeException ex) {
                                Logger.getLogger(FXMLTabPageUnitController.class.getName()).log(Level.SEVERE, null, ex);
                            }
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
                         */
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
        } else if (textAreaTitle.getText() == null
                || textAreaTitle.getText().trim().length() == 0) {
            //タイトルが空白文字だけかゼロ文字
            Alert alert = new Alert(Alert.AlertType.WARNING,
                    "タイトル名が不正です。\n"
            );
            Optional<ButtonType> showAndWait = alert.showAndWait();
        } else if (textAreaCreator.getText() == null
                || textAreaCreator.getText().trim().length() == 0) {
            //製作者が空白文字だけかゼロ文字
            Alert alert = new Alert(Alert.AlertType.WARNING,
                    "製作者名が不正です。\n"
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
            registerUnitDTO // ?????UnitDTO
                    = pushDTO(new UnitDTO(), UnitAim.MAKE_ANOTHER_VERSION);
            System.out.println("call version");
            Alert alertRegisterNew = new Alert(Alert.AlertType.CONFIRMATION,
                    "別版新規：既存のユニットの改変を作成します。"
                    + "ID:" + this.textFieldId.getText()
                    + "TITLE:" + this.textAreaTitle.getText());
            alertRegisterNew.showAndWait()
                    .filter(response -> response == ButtonType.OK)
                    .ifPresent(response -> {
                        this.unitProcess();
                        /*
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
                            return; // DBは失敗したらそこで終了。
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
                         */
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
        } else if (textAreaTitle.getText() == null
                || textAreaTitle.getText().trim().length() == 0) {
            //タイトルが空白文字だけかゼロ文字
            Alert alert = new Alert(Alert.AlertType.WARNING,
                    "タイトル名が不正です。\n"
            );
            Optional<ButtonType> showAndWait = alert.showAndWait();
        } else if (textAreaCreator.getText() == null
                || textAreaCreator.getText().trim().length() == 0) {
            //製作者が空白文字だけかゼロ文字
            Alert alert = new Alert(Alert.AlertType.WARNING,
                    "製作者名が不正です。\n"
            );
            Optional<ButtonType> showAndWait = alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING,
                    "変更登録を開始します。\n"
            );
            Optional<ButtonType> showAndWait = alert.showAndWait();
            // blockRegisterButton();
            FXMLBaseDocumentController.getLabelCentralMessage().setText(
                    "テンプレート複写の受付中。");
            // ここでプレビューと可否の入力を受け付け
            registerUnitDTO = pushDTO(new UnitDTO(), UnitAim.MAKE_FROM_TEMPLATE);
            Alert alertRegisterNew = new Alert(Alert.AlertType.CONFIRMATION,
                    "テンプレート複写登録：既存のユニットからテンプレート複写します。"
                    + "ID:" + this.textFieldId.getText() // IDは表示されないはず
                    + "TITLE:" + this.textAreaTitle.getText());
            alertRegisterNew.showAndWait()
                    .filter(response -> response == ButtonType.OK)
                    .ifPresent(response -> {
                        this.unitProcess();
                        /*
                        registerUnitDTO // 登録予定のUnitDTO
                                = pushDTO(new UnitDTO(), UnitAim.MAKE_FROM_TEMPLATE);
                        System.out.println("call template");
                        if (UnitDAO.register(registerUnitDTO)) {
                            FXMLBaseDocumentController.getLabelCentralMessage().setText(
                                    "複写登録しました。");
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
                            return; // 失敗したらDB削除して終了。
                            // pdfの作成を行う

                        }
                        this.lockAllControls(true);
                        clearAllView();
                        textFieldId.clear();
                        textFieldId.setDisable(false);
                        textFieldId.requestFocus();
                         */
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
        this.textAreaCreator.setDisable(value);
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
        this.textAreaCreator.setText(null);
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
        putTabOrderTextArea(textAreaTitle);
        putTabOrderTextArea(textAreaCreator);
        putTabOrderTextArea(textAreaRemark);
        mainTitleIdChangeListener();
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
        unitDTO.setCreator(this.textAreaCreator.getText());
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
        unitDTO.setTimestamp(TimestampUtil.current());
//String TIME_FORMAT = "yyyy/MM/dd HH:mm:ss";
//System.out.println(TimestampUtil.formattedTimestamp(TimestampUtil.current(), TIME_FORMAT));
        return unitDTO;
    }

    // IF JAVA 9 LOOK AT -> https://code.i-harness.com/ja/q/c43c3e
    private void putTabOrderTextArea(TextArea textArea) {
        textArea.addEventFilter(KeyEvent.KEY_PRESSED, (KeyEvent event) -> {
            if (event.getCode() == KeyCode.TAB) {
                TextAreaSkin skin = (TextAreaSkin) textArea.getSkin();
                BehaviorBase<?> bb = skin.getBehavior();
                if (bb instanceof TextAreaBehavior) {
                    TextAreaBehavior behavior = (TextAreaBehavior) skin.getBehavior();
                    if (event.isControlDown()) {

                        System.out.println("CTNR PUSH");
                        // behavior.callAction("InsertTab"); 作動しない
                        textArea.replaceSelection("\t");
                    } else if (event.isShiftDown()) {
                        behavior.traversePrevious(); // java9 available?
                        //behavior.callAction("TraversePrevious"); 
                    } else {
                        behavior.traverseNext(); // java9 available?
                        //behavior.callAction("TraverseNext");
                    }
                    event.consume();
                } else {
                }
            } else {
            }
        });
    }

    private void unitProcess() {

        // registerUnitDTO // 登録予定のUnitDTO
        //       = pushDTO(new UnitDTO(), UnitAim.REGISTER_NEW);
        if (UnitDAO.register(registerUnitDTO)) {
            FXMLBaseDocumentController.getLabelCentralMessage().setText(
                    "データベースに新規登録しました。"
            );
            System.out.println(registerUnitDTO.toString());
        } else {
            registerUnitDTO = null;
            FXMLBaseDocumentController.getLabelCentralMessage().setText(
                    "データーベース登録に失敗しました。");
            return; // 失敗したらそこで終了。
        }
        if (OperationTool.createUnitDir(registerUnitDTO)) {
            FXMLBaseDocumentController.getLabelCentralMessage().setText(
                    "UNIT_BASEに新規登録しました。");
            try {
                StructUnitSlip.creatSlip( // 伝票の作成
                        // 構造体に詰めて渡そうとしたが変数とテキストエリアをバインドさせないと意味がなかったかも？
                        registerUnitDTO,
                        this.textFieldOverallSeriesId.getText(),
                        this.textAreaOverallSeriesName.getText(),
                        this.textFieldSeriesId.getText(),
                        this.textAreaSeriesName.getText(),
                        this.textAreaMainTitleName.getText()
                );
                // 伝票の出力
                JOptionPane.showMessageDialog(null, "伝票印刷します。");
                StructUnitSlip.printSlip(registerUnitDTO.getId(), registerUnitDTO.getTimestamp());
            } catch (IOException | DocumentException | RuntimeException ex) {
                Logger.getLogger(FXMLTabPageUnitController.class.getName()).log(Level.SEVERE, null, ex);
            }
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

    }

    @FXML
    private void testInvoke(ActionEvent event) {

        try {
            //FXMLTestInvoke.invokeWindowOpen();
            /*
            try {
            Parent root = FXMLLoader.load(getClass().getResource("FXMLTestInvoke.fxml"));
            } catch (IOException ex) {
            Logger.getLogger(FXMLTabPageUnitController.class.getName()).log(Level.SEVERE, null, ex);
            }
            Stage newStage = new Stage();
            // \u30e2\u30fc\u30c0\u30eb\u30a6\u30a4\u30f3\u30c9\u30a6\u306b\u8a2d\u5b9a
            newStage.initModality(Modality.APPLICATION_MODAL);
            // \u30aa\u30fc\u30ca\u30fc\u3092\u8a2d\u5b9a
            newStage.initOwner(this.textFieldId.getScene().getWindow());
            
            // FXMLTestInvoke FXMLTestInvoke =  new FXMLTestInvoke();
            // FXMLTestInvoke.invokeWindowOpen();
            */
            // FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLTestInvoke.fxml"));
            //BorderPane root = (BorderPane) loader.load();
            Parent root = FXMLLoader.load(getClass().getResource("/test/FXMLTestInvoke.fxml"));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene); 
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        } catch (IOException ex) {
            Logger.getLogger(FXMLTabPageUnitController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
