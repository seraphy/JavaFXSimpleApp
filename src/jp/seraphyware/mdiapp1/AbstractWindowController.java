package jp.seraphyware.mdiapp1;

import java.util.Objects;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.stage.WindowEvent;

public abstract class AbstractWindowController {

	private boolean sizeToScene = true;

	private Window owner;

	private Parent root;

	private Stage stage;

	private Scene scene;

	protected final void setRoot(Parent root) {
		Objects.nonNull(root);
		this.root = root;
	}

	protected abstract void makeRoot();

	public abstract void onCloseRequest(WindowEvent event);

	protected AbstractWindowController() {
		this(null);
	}

	protected AbstractWindowController(Stage stage) {
		this.stage = stage;
	}

	private final EventHandler<WindowEvent> closeRequestHandler = event -> {
		onCloseRequest(event);
		event.consume();
	};

	public Parent getRoot() {
		if (root == null) {
			makeRoot();
			assert root != null;
		}

		return root;
	}

	public Scene getScene() {
		assert Platform.isFxApplicationThread();

		if (scene == null) {
			scene = new Scene(getRoot());
		}

		return scene;
	}

	public Stage getStage() {
		assert Platform.isFxApplicationThread();

		if (stage == null) {
			stage = new Stage();
			stage.initOwner(owner);
			stage.setOnCloseRequest(closeRequestHandler);
			stage.setScene(getScene());

			if (sizeToScene) {
				stage.sizeToScene();
			}
		}

		return stage;
	}

	public void openWindow() {
		assert Platform.isFxApplicationThread();

		getStage().show();
		getStage().toFront();
	}

	public void closeWindow() {
		assert Platform.isFxApplicationThread();
		getStage().close();
	}
}
