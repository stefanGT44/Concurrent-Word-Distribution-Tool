package cruncher_component;

import java.util.List;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import output_component.OutputMainThread;

public class CruncherProgressLabelRunnable implements Runnable {

	private VBox progress;
	private String text;
	private boolean add;
	private List<OutputMainThread> outputs;
	private int arity;
	
	public CruncherProgressLabelRunnable(VBox progress, String text, boolean add, List<OutputMainThread> outputs, int arity) {
		this.progress = progress;
		this.text = text;
		this.add = add;
		this.outputs = outputs;
		this.arity = arity;
	}

	@Override
	public void run() {
		//synchronized (progress) {
			if (add) {
				progress.getChildren().add(new Label(text));
				progress.setVisible(true);
			} else {
				Label l2 = null;
				for (Node node : progress.getChildren()) {
					if (node instanceof Label) {
						Label label = (Label) node;
						if (label.getText().equals(text)) {
							l2 = label;
							break;
						}
					}
				}
				progress.getChildren().remove(l2);
				if (progress.getChildren().size() == 1)
					progress.setVisible(false);
			}
		//}
		if (add) {
				for (OutputMainThread output: outputs)
					output.addNewResult("*" + text+"-arity" + arity);
		} else {
			String str = "*" + text + "-arity" + arity;
				for (OutputMainThread output: outputs)
					output.renameResult(str);
		}
	}

}
