package jp.seraphyware.simpleapp6;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

import com.sun.glass.ui.Application;
import com.sun.glass.ui.Application.EventHandler;

@SuppressWarnings({"restriction"})
public class Deprecation {
	
	/**
	 * ロガー
	 */
	private static final Logger logger = Logger.getLogger(Deprecation.class.getName());

    private Deprecation() {
        assert false;
    }
    
    public interface AppNotificationHandler {
        public void handleLaunch(List<String> files);
        public void handleOpenFilesAction(List<String> files);
        public void handleMessageBoxFailure(Exception x);
        public void handleQuitAction();
    }
    
    public static class MacEventHandler extends com.sun.glass.ui.Application.EventHandler {
        
        private final AppNotificationHandler notificationHandler;

        private final com.sun.glass.ui.Application.EventHandler oldEventHandler;
        
        private int openFilesCount;
        
        public MacEventHandler(AppNotificationHandler notificationHandler,
                com.sun.glass.ui.Application.EventHandler oldEventHandler) {
            Objects.requireNonNull(notificationHandler);
            this.notificationHandler = notificationHandler;
            this.oldEventHandler = oldEventHandler;
        }
        
        /*
         * com.sun.glass.ui.Application.AppNotificationHandler
         */
        @Override
        public void handleDidFinishLaunchingAction(com.sun.glass.ui.Application app, long time) {
        	logger.info(() -> {
        		return "app:" + app + " /time: " + time;
        	});
            if (oldEventHandler != null) {
                oldEventHandler.handleDidFinishLaunchingAction(app, time);
            }
        }

        @Override
        public void handleDidBecomeActiveAction(com.sun.glass.ui.Application app, long time) {
        	logger.info(() -> {
        		return "app:" + app + " /time: " + time;
        	});
            if (oldEventHandler != null) {
                oldEventHandler.handleDidBecomeActiveAction(app, time);
            }
        }

        @Override
        public void handleOpenFilesAction(com.sun.glass.ui.Application app, long time, final String[] files) {
        	logger.info(() -> {
        		return "app:" + app + " /time: " + time + " /files: " + Arrays.toString(files);
        	});
            if (oldEventHandler != null) {
                oldEventHandler.handleOpenFilesAction(app, time, files);
            }
            
            /*
             * When SB is started from NB or test environment on Mac OS, this 
             * method is called a first time with dummy parameter like this:
             * files[0] == "com.oracle.javafx.scenebuilder.app.SceneBuilderApp". //NOI18N
             * We ignore this call here.
             * 
             * With Eclipse on Mac, files[0] == System.getProperty("java.class.path") (!) //NOI18N
             */
            final boolean openRejected;
            if (openFilesCount++ == 0) {
                openRejected = (files.length == 1) 
                        && ( files[0].equals(SimpleApp6.class.getName()) || //NOI18N
                             files[0].equals(System.getProperty("java.class.path"))); //NOI18N
            } else {
                openRejected = false;
            }
            
            if (openRejected == false) {
                notificationHandler.handleOpenFilesAction(Arrays.asList(files));
            }
        }

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
}
