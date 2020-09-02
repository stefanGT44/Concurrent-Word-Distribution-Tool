package actions;

import javafx.event.EventHandler;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import view.InputView;

public class SelectCruncherListView implements EventHandler<MouseEvent>{

	@Override
	public void handle(MouseEvent event) {
		InputView iv = (InputView)((ListView)event.getSource()).getParent();
		if (iv.getCrunchers().getSelectionModel().getSelectedItem() != null) {
			iv.getUnlink().setDisable(false);
		}
	}
	
}
