package jp.seraphyware.mdiapp;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

public class MenuController implements Initializable {

	@FXML
	private MenuBar menuBar;


	@FXML
	private Menu menuFile;

	@FXML
	private Menu menuEdit;

	@FXML
	private Menu menuHelp;


	@FXML
	private MenuItem menuNew;

	@FXML
	private MenuItem menuSave;

	@FXML
	private MenuItem menuSaveAs;

	@FXML
	private MenuItem menuClose;


	@FXML
	private MenuItem menuDelete;

	@FXML
	private MenuItem menuAbout;


	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}

	public MenuBar getMenuBar() {
		if (menuBar == null) {
			FXMLLoader ldr = new FXMLLoader();
			ldr.setLocation(getClass().getResource("menu.fxml"));
			ldr.setController(this);
			try {
				ldr.load();

			} catch (IOException ex) {
				throw new UncheckedIOException(ex);
			}
		}
		return menuBar;
	}
}
