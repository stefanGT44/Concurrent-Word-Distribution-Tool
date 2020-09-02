package output_component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import actions.OutOfMemoryAlert;
import javafx.application.Platform;
import view.MainView;
import view.OutputView;
import view.ResultListViewItem;
import view.SumProgress;

public class OutputSumResultWorker implements Runnable {
	
	private String name;
	private OutputView view;
	private List<String> keys;

	public OutputSumResultWorker(OutputView view, List<String> keys, String name) {
		this.view = view;
		this.keys = keys;
		this.name = name;
	}
	
	@Override
	public void run() {
		MainView.get().getActiveOutputWorkers().incrementAndGet();
		Map<String, Integer> resultMap = new HashMap<>();
		double jobIncrement = 1.0/keys.size();
		double progress = 0;
		
		SumProgress sumProgress = new SumProgress(name);
		
		Platform.runLater(new Runnable() {
			
			@Override
			public void run() {
				view.addSumProgress(sumProgress);
			}
		});
		
		for (String key: keys) {
			
			Map<String, Integer> map;
			
			//cekanje
			if (key.startsWith("*"))
				map = view.getOutputMainThread().take(key.substring(1));
			else
				map = view.getOutputMainThread().take(key);
			
			//spajanje mapa
			try {
				for (Map.Entry<String, Integer> entry:map.entrySet())
					if (resultMap.containsKey(entry.getKey())) 
						resultMap.put(entry.getKey(), resultMap.get(entry.getKey()) + entry.getValue());
					else
						resultMap.put(entry.getKey(), entry.getValue());
			} catch (OutOfMemoryError e) {
				if (!MainView.outOfMemory) {
					MainView.outOfMemory = true;
					Platform.runLater(new OutOfMemoryAlert("Out of memory while computing sumResult, shutting down..."));
				}
			}
			
			//update progress
			progress += jobIncrement;
			Platform.runLater(new Runnable() {
				
				@Override
				public void run() {
					sumProgress.getProgressBar().setProgress(jobIncrement);
				}
				
			});
			
		}
		
		//upisi u mapu rezultat
		view.getOutputMainThread().addResult(name, resultMap);
		
		Platform.runLater(new Runnable() {
			
			@Override
			public void run() {
				view.removeSumProgress(sumProgress);
				view.getResults().getItems().add(new ResultListViewItem(name));
			}
			
		});
		MainView.get().getActiveOutputWorkers().decrementAndGet();
	}
	
}
