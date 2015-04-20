package jp.seraphyware.mdiapp0;

import java.sql.Timestamp;
import java.util.List;
import java.util.Locale;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import jp.seraphyware.mdiapp0.Deprecation.AppNotificationHandler;

/**
 * Macでウィンドウを1つも作成せずデフォルトのスクリーンメニューとアプリケーションイベントを
 * ハンドリングする実験例.
 * (Macのみ動作可能.)
 */
public class MDIApp0Mac extends Application implements AppNotificationHandler {

    private static final String osName = System.getProperty("os.name").toLowerCase(Locale.ROOT);

    public static final boolean IS_MAC = osName.contains("mac");
    public static final boolean IS_LINUX = osName.contains("linux");
    public static final boolean IS_WINDOWS = osName.contains("windows");


    private MenuController defaultMenuController;

	@Override
	public void start(Stage primaryStage) throws Exception {

		if (IS_MAC) {
			// Macの場合は、すべてのウィンドウが閉じられても
			// 明示的にExitするまでアプリケーションを終了しない.
			Platform.setImplicitExit(false);

			// Macのデフォルトのスクリーンメニューを設定する.
			defaultMenuController = new MenuController(this);
			defaultMenuController.load();

			Deprecation.setDefaultSystemMenuBar(defaultMenuController.getMenuBar());

			// Macのアプリケーションイベントをハンドリングする
			Deprecation.setPlatformEventHandler(this);
		}

		// 新規メニュー
		newDocument();
	}

	public void newDocument() {
		MenuController menuController = new MenuController(this);
		menuController.load();

		Stage stage = new Stage();
		BorderPane pane = new BorderPane();
		pane.setTop(menuController.getMenuBar());

		Canvas canvas = new Canvas(300, 100);
		GraphicsContext gc = canvas.getGraphicsContext2D();
		gc.setFill(Color.AQUA);
		gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
		gc.strokeText(new Timestamp(System.currentTimeMillis()).toString(), 20, 20);

		pane.setCenter(canvas);
		stage.setScene(new Scene(pane));
		stage.sizeToScene();

		stage.show();
	}

	@Override
	public void handleOpenFilesAction(List<String> files) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setHeaderText("handleOpenFilesAction");
		alert.setContentText(files.toString());
		alert.show();
	}

	@Override
	public void handleQuitAction() {
		// 明示的な終了(Macの場合のみCommand+Qまたはアプリの終了メニューにより通知される)
		Platform.exit();
	}

	public static void main(String... args) {
		launch(args);
	}
}
