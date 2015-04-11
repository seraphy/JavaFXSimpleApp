package jp.seraphyware.simpleapp3;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.DirectoryChooser;
import jp.seraphyware.utils.ErrorDialogUtils;
import jp.seraphyware.utils.XMLResourceBundleControl;

public class DirTextField extends BorderPane {

	@FXML
	private TextField txtDir;

	/**
	 * ディレクトリ選択ダイアログ
	 */
	private DirectoryChooser dirChooser = new DirectoryChooser();

	/**
	 * コンストラクタ
	 */
	public DirTextField() {
		URL url = getClass().getResource(getClass().getSimpleName() + ".fxml");
		ResourceBundle rb = ResourceBundle.getBundle(getClass().getName(),
				new XMLResourceBundleControl());
		FXMLLoader ldr = new FXMLLoader(url, rb);

		// このインスタンス自身がルートオブジェクト
		ldr.setRoot(this);

		// このインスタンス自身がコントローラ
		ldr.setController(this);

		try {
			// ルートを指定済みなので、このインスタンスにFXMLがロードされる.
			ldr.load();

		} catch (IOException ex) {
			throw new UncheckedIOException(ex);
		}

		// ダイアログのタイトル設定
		dirChooser.setTitle(rb.getString("dirChooser.title"));

		// フォーカスリスナ
		focusedProperty().addListener((o, old, newval) -> {
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
