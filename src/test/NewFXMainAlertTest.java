/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author kyokuto
 */
public class NewFXMainAlertTest extends Application {

    @Override
    public void start(Stage primaryStage) {
        Button btn = new Button();
        btn.setText("Say 'Hello World'");
        btn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                System.out.println("Hello World!");
                Alert alert = new Alert(Alert.AlertType.NONE,
                        "    AlertクラスはDialogクラスのサブクラスであり、ユーザーにレスポンスを求める場合に簡単に表示できる事前作成済ダイアログ・タイプを数多くサポートしています。したがって、Alertクラスは、(Dialogを直接使用する場合と比べて、)多くのユーザーのニーズに最も適したクラスです。また、ユーザーにテキスト入力を求める場合はTextInputDialog、オプション・リストからの選択を求める場合はChoiceDialogを使用する方が適しています。\n"
                        + "    Alertインスタンスを作成する際、ユーザーはAlert.AlertType列挙値を渡す必要があります。この値を渡すことにより、Alertインスタンスによって自身が適切に構成されます(title、header、graphicなどの多くのDialogプロパティのデフォルト値と、特定のタイプのダイアログで必要なデフォルトのbuttonsが設定されます)。\n"
                        + "    Alertを(まだ表示しないが)インスタンス化する場合は、Alert alert = new Alert(AlertType.CONFIRMATION, \"Are you sure you want to format your system?\");のようなコードを使用します。\n"
                        + "    Alertがインスタンス化されたら、表示する必要があります。通常、アラート(およびダイアログ一般)はモーダルなブロッキング形式で表示されます。'モーダル'とは、ダイアログの表示中はユーザーが所有アプリケーションを操作できないことを意味し、'ブロッキング'とは、ダイアログが表示された時点でコード実行が停止されることを意味します。つまり、ダイアログを表示し、ユーザー・レスポンスを待機してから、showコールの直後のコードから実行を続行できるため、開発者はダイアログからのユーザー入力にすぐに対応することができます(関連する場合)。\n"
                        + "    JavaFXダイアログは、デフォルトでモーダルです(これは、Dialog.initModality(javafx.stage.Modality) APIを使用して変更できます)。ブロッキング・ダイアログまたは非ブロッキング・ダイアログを指定するには、開発者が(それぞれ) Dialog.showAndWait()またはDialog.show()を呼び出すことを選択します。このような状況でのコーディングを容易にするために、ほとんどの開発者がデフォルトでDialog.showAndWait()を選択する必要があります。次の3つのコード・スニペットは、前述のように指定されたAlertダイアログを表示するための方法を示したもので、3つとも等しく有効です。\n"
                        + "    オプション3: 完全なラムダ式アプローチ\n"
                        + "\n"
                        + "     alert.showAndWait()\n"
                        + "          .filter(response -> response == ButtonType.OK)\n"
                        + "          .ifPresent(response -> formatSystem());\n"
                        + "     \n"
                        + "\n"
                        + "    上の3つのオプションに優劣はないので、開発者は自分の好きなスタイルで作業できます。上のオプションを示した目的は、開発者にOptional APIを紹介することにあります。これはJava 8で新しく導入されたものであり、多くの開発者には馴染みがないと思われます。\n",
                        ButtonType.APPLY,
                        ButtonType.CANCEL,
                        ButtonType.CLOSE,
                        ButtonType.FINISH,
                        ButtonType.NEXT,
                        ButtonType.NO,
                        ButtonType.OK,
                        ButtonType.PREVIOUS,
                        ButtonType.YES);
                alert.showAndWait()
                        //.filter(response -> response == ButtonType.OK)
                        //.ifPresent(response -> System.out.println(response.getButtonData().name()));
                        .ifPresent(response -> {
                            switch (response.getButtonData().getTypeCode()) {
                                case "C":
                                    System.out.println("キャンセル");
                                    System.out.println(response.getButtonData().getTypeCode());
                                    break;
                                default:
                                    System.out.println(response.getButtonData().getTypeCode());
                            }
                        });
            }
        });

        StackPane root = new StackPane();
        root.getChildren().add(btn);

        Scene scene = new Scene(root, 300, 250);

        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
