package view;

import cruncher_component.CruncherMainThread;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class CruncherView extends VBox {
	
	private int id;
	private int arity;
	private volatile VBox progress;
	
	private CruncherMainThread cruncherMainThread;
	
	public CruncherView(int id, double width, int arity) {
		this.id = id;
		this.arity = arity;
		setPrefWidth(width);
		initView();
		startCruncherMainThread();
	}
	
	private void startCruncherMainThread() {
		cruncherMainThread = new CruncherMainThread(arity, progress);
		Thread thread = new Thread(cruncherMainThread);
		thread.start();
	}
	
	private void initView() {
		setStyle("-fx-background-color: #ffe3fb;");
		setSpacing(4);
		setPadding(new Insets(4));
		
		HBox hbox1 = new HBox();
		Label l1 = new Label("Name: ");
		Label l2 = new Label("Counter " + id);
		l2.setStyle("-fx-text-fill: #ff30e1;");
		hbox1.getChildren().addAll(l1, l2);
		
		HBox hbox2 = new HBox();
		Label l3 = new Label("Arity: ");
		Label l4 = new Label("" + arity);
		l4.setStyle("-fx-text-fill: #5757ff;");
		hbox2.getChildren().addAll(l3, l4);
		
		Button bt = new Button("Remove cruncher");
		bt.setOnAction(MainView.get().getActionManager().getRemoveCruncher());
		
		progress = new VBox();
		Label l5 = new Label("Crunching: ");
		progress.getChildren().add(l5);
		progress.setVisible(false);
	
		getChildren().addAll(hbox1, hbox2, bt, progress);
	}
	
	public CruncherMainThread getCruncherMainThread() {
		return cruncherMainThread;
	}
	
	public VBox getProgress() {
		return progress;
	}
	
	public int getCruncherId() {
		return id;
	}

}
