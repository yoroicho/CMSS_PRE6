/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import DB.ConnectionShip;
import DB.DatabaseUty;
import DB.SampleConnectionClass;
import FileDirController.CreateUnderDir;
import common.SystemPropertiesAcc;
import common.SystemPropertiesItem;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author tokyo
 */
public class FXMLTabPageSystemConfigController implements Initializable {

    private boolean flgExsistShip;

    /**
     * ー設定変更タブについてー 各BASE等すべての設定をメモリへ読み出すのは起動時の一回のみ。 ウインドウのロード前にファイルからメモリにロード。
     * 画面を開いた時にはメモリの内容をウィンドウに表示させる。 変更は確定ボタンでメモリを介さず直接ファイルに書き込み。
     * タブ方式につきキャンセルボタンは実装しないがメモリの内容をウィンドウに復元 するボタンを実装している。
     * すでにファイルに書き込み済みの内容が何かの具合で目に触れることなく有効にならないように配慮。
     */
    @FXML
    private TextField textFieldDatabaseUrl;

    @FXML
    private TextField textFieldDatabaseUser;

    @FXML
    private PasswordField passwordFieldDatabasePass;

    private Label label;

    private TextField textField_UNIT_ID;

    private TextField textField_UNIT_SERVICE;

    private TextArea textArea_UNIT_NAME;

    private TextArea textArea_UNIT_REMARK;

    @FXML
    private TextField textFieldUnitBaseDir;

    private Label labelEnterCreateShipDir;

    public TextField testTextField;
    @FXML
    private Button UNIT_BASE_DIR;
    @FXML
    private Button ButtonEnterSystemProperties;
    @FXML
    private Button buttonReloadPropertiesOnMemory;

    @FXML
    private TextField textFieldUnitWorkspaceDir;
    @FXML
    private Button UNIT_WORKSPACE_DIR;

    private void testButtonAction(ActionEvent event) {
        testTextField.setText(String.valueOf(Integer.parseInt(testTextField.getText()) * 2));
    }

