package actions;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import view.InputView;

public class UnlinkCruncher implements EventHandler<ActionEvent>{
	
	@Override
	public void handle(ActionEvent event) {
		InputView iv = (InputView)((Button)event.getSource()).getParent().getParent();
		String selected = iv.getCrunchers().getSelectionModel().getSelectedItem();
		iv.unlinkCruncher(selected);
		iv.getCrunchers().getSelectionModel().clearSelection();
		iv.getUnlink().setDisable(true);
		iv.getLink().setDisable(false);
	}

}
