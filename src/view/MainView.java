package view;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.atomic.AtomicInteger;

import actions.ActionManager;
import actions.CloseApp;
import config.AppConfig;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class MainView extends Stage {
	
	public static final int HEIGHT = 850;
	public static final int WIDTH = 1500;
	
	private static MainView instance;
	
	public static volatile boolean running;
	public static volatile boolean freeze;
	public static volatile boolean outOfMemory;
	
	public static int inputsCounter = 0;
	private volatile AtomicInteger activeInputWorkers;
	private ArrayList<InputView> inputs;
	
	public static int crunchersCount = 0;
	private volatile AtomicInteger activeCruncherWorkers;
	private ArrayList<CruncherView> crunchers;
	
	private volatile AtomicInteger activeOutputWorkers;
	private ArrayList<OutputView> outputs;
	
	//jer ne moze lista viewOva da bude u isto vreme u comboBoxu i na VBoxu prikazan
	private ArrayList<String> cruncherNames;
	
	public ExecutorService inputThreadPool;
	public ForkJoinPool cruncherThreadPool;
	public ExecutorService outputThreadPool;
	
	private ActionManager actionManager;
	
	private InputColumnView inputColumnView;
	private CruncherColumnView cruncherColumnView;
	private OutputColumnView outputColumnView;
	
	public static MainView get() {
		if (instance == null)
			new MainView();
		return instance;
	}
	
	public MainView() {
		instance = this;
		running = true;
		
		AppConfig.readConfig();
		
		activeInputWorkers = new AtomicInteger(0);
		activeCruncherWorkers = new AtomicInteger(0);
		activeOutputWorkers = new AtomicInteger(0);
		
		inputs = new ArrayList<>();
		crunchers = new ArrayList<>();
		outputs = new ArrayList<>();
		cruncherNames = new ArrayList<>();
		actionManager = new ActionManager();
		
		inputThreadPool = Executors.newCachedThreadPool();
		cruncherThreadPool = new ForkJoinPool();
		outputThreadPool = Executors.newCachedThreadPool();
		
		initView();
		
		
		outputColumnView.addOutputComponent(new OutputView(outputColumnView.getPrefWidth() - 20, outputColumnView.getPrefHeight() - 10));
	}
	
	private void initView() {
		HBox pane = new HBox();
		pane.setPrefSize(WIDTH, HEIGHT);
		
		inputColumnView = new InputColumnView(300, HEIGHT);
		cruncherColumnView = new CruncherColumnView(200, HEIGHT);
		outputColumnView = new OutputColumnView(1000, HEIGHT);
		
		pane.getChildren().addAll(inputColumnView, cruncherColumnView, outputColumnView);
		
		Scene scene = new Scene(pane);
		scene.getStylesheets().add("file:styleSheet.css");
		
		setOnCloseRequest(new CloseApp());
		setScene(scene);
		setResizable(false);
		setTitle("Word distribution tool");
		show();
	}
	
	public AtomicInteger getActiveInputWorkers() {
		return activeInputWorkers;
	}
	
	public AtomicInteger getActiveCruncherWorkers() {
		return activeCruncherWorkers;
	}
	
	public AtomicInteger getActiveOutputWorkers() {
		return activeOutputWorkers;
	}
	
	public ActionManager getActionManager() {
		return actionManager;
	}
	
	public ExecutorService getInputThreadPool() {
		return inputThreadPool;
	}
	
	public ForkJoinPool getCruncherThreadPool() {
		return cruncherThreadPool;
	}
	
	public InputColumnView getInputColumnView() {
		return inputColumnView;
	}
	
	public CruncherColumnView getCruncherColumnView() {
		return cruncherColumnView;
	}
	
	public ArrayList<InputView> getInputs() {
		return inputs;
	}

	public ArrayList<CruncherView> getCrunchers() {
		return crunchers;
	}
	
	public ArrayList<String> getCruncherNames() {
		return cruncherNames;
	}
	
	public ArrayList<OutputView> getOutputs() {
		return outputs;
	}
	
	public ExecutorService getOutputThreadPool() {
		return outputThreadPool;
	}
	
}
