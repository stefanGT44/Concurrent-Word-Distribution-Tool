package actions;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import output_component.OutputSumResultWorker;
import javafx.scene.control.Button;
import javafx.scene.control.TextInputDialog;
import view.MainView;
import view.OutputView;
import view.ResultListViewItem;

public class SumResult implements EventHandler<ActionEvent> {

	@Override
	public void handle(ActionEvent event) {
		OutputView ov = (OutputView) ((Button) event.getSource()).getParent().getParent().getParent();

		TextInputDialog dialog = new TextInputDialog("sum");
		dialog.setTitle("Create sum");
		dialog.setHeaderText("Please enter unique sum name: ");
		dialog.setContentText(null);

		Optional<String> result = dialog.showAndWait();
		if (!result.isPresent()) {
			return;
		}

		String name = result.get();
		boolean test = false;
		for (ResultListViewItem item : ov.getResults().getItems()) {
			if (item.getName().equals(name)) {
				test = true;
				break;
			}
		}

		if (test) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Create sum");
			alert.setHeaderText("Entered name is already being used.");
			alert.show();
			return;
		} else {
			List<String> list = new ArrayList<>();
			for (ResultListViewItem item : ov.getResults().getSelectionModel().getSelectedItems())
				list.add(item.getName());
			MainView.get().outputThreadPool.execute(new OutputSumResultWorker(ov, list, name));
		}

	}

}
