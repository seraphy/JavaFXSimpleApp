package jp.seraphyware.simpleapp6;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

import com.sun.glass.ui.Application;
import com.sun.glass.ui.Application.EventHandler;
import com.sun.glass.ui.Menu;
import com.sun.glass.ui.MenuBar;

@SuppressWarnings({ "restriction" })
public class Deprecation {

	/**
	 * ロガー
	 */
	private static final Logger logger = Logger.getLogger(Deprecation.class
			.getName());

	private Deprecation() {
		assert false;
	}

	public interface AppNotificationHandler {
		public void handleLaunch(List<String> files);

		public void handleOpenFilesAction(List<String> files);

		public void handleQuitAction();
	}

	public static class MacEventHandler extends
			com.sun.glass.ui.Application.EventHandler {

		private final AppNotificationHandler notificationHandler;

		private final com.sun.glass.ui.Application.EventHandler oldEventHandler;

		public MacEventHandler(AppNotificationHandler notificationHandler,
				com.sun.glass.ui.Application.EventHandler oldEventHandler) {
			Objects.requireNonNull(notificationHandler);
			this.notificationHandler = notificationHandler;
			this.oldEventHandler = oldEventHandler;
		}

		/**
		 * アプリケーションが起動し終わる直前に呼ばれます<br>
		 * 参考:
		 * https://developer.apple.com/library/mac/documentation/Cocoa/Reference/NSApplicationDelegate_Protocol/
		 */
		@Override
		public void handleWillFinishLaunchingAction(Application app, long time) {
			logger.info(() -> {
				return "app:" + app + " /time: " + time;
			});
			if (oldEventHandler != null) {
				oldEventHandler.handleWillFinishLaunchingAction(app, time);
			}
		}

		/**
		 * アプリケーションがアクティブになろうとする前に呼ばれます
		 */
		@Override
		public void handleWillBecomeActiveAction(Application app, long time) {
			logger.info(() -> {
				return "app:" + app + " /time: " + time;
			});
			if (oldEventHandler != null) {
				oldEventHandler.handleWillBecomeActiveAction(app, time);
			}
		}

		/**
		 * アプリケーションがアクティブでなくなる直前に呼ばれます
		 */
		@Override
		public void handleWillResignActiveAction(Application app, long time) {
			logger.info(() -> {
				return "app:" + app + " /time: " + time;
			});
			if (oldEventHandler != null) {
				oldEventHandler.handleWillResignActiveAction(app, time);
			}
		}

		/**
		 * アプリケーションが使用されなくなった（隠されたり終了したり）直後呼び出されます
		 */
		@Override
		public void handleDidResignActiveAction(Application app, long time) {
			logger.info(() -> {
				return "app:" + app + " /time: " + time;
			});
			if (oldEventHandler != null) {
				oldEventHandler.handleDidResignActiveAction(app, time);
			}
		}

		/**
		 * フリーのメモリ残量が少なくなってきた時に呼び出されます。
		 */
		@Override
		public void handleDidReceiveMemoryWarning(Application app, long time) {
			logger.info(() -> {
				return "app:" + app + " /time: " + time;
			});
			if (oldEventHandler != null) {
				oldEventHandler.handleDidReceiveMemoryWarning(app, time);
			}
		}

		/**
		 * アプリケーションが隠れようとする直前に呼び出されます
		 */
		@Override
		public void handleWillHideAction(Application app, long time) {
			logger.info(() -> {
				return "app:" + app + " /time: " + time;
			});
			if (oldEventHandler != null) {
				oldEventHandler.handleWillHideAction(app, time);
			}
		}

		/**
		 * アプリケーションが隠された直後呼ばれます
		 */
		@Override
		public void handleDidHideAction(Application app, long time) {
			logger.info(() -> {
				return "app:" + app + " /time: " + time;
			});
			if (oldEventHandler != null) {
				oldEventHandler.handleDidHideAction(app, time);
			}
		}

		/**
		 * アプリケーションが隠されなくなる直前に呼び出されます
		 */
		@Override
		public void handleWillUnhideAction(Application app, long time) {
			logger.info(() -> {
				return "app:" + app + " /time: " + time;
			});
			if (oldEventHandler != null) {
				oldEventHandler.handleWillUnhideAction(app, time);
			}
		}

		/**
		 * アプリケーションが隠されなくなった直後に、呼び出されます
		 */
		@Override
		public void handleDidUnhideAction(Application app, long time) {
			logger.info(() -> {
				return "app:" + app + " /time: " + time;
			});
			if (oldEventHandler != null) {
				oldEventHandler.handleDidUnhideAction(app, time);
			}
		}

		/**
		 * テーマが変更された場合(？)
		 */
		@Override
		public boolean handleThemeChanged(String themeName) {
			logger.info(() -> {
				return "themeName=" + themeName;
			});
			if (oldEventHandler != null) {
				return oldEventHandler.handleThemeChanged(themeName);
			}
			return false;
		}

		/**
		 * アプリケーションの起動が終了した直後
		 */
		@Override
		public void handleDidFinishLaunchingAction(
				com.sun.glass.ui.Application app, long time) {
			logger.info(() -> {
				return "app:" + app + " /time: " + time;
			});
			if (oldEventHandler != null) {
				oldEventHandler.handleDidFinishLaunchingAction(app, time);
			}
		}

		/**
		 * アプリケーションがアクティブになった直後に呼び出されます
		 */
		@Override
		public void handleDidBecomeActiveAction(
				com.sun.glass.ui.Application app, long time) {
			logger.info(() -> {
				return "app:" + app + " /time: " + time;
			});
			if (oldEventHandler != null) {
				oldEventHandler.handleDidBecomeActiveAction(app, time);
			}
		}

		/**
		 * ファイルドロップを受け取った場合
		 */
		@Override
		public void handleOpenFilesAction(com.sun.glass.ui.Application app,
				long time, final String[] files) {
			logger.info(() -> {
				return "app:" + app + " /time: " + time + " /files: "
						+ Arrays.toString(files);
			});
			if (oldEventHandler != null) {
				oldEventHandler.handleOpenFilesAction(app, time, files);
			}
			notificationHandler.handleOpenFilesAction(Arrays.asList(files));
		}

		/**
		 * アプリケーションの終了
		 */
		@Override
		public void handleQuitAction(com.sun.glass.ui.Application app, long time) {
			logger.info(() -> {
				return "app:" + app + " /time: " + time;
			});
			if (oldEventHandler != null) {
				oldEventHandler.handleQuitAction(app, time);
			}
			notificationHandler.handleQuitAction();
		}
	}

	public static void setPlatformEventHandler(EventHandler eventHandler) {
		Application.GetApplication().setEventHandler(eventHandler);
	}

	public static EventHandler getPlatformEventHandler() {
		return Application.GetApplication().getEventHandler();
	}

	public static void setName(String name) {
		Application.GetApplication().setName(name);
	}

	public static String getName() {
		return Application.GetApplication().getName();
	}
}
