package jp.seraphyware.mdiapp0;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Objects;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class MenuController {

	/**
	 * メニューバー
	 */
	@FXML
	private MenuBar menuBar;
	
	@FXML
	private Menu menuFile;
	
	/**
	 * 閉じるメニュー
	 */
	@FXML
	private MenuItem menuFileClose;

	/**
	 * コマンドターゲット
	 */
	private MDIApp0Mac app;

	public MenuController(MDIApp0Mac app) {
		Objects.nonNull(app);
		this.app = app;
	}

	public void load() {
		// FXMLのロード
		FXMLLoader ldr = new FXMLLoader();
		ldr.setLocation(getClass().getResource("Menu.fxml"));
		ldr.setController(this);
		try {
			ldr.load();
		} catch (IOException ex) {
			throw new UncheckedIOException(ex);
		}
		
		// menuFileCloseの活性制御
		// デフォルトメニューの場合はメニューバーがSceneに関連づけられていないので
		// クローズボタンを非活性とする.
		menuFile.setOnMenuValidation(evt -> {
			menuFileClose.setDisable(menuBar.getScene() == null);
		});
	}

	public MenuBar getMenuBar() {
		return menuBar;
	}

	@FXML
	public void onFileNew() {
		app.newDocument();
	}

	@FXML
	public void onFileClose(ActionEvent evt) {
		Scene scene = menuBar.getScene();
		Stage stage = scene != null ? (Stage) scene.getWindow() : null;
		if (stage != null) {
			stage.fireEvent(new WindowEvent(stage,
					WindowEvent.WINDOW_CLOSE_REQUEST));
		}
	}
}
