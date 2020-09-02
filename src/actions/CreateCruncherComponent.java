package actions;

import java.util.Optional;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.TextInputDialog;
import view.CruncherColumnView;
import view.CruncherView;
import view.InputView;
import view.MainView;

public class CreateCruncherComponent implements EventHandler<ActionEvent>{

	@Override
	public void handle(ActionEvent event) {
		TextInputDialog dialog = new TextInputDialog("1");
		dialog.setTitle("Create cruncher");
		dialog.setHeaderText("Please enter desired arity: ");
		dialog.setContentText(null);
		
		int arity = 1;
		
		Optional<String> result = dialog.showAndWait();
		if (!result.isPresent()) {
			return;
		}
		
		try {
			if (result.isPresent())
			arity = Integer.parseInt(result.get());
		} catch (NumberFormatException e) {
			System.out.println("Given arity not a number, using default arity 1");
			arity = 1;
		}
		
		if (!result.isPresent())
			arity = 1;
		
		CruncherColumnView ccv = MainView.get().getCruncherColumnView();
		CruncherView cv = new CruncherView(MainView.crunchersCount++, ccv.getWidth() - 8, arity);
		MainView.get().getCrunchers().add(cv);
		MainView.get().getCruncherNames().add("Counter " + cv.getCruncherId());
		
		cv.getCruncherMainThread().linkOutput(MainView.get().getOutputs().get(0).getOutputMainThread());
		
		for (InputView iv: MainView.get().getInputs()) {
			iv.refreshComboBox();
		}
		
		ccv.addCruncher(cv);
	}
	
}
