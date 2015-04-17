package jp.seraphyware.mdiapp1;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Optional;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class DocumentController extends AbstractWindowController {

	// リサイズ可能なキャンバス
	// https://dlemmermann.wordpress.com/2014/04/10/javafx-tip-1-resizable-canvas/
	// (このオーバーライドしたバージョンでないと、初回表示でdrawがうまく表示されない.)
	private static class ResizableCanvas extends Canvas {

		private Color color;

		public ResizableCanvas() {
			// Redraw canvas when size changes.
			widthProperty().addListener(evt -> draw());
			heightProperty().addListener(evt -> draw());
		}

		@Override
		public boolean isResizable() {
			return true;
		}

		@Override
		public double prefWidth(double height) {
			return getWidth();
		}

		@Override
		public double prefHeight(double width) {
			return getHeight();
		}

		private void draw() {
			GraphicsContext gc = getGraphicsContext2D();
			gc.clearRect(0, 0, getWidth(), getHeight());

			if (color == null) {
				double r, g, b;
				r = Math.random();
				g = Math.random();
				b = Math.random();
				color = new Color(r, g, b, 1.0);
			}

			gc.setFill(color);
			gc.fillRoundRect(10, 10, getWidth() - 20, getHeight() - 20, 10, 10);
		}
	}

	// 内包するキャンバスのサイズが常にフィットするようにするパネル
	// http://fxexperience.com/2014/05/resizable-grid-using-canvas/
	public static class DotGrid extends Pane {

		private Canvas canvas;

		public DotGrid(Canvas canvas) {
			this.canvas = canvas;
			getChildren().add(canvas);
		}

		@Override
		protected void layoutChildren() {
			final int top = (int) snappedTopInset();
			final int right = (int) snappedRightInset();
			final int bottom = (int) snappedBottomInset();
			final int left = (int) snappedLeftInset();

			final int w = (int) getWidth() - left - right;
			final int h = (int) getHeight() - top - bottom;

			canvas.setLayoutX(left);
			canvas.setLayoutY(top);

			if (w != canvas.getWidth()) {
				canvas.setWidth(w);
			}

			if (h != canvas.getHeight()) {
				canvas.setHeight(h);
			}
		}
	}

	private MenuController menuController;

	private ResizableCanvas canvas;

	private Image icon;

	@FXML
	private StackPane canvasContainer;

	@Override
	protected void makeRoot() {
		FXMLLoader ldr = new FXMLLoader();
		ldr.setLocation(getClass().getResource("Document.fxml"));
		ldr.setController(this);

		BorderPane root;
		try {
			root = ldr.load();

		} catch (IOException ex) {
			throw new UncheckedIOException(ex);
		}

		// メニューの取り付け
		menuController = new MenuController(this);
		root.setTop(menuController.getMenuBar());

		// Canvasの生成
		canvas = new ResizableCanvas();

		DotGrid pane = new DotGrid(canvas);

		canvasContainer.getChildren().add(pane);

		setRoot(root);
	}

	@Override
	public Stage getStage() {
		Stage stage = super.getStage();

		// アイコンのロード
		icon = new Image(getClass().getResourceAsStream("icon.png"));
		stage.getIcons().add(icon);

		return stage;
	}

	public void performClose() {
		Alert closeConfirmAlert = new Alert(AlertType.CONFIRMATION);
		closeConfirmAlert.initOwner(getStage());
		closeConfirmAlert.setHeaderText("閉じてよろしいですか？");
		Optional<ButtonType> result = closeConfirmAlert.showAndWait();
		if (result.get() == ButtonType.OK) {
			MDIApp1.getSingleton().closeDocument(this);
		}
	}

	@Override
	public void onCloseRequest(WindowEvent event) {
		event.consume();
		performClose();
	}
}
