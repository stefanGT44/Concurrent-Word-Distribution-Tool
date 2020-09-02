package actions;

import javafx.event.EventHandler;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import view.OutputView;

public class ResultsSelect implements EventHandler<MouseEvent> {
	
	@Override
	public void handle(MouseEvent event) {
		OutputView ov = (OutputView)((ListView)event.getSource()).getParent().getParent();
		int size = ov.getResults().getSelectionModel().getSelectedItems().size();
		if (size == 1) {
			ov.getSingleResult().setDisable(false);
			ov.getSumResult().setDisable(true);
		} else if (size > 1) {
			ov.getSingleResult().setDisable(true);
			ov.getSumResult().setDisable(false);
		} else {
			ov.getSingleResult().setDisable(true);
			ov.getSumResult().setDisable(true);
		}
	}

}
