package org.mql.jcodeeditor.properties;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import org.mql.jcodeeditor.Context;

public class PropertiesManager {
	public static String readProperty(String property) {
		Properties properties = new Properties();
		File propertiesFile = new File(Context.getSettingPropertiesPath());
		try {
			if (!propertiesFile.exists()) {
				propertiesFile.getParentFile().mkdirs();
				propertiesFile.createNewFile();
				// TODO write default properties
			}
			try (FileInputStream input = new FileInputStream(propertiesFile)) {
				properties.load(input);

				String result = properties.getProperty(property);
				System.out.println("Last Opened File: " + result);
				if (result != null) {
					return result;
				} else {
					return "";
				}

			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return "";

	}

	public static void writeProperty(String property, String value) {
		Properties properties = new Properties();
		File propertiesFile = new File(Context.getSettingPropertiesPath());

		// load properties
		try (FileInputStream input = new FileInputStream(propertiesFile)) {
			properties.load(input);
			properties.setProperty(property, value);
		} catch (IOException ex) {
			ex.printStackTrace();
		}

		// write new property
		try {
			if (!propertiesFile.exists()) {
				propertiesFile.getParentFile().mkdirs();
				propertiesFile.createNewFile();

			}
			try (FileOutputStream output = new FileOutputStream(propertiesFile)) {
				properties.store(output, "Application Settings");
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
}
