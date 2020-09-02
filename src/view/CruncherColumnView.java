package view;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

public class CruncherColumnView extends VBox {
	
	private VBox crunchersPane;

	public CruncherColumnView(int width, int height) {
		initView(width, height);
	}
	
	private void initView(int width, int height) {
		setPrefSize(width, height);
		setPadding(new Insets(0, 0, 5, 0));
		
		VBox box = new VBox();
		box.setSpacing(5);
		box.setPadding(new Insets(4));
		
		Label l1 = new Label("Crunchers");
		Button bt = new Button("Add cruncher");
		bt.setOnAction(MainView.get().getActionManager().getCreateCruncherComponent());
		
		box.getChildren().addAll(l1, bt);
		
		ScrollPane sp = new ScrollPane();
		sp.setPrefSize(width - 10, height - 10 - box.getHeight());
		sp.setPadding(new Insets(4));
		
		crunchersPane = new VBox();
		crunchersPane.setSpacing(15);
		
		sp.setContent(crunchersPane);
		getChildren().addAll(box, sp);
	}
	
	public void addCruncher(CruncherView cv) {
		crunchersPane.getChildren().add(cv);
	}
	
	public VBox getCrunchersPane() {
		return crunchersPane;
	}
	
}
