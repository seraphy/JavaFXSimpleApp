package jp.seraphyware.mdiapp1;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javafx.application.Application;
import javafx.application.Platform;
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
	}

	@Override
	public void handleQuitAction() {
		if (windowList.isEmpty()) {
			// すべてのドキュメントが閉じられていればアプリケーションを終了する.
			Platform.exit();
		}
	}

	public void onFileNew() {
		DocumentController wnd = new DocumentController();
		wnd.openWindow();
		windowList.add(wnd);
	}

	public void onFileQuit() {
		for (DocumentController docCtrl : new ArrayList<>(windowList)) {
			docCtrl.performClose();
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
