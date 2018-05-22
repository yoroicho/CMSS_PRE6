/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import DB.DatabaseUty;
import DB.ProcessDAO;
import DB.ProcessDTO;
import FileDirController.FileIO;
import Slip.StructSheet;
import com.itextpdf.text.DocumentException;
import common.SystemPropertiesAcc;
import common.SystemPropertiesItem;
import common.TimestampUtil;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javax.swing.JOptionPane;
import test.IOLib;

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
    private TextArea textAreaDivSubName;
    @FXML
    private TextArea textAreaComment;
    @FXML
    private DatePicker //<editor-fold defaultstate="collapsed" desc="comment">
            datePickerETD //</editor-fold>
            ;

    //@FXML
    //private Button buttonClear;
    //@FXML
    //AnchorPane anchorPaneTabPageProcess;
    @FXML
    private FXMLBaseDocumentController FXMLBaseDocumentController;

    private State state;

    private long tempId;
    private long tempDivTime;

    private boolean isExistDivDateTimeChanging;

    private boolean isNewIdMode = false; // 親フォルダを作る必要があるモードの宣言

    private static final String FILE_SEPARATOR = System.getProperty("file.separator");

    protected void testCase(String txt) {
        System.out.println("代入指示" + txt);
    }

    private void setTempIdAndDivTimeByUniqueTime() {
        // idとDivDateTimeの番号が絶対に重ならないように設定する処理
        long dt;
        tempId = Instant.now().getEpochSecond();
        dt = Instant.now().getEpochSecond();
        if (tempId == dt) {
            tempDivTime = ++dt; // 二回idをスキャンしてもデータに行当たってしまうのを防止。
        } else {
            tempDivTime = dt;
        }
    }

    private enum State {
        // 下記は確定でない。
        NEW_CREATE, DIV_FORK, UPDATE_RECORD, PEEK, DELETE
    }

    private void clearAllProperty() {
        textAreaDivName.clear();
        textAreaComment.clear();
    }

    @FXML
    protected void clearIdAndDivDateTime() { // タブが切り替わる時にも外側からも呼ばれる。
        textFieldId.clear();
        comboBoxDivTime.getItems().clear();
        comboBoxDivTime.getEditor().clear();
        textFieldId.setDisable(false);
        comboBoxDivTime.setDisable(false);
    }

    private void syncroItem() {
        this.comboBoxDivTime.getEditor().textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> ov, String t, String t1) {
                // clearAllProperty();
                System.out.println("Change");
                isExistDivDateTimeChanging = false;
                comboBoxDivTime
                        .getItems()
                        .stream()
                        .filter(pri -> String.valueOf(pri.getDivtime()).equals(t1))
                        .findFirst()
                        .ifPresent(s -> {
                            textAreaDivName.setText(s.getDivname());
                            textAreaComment.setText(s.getComment());
                            datePickerETD.setValue(s.getEtd().toLocalDate());
                            isExistDivDateTimeChanging = true;
                        });
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
                            // 試験的に外側Masterにも表示
                            FXMLBaseDocumentController.getTextFieldBaseMasterId().setText(textFieldId.getText());
                            textFieldId.setDisable(true);
                            isExistDivDateTimeChanging = false; // DivDateTimeで選択されるまでは存在せず。
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

        this.comboBoxDivTime.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue) {
                if (newPropertyValue) {
                    System.out.println("DivDateTime on focus");
                } else {
                    System.out.println("DivDateTime out focus");
                    // 値が入っていてDBに無い場合はエラーで抜ける。
                    if (isExistDivDateTimeChanging) {
                        comboBoxDivTime.setDisable(true);
                    } else {
                        JOptionPane.showMessageDialog(null, "対象が見つかりません。"
                                + "内部階層での新規作成となります。");
                        comboBoxDivTime.getEditor().clear();
                        clearAllProperty(); // 改造ではなく新規なのでクリアとする。
                    }
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
    private void duplicateButtonAction(ActionEvent event) {
        // 公開後に複写して新規制作、もしくは雛形に基く新規作成。
        // id,divDateTimeともに引き継がない。
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "id= divDateTime= を引き継ぎ複製制作しますか。");
        alert.showAndWait()
                .filter(response -> response == ButtonType.OK)
                .ifPresent(response -> {
                    System.out.println("複製／株分処理中");
                    // 新規に親と子の番号をセット
                    setTempIdAndDivTimeByUniqueTime();
                    /*
                    // まず収納すべき親を作る。
                    FileIO.makeUnderDirNamed(
                            SystemPropertiesItem.SHIP_BASE,
                            String.valueOf(tempId)
                    );
                    // 次に新しく作った親に子をコピー。
                    String NewParentDirString // 新しい親のフルパス
                            = SystemPropertiesItem.SHIP_BASE
                            + FILE_SEPARATOR
                            + String.valueOf(tempId);
                    String OldChildDirString // 元の子のフルパス
                            = SystemPropertiesItem.SHIP_BASE
                            + FILE_SEPARATOR
                            + this.textFieldId.getText()
                            + FILE_SEPARATOR
                            + this.comboBoxDivTime.getEditor().getText();
                    System.out.println("oldChild"+ OldChildDirString);
                    System.out.println("NewParent"+ NewParentDirString);
                    IOLib.copy(
                            OldChildDirString,
                            NewParentDirString);
                    コピーとリネームが同時にできるのでこの機能を利用する。
                     */
                    String newParentDirString // 新しい親のフルパス
                            = SystemPropertiesItem.SHIP_BASE
                            + FILE_SEPARATOR
                            + String.valueOf(tempId);
                    String newChildDirString // 新しい子の名称変更前のフルパス
                            = newParentDirString 
                            +FILE_SEPARATOR
                            +String.valueOf(this.comboBoxDivTime.getEditor().getText());
                    String newChildDirNewNameDir // 新しい子の名称変更後のフルパス
                            =newParentDirString 
                            +FILE_SEPARATOR
                            +String.valueOf(tempDivTime);
                            
                    String oldChildDirString // 元の子のフルパス 使わないかも？
                            = SystemPropertiesItem.SHIP_BASE
                            + FILE_SEPARATOR
                            + this.textFieldId.getText()
                            + FILE_SEPARATOR
                            + this.comboBoxDivTime.getEditor().getText();
                    //System.out.println("newChild" + oldChildDirString);
                    System.out.println("NewParent" + newParentDirString);
                    IOLib.copy(
                            SystemPropertiesItem.SHIP_BASE
                            + FILE_SEPARATOR
                            + String.valueOf(this.textFieldId.getText()),
                            SystemPropertiesItem.SHIP_BASE
                            + FILE_SEPARATOR
                            + String.valueOf(tempId));

                    // コピー後に子のディレクトリの名前を変更
                    Path newChild = Paths.get(newChildDirString);
                    System.out.println("変更する予定の子フォルダ"+newChildDirString);
                    System.out.println("変更する予定の名前"+tempDivTime);
                    try {
                        Files.move(newChild, newChild.resolveSibling(String.valueOf(tempDivTime)));
                    } catch (IOException ex) {
                        Logger.getLogger(FXMLTabPageProcessController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    /*
                    新しい子の古いPDFファイルの名前の末尾に「-D(Duplicated)」を追加する
                    削除しないのはその時点での複製元情報を記録する為と事故防止。
                     */
                    // コピー後の子のSLIPファイルPDFの名前を変更
                    //String newChildrenDirString // 新しい子の名称変更後のフルパス 
                      //      = newParentDirString + FILE_SEPARATOR + tempDivTime;
                    String oldSlipString
                            = // 新しいフォルダの古いスリップPDFの名前
                            this.textFieldId.getText()
                            + "-"
                            + this.comboBoxDivTime.getEditor().getText()
                            + ".pdf";
                    Path oldSlipDir
                            = Paths.get(newChildDirNewNameDir+FILE_SEPARATOR+oldSlipString);
                    try {
                        Files.move(oldSlipDir,
                                oldSlipDir.resolveSibling(
                                        String.valueOf(oldSlipString + "-D")));

                        /*
                        新しい親子の中でコピーしないファイルを削除することも
                        検討したが、大容量の共通資料などは外だしされていることなどから
                        需要が乏しいのと、万一の事故の可能性があるので見合せる。
                         */
                    } catch (IOException ex) {
                        Logger.getLogger(FXMLTabPageProcessController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    // 新しくなった旨を加えて新しいPDFを作成

                    StructSheet structSheet = new StructSheet();
                    try {
                        structSheet.creatSlip( // ここはDTOを渡すよう改善するべき
                                this.textAreaDivName.getText(),
                                "cutDateTime",
                                "compData",
                                "makerName",
                                this.textAreaComment.getText(),
                                String.valueOf(this.tempId), // 実際にはDTOを変更してDTOを渡す
                                String.valueOf(this.tempDivTime), // 実際にDTOを変更してDTOを渡す
                                newParentDirString
                                + FILE_SEPARATOR
                                + newParentDirString
                                + FILE_SEPARATOR
                                + this.tempDivTime,
                                Boolean.FALSE);
                    } catch (IOException ex) {
                        Logger.getLogger(FXMLTabPageProcessController.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (DocumentException ex) {
                        Logger.getLogger(FXMLTabPageProcessController.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (RuntimeException ex) {
                        Logger.getLogger(FXMLTabPageProcessController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    JOptionPane.showMessageDialog(null, "登録／更新が完了しました");
                    if (Desktop.isDesktopSupported()) {
                        new Thread(() -> {
                            try {
                                System.out.println("tyr open file");
                                File file = new File(
                                        newParentDirString
                                        + FILE_SEPARATOR
                                        + this.tempDivTime
                                              + FILE_SEPARATOR
                                                +tempId
                                        + "-"
                                        + this.tempDivTime+".pdf");
                                Desktop.getDesktop().open(file);
                                System.out.println("opend file");
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }).start();
                    }
                });
        JOptionPane.showMessageDialog(null, "にて処理しました。");
    }

    @FXML
    private void retakeButtonAction(ActionEvent event) {
        // 未公開での複写してのやりなおし。全面改訂の場合は手動削除してから。
        // idを引き継ぐ。divDateTimeは新規取得する。
        // 自動的に他のdivDateTimeは閉鎖される。
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "id= divDateTime= を閉鎖して改造制作しますか。");
        alert.showAndWait()
                .filter(response -> response == ButtonType.OK)
                .ifPresent(response -> System.out.println("処理中"));
        JOptionPane.showMessageDialog(null, "にて処理しました。");

    }

    @FXML
    private void clearButtonAction(ActionEvent event) {
        clearIdAndDivDateTime();
    }

    @FXML
    private void enterButtonAction(ActionEvent event) {
        ProcessDTO processDTO = new ProcessDTO();
        if (textFieldId.getText().length() > 0) {
            processDTO.setId(Long.parseLong(textFieldId.getText()));
        } else {
            tempId = Instant.now().getEpochSecond();
            isNewIdMode = true; // 親フォルダから新規作成する必要がある事にする。
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
        processDTO.setComment(this.textAreaComment.getText());
        //SimpleDateFormat dateFormatETD = new SimpleDateFormat("yyyy/MM/dd");

        System.out.println(this.datePickerETD.getValue());
        // System.out.println(dateFormatETD.parse(datePickerETD.getEditor().getText()+" 00:00:00"));
        processDTO.setEtd(Date.valueOf(this.datePickerETD.getValue()));
//processDTO.setEtd(Timestamp.valueOf(this.datePickerETD.getValue().format(DateTimeFormatter.ofPattern("yyyy-mm-dd hh:mm:ss"))));

//processDTO.setETD(Timestamp.valueOf("2018-05-16 00:00:00"));
// ... ここでDTOにすべての要素を登録をする
        String parentDirString // 親のフルパス
                = SystemPropertiesItem.SHIP_BASE + FILE_SEPARATOR + String.valueOf(processDTO.getId());
        // 親がないことになっていれば親を作る。
        if (isNewIdMode) {
            FileIO.makeUnderDirNamed(
                    SystemPropertiesItem.SHIP_BASE,
                    String.valueOf(processDTO.getId())
            );
            isNewIdMode = false; // この段階でリセットすべきか疑問は残る。
        }
        // 親があるはずなので子を作る。
        FileIO.makeUnderDirNamed(parentDirString,
                String.valueOf(processDTO.getDivtime()));

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

                StructSheet structSheet = new StructSheet();
                structSheet.creatSlip( // ここはDTOを渡すよう改善するべき
                        this.textAreaDivName.getText(),
                        "cutDateTime",
                        "compData",
                        "makerName",
                        this.textAreaComment.getText(),
                        this.textFieldId.getText(),
                        this.comboBoxDivTime.getEditor().getText(),
                        parentDirString
                        + FILE_SEPARATOR
                        + String.valueOf(processDTO.getDivtime()),
                        Boolean.FALSE);
                JOptionPane.showMessageDialog(null, "登録／更新が完了しました");
                if (Desktop.isDesktopSupported()) {
                    new Thread(() -> {
                        try {
                            System.out.println("tyr open file");
                            File file = new File(
                                    parentDirString
                                    + FILE_SEPARATOR
                                    + String.valueOf(processDTO.getDivtime())
                                    + FILE_SEPARATOR
                                    + String.valueOf(processDTO.getId())
                                    + "-"
                                    + String.valueOf(processDTO.getDivtime()) + ".pdf");
                            Desktop.getDesktop().open(file);
                            System.out.println("opend file");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }).start();
                }

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
