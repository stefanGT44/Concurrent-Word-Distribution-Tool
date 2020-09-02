package actions;

import java.io.File;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.stage.DirectoryChooser;
import view.InputView;
import view.MainView;

public class AddDir implements EventHandler<ActionEvent>{
	
	private DirectoryChooser chooser;
	
	public AddDir() {
		chooser = new DirectoryChooser();
	}
	
	@Override
	public void handle(ActionEvent event) {
		InputView iv = (InputView)((HBox)((Button)(event.getSource())).getParent()).getParent();
		
		chooser.setInitialDirectory(new File(iv.getDisk()));
		File selectedDirectory = chooser.showDialog(MainView.get());
		
		if (selectedDirectory == null)
			return;
		
		iv.addDirectory(selectedDirectory.getAbsolutePath().replace('\\', '/'));
	}
	
}
