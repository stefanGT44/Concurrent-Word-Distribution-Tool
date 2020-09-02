package input_component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import actions.OutOfMemoryAlert;
import cruncher_component.CruncherMainThread;
import javafx.application.Platform;
import javafx.scene.control.Label;
import output_component.ObjectType;
import view.MainView;

public class InputWorker implements Runnable {

	private String disk;
	private File file;
	private List<CruncherMainThread> crunchers;
	private Label state;
	
	public InputWorker(String disk, File file, List<CruncherMainThread> crunchers, Label state) {
		this.disk = disk;
		this.file = file;
		this.state = state;
		this.crunchers = new ArrayList<>();
		synchronized(crunchers) {
			this.crunchers.addAll(crunchers);
		}
	}
	
	@Override
	public void run() {
		MainView.get().getActiveInputWorkers().incrementAndGet();
		try {
			String text = null;
			synchronized(disk) {
				if (!MainView.running) {
					MainView.get().getActiveInputWorkers().decrementAndGet();
					return;
				}
				
				Platform.runLater(new Runnable() {
					
					@Override
					public void run() {
						state.setText("Reading: " + file.getName());
						state.setStyle("-fx-text-fill: #4785ff;");
					}
				});
				
				try {
					text = new String(Files.readAllBytes(file.toPath()));
				} catch (OutOfMemoryError e) {
					if (!MainView.outOfMemory) {
						MainView.outOfMemory = true;
						Platform.runLater(new OutOfMemoryAlert("Out of memory while reading file(s), shutting down..."));
					}
				}
					
				//cekanje za debug
				//long curr = System.currentTimeMillis();
				//while (System.currentTimeMillis() - curr < 2000) {}
				
				Platform.runLater(new Runnable() {
					
					@Override
					public void run() {
						state.setText("idle");
						state.setStyle("-fx-text-fill: #fc6b03;");
					}
					
				});
			}
			TextObject obj = new TextObject(file.getName(), text, ObjectType.WORK);
			for (CruncherMainThread cruncher: crunchers) {
				cruncher.submitObject(obj);
			}
		} catch (IOException e) {
			System.out.println("Error reading contents of file: " + file.getAbsolutePath());
		}
		MainView.get().getActiveInputWorkers().decrementAndGet();
	}

}
