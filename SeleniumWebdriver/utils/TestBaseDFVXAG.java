package apps.utils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.asserts.SoftAssert;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import io.github.bonigarcia.wdm.WebDriverManager;
import apps.reportmanager.ExtentManager;
import apps.reportmanager.ExtentTestManager;
import apps.reportmanager.Reporter;

public class TestBaseDFVXAG {
	public WebDriver driver;
	public Reporter reporter;
	//public ExcelUtil reader;
	public SoftAssert sAssert;
	public static ThreadLocal<ExtentTest> parentTest = new ThreadLocal<ExtentTest>();
	public static ThreadLocal<ExtentTest> test = new ThreadLocal<ExtentTest>();

	private static String reportFile = "";

	public TestBaseDFVXAG() {
		File folder = new File(getOutputFolder());
		folder.mkdirs();
	}

	public void setDriver(WebDriver d) {
		driver = d;
	}

	@BeforeSuite
	public void beforeSuite(ITestContext itc) {
		reportFile = getOutputFolder() + itc.getSuite().getName() + ".html";
		ExtentManager.createInstance(reportFile);
		ExtentTestManager.setReporter(ExtentManager.getInstance());
	}

	/*@BeforeTest
	public void beforeTest(ITestContext iTC) {
		CommonUtil.loadProps();
		reporter = new Reporter(iTC.getCurrentXmlTest().getName());
	}

	@BeforeClass
	public void browserInitilization(ITestContext iTC) throws InterruptedException, IOException {
		
		ExtentTest parent = ExtentManager.getInstance()
				.createTest(iTC.getCurrentXmlTest().getName() + "-" + getClass().getSimpleName());
		parentTest.set(parent);
		
			sAssert = new SoftAssert();
			
	}*/
	
	@BeforeTest(alwaysRun=true)
    public void beforeTest(ITestContext iTC) {
        CommonUtil.loadProps();
        ExtentTest parent = ExtentManager.getInstance()
                .createTest(iTC.getCurrentXmlTest().getName());
        parentTest.set(parent);
        sAssert = new SoftAssert();
    }
   
       
    @BeforeClass(alwaysRun=true)
    public void browserInitilization(ITestContext iTC) throws InterruptedException, IOException {
        reporter = new Reporter(iTC.getCurrentXmlTest().getName());
    }

	@BeforeMethod
	public void beforeMethod(Method method, ITestResult result) {
		sAssert = new SoftAssert();
		//driver.manage().deleteAllCookies();
		ExtentTest child = parentTest.get().createNode(method.getName());
		test.set(child);
		reporter.initlogger(child);
		System.out.println("==========================================");
		System.out.println("Started executing test:" + result.getMethod().getMethodName());
		System.out.println("=====================================");
		reporter.log(Status.INFO, result.getMethod().getDescription());
	}

	@AfterMethod
	public void afterMethod(ITestResult result) {
		if (result.getStatus() == ITestResult.FAILURE) {
			test.get().fail(result.getThrowable());
			reporter.log("TestScript failed: " + result.getMethod().getMethodName(), driver);
		} else if (result.getStatus() == ITestResult.SKIP)
			test.get().skip(result.getThrowable());
		else if (result.getStatus() == ITestResult.SUCCESS)
			test.get().pass("Test passed");
		System.out.println("=====================================");
		System.out.println("Completed executing test:" + result.getMethod().getMethodName());
		System.out.println("==========================================");
		//reporter.logWithScreenshot("Test case Completed", driver);
	}

	/*@AfterClass
	public void afterClass() {
		driver.quit();
	}*/

	@AfterSuite
	public void afterSuite() {
		ExtentManager.getInstance().flush();
		System.gc();
	}

	public void waitforSec(int milliSeconds) {
		try {
			Thread.sleep(milliSeconds);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	protected String getOutputFolder() {
		return System.getProperty("user.dir") + "/reports/";
	}

	public void sAssert(String actual, String expected, String message) {
		if (expected.equals(actual))
			reporter.log(Status.PASS, message + " -Success.");
		else
			reporter.log(message + "-Failed.<br>Expected : " + expected + " <br>Actual : " + actual, driver);
		sAssert.assertEquals(actual, expected, message + " -Failed");
	}

	public void sAssert(boolean actual, boolean expected, String message) {
		if (expected==actual)
			reporter.log(Status.PASS, message + "-Success.");
		else
			reporter.log(message + "-Failed.<br>Expected : " + expected + " <br>Actual : " + actual, driver);
		sAssert.assertEquals(actual, expected, message + "-Failed");
	}

	
	public void sAssertTrue(Boolean condition, String message) {
		if (condition)
			reporter.log(Status.PASS, message + "- Success");
		else
			reporter.log(message + "- Failed", driver);
		sAssert.assertTrue(condition, message + "-Failed");
	}

	public void sAssertFalse(Boolean condition, String message) {
		if (condition)
			reporter.log(message + "- Failed .", driver);
		else
			reporter.log(Status.PASS, message + "- Success.");
		sAssert.assertFalse(condition, message + "-Success");
	}

	
	
}
