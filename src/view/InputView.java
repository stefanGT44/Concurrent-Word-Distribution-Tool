package view;

import java.util.ArrayList;

import input_component.InputMainThread;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class InputView extends VBox{

	private String disk;
	private int id;
	private double width;
	private Button removeDir;
	private Button start;
	private Button link;
	private Button unlink;
	private Label state;
	private ComboBox<String> cb;
	
	private ListView<String> crunchers;
	private ListView<String> directories;
	
	private InputMainThread inputMainThread;
	
	public InputView(String disk, double width, int id) {
		this.disk = disk;
		this.id = id;
		this.width = width;
		
		setPrefWidth(width);
		initView();
		startInputThread();
	}
	
	private void startInputThread() {
		inputMainThread = new InputMainThread(id, disk, state);
		Thread thread = new Thread(inputMainThread);
		thread.start();
	}
	
	private void initView() {
		setStyle("-fx-background-color: #ffeecc;");
		setSpacing(4);
		setPadding(new Insets(4));
		
		HBox hbox1 = new HBox();
		Label l1 = new Label("Input " + id);
		l1.setStyle("-fx-text-fill: #fc6b03;");
		Label l3 = new Label(": " + disk);
		l3.setStyle("-fx-text-fill: #005bc9;");
		hbox1.getChildren().addAll(l1, l3);
		
		Label l4 = new Label("Linked crunchers: ");
		crunchers = new ListView<>(FXCollections.observableArrayList(new ArrayList<>()));
		crunchers.setOnMouseClicked(MainView.get().getActionManager().getSelectCruncherListView());
		crunchers.setPrefSize(width - 8, 80);
		crunchers.setId("myListView");
		
		HBox hbox2 = new HBox();
		hbox2.setSpacing(5);
		cb = new ComboBox<>(FXCollections.observableArrayList(MainView.get().getCruncherNames()));
		cb.setOnAction(MainView.get().getActionManager().getCruncherSelectCBAction());
		cb.setPromptText("Select cruncher...");
		
		link = new Button("Link");
		link.setOnAction(MainView.get().getActionManager().getLinkCruncher());
		link.setDisable(true);
		unlink = new Button("Unlink");
		unlink.setOnAction(MainView.get().getActionManager().getUnlinkCruncher());
		unlink.setDisable(true);
		
		hbox2.getChildren().addAll(cb, link, unlink);
		
		Separator sep = new Separator();
		sep.setOrientation(Orientation.VERTICAL);
		
		Label l5 = new Label("Directories: ");
		directories = new ListView<>(FXCollections.observableArrayList(new ArrayList<>()));
		directories.setPrefSize(width - 8, 80);
		directories.setOnMouseClicked(MainView.get().getActionManager().getDirectorySelected());
		directories.setId("myListView2");
		
		HBox hbox3 = new HBox();
		hbox3.setSpacing(5);
		
		Button addDir = new Button("Add dir");
		addDir.setAccessibleHelp(this.disk);
		addDir.setOnAction(MainView.get().getActionManager().getAddDir());
		
		removeDir = new Button("Remove dir");
		removeDir.setDisable(true);
		removeDir.setOnAction(MainView.get().getActionManager().getRemoveDir());
		
		hbox3.getChildren().addAll(addDir, removeDir);
		
		Separator sep2 = new Separator();
		
		HBox hbox4 = new HBox();
		hbox4.setSpacing(5);
		
		start = new Button("Start");
		start.setOnAction(MainView.get().getActionManager().getStartInputWorker());
		Button removeInput = new Button("Remove input component");
		removeInput.setOnAction(MainView.get().getActionManager().getRemoveInput());
		
		hbox4.getChildren().addAll(start, removeInput);
		
		state = new Label("idle");
		state.setStyle("-fx-text-fill: #fc6b03;");
		
		getChildren().addAll(hbox1, l4, crunchers, hbox2, sep, l5, directories, hbox3, sep2, hbox4, state);
	}
	
	public void linkCruncher(CruncherView cv) {
		crunchers.getItems().add("Counter " + cv.getCruncherId());
		inputMainThread.linkCruncher(cv.getCruncherMainThread());
	}
	
	public void unlinkCruncher(String cruncherName) {
		crunchers.getItems().remove(cruncherName);
		int id = Integer.parseInt(cruncherName.split(" ")[1]);
		CruncherView cv;
		for (CruncherView c:MainView.get().getCrunchers()) {
			if (c.getCruncherId() == id) {
				inputMainThread.unlinkCruncher(c.getCruncherMainThread());
				break;
			}
		}
	}
	
	public void addDirectory(String path) {
		if (!directories.getItems().contains(path)) {
			directories.getItems().add(path);
			inputMainThread.addDirectory(path);
		}
	}
	
	public void removeDirectory() {
		if (directories.getSelectionModel().getSelectedItem() != null){
			String selectedItem = directories.getSelectionModel().getSelectedItem();
			directories.getItems().remove(selectedItem);
			inputMainThread.removeDirectory(selectedItem);
			directories.getSelectionModel().clearSelection();
			removeDir.setDisable(true);
		}
	}
	
	public Button getRemoveDir() {
		return removeDir;
	}
	
	public void refreshComboBox() {
		this.cb.setItems(FXCollections.observableArrayList(MainView.get().getCruncherNames()));
	}
	
	public ComboBox<String> getCb() {
		return cb;
	}
	
	public ListView<String> getCrunchers() {
		return crunchers;
	}
	
	public Button getStart() {
		return start;
	}
	
	public String getDisk() {
		return disk;
	}
	
	public Button getLink() {
		return link;
	}
	
	public Button getUnlink() {
		return unlink;
	}
	
	public InputMainThread getInputMainThread() {
		return inputMainThread;
	}
	
}
