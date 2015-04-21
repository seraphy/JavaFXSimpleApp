package jp.seraphyware.mdiapp1;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import jp.seraphyware.mdiapp1.Deprecation.AppNotificationHandler;

public class MDIApp1 extends Application implements AppNotificationHandler {

    private static final String osName = System.getProperty("os.name").toLowerCase(Locale.ROOT);

    public static final boolean IS_MAC = osName.contains("mac");
    public static final boolean IS_LINUX = osName.contains("linux");
    public static final boolean IS_WINDOWS = osName.contains("windows");


    private static MDIApp1 singletion;

	private List<DocumentController> windowList = new ArrayList<>();

	@Override
	public void init() throws Exception {
		singletion = this;
	}

	public static MDIApp1 getSingleton() {
		return singletion;
	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		if (IS_MAC) {
			// Macの場合は、すべてのウィンドウが閉じられても
			// 明示的にExitするまでアプリケーションを終了しない.
			Platform.setImplicitExit(false);

			// Macのデフォルトのスクリーンメニューを設定する.
			MenuController defaultMenuController = new MenuController(null);
			Deprecation.setDefaultSystemMenuBar(defaultMenuController.getMenuBar());

			// Macのアプリケーションイベントをハンドリングする
			Deprecation.setPlatformEventHandler(this);
		}

		// 初回ウィンドウの作成
		onFileNew();
	}

	@Override
	public void handleOpenFilesAction(List<String> files) {
		System.out.println("handleOpenFilesAction:" + files);
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setHeaderText("handleOpenFilesAction");
		alert.setContentText(files.toString());
		alert.showAndWait();
	}

	@Override
	public void handleQuitAction() {
		onFileQuit();
	}

	public void onFileNew() {
		DocumentController wnd = new DocumentController();
		wnd.openWindow();
		wnd.getStage().setTitle(new Timestamp(System.currentTimeMillis()).toString());
		windowList.add(wnd);
	}

	public void onFileQuit() {
		// 現在開いている、すべてのドキュメントに対してクローズを要求する.
		// (キャンセルされる可能性がある)
		for (DocumentController docCtrl : new ArrayList<>(windowList)) {
			if (!docCtrl.performClose()) {
				// キャンセルしたものがある場合は、ここで終了する
				return;
			}
		}
		if (windowList.isEmpty()) {
			// すべてのドキュメントが閉じられていればアプリケーションを終了する.
			System.out.println("exit.");
			Platform.exit();
		}
	}

	public void closeDocument(DocumentController docCtrl) {
		docCtrl.closeWindow();
		windowList.remove(docCtrl);
	}

	public static void main(String... args) {
		launch(args);
	}
}
