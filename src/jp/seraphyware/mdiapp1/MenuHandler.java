package jp.seraphyware.mdiapp1;

public interface MenuHandler {

	boolean canPerform(DocumentController docCtrl);

	void perform(DocumentController docCtrl);
}
