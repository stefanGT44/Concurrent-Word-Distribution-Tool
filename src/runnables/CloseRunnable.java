package runnables;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import view.MainView;

public class CloseRunnable implements Runnable {
	
	private Alert alert;
	
	public CloseRunnable(Alert alert) {
		this.alert = alert;
	}
	
	@Override
	public void run() {
		
		long curr = System.currentTimeMillis();
		
		//waiting....
		while (MainView.get().getActiveCruncherWorkers().get() != 0 || 
				MainView.get().getActiveInputWorkers().get() != 0 || MainView.get().getActiveOutputWorkers().get() != 0 ) {
		}
		
		//Final SHUT DOWN!
		MainView.get().freeze = false;
		Platform.runLater(new Runnable() {
			
			@Override
			public void run() {
				alert.close();
				MainView.get().close();
			}
		});
		
	}

}
