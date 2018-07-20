/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import DB.UnitDAO;
import DB.UnitDTO;
import java.net.URL;
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
        if (textFieldId.getText().trim().length() == 0) { //空欄は新規入力扱い
            FXMLBaseDocumentController.getLabelCentralMessage().setText("新規入力");
            this.textFieldId.setEditable(false); // スキップ後は主キー欄をブロック
            this.textAreaTitle.setEditable(true); // タイトル入力欄を解放
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
                    textFieldMaintitleid.setText(s.getMaintitleid());
                    textAreaTitle.setText(s.getTitle());
                    datePickerMtg.setValue(s.getMtg().toLocalDate());
                    textAreaRemark.setText(s.getRemark());
                });
                FXMLBaseDocumentController.getLabelCentralMessage().setText("入力受付中。");
                this.textFieldId.setEditable(false); // スキップ後は主キー欄をブロック
                this.textAreaTitle.setEditable(true); // タイトル入力欄を解放
            }
        }
    }

    @FXML
    private void registerNew() { //新規にレコードを追加
        /*
        条件
            IDが空欄で、CUT日を含みそれ以前で、かつCLOSEに日が入っていないこと
         */
        if (textFieldId.getText().isEmpty()==false) { //IDが空欄ではない
            Alert alert = new Alert(Alert.AlertType.WARNING,
                     "IDが入力されています。\n"
                    + "既存のレコードを変更しての新規作成はできません。");
            Optional<ButtonType> showAndWait = alert.showAndWait();
        } else if (LocalDate.now().isAfter(datePickerCut.getValue())){
            Alert alert = new Alert(Alert.AlertType.WARNING,
                     "設定されているCUT日以降に登録はできません。\n"
                    );
            Optional<ButtonType> showAndWait = alert.showAndWait();
        }else if(this.datePickerClose.getValue().toString().isEmpty()==false){
            //CLOSE(閉鎖日が入力されている)
                        Alert alert = new Alert(Alert.AlertType.WARNING,
                     "CLOSE（閉鎖）が宣言された登録はできません。\n"
                    );
        }
    }

    private void registerChange() { //既存のレコードを変更
        /*
        条件
            IDが入力されており、CUT日を含みそれ以前で、かつCLOSEに日が入っていないこと
         */

    }

    @FXML
    private void initializeAllItems() {
        this.datePickerClose.getEditor().clear();
        this.textAreaTitle.setEditable(false); //エンターせずに入力してまたIDに戻って来れるのを防止
        this.textFieldId.clear();
        this.textFieldId.setEditable(true);
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        /*
         *ID欄はエンターを押さずにフォーカス遷移させると、後で戻って入力できてしまう。
         *タイトルは必須項目なのでとりあえずここをブロックしておき、ID欄でエンターが
         *押されたタイミングに解放する事でID検査を回避して入力が先行するのを回避する。
         *（本来はすべての入力項目に対してブロックを行った方がよい）
         */
        this.textAreaTitle.setEditable(false); // タイトル入力欄をブロック

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
