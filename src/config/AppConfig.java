package config;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class AppConfig {
	
	public final static String path = "app.properties";
	
	public static long fileInputSleepTime;
	public static List<String> disks;
	public static int counterDataLimit;
	public static long sortProgressLimit;
	
	public static void readConfig() {
		
		Properties properties = new Properties();
		try {
			properties.load(new FileInputStream(new File(path)));
		} catch (IOException e) {
			System.out.println("Error reading config file!");
			System.exit(0);
		}
		
		try {
			fileInputSleepTime = Long.parseLong(properties.getProperty("file_input_sleep_time"));
		} catch (NumberFormatException e) {
			System.out.println("Problem reading file_input_sleep_time");
			System.exit(0);
		}
		
		String disksString = properties.getProperty("disks");
		if (disksString == null) {
			System.out.println("Error reading disks");
			System.exit(0);
		}
		
		disks = Arrays.asList(disksString.split(";"));
		
		try {
			counterDataLimit = Integer.parseInt(properties.getProperty("counter_data_limit"));
		} catch (NumberFormatException e) {
			System.out.println("Error reading counter_data_limit");
			System.exit(0);
		}
		
		try {
			sortProgressLimit = Long.parseLong(properties.getProperty("sort_progress_limit"));
		} catch (NumberFormatException e) {
			System.out.println("Error reading sort_progress_limit");
			System.exit(0);
		}
		
		System.out.println("Config: ");
		System.out.println("file_input_sleep_time = " + fileInputSleepTime);
		System.out.println("disks: " + disks);
		System.out.println("counter_data_limit = " + counterDataLimit);
		System.out.println("sort_progress_limit = " + sortProgressLimit);
		
	}
	
}
