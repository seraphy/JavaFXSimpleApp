package jp.seraphyware.mdiapp1;

public enum Menus implements MenuHandler {

	FILE_NEW {
		@Override
		public void perform(DocumentController docCtrl) {
			MDIApp1.getSingleton().onFileNew();
		}

		@Override
		public boolean canPerform(DocumentController docCtrl) {
			return true; // ドキュメントの有無に関わらず利用可能
		}
	},
	FILE_SAVE {
		@Override
		public void perform(DocumentController docCtrl) {
		}

		@Override
		public boolean canPerform(DocumentController docCtrl) {
			return docCtrl != null; // ドキュメントがあれば保存可能
		}
	},
	FILE_SAVE_AS {
		@Override
		public void perform(DocumentController docCtrl) {
		}

		@Override
		public boolean canPerform(DocumentController docCtrl) {
			return docCtrl != null; // ドキュメントがあれば保存可能
		}
	},
	FILE_CLOSE {
		@Override
		public void perform(DocumentController docCtrl) {
			if (docCtrl != null) {
				docCtrl.performClose();
			}
		}

		@Override
		public boolean canPerform(DocumentController docCtrl) {
			return docCtrl != null; // ドキュメントがあればクローズ可能
		}
	},
	FILE_QUIT {
		@Override
		public void perform(DocumentController docCtrl) {
			MDIApp1.getSingleton().onFileQuit();
		}

		@Override
		public boolean canPerform(DocumentController docCtrl) {
			return true; // いつでもクローズ可能
		}
	};
}
