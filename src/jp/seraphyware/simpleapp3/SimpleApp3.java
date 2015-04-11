package jp.seraphyware.simpleapp3;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import jp.seraphyware.utils.XMLResourceBundleControl;

/**
 * FXMLカスタムコンポーネントを使うアプリケーション作成例.
 * (リソースバンドル使用)
 *
 * @author seraphy
 */
public class SimpleApp3 extends Application implements Initializable {

	@FXML
	private DirTextField dirTextField;

	@FXML
	private TextField txtNamePattern;

	@FXML
	private CheckBox chkSubdir;

	@FXML
	private Button btnOK;

	@Override
	public void start(Stage primaryStage) throws Exception {
		// FXMLをリソースから取得する.
		// (タブオーダーもFXMLの定義順になる.)
		// (FXML中に「@参照」による相対位置指定がある場合は、このURL相対位置となる.)
		URL fxml = getClass().getResource(getClass().getSimpleName() + ".fxml");

		// XMLリソースバンドルを読み込む
		ResourceBundle rb = ResourceBundle.getBundle(getClass().getName(),
				new XMLResourceBundleControl());

		// FXMLをロードする.
		// (ローカライズするのでリソースバンドルも指定する)
		FXMLLoader ldr = new FXMLLoader(fxml, rb);

		// このインスタンス自身をコントローラとする.
		// @FXMLアノテーションによりFXMLと結び付けられる.
		ldr.setController(this);

		// FXMLをロードする.
		Parent root = ldr.load();

		// ステージのタイトル
		primaryStage.setTitle(rb.getString("title"));

		// ステージの表示
		primaryStage.setScene(new Scene(root));
		primaryStage.show();

		// ディレクトリ選択テキストにフォーカスを当てる
		dirTextField.requestFocus();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// OKボタンの活性制御
		btnOK.disableProperty().bind(
				Bindings.or(
						dirTextField.textProperty().isEmpty(),
						txtNamePattern.textProperty().isEmpty()
						));
	}

	@FXML
	protected void onOK(ActionEvent evt) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setHeaderText("実行！");

		StringBuilder buf = new StringBuilder();
		buf.append("dir=").append(dirTextField.getText()).append("\r\n")
				.append("namePattern=").append(txtNamePattern.getText())
				.append("\r\n").append("subdir=")
				.append(chkSubdir.isSelected() ? "Yes" : "No");
		alert.setContentText(buf.toString());

		alert.showAndWait();
	}

	@FXML
	protected void onCancel(ActionEvent evt) {
		// 現在ボタンを表示しているシーンを表示しているステージに対して
		// クローズを要求する.
		((Stage) ((Button) evt.getSource()).getScene().getWindow()).close();
	}

	/**
	 * エントリポイント
	 * @param args
	 * @throws Exception
	 */
	public static void main(String... args) throws Exception {
		launch(args);
	}
}
