package jp.seraphyware.mdiapp1;

import java.util.Arrays;
import java.util.List;

import javafx.scene.control.MenuBar;

import com.sun.glass.ui.Application;
import com.sun.glass.ui.Application.EventHandler;
import com.sun.javafx.scene.control.skin.MenuBarSkin;

/**
 * 非公開クラスを間接利用させるための補助的なユーテリティクラス.
 */
@SuppressWarnings("restriction")
public final class Deprecation {

	/**
	 * Mac用のアプリケーションイベントハンドラ.
	 */
	public interface AppNotificationHandler {

		/**
		 * Dockからのファイル起動
		 *
		 * @param files
		 */
		void handleOpenFilesAction(List<String> files);

		/**
		 * Command+Qによるアプリ終了指示
		 */
		void handleQuitAction();
	}

	private Deprecation() {
		assert false;
	}

	/**
	 * デフォルトのスクリーンメニューを設定する.
	 *
	 * @param menuBar
	 */
	public static void setDefaultSystemMenuBar(MenuBar menuBar) {
		MenuBarSkin.setDefaultSystemMenuBar(menuBar);
	}

	/**
	 * Macのアプリケーションイベントをハンドリングできるようにする.
	 *
	 * @param handler
	 */
	public static void setPlatformEventHandler(AppNotificationHandler handler) {
		Application app = Application.GetApplication();
		app.setEventHandler(new EventHandler() {
			@Override
			public void handleOpenFilesAction(Application app, long time,
					String[] files) {
				handler.handleOpenFilesAction(Arrays.asList(files));
			}

			@Override
			public void handleQuitAction(Application app, long time) {
				handler.handleQuitAction();
			}
		});
	}
}
