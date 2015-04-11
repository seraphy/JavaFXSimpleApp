package jp.seraphyware.simpleapp0;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class JavaFXSimpleModel {

	private SimpleStringProperty textProperty;

	public StringProperty textProperty() {
		if (textProperty == null) {
			textProperty  = new SimpleStringProperty(this, "text");
		}
		return textProperty;
	}

	public String getText() {
		return textProperty().get();
	}

	public void setText(String text) {
		textProperty().set(text);
	}
}
