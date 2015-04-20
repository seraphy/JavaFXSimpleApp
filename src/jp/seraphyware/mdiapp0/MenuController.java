package jp.seraphyware.mdiapp0;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Objects;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class MenuController {

	/**
	 * メニューバー
	 */
	@FXML
	private MenuBar menuBar;

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
