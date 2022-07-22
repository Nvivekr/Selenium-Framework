package apps.utils;


import java.sql.Time;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

/**
 * <b>Description</b><br>
 * This class should be used to store all custom wait methods in the framework
 * 
 */
public class WaitUtil {

	/**
	 * @param driver
	 * This method can be used for waiting till the page is loaded
	 */
	public static synchronized void waitTillPageIsLoaded(WebDriver driver) {
		ExpectedCondition<Boolean> expectation = new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver driver) {
				return ((JavascriptExecutor) driver).executeScript("return document.readyState").toString()
						.equals("complete");
			}
		};
		try {
			Thread.sleep(1000);
			WebDriverWait wait = new WebDriverWait(driver, 30);
			wait.until(expectation);
		} catch (Throwable error) {
			Assert.fail("Timeout waiting for Page Load Request to complete.");
		}
	}
	
	public static synchronized void waittillpageload(WebDriver webdriver) {
		try {
			System.out.println("inside page load function");
		new WebDriverWait(webdriver, 30).until(
			      driver -> ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete"));
	}catch (Throwable error) {
		// TODO: handle exception
		Assert.fail("Timeout waiting for Page Load Request to complete.");
		
	}
	}
	
	public static synchronized void waitwithseconds(WebDriver driver, int time) {
		WebDriverWait wait = new WebDriverWait(driver, time);
		
	}

}