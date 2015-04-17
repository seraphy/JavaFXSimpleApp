package jp.seraphyware.mdiapp1;

public interface MenuHandler {

	boolean canPerform();

	void perform(DocumentController docCtrl);
}
