package view;

import java.util.ArrayList;

import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class OutputColumnView extends HBox{
	
	private int width, height;
	private VBox outputPane;

	public OutputColumnView(int width, int height) {
		this.width = width + 10;
		this.height = height;
		initView();
	}
	
	private void initView() {
		setPrefSize(width, height);
		setPadding(new Insets(5));
		
		ScrollPane sp = new ScrollPane();
		sp.setPrefSize(width, height);
		sp.setPadding(new Insets(5));
		
		outputPane = new VBox();
		outputPane.setSpacing(15);
		sp.setContent(outputPane);
		
		getChildren().add(sp);
	}
	
	
	
	public void addOutputComponent(OutputView outputView) {
		this.outputPane.getChildren().add(outputView);
		MainView.get().getOutputs().add(outputView);
	}
	
}