    private void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");
        label.setText(passwordFieldDatabasePass.getText());
        SampleConnectionClass.preInsertShip();

    }

    @FXML
    private void handleUnitBaseDirButtonAction(ActionEvent event) {
        final DirectoryChooser fc = new DirectoryChooser();
        fc.setTitle("SLECT UNIT BASE DIR");
        textFieldUnitBaseDir.setText(fc.showDialog(null).getPath());
    }

    @FXML
    private void handleUnitWorkspaceDirButtonAction(ActionEvent event) {
        final DirectoryChooser fc = new DirectoryChooser();
        fc.setTitle("SELECT UNIT WORKSPACE");
        textFieldUnitWorkspaceDir.setText(fc.showDialog(null).getPath());
    }

    @FXML
    private void handleButtonEnterSystemPropertiesButtonAction(ActionEvent event) {
        JOptionPane.showMessageDialog(null, "この設定が有効になるのはシステム再起動後です。");
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "厳重確認：この操作は重大な影響を永続的に及ばします。");
        System.out.println("Check" + (textFieldDatabaseUrl.getText() == null ? "nulled" : "conted"));
        alert.showAndWait()
                .filter(response -> response == ButtonType.OK)
                .ifPresent(response -> SystemPropertiesAcc.storeSystemProperties(
                textFieldDatabaseUrl.getText() == null ? "" : textFieldDatabaseUrl.getText().trim(),
                textFieldDatabaseUser.getText() == null ? "" : textFieldDatabaseUser.getText().trim(),
                passwordFieldDatabasePass.getText() == null ? "" : passwordFieldDatabasePass.getText(), // パスワードは前後の空白を除かない
                textFieldUnitBaseDir.getText() == null ? "" : textFieldUnitBaseDir.getText().trim(),
                "",
                "",
                ""));
    }

    private void handleButtonEnterCreateShipDirButtonAction(ActionEvent event) {
        ConnectionShip.replaceShip( // データベースを更新。
                Normalizer.normalize(textField_UNIT_ID.getText(), Normalizer.Form.NFKC), // 全角を限り無く半角に
                textField_UNIT_SERVICE.getText(),
                textArea_UNIT_NAME.getText(),
                textArea_UNIT_REMARK.getText()
        );
        if (flgExsistShip) {
            // ファイルに上書きを行う処理。フルパスをDBに登録する必要がある。
            // UUIDからの導出では増設HDDにBASEが切り替わった時に迷子になる。
            // 結局フォルダを作るのはUUIDではなく主キーの方がいいのかもしれない。
        } else {
            String createdDir = CreateUnderDir.makeUnderDirUUID("UNIT-", SystemPropertiesItem.UNIT_BASE);
            Date d = new Date();
            List<String> list = new ArrayList<>();
            list.add(createdDir);
            list.add("UNIT_ID:" + Normalizer.normalize(textField_UNIT_ID.getText(), Normalizer.Form.NFKC));// 全角を限り無く半角に
            list.add("UNIT_SERVICE:" + this.textField_UNIT_SERVICE.getText());
            list.add("UNIT_NAME:" + this.textArea_UNIT_NAME.getText());
            list.add("UNIT_REMARK" + this.textArea_UNIT_REMARK.getText());
            list.add("----------------------------------------");
            list.add(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(d));
            list.add(new SimpleDateFormat("yyyy年MM月dd日 HH時mm分ss秒").format(d));
            list.add("----------------------------------------");
            CreateUnderDir.makeFileUnderDirContAppend("log.txt", list, createdDir);
            labelEnterCreateShipDir.setText(createdDir);
            String createdSubDir = CreateUnderDir.makeUnderDirNamed("Documents", createdDir);
        }
    }

    @FXML // すべての内容を現在メモリに登録している状態にもどす。
    private void handleButtonReloadPropertiesOnMemory(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to reload Properties from this system's memory?");
        System.out.println("DB_URL: " + common.StaticSystemPropertiesAcc.DB_URL); // 取得に成功。
        alert.showAndWait()
                .filter(response -> response == ButtonType.OK)
                .ifPresent(response -> initializeSystemPropertiesWindow());
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //initFocuseConditionForTask(); // 編集不可にして主キーを保護。
        initializeSystemPropertiesWindow(); // システム設定に初期状態の表示をさせる。
    }

    private void initializeSystemPropertiesWindow() {
        // SystemPropertiesの表示部に情報を流し込み。
        textFieldDatabaseUrl.setText(SystemPropertiesItem.DB_URL);
        textFieldDatabaseUser.setText(SystemPropertiesItem.DB_USER);
        passwordFieldDatabasePass.setText(SystemPropertiesItem.DB_PASS);
        textFieldUnitBaseDir.setText(SystemPropertiesItem.UNIT_BASE);
    }

//    private void initFocuseConditionForTask() {
//        this.textField_UNIT_ID.focusedProperty().addListener(new ChangeListener<Boolean>() {
//            @Override
//            public void changed(ObservableValue<? extends Boolean> arg0,
//                    Boolean oldPropertyValue, Boolean newPropertyValue) {
//                if (newPropertyValue) {
//                    //textField_UNIT_ID.setEditable(true);
//                    System.out.println("Textfield on focus");
//                } else {
//                    //textField_UNIT_ID.setEditable(false);
//                    textField_UNIT_ID.setDisable(true); // 編集不可になっていることが明確。ただし文字は見にくい。
//                    System.out.println("Textfield out focus");
//                    ResultSet rs = DatabaseUty.getResultSetByKey("ship", "id", textField_UNIT_ID.getText().trim());
//                    try {
//                        rs.next();
//                        textField_UNIT_SERVICE.setText(rs.getString("SERVICE"));
//                        textArea_UNIT_NAME.setText(rs.getString("NAME"));
//                        textArea_UNIT_REMARK.setText(rs.getString("REMARK"));
//                        flgExsistShip = true;
//                    } catch (SQLException ex) {
//                        Logger.getLogger(FXMLTabPageSystemConfigController.class.getName()).log(Level.SEVERE, null, ex);
//                        flgExsistShip = false;
//                        JOptionPane.showMessageDialog(null, "新規もしくはエラーです");
//                    }
//
//                }
//            }
//        });
//    }
}
