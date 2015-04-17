package jp.seraphyware.mdiapp1;

public enum Menus implements MenuHandler {

	FILE_NEW {
		@Override
		public void perform(DocumentController docCtrl) {
			MDIApp1.getSingleton().onFileNew();
		}

		@Override
		public boolean canPerform() {
			return true;
		}
	},
	FILE_SAVE {
		@Override
		public void perform(DocumentController docCtrl) {
		}

		@Override
		public boolean canPerform() {
			return false;
		}
	},
	FILE_SAVE_AS {
		@Override
		public void perform(DocumentController docCtrl) {
		}

		@Override
		public boolean canPerform() {
			return false;
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
		public boolean canPerform() {
			return true;
		}
	},
	FILE_QUIT {
		@Override
		public void perform(DocumentController docCtrl) {
			MDIApp1.getSingleton().onFileQuit();
		}

		@Override
		public boolean canPerform() {
			return true;
		}
	};
}
