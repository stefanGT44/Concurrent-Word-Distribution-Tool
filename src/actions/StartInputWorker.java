package actions;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import view.InputView;

public class StartInputWorker implements EventHandler<ActionEvent>{

	@Override
	public void handle(ActionEvent event) {
		Button start = (Button)event.getSource();
		InputView iv = ((InputView)((HBox)start.getParent()).getParent());
		if (start.getText().equals("Start")) {
			iv.getInputMainThread().start();
			start.setText("Pause");
		} else {
			iv.getInputMainThread().pause();
			start.setText("Start");
		}
	}
	
}
