package actions;

import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import output_component.OutputSingleResultWorker;
import javafx.scene.control.Button;
import view.MainView;
import view.OutputView;

public class SingleResult implements EventHandler<ActionEvent> {

	@Override
	public void handle(ActionEvent event) {
		OutputView ov = (OutputView) ((Button) event.getSource()).getParent().getParent().getParent();
		String selectedItem = ov.getResults().getSelectionModel().getSelectedItem().getName();
		if (selectedItem.startsWith("*")) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Single Result");
			alert.setHeaderText("Selected result is not ready yet.");
			alert.show();
		} else {
			Object obj = ov.getOutputMainThread().poll(selectedItem);
			if (obj == null) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Single Result");
				alert.setHeaderText("Internal error, result doesn't exist");
				alert.show();
			} else {
				if (obj instanceof Map)
					MainView.get().getOutputThreadPool().execute(new OutputSingleResultWorker(ov, (Map)obj, selectedItem));
				else
					try {
						MainView.get().getOutputThreadPool().execute(new OutputSingleResultWorker(ov, (Map)((Future)obj).get(), selectedItem));
					} catch (InterruptedException | ExecutionException e) {
						e.printStackTrace();
					}
			}
		}
	}

}
