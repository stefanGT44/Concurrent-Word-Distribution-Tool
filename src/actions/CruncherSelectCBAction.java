package actions;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.HBox;
import view.InputView;

public class CruncherSelectCBAction implements EventHandler<ActionEvent>{

	@Override
	public void handle(ActionEvent event) {
		InputView iv = (InputView)((ComboBox)event.getSource()).getParent().getParent();
		String selected = iv.getCb().getSelectionModel().getSelectedItem();
		if (selected != null) {
			iv.getLink().setDisable(false);
			if (iv.getCrunchers().getItems().contains(selected))
				iv.getLink().setDisable(true);
		}
	}
	
}
