package apps.reporting;

import java.io.IOException;

import org.openqa.selenium.WebDriver;

import com.assertthat.selenium_shutterbug.core.Shutterbug;
import com.assertthat.selenium_shutterbug.utils.web.ScrollStrategy;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;

public class Reporter {
	
	private ExtentReports extentreport;
	private ExtentTest test;
	private int screenshotnum = 1;

	public Reporter() {
	}

	public void createReport() {
		extentreport = ExtentManager.getInstance();
	}

	public void initlogger(ExtentTest test) {
		this.test = test;
	}

	public void log(Status status, String msg) {
		if (status == Status.PASS) {
			test.log(Status.PASS, MarkupHelper.createLabel(msg, ExtentColor.GREEN));
			System.out.println(msg);
		} else if (status == Status.SKIP) {
			test.log(Status.INFO, MarkupHelper.createLabel(msg, ExtentColor.ORANGE));
			System.out.println("SKIPPED - " + msg);
		} else if (status == Status.ERROR) {
			test.log(status, MarkupHelper.createLabel(msg, ExtentColor.RED));
			System.err.println(msg);
		} else if (status == Status.INFO) {
			test.log(Status.INFO, MarkupHelper.createLabel(msg, ExtentColor.WHITE));
			System.out.println(msg);
		} else if (status == Status.WARNING) {
			test.log(Status.WARNING, MarkupHelper.createLabel(msg, ExtentColor.INDIGO));
			System.out.println(msg);
		} else if (status == Status.FAIL) {
			test.log(status, MarkupHelper.createLabel(msg, ExtentColor.RED));
			System.out.println(msg);
//			org.testng.Assert.fail();
		}
	}

	public void log(String msg, WebDriver driver) {
		try {
			test.log(Status.FAIL, msg, MediaEntityBuilder.createScreenCaptureFromPath(this.captureScreen(driver)).build());
//			System.err.println(msg);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void logWithScreenshot(String msg, WebDriver driver) {
		try {
			test.log(Status.INFO, msg,
					MediaEntityBuilder.createScreenCaptureFromPath(this.captureScreen(driver)).build());
			System.out.println(msg);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String captureScreen(WebDriver driver) {
		String filePath = "screenshots/" +"SS"+ screenshotnum + ".png";
		Shutterbug.shootPage(driver, ScrollStrategy.WHOLE_PAGE_CHROME, 500, true)
				.withName("SS"+screenshotnum)
				.save(System.getProperty("user.dir") + "/Reports/" + "screenshots/");
		screenshotnum += 1;
		return filePath;
	}

	public void close() {
		extentreport.flush();
	}
}
