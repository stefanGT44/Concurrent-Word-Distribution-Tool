package view;

import config.AppConfig;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

public class InputColumnView extends VBox{
	
	private Button addInputButton;
	private ComboBox<String> disks;
	private VBox inputsPane;
	
	public InputColumnView(int width, int height) {
		initView(width, height);
	}
	
	private void initView(int width, int height) {
		//setStyle("-fx-background-color: #252a33;");
		setPrefSize(width, height);
		setPadding(new Insets(5));
	
		
		
		VBox box = new VBox();
		//box.setStyle("-fx-background-color: #252a33;");
		box.setSpacing(5);
		box.setPadding(new Insets(4));
		Label l1 = new Label("File inputs: ");
		
		disks = new ComboBox<>(FXCollections.observableArrayList(AppConfig.disks));
		disks.setOnAction(MainView.get().getActionManager().getDiskSelectCBAction());
		disks.setPromptText("Select a disk");
		disks.setPrefWidth(width - 10 - 8);
		
		addInputButton = new Button("Add Input Component");
		addInputButton.setOnAction(MainView.get().getActionManager().getCreateInputComponent());
		addInputButton.setDisable(true);
		
		box.getChildren().addAll(l1, disks, addInputButton);
		
		
		
		ScrollPane sp = new ScrollPane();
		sp.setPrefSize(width - 10, height - 10 - box.getHeight());
		sp.setPadding(new Insets(4));
		
		inputsPane = new VBox();
		inputsPane.setSpacing(15);
		
		sp.setContent(inputsPane);
		
		getChildren().addAll(box, sp);
	}
	
	public void addInput(InputView inputView) {
		inputsPane.getChildren().add(inputView);
	}
	
	public void removeInput(InputView inputView) {
		inputsPane.getChildren().remove(inputView);
	}
	
	public Button getAddInputButton() {
		return addInputButton;
	}
	
	public ComboBox<String> getDisks() {
		return disks;
	}
	
}
