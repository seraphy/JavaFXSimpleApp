package jp.seraphyware.utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

public final class ErrorDialogUtils {

    private ErrorDialogUtils() {
        super();
    }

    public static void showException(Throwable ex) throws IOException {
        if (ex == null) {
            return;
        }

        // jdk1.8u40から、Alertクラスによるダイアログがサポートされた.
        // 使い方は以下引用
        // http://code.makery.ch/blog/javafx-dialogs-official/

        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Exception Dialog");
        alert.setHeaderText(ex.getClass().getName());
        alert.setContentText(ex.getMessage());

        GridPane expContent = new FXMLLoader(ErrorDialogUtils.class.getResource("ErrorDialogExpandableContent.fxml")).load();

        // Create expandable Exception.
        try (StringWriter sw = new StringWriter()) {
            try (PrintWriter pw = new PrintWriter(sw)) {
                ex.printStackTrace(pw);
            }
            ((TextArea) expContent.getChildren().get(1)).setText(sw.toString());
        }

        // Set expandable Exception into the dialog pane.
        alert.getDialogPane().setExpandableContent(expContent);

        alert.showAndWait();
    }
}
