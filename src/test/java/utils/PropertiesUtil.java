package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtil {

	static Properties properties = new Properties();

	// This method is used to load the properties file
	public static void loadPropertiesFile() {
		InputStream iStream = null;
		try {
			// Loading properties file from the path
			iStream = new FileInputStream("src/test/resources/config.properties");
			properties.load(iStream);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (iStream != null) {
					iStream.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Method to read the properties from a loaded property file
	 */
	public static String getConfig(String propertyName) {
		//System.out.println("Property:- " + propertyName + " Value:- " + properties.getProperty(propertyName));
		return properties.getProperty(propertyName);
	}

	/*public static void main(String[] args) {
		loadPropertiesFile();
		System.out.print(getPropertyValue("programIdEndPoint"));
	} */
}
