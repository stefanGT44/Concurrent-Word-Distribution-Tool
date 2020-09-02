package output_component;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;

import cruncher_component.ResultObject;
import javafx.scene.control.ListView;
import view.ResultListViewItem;

public class OutputMainThread implements Runnable {

	private LinkedBlockingQueue<ResultObject> outputQueue;

	private ListView<ResultListViewItem> listViewResults;
	private Map<String, Object> resultMap;

	public OutputMainThread(ListView<ResultListViewItem> listViewResults) {
		this.listViewResults = listViewResults;
		outputQueue = new LinkedBlockingQueue<>();
		resultMap = Collections.synchronizedMap(new HashMap<String, Object>());
	}

	@Override
	public void run() {
		while (true) {
			try {
				//update neophodan
				ResultObject obj = outputQueue.take();
				
				if (obj.getObjectType() == ObjectType.SHUTDOWN)
					break;
				
				resultMap.put(obj.getFileName(), obj.getData());

			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void addResult(String key, Map<String, Integer> value) {
		resultMap.put(key, value);
	}

	public void submitResult(ResultObject resultObject) {
		try {
			outputQueue.put(resultObject);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public Object poll(String name) {
		Object obj = resultMap.get(name);
		if (obj == null) return null;
		if (obj instanceof Map)
			return obj;
		else {
			Future<Map<String, Integer>> future = (Future)obj;
			if (future.isDone())
				return future;
		}
		return null;
	}
	
	public Map<String, Integer> take(String name) {
		Object obj = resultMap.get(name);
		if (obj == null) return null;
		if (obj instanceof Map)
			return (Map)obj;
		else {
			Future<Map<String, Integer>> future = (Future)obj;
			try {
				return future.get();
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	//za listview GUI DEO
	public void addNewResult(String str) {
		String test = str.substring(1);
		for (ResultListViewItem item:listViewResults.getItems()) {
			if (item.getName().equals(test)) {
				item.setName(str);
				listViewResults.refresh();
				return;
			}
		}
		listViewResults.getItems().add(new ResultListViewItem(str));
	}
	
	//za listview GUI DEO
	public void renameResult(String old) {
		for (ResultListViewItem item: listViewResults.getItems()) {
			if (item.getName().equals(old)) {
				item.setName(old.substring(1));
			}
		}
		listViewResults.refresh();
	}

}
