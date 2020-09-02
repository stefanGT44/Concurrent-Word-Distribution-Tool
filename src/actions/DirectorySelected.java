package actions;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import view.InputView;

public class DirectorySelected implements EventHandler<MouseEvent> {

	@Override
	public void handle(MouseEvent event) {
		ListView listView = (ListView) event.getSource();
		InputView iv = (InputView) listView.getParent();

		if (listView.getSelectionModel().getSelectedItem() != null) {
			iv.getRemoveDir().setDisable(false);
		}
	}

}
