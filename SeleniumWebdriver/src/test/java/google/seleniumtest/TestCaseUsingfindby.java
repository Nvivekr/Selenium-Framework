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
import googleusingxml.dataextractor;

/**
 * Hello world!
 *
 */
public class TestCaseUsingfindby extends TestBase
{
	private GooglePageObjectManager google;
	private Google goog;
	private Google2 goog2;
	private dataextractor dt;
	@Test(priority = 1, description = " Testing the workflow at testmethod by typing vivek")
	public void openGooglepage() throws InterruptedException {
		google = new GooglePageObjectManager();
//		goog2 = google.getLoginPage();
		goog2= google.getGooglePage();
		goog2.sendkeysinsearchbox("red");
		System.out.println(driver.findElement(By.id("Mses6b")).getText());
		System.out.println(driver.findElement(By.className("uU7dJb")).getText());
//		goog2.enterbuttonsearch();
		Thread.sleep(2000);
		sAssert(1, 2, "check");
		//added 15:15
		Thread.sleep(2000);
		
		sAssert.assertAll();
		
	}
	@Test(description = "Testing the workflow at testmethod by typing lionel", enabled=false)
	public void typelionel() throws InterruptedException {
		google = new GooglePageObjectManager();
//		goog = google.getLoginPage();
//		goog2= google.getGooglePage(driver);
		goog2.sendkeysinsearchbox("lionel");
//		goog2.enterbuttonsearch();
//		Thread.sleep(2000);
		sAssert(1, 2, ",check");
//		sAssert.assertAll();
	}
}
