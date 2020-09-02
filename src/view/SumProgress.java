package view;

import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.VBox;

public class SumProgress extends VBox{
	
	private Label label;
	private ProgressBar progressBar;
	
	public SumProgress(String name) {
		label = new Label(name);
		progressBar = new ProgressBar();
		progressBar.setPrefSize(300-8, 25);
		progressBar.setProgress(-1);
		getChildren().addAll(label, progressBar);
	}
	
	public ProgressBar getProgressBar() {
		return progressBar;
	}
	
}
