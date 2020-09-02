package actions;

import cruncher_component.ResultObject;
import input_component.TextObject;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.WindowEvent;
import output_component.ObjectType;
import runnables.CloseRunnable;
import view.CruncherView;
import view.MainView;
import view.OutputView;

public class CloseApp implements EventHandler<WindowEvent> {

	@Override
	public void handle(WindowEvent event) {
		if (MainView.get().running) {
			System.out.println("Shutting down...");
			MainView.running = false;
			MainView.get().getInputThreadPool().shutdown();
			MainView.get().getCruncherThreadPool().shutdown();
			MainView.get().getOutputThreadPool().shutdown();

			for (CruncherView cruncher : MainView.get().getCrunchers()) {
				cruncher.getCruncherMainThread().submitObject(new TextObject(null, null, ObjectType.SHUTDOWN));
			}

			for (OutputView output : MainView.get().getOutputs()) {
				output.getOutputMainThread().submitResult(new ResultObject(null, null, ObjectType.SHUTDOWN));
			}

			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Close");
			alert.setHeaderText("Shutting down...");
			MainView.get().freeze = true;
			alert.setOnCloseRequest(e -> {
				if (MainView.get().freeze)
					e.consume();
			});
			alert.show();

			event.consume();

			new Thread(new CloseRunnable(alert)).start();
		}
	}

}
