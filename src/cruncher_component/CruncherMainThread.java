package cruncher_component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionException;

import input_component.TextObject;
import javafx.scene.layout.VBox;
import output_component.ObjectType;
import output_component.OutputMainThread;
import view.MainView;

public class CruncherMainThread implements Runnable {

	private int arity;
	private VBox progress;

	private LinkedBlockingQueue<TextObject> cruncherQueue;
	private List<OutputMainThread> linkedOutputs;

	public CruncherMainThread(int arity, VBox progress) {
		this.arity = arity;
		this.progress = progress;
		cruncherQueue = new LinkedBlockingQueue<>();
		linkedOutputs = Collections.synchronizedList(new ArrayList<>());
	}

	@Override
	public void run() {
		while (true) {
			try {
				
				TextObject obj = cruncherQueue.take();
				
				if (obj.getObjectType() == ObjectType.SHUTDOWN)
					break;
				
				List<OutputMainThread> copyOutputs = new ArrayList<>();
				synchronized(linkedOutputs) {
					copyOutputs.addAll(linkedOutputs);
				}
				Future<Map<String, Integer>> future = null;
				try {
				future = MainView.get().getCruncherThreadPool().submit(new CruncherWorker(obj, 0, arity, progress, copyOutputs));
				} catch (RejectedExecutionException e) {
					//shutting down, job not accepted
				}
				
				synchronized(linkedOutputs) {
					for (OutputMainThread output: linkedOutputs) {
						output.submitResult(new ResultObject(obj.getName() +  "-arity" + arity, future, ObjectType.WORK));
					}
				}
				

			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void submitObject(TextObject obj) {
		try {
			cruncherQueue.put(obj);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void linkOutput(OutputMainThread output) {
		linkedOutputs.add(output);
	}

}
