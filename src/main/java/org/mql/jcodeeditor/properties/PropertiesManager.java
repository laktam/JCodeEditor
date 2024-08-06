package org.mql.jcodeeditor.properties;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesManager {
	public static String readProperty(String property) {
		Properties properties = new Properties();

		try (FileInputStream input = new FileInputStream("settings.properties")) {
			properties.load(input);

			String result = properties.getProperty(property);
			System.out.println("Last Opened File: " + result);
			return result;
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return "";
	}

	public static void writeProperty(String property, String value) {
		Properties properties = new Properties();
		properties.setProperty(property, value);

		try (FileOutputStream output = new FileOutputStream("settings.properties")) {
			properties.store(output, "Application Settings");
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
}
