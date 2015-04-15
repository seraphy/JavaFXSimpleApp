package jp.seraphyware.mdiapp;

import java.io.IOException;
import java.io.UncheckedIOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class MDIWndController {

	private MenuController menuController;

	@FXML
	private BorderPane root;

	@FXML
	private Canvas canvas;

	@FXML
	private Pane canvasContainer;

	public MDIWndController() {
		FXMLLoader ldr = new FXMLLoader();
		ldr.setLocation(getClass().getResource("MDIWnd.fxml"));
		ldr.setController(this);

		try {
			ldr.load();

		} catch (IOException ex) {
			throw new UncheckedIOException(ex);
		}

		canvas.widthProperty().bind(canvasContainer.widthProperty());
		canvas.heightProperty().bind(canvasContainer.heightProperty());

		root.heightProperty().addListener(l -> drawCanvas());
		root.widthProperty().addListener(l -> drawCanvas());

		// メニューの取り付け
		menuController = new MenuController();
		root.setTop(menuController.getMenuBar());
	}

	public void drawCanvas() {
		GraphicsContext gc = canvas.getGraphicsContext2D();
		int x = (int)(canvas.getWidth() / 2);
		int y = (int)(canvas.getHeight() / 2);
		gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
		gc.setFill(Color.CYAN);
		gc.fillRoundRect(x - 50, y - 50, 100, 100, 10, 10);
	}

	public Parent getRoot() {
		return root;
	}
}
