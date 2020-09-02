package actions;

import application.Main;
import cruncher_component.CruncherMainThread;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import view.CruncherView;
import view.InputView;
import view.MainView;

public class RemoveInput implements EventHandler<ActionEvent> {
	
	@Override
	public void handle(ActionEvent event) {
		InputView iv = (InputView)((Button)event.getSource()).getParent().getParent();
		
		iv.getInputMainThread().shutDown();
		
		MainView.get().getInputColumnView().removeInput(iv);
		MainView.get().getInputs().remove(iv);
		String disk = iv.getDisk();
		if (MainView.get().getInputColumnView().getDisks().getSelectionModel().getSelectedItem().equals(disk))
			MainView.get().getInputColumnView().getAddInputButton().setDisable(false);
	}

}
