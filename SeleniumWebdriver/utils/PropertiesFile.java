package apps.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesFile {

	public static Properties readPropertiesFile() throws IOException {

		FileReader reader=new FileReader("src/test/resources/config.properties");  

		Properties p=new Properties();  
		p.load(reader);
		/*System.out.println(p.getProperty("LOGINID"));  
	    System.out.println(p.getProperty("PWD"));*/
		return p;
		

	}

	public static void main(String[] args) throws IOException {
		readPropertiesFile();
	}

}
