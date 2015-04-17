package jp.seraphyware.mdiapp1;

import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.stage.Stage;

public class MDIApp1 extends Application {

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

		// ウィンドウの作成
		onFileNew();
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
