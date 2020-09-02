package actions;

public class ActionManager {

	private CreateInputComponent createInputComponent;
	private CreateCruncherComponent createCruncherComponent;
	private DiskSelectCBAction diskSelectCBAction;
	private AddDir addDir;
	private DirectorySelected directorySelected;
	private RemoveDir removeDir;
	private StartInputWorker startInputWorker;
	private CruncherSelectCBAction cruncherSelectCBAction;
	private LinkCruncher linkCruncher;
	private SelectCruncherListView selectCruncherListView;
	private UnlinkCruncher unlinkCruncher;
	private ResultsSelect resultsSelect;
	private SingleResult singleResult;
	private SumResult sumResult;
	private RemoveInput removeInput;
	private RemoveCruncher removeCruncher;
	
	public ActionManager() {
		createInputComponent = new CreateInputComponent();
		createCruncherComponent = new CreateCruncherComponent();
		diskSelectCBAction = new DiskSelectCBAction();
		addDir = new AddDir();
		directorySelected = new DirectorySelected();
		removeDir = new RemoveDir();
		startInputWorker = new StartInputWorker();
		cruncherSelectCBAction = new CruncherSelectCBAction();
		linkCruncher = new LinkCruncher();
		selectCruncherListView = new SelectCruncherListView();
		unlinkCruncher = new UnlinkCruncher();
		resultsSelect = new ResultsSelect();
		singleResult = new SingleResult();
		sumResult = new SumResult();
		removeInput = new RemoveInput();
		removeCruncher = new RemoveCruncher();
	}
	
	public RemoveCruncher getRemoveCruncher() {
		return removeCruncher;
	}
	
	public RemoveInput getRemoveInput() {
		return removeInput;
	}
	
	public SumResult getSumResult() {
		return sumResult;
	}
	
	public SingleResult getSingleResult() {
		return singleResult;
	}
	
	public ResultsSelect getResultsSelect() {
		return resultsSelect;
	}
	
	public UnlinkCruncher getUnlinkCruncher() {
		return unlinkCruncher;
	}
	
	public SelectCruncherListView getSelectCruncherListView() {
		return selectCruncherListView;
	}
	
	public LinkCruncher getLinkCruncher() {
		return linkCruncher;
	}
	
	public CruncherSelectCBAction getCruncherSelectCBAction() {
		return cruncherSelectCBAction;
	}
	
	public StartInputWorker getStartInputWorker() {
		return startInputWorker;
	}
	
	public RemoveDir getRemoveDir() {
		return removeDir;
	}
	
	public DirectorySelected getDirectorySelected() {
		return directorySelected;
	}
	
	public AddDir getAddDir() {
		return addDir;
	}
	
	public CreateInputComponent getCreateInputComponent() {
		return createInputComponent;
	}
	
	public CreateCruncherComponent getCreateCruncherComponent() {
		return createCruncherComponent;
	}
	
	public DiskSelectCBAction getDiskSelectCBAction() {
		return diskSelectCBAction;
	}
	
}
