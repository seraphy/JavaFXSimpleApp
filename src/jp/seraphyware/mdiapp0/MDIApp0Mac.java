package jp.seraphyware.mdiapp0;

import java.util.List;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.MenuBar;
import javafx.stage.Stage;
import jp.seraphyware.mdiapp0.Deprecation.AppNotificationHandler;

/**
 * Macでウィンドウを1つも作成せずデフォルトのスクリーンメニューとアプリケーションイベントを
 * ハンドリングする実験例.
 * (Macのみ動作可能.)
 */
public class MDIApp0Mac extends Application implements AppNotificationHandler {

	/**
	 * メニューバー
	 */
	@FXML
	private MenuBar menuBar;

	@Override
	public void start(Stage primaryStage) throws Exception {
		// FXMLのロード
		FXMLLoader ldr = new FXMLLoader();
		ldr.setLocation(getClass().getResource("Menu.fxml"));
		ldr.setController(this);
		ldr.load();

		// すべてのウィンドウが閉じられても
		// 明示的にExitするまでアプリケーションを終了しない.
		Platform.setImplicitExit(false);

		// Macのデフォルトのスクリーンメニューを設定する.
		System.out.println("menuBar=" + menuBar);
		Deprecation.setDefaultSystemMenuBar(menuBar);

		// Macのアプリケーションイベントをハンドリングする
		Deprecation.setPlatformEventHandler(this);
	}

	@FXML
	public void onShowMessage() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setHeaderText("Hello!");
		alert.show();
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
		// 明示的な終了
		System.out.println("quit!");
		Platform.exit();
	}

	public static void main(String... args) {
		launch(args);
	}
}
