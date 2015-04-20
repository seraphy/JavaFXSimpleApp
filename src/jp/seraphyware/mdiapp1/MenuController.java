package jp.seraphyware.mdiapp1;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

public class MenuController implements Initializable {

	private DocumentController docCtrl;

	@FXML
	private MenuBar menuBar;

	@FXML
	private MenuItem menuQuit;

	public MenuController(DocumentController docCtrl) {
		this.docCtrl = docCtrl;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		if (MDIApp1.IS_MAC) {
			menuQuit.getParentMenu().getItems().remove(menuQuit);
		}
		
		for (Menu m : menuBar.getMenus()) {
			setupMenuItemHandlers(m);
		}
	}

	private void setupMenuItemHandlers(MenuItem i) {
		Object userData = i.getUserData();
		if (userData instanceof MenuHandler) {
			MenuHandler mh = (MenuHandler) userData;
			i.setOnAction(evt -> mh.perform(docCtrl));
		}

		if (i instanceof Menu) {
			final Menu m = (Menu) i;
			m.setOnMenuValidation(this::handleOnMenuValidation);

			for (MenuItem child : m.getItems()) {
				setupMenuItemHandlers(child);
			}
		}
	}

	private void handleOnMenuValidation(Event evt) {
		Menu menu = (Menu) evt.getSource();
		for (MenuItem i : menu.getItems()) {
			Object userData = i.getUserData();
			if (userData instanceof MenuHandler) {
				MenuHandler mh = (MenuHandler) userData;
				i.setDisable(!mh.canPerform(docCtrl));
			} else {
				i.setDisable(true);
			}
		}
	}

	public MenuBar getMenuBar() {
		if (menuBar == null) {
			FXMLLoader ldr = new FXMLLoader();
			ldr.setLocation(getClass().getResource("Menu.fxml"));
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
