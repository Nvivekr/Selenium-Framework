package google.seleniumtest;

import java.awt.AWTException;
import java.sql.Driver;

import org.testng.Assert;
import org.testng.annotations.Test;

import apps.googlepageobjects.GooglePageObjectManager;
import apps.utils.CommonUtilusingelement;
import apps.utils.TestBase;
import googleusingby.Google;
import googleusingby.Google2;
import googleusingby.Googleusingxml;
import static apps.utils.SoftAssertion.*;
import googleusingxml.dataextractor;

/**
 * Hello world!
 *
 */
public class TestCaseUsingXml extends TestBase
{

	private CommonUtilusingelement common;
	private GooglePageObjectManager pageobjectmanager;
	private Googleusingxml xml;
	@Test(priority = 1, description = " Testing the workflow at testmethod by typing vivek", enabled =true)
	public void openGooglepage() throws InterruptedException, AWTException {
		pageobjectmanager = new GooglePageObjectManager();

		xml = pageobjectmanager.getGoogleusingxml();
		xml.enterandsearchname();

		Thread.sleep(2000);
		sAssert(2, 2, "check");
		Thread.sleep(2000);
		sAssert.assertAll();

	}
	@Test(description = "Testing the workflow at testmethod by typing lionel", enabled=false)
	public void typelionel() throws InterruptedException {
		try {
		dataextractor.getElementDynamicIndexBased("LoginPage.StartingSoonDynamicUserNameLbl", 5);
		}catch (Exception e) {
			// TODO: handle exception
			System.out.println("error");
		}

	}
}


//System.out.println("Text extracted from google with xpath" +dataextractor.getElement("LoginPage.gmail").getText());
//System.out.println("Setting text with id: "+ dataextractor.getElement("LoginPage.Settings").getText());
//System.out.println("Signinbutton using classname check : "+ dataextractor.getElement("LoginPage.signinbutton").getText());
