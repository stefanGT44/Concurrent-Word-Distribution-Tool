package actions;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import view.InputColumnView;
import view.InputView;
import view.MainView;

public class CreateInputComponent implements EventHandler<ActionEvent>{

	@Override
	public void handle(ActionEvent event) {
		InputColumnView icv = MainView.get().getInputColumnView();
		icv.getAddInputButton().setDisable(true);
		InputView iv = new InputView(icv.getDisks().getSelectionModel().getSelectedItem(), icv.getWidth() - 10 - 8, MainView.inputsCounter++);
		MainView.get().getInputs().add(iv);
		icv.addInput(iv);
	}
	
}
