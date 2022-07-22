package googleusingxml;

import java.lang.reflect.Method;
import java.util.List;

import org.apache.commons.configuration.XMLConfiguration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


import apps.utils.TestBase;


public class dataextractor {


	public static WebElement getElement(String identifier) {
		WebElement element = null;
		XMLConfiguration config = null;
		try {
			//				System.out.println("inside method"  +"  "+  System.getProperty("user.dir"));
			config = new XMLConfiguration(System.getProperty("user.dir")+"\\src\\main\\java\\Google.xml");
			Method method = By.class.getMethod(config.getString(identifier + "[@locator]"), String.class);
			//				System.out.println( method.invoke(By.class, config.getString(identifier)));
			By by = (By) method.invoke(By.class, config.getString(identifier));
			WebDriverWait wait = new WebDriverWait(TestBase.getDriver(), 90);
			element = wait.until(ExpectedConditions.presenceOfElementLocated(by));
		} catch (Exception e) {
			System.err.println("Object Not found. Please verify if " + identifier + " is configured object-config.xml");
			e.printStackTrace();
		} 
		return element;
	}

	public static WebElement getElementDynamicIndexBased(String identifier, int i) {
		WebElement element = null;
		XMLConfiguration config = null;
		try {
			config = new XMLConfiguration(System.getProperty("user.dir")+"\\src\\main\\java\\Google.xml");
			Method method = By.class.getMethod(config.getString(identifier + "[@locator]"), String.class);
//			System.out.println( config.getString(identifier + "[@locator]"));  //xpath
			identifier = config.getString(identifier).replace("$", "" + i);
			System.out.println("identifier String:" + identifier);
			By by = (By) method.invoke(By.class, identifier);
			// element = DriverFactory.getDriver().findElement(by);
			WebDriverWait wait = new WebDriverWait(TestBase.getDriver(), 2);
			element = wait.until(ExpectedConditions.presenceOfElementLocated(by));
		} catch (Exception e) {
			System.out.println("Object Not found. Please verify if " + identifier + " is configured object-config.xml");
			e.printStackTrace();
		} finally {
			config.clear();
		}
		return element;
	}
	
	public static List<WebElement> getElements(String identifier) {
		List<WebElement> element = null;
		XMLConfiguration config = null;
		try {
			config = new XMLConfiguration(System.getProperty("user.dir")+"\\src\\main\\java\\Google.xml");
			System.out.println(System.getProperty("user.dir"));
			System.out.println("identifier String:" + config.getString(identifier));
			Method method = By.class.getMethod(config.getString(identifier + "[@locator]"), String.class);
			By by = (By) method.invoke(By.class, config.getString(identifier));
			WebDriverWait wait = new WebDriverWait(TestBase.getDriver(), 2);
			element = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(by));
		} catch (Exception e) {
			System.err.println("Object Not found. Please verify if " + identifier + " is configured object-config.xml");
			e.printStackTrace();
		} finally {
			config.clear();
		}
		return element;
	}

}
