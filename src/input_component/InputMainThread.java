package input_component;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import config.AppConfig;
import cruncher_component.CruncherMainThread;
import javafx.scene.control.Label;
import view.MainView;

public class InputMainThread implements Runnable{
	
	private int id;
	private String disk;
	private Label state;
	private volatile boolean paused;
	private volatile boolean running;
	
	
	private	List<String> directories;
	private Map<String, Long> changes;
	
	
	private List<CruncherMainThread> linkedCrunchers;

	public InputMainThread(int id, String disk, Label state) {
		this.id = id;
		this.disk = disk;
		this.state = state;
		paused = true;
		running = true;
		changes = Collections.synchronizedMap(new HashMap<>());
		directories = Collections.synchronizedList(new ArrayList<>());
		linkedCrunchers = Collections.synchronizedList(new ArrayList<>());
	}
	
	@Override
	public void run() {
		while (running) {
			if (!MainView.running)
				break;
			if (paused) continue;
			
			
			findWork();
			
			
			//waiting
			long startWait = System.currentTimeMillis();
			while (running && System.currentTimeMillis() - startWait < AppConfig.fileInputSleepTime && MainView.running && !paused) {
			}
		}
		
		System.out.println("Input[" + id + "] stopped.");
	}
	
	private void findWork() {
		synchronized(directories) {
			for (String dir:directories) {
				File file = new File(dir);
				if (file.exists() && file.isDirectory())
					recursiveCalls(file);
			}
		}
	}
	
	private void recursiveCalls(File file) {
		File files[] = file.listFiles();
		for (File f:files) {
			if (f.isDirectory())
				recursiveCalls(f);
			else if (f.getName().endsWith(".txt"))
				createJob(f);
		}
	}
	
	private void createJob(File file) {
		Long lastModified = changes.get(file.getAbsolutePath());
		if (lastModified == null || file.lastModified() != lastModified) {
			changes.put(file.getAbsolutePath(), file.lastModified());
			System.out.println("Creating job for file: " + file.getAbsolutePath());
			MainView.get().getInputThreadPool().execute(new InputWorker(disk, file, linkedCrunchers, state));
		}
	}
	
	public void pause() {
		paused = true;
		System.out.println("Input[" + id + "] paused.");
	}
	
	public void start() { 
		paused = false;
		System.out.println("Input[" + id + "] started.");
	}
	
	public void addDirectory(String path) {
		directories.add(path);
	}
	
	public void removeDirectory(String path) {
		directories.remove(path);
		List<String> remove = new ArrayList<>();
		synchronized(changes) {
			for (String str:changes.keySet()) {
				if (str.replace('\\', '/').startsWith(path))
					remove.add(str);
			}
		}
		for (String str:remove)
			changes.remove(str);
	}
	
	public void linkCruncher(CruncherMainThread cruncher) {
		linkedCrunchers.add(cruncher);
	}
	
	public void unlinkCruncher(CruncherMainThread cruncher) {
		linkedCrunchers.remove(cruncher);
	}
	
	public void shutDown() {
		running = false;
	}
	
}
