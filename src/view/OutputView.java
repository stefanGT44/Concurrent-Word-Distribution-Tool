package view;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import output_component.OutputMainThread;

public class OutputView extends HBox {
	
	private double width, height;
	private LineChart graph;
	private Pane graphPane;
	private ListView<ResultListViewItem> results;
	private Button singleResult;
	private Button sumResult;
	
	private VBox singleResultProgress;
	private ProgressBar singleResultProgressBar;
	
	private VBox resultsPane;
	
	private OutputMainThread outputMainThread;
	
	public OutputView(double width, double height) {
		this.width = width;
		this.height = height;
		initView();
		startOutputThread();
	}
	
	private void startOutputThread() {
		outputMainThread = new OutputMainThread(results);
		Thread thread = new Thread(outputMainThread);
		thread.start();
	}
	
	private void initView(){
		setPrefSize(width, height);
		setStyle("-fx-background-color: #ebebeb");
		setPadding(new Insets(4));
		setSpacing(4);
		
		NumberAxis xAxis = new NumberAxis(0, 100, 10);
		NumberAxis yAxis = new NumberAxis(0, 100, 10);
		xAxis.setLabel("Words");
		yAxis.setLabel("Word occurence");
		graph = new LineChart(new NumberAxis(0, 100, 10), new NumberAxis(0, 100, 10));
		graph.setPrefSize(690, height - 8);
		
		graphPane = new Pane();
		graphPane.setPrefSize(700, height - 8);
		graphPane.getChildren().add(graph);
		
		resultsPane = new VBox();
		resultsPane.setPrefSize(300, height - 8);
		resultsPane.setStyle("-fx-background-color: #ffd6c7");
		resultsPane.setPadding(new Insets(4));
		resultsPane.setSpacing(4);
		
		results = new ListView<>(FXCollections.observableArrayList(new ArrayList<>()));
		results.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		results.setPrefSize(resultsPane.getPrefWidth() - 8, 400);
		results.setOnMouseClicked(MainView.get().getActionManager().getResultsSelect());
		
		HBox box = new HBox();
		box.setSpacing(4);
		
		singleResult = new Button("Single result");
		singleResult.setDisable(true);
		singleResult.setOnAction(MainView.get().getActionManager().getSingleResult());
		
		sumResult = new Button("Sum result");
		sumResult.setDisable(true);
		sumResult.setOnAction(MainView.get().getActionManager().getSumResult());
		
		box.getChildren().addAll(singleResult, sumResult);
		
		singleResultProgress = new VBox();
		Label l1 = new Label("Sorting...");
		singleResultProgressBar = new ProgressBar(0);
		singleResultProgressBar.setPrefSize(resultsPane.getPrefWidth() - 8, 25);
		
		singleResultProgress.getChildren().addAll(l1, singleResultProgressBar);
		
		resultsPane.getChildren().addAll(results, box);
		
		getChildren().addAll(graphPane, resultsPane);
	}
	
	public void addSingleResultProgress() {
		singleResultProgressBar.setProgress(0);
		resultsPane.getChildren().add(singleResultProgress);
	}
	
	public void addSumProgress(SumProgress sumProgress) {
		resultsPane.getChildren().add(sumProgress);
	}
	
	public void removeSumProgress(SumProgress sumProgress) {
		resultsPane.getChildren().remove(sumProgress);
	}
	
	public void removeSingleResultProgress() {
		resultsPane.getChildren().remove(singleResultProgress);
	}
	
	public ProgressBar getSingleResultProgressBar() {
		return singleResultProgressBar;
	}
	
	public void setGraph(LineChart graph) {
		this.graphPane.getChildren().clear();
		this.graphPane.getChildren().add(graph);
	}
	
	public OutputMainThread getOutputMainThread() {
		return outputMainThread;
	}
	
	public Button getSingleResult() {
		return singleResult;
	}
	
	public ListView<ResultListViewItem> getResults() {
		return results;
	}
	
	public LineChart getGraph() {
		return graph;
	}
	
	public Button getSumResult() {
		return sumResult;
	}

}
