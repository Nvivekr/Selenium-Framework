package google.seleniumtest;

import java.sql.Driver;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

import apps.googlepageobjects.GooglePageObjectManager;
import static apps.utils.SoftAssertion.*;
import apps.utils.TestBase;
import googleusingby.Google;
import googleusingby.Google2;

/**
 * Hello world!
 *
 */
public class TestCaseusingby extends TestBase
{
	private GooglePageObjectManager google;
	private Google goog ;
	private Google2 goog2;
	@Test(priority = 1, description = " Testing the workflow at testmethod by typing vivek")
	public void openGooglepage() throws InterruptedException {
		google = new GooglePageObjectManager();
		goog = google.getLoginPage();
//		goog= google.getGooglePage(driver);
		goog.sendkeysinsearchbox("vivek");
		Thread.sleep(2000);
		
//		goog.enterbuttonsearch();
//		driver.findElement(By.id("xyz"));
		Thread.sleep(2000);
		sAssert(2, 2, "check");
		sAssert.assertAll();
		
	}
	@Test(description = "Testing the workflow at testmethod by typing lionel", enabled=false)
	public void typelionel() throws InterruptedException {
		google = new GooglePageObjectManager();
//		goog = google.getLoginPage();
		goog2= google.getGooglePage();
		goog2.sendkeysinsearchbox("lionel");
//		goog2.enterbuttonsearch();
//		Thread.sleep(2000);
		sAssert(1, 2, ",check");
//		sAssert.assertAll();
	}
}
