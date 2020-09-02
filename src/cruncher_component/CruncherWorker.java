package cruncher_component;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.RecursiveTask;

import actions.OutOfMemoryAlert;
import config.AppConfig;
import input_component.TextObject;
import javafx.application.Platform;
import javafx.scene.layout.VBox;
import output_component.OutputMainThread;
import view.MainView;

public class CruncherWorker extends RecursiveTask<Map<String, Integer>> {

	private TextObject textObj;
	private int start;
	private int arity;
	private VBox progress;

	private List<OutputMainThread> outputs;

	public CruncherWorker(TextObject textObj, int start, int arity, VBox progress, List<OutputMainThread> outputs) {
		this.textObj = textObj;
		this.start = start;
		this.arity = arity;
		this.progress = progress;
		this.outputs = outputs;
	}

	@Override
	protected Map<String, Integer> compute() {
		MainView.get().getActiveCruncherWorkers().incrementAndGet();
		Map<String, Integer> toReturn = null;
		if (start == 0) {
			Platform.runLater(new CruncherProgressLabelRunnable(progress, textObj.getName(), true, outputs, arity));
			//long curr = System.currentTimeMillis();
			//while (System.currentTimeMillis() - curr < 10000) {}
		}
		if (start + AppConfig.counterDataLimit >= textObj.getData().length()) {

			toReturn = computeDistribution(textObj.getData().length());

		} else {
			// compute end
			int end = start + AppConfig.counterDataLimit;
			String text = textObj.getData();
			while (end != text.length() && !Character.isWhitespace(text.charAt(end)))
				end++;

			if (end == text.length()) {

				toReturn = computeDistribution(end);

			} else {

				// treba implementirati
				int newStart = end;
				// String preklapanja = "";
				if (arity > 1) {
					int test = 0;
					while (test < arity - 1) {

						while (newStart != start && Character.isWhitespace(text.charAt(newStart))) {
							// preklapanja += text.charAt(newStart);
							newStart--;
						}

						while (newStart != start && !Character.isWhitespace(text.charAt(newStart))) {
							// preklapanja += text.charAt(newStart);
							newStart--;
						}

						while (newStart != start && Character.isWhitespace(text.charAt(newStart))) {
							// preklapanja += text.charAt(newStart);
							newStart--;
						}

						test++;

					}
				}

				// System.out.println(text.substring(start, end));
				// System.out.println("Preklapanja: " + preklapanja);
				// System.out.println("Start = " + start + ", end = " + end + ", newStart = " +
				// newStart);

				if (arity > 1 && newStart <= start)
					newStart = end;

				// create new task with new start
				CruncherWorker newWorker = new CruncherWorker(textObj, newStart + 1, arity, progress, outputs);
				newWorker.fork();

				// compute distribution
				toReturn = computeDistribution(end);

				// wait for other task result
				Map<String, Integer> otherResult = newWorker.join();

				// combine results and return

				try {

					for (Map.Entry<String, Integer> entry : otherResult.entrySet()) {
						if (toReturn.containsKey(entry.getKey()))
							toReturn.put(entry.getKey(), toReturn.get(entry.getKey()) + entry.getValue());
						else
							toReturn.put(entry.getKey(), entry.getValue());
					}

				} catch (OutOfMemoryError e) {
					if (!MainView.outOfMemory) {
						MainView.outOfMemory = true;
						Platform.runLater(new OutOfMemoryAlert("Out of memory while counting, shutting down..."));
					}
					e.printStackTrace();
				}

			}
		}
		
		if (start == 0) 
			Platform.runLater(new CruncherProgressLabelRunnable(progress, textObj.getName(), false, outputs, arity));
		
		
		MainView.get().getActiveCruncherWorkers().decrementAndGet();
		return toReturn;
	}

	private Map<String, Integer> computeDistribution(int end) {
		Map<String, Integer> map = new HashMap<>();
		try {
			StringBuilder sb = new StringBuilder();
			String text = textObj.getData();
			int textLength = text.length();
			int position = start;
			int helpPos = 0;
			while (position < end) {
				String bag[] = new String[arity];

				// pronalazenje reci
				for (int j = 0; j < arity; j++) {

					while (position != textLength && position != end && Character.isWhitespace(text.charAt(position)))
						position++;

					if (arity > 1 && j == 1)
						helpPos = position;

					while (position != textLength && position != end && !Character.isWhitespace(text.charAt(position)))
						sb.append(text.charAt(position++));

					while (position != textLength && position != end && Character.isWhitespace(text.charAt(position)))
						position++;

					// ako je position kraj fajla a trenutna vreca nije puna, onda ne treba gledati
					// trenutnu vrecu

					if ((position == textLength || position == end) && j < arity - 1)
						return map;

					bag[j] = sb.toString();
					sb.setLength(0);

				}

				Arrays.sort(bag);
				String s = Arrays.toString(bag);
				if (map.containsKey(s))
					map.put(s, map.get(s) + 1);
				else
					map.put(s, 1);

				if (arity > 1)
					position = helpPos;
			}
		} catch (OutOfMemoryError e) {
			if (!MainView.outOfMemory) {
				MainView.outOfMemory = true;
				Platform.runLater(new OutOfMemoryAlert("Out of memory while counting, shutting down..."));
			}
			e.printStackTrace();
		}
		return map;
	}

}
