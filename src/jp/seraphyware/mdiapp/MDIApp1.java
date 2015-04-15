package jp.seraphyware.mdiapp;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MDIApp1 extends Application {


	@Override
	public void start(Stage primaryStage) throws Exception {

		// ウィンドウの作成
		MDIWndController wnd = new MDIWndController();

		// 表示
		primaryStage.setScene(new Scene(wnd.getRoot()));
		primaryStage.show();

		wnd.drawCanvas();
	}

	public static void main(String... args) {
		launch(args);
	}
}
