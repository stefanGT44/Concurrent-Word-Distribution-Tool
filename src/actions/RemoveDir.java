package actions;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import view.InputView;

public class RemoveDir implements EventHandler<ActionEvent>{
	
	@Override
	public void handle(ActionEvent event) {
		InputView iv = (InputView)((HBox)((Button)event.getSource()).getParent()).getParent();
		iv.removeDirectory();
	}

}
