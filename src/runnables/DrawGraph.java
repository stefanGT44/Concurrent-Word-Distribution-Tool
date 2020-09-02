package runnables;

import java.util.Map;

import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import view.OutputView;

public class DrawGraph implements Runnable {

	private Map<Integer, Integer> mapToDraw;
	private OutputView view;
	private String name;
	
	public DrawGraph(Map<Integer, Integer> mapToDraw, OutputView view, String name) {
		this.mapToDraw = mapToDraw;
		this.view = view;
		this.name = name;
	}

	@Override
	public void run() {
		
		NumberAxis xAxis = new NumberAxis(0, 100, 10);
		NumberAxis yAxis = new NumberAxis();
		//xAxis.setLabel("Words");
		//yAxis.setLabel("Word occurence");
		LineChart graph = new LineChart(xAxis, yAxis);
		graph.setTitle(name);
		//graph.setCreateSymbols(false);
		graph.setPrefSize(690, view.getGraph().getPrefHeight());
		
		XYChart.Series<Integer, Integer> series = new Series<>();
		for (Map.Entry<Integer, Integer> entry: mapToDraw.entrySet()) {
			series.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
		}
		graph.getData().add(series);
		graph.setLegendVisible(false);
		
		view.setGraph(graph);
		
		view.getSingleResult().setDisable(false);
	}
	
}
