package actions;

import input_component.TextObject;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import output_component.ObjectType;
import view.CruncherView;
import view.InputView;
import view.MainView;

public class RemoveCruncher implements EventHandler<ActionEvent> {
	
	@Override
	public void handle(ActionEvent event) {
		CruncherView cv  = (CruncherView)((Button)event.getSource()).getParent();
		cv.getCruncherMainThread().submitObject(new TextObject(null, null, ObjectType.SHUTDOWN));
		MainView.get().getCruncherColumnView().getCrunchersPane().getChildren().remove(cv);
		MainView.get().getCruncherNames().remove("Counter " + cv.getCruncherId());
		for (InputView iv:MainView.get().getInputs()) {
			if (iv.getCrunchers().getItems().contains("Counter " + cv.getCruncherId())) {
				iv.getCrunchers().getItems().remove("Counter " + cv.getCruncherId());
				iv.getInputMainThread().unlinkCruncher(cv.getCruncherMainThread());
				iv.getCrunchers().refresh();
			}
			if (iv.getCb().getSelectionModel().getSelectedItem() != null && iv.getCb().getSelectionModel().getSelectedItem().equals("Counter " + cv.getCruncherId()))
				iv.getLink().setDisable(true);
			iv.getCb().setItems(FXCollections.observableArrayList(MainView.get().getCruncherNames()));
		}
		MainView.get().getCrunchers().remove(cv);
	}

}
