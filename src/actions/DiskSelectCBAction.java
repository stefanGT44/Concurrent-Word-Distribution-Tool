package actions;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import view.InputView;
import view.MainView;

public class DiskSelectCBAction implements EventHandler<ActionEvent>{

	@Override
	public void handle(ActionEvent event) {
		//TREBA OPTIMIZOVATI
		boolean test = false;
		for (InputView iv:MainView.get().getInputs()) {
			if (iv.getDisk().equals(MainView.get().getInputColumnView().getDisks().getSelectionModel().getSelectedItem())) {
				test = true;
				break;
			}
		}
		if (test)
			MainView.get().getInputColumnView().getAddInputButton().setDisable(true);
		else
			MainView.get().getInputColumnView().getAddInputButton().setDisable(false);
	}

	

}
