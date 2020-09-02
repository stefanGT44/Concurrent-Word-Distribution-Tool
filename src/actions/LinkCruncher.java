package actions;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import view.CruncherView;
import view.InputView;
import view.MainView;

public class LinkCruncher implements EventHandler<ActionEvent>{

	@Override
	public void handle(ActionEvent event) {
		InputView iv = (InputView)((Button)event.getSource()).getParent().getParent();
		String selected = iv.getCb().getSelectionModel().getSelectedItem();
		selected = selected.split(" ")[1];
		for (CruncherView cruncher: MainView.get().getCrunchers()) {
			if (cruncher.getCruncherId() == Integer.parseInt(selected)) {
				iv.linkCruncher(cruncher);
				iv.getLink().setDisable(true);
			}
		}
	}
	
}
