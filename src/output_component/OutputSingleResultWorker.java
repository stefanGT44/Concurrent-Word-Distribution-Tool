package output_component;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import config.AppConfig;
import javafx.application.Platform;
import runnables.DrawGraph;
import view.MainView;
import view.OutputView;

public class OutputSingleResultWorker implements Runnable {
	
	private OutputView view;
	private Map<String, Integer> map;
	private String name;
	private double jobSize;
	private volatile double progress;
	private int counter, updateCounter;

	public OutputSingleResultWorker(OutputView view, Map<String, Integer> map, String name) {
		this.view = view;
		this.map = map;
		this.name = name;
		this.progress = 0;
		this.counter = 0;
		this.updateCounter = 0;
	}
	
	@Override
	public void run() {
		MainView.get().getActiveOutputWorkers().incrementAndGet();
		jobSize = map.size() * Math.log(map.size());
		
		System.out.println("Single result for: " + name);
		
		Platform.runLater(new Runnable() {
			
			@Override
			public void run() {
				view.getSingleResult().setDisable(true);
				view.addSingleResultProgress();
			}
		});
		
		List<Map.Entry<String, Integer>> list = new LinkedList<>(map.entrySet());

		Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
			
			@Override
			public int compare(Entry<String, Integer> o1, Entry<String, Integer> o2) {
				updateCounter += 1;
				counter += 1;
				if (updateCounter == AppConfig.sortProgressLimit) {
					updateCounter = 0;
					progress = ((100.0 * counter) / jobSize)/100.0;
					Platform.runLater(new Runnable() {
						
						@Override
						public void run() {
							view.getSingleResultProgressBar().setProgress(progress);
						}
						
					});
				}
				return (o2.getValue().compareTo(o1.getValue()));
			}
			
		});
		
		Platform.runLater(new Runnable() {
			
			@Override
			public void run() {
				view.removeSingleResultProgress();
			}
		});
		
		
		Map<Integer, Integer> newMap = new HashMap<Integer, Integer>();
		int count = 0;
		for (Map.Entry<String, Integer> entry: list) {
			if (count == 100) break;
			if (count < 1)
				System.out.println("Word: " + entry.getKey() + " - " + entry.getValue());
			newMap.put(count, entry.getValue());
			count++;
		}
		
		Platform.runLater(new DrawGraph(newMap, view, name));
		MainView.get().getActiveOutputWorkers().decrementAndGet();
	}

}
