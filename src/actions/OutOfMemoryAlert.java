package actions;

import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;

public class OutOfMemoryAlert implements Runnable {

	private String text;
	
	public OutOfMemoryAlert(String text) {
		this.text = text;
	}
	
	@Override
	public void run() {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Error");
		alert.setHeaderText("Out of memory");
		alert.setContentText(text);
		Optional<ButtonType> option = alert.showAndWait();
		System.exit(1);
	}
	
}
