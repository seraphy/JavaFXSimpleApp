package jp.seraphyware.simpleapp4;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.DirectoryChooser;
import jp.seraphyware.utils.ErrorDialogUtils;

public class DirTextFieldController implements Initializable {

	@FXML
	private BorderPane root;

	@FXML
	private TextField txtDir;

	/**
	 * ディレクトリ選択ダイアログ
	 */
	private DirectoryChooser dirChooser = new DirectoryChooser();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// ダイアログのタイトル設定
		dirChooser.setTitle(resources.getString("dirChooser.title"));

		// フォーカスリスナ
		root.focusedProperty().addListener((o, old, newval) -> {
			if (newval) {
				txtDir.requestFocus();
			}
		});
	}

	@FXML
	protected void onBrowse(ActionEvent evt) {
		try {
			String srcDir = txtDir.textProperty().get();
			if (srcDir != null && !srcDir.isEmpty()) {
				File initDir = new File(srcDir);
				if (initDir.isDirectory()) {
					dirChooser.setInitialDirectory(initDir);
				}
			}
			File selectedDir = dirChooser.showDialog(((Button) evt.getSource())
					.getScene().getWindow());
			if (selectedDir != null) {
				txtDir.setText(selectedDir.getAbsolutePath());
				txtDir.requestFocus();
			}

		} catch (Exception ex) {
			ErrorDialogUtils.showException(ex);
		}
	}

	public StringProperty textProperty() {
		return txtDir.textProperty();
	}

	public String getText() {
		return textProperty().get();
	}

	public void setText(String text) {
		textProperty().set(text);
	}
}
