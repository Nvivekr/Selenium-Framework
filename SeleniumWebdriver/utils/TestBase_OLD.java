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

public class TestBase_OLD {
	public WebDriver driver;
	public Reporter reporter;
	//public ExcelUtil reader;
	public SoftAssert sAssert;
	public static ThreadLocal<ExtentTest> parentTest = new ThreadLocal<ExtentTest>();
	public static ThreadLocal<ExtentTest> test = new ThreadLocal<ExtentTest>();

	private static String reportFile = "";

	public TestBase_OLD() {
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

	@BeforeTest
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
						
		//String browser = "Chrome";
		String browser = PropertiesFile.readPropertiesFile().getProperty("browserName");
		iTC.getCurrentXmlTest().getSuite().getParameters().put("browser", browser);
		//Later will Replace with enum
		switch(browser.toLowerCase()) 
		{ 
		case "chrome": 
			//System.out.println("Chrome Browser");
			
			ChromeOptions options = new ChromeOptions();
			options.addArguments("--headless");
			options.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
			options.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS,true);
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();
			break; 
			
		case "ie": 
			System.out.println("IE Browser");
			/*DesiredCapabilities cap=DesiredCapabilities.internetExplorer();
			cap.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);*/
			
			WebDriverManager.iedriver().browserVersion("11").setup();
			driver = new InternetExplorerDriver();
			//driver.get("javascript:document.getElementById('overridelink').click();");
			break;
									
		case "firefox": 
			System.out.println("Firefox");
			WebDriverManager.firefoxdriver().arch32().setup();
			driver = new FirefoxDriver();
			break; 
		default: 
			System.out.println("Browser Not Supported");
		} 

		if (driver!=null) {
		System.out.println("Driver Loaded for " + browser);
			
			driver.manage().window().maximize();
			//driver.manage().timeouts().implicitlyWait(25, TimeUnit.SECONDS);
			//driver.manage().deleteAllCookies();
		}else {
			System.exit(0);
		}
		
		String className = this.getClass().getName();
		//System.out.println("Current Class name for Execution  : " +this.getClass().getName());
		if (className.contains("DFV")) {
			driver.get(ConstUtil.DFVURL);
		System.out.println("Launched DFV application URL  : " +ConstUtil.DFVURL);
	    } 
		else if (className.contains("CASA")) {
			driver.get(ConstUtil.CASAURL);
		System.out.println("Launched CASA application URL  : " +ConstUtil.CASAURL);
	    }
		else if (className.contains("CAS")) {
			driver.get(ConstUtil.CASURL);
		System.out.println("Launched CAS application URL  : " +ConstUtil.CASURL);
	    } else if (className.contains("XAG")) {
			driver.get(ConstUtil.XAGURL);
		System.out.println("Launched XAG application URL  : " +ConstUtil.XAGURL);
	    } else if (className.contains("XDH")) {
			driver.get(ConstUtil.XDHURL);
		System.out.println("Launched XDH application URL  : " +ConstUtil.XDHURL);
	    } else if (className.contains("IMR")) {
			driver.get(ConstUtil.IMRURL);
		System.out.println("Launched IMR application URL  : " +ConstUtil.IMRURL);
	    }
		else 
			System.err.println("No Class found");
		Thread.sleep(4000);
	}

	@BeforeMethod
	public void beforeMethod(Method method, ITestResult result) {
		sAssert = new SoftAssert();
		driver.manage().deleteAllCookies();
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
		reporter.logWithScreenshot("Test case Completed", driver);
	}

	@AfterClass
	public void afterClass() {
		driver.quit();
	}

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

	public void sAssert(int actual, int expected, String message) {
		if (expected == actual)
			reporter.log(Status.PASS, message + "-Success.");
		else
			reporter.log(message + "-Failed.<br>Expected : " + expected + " <br>Actual : " + actual, driver);
		sAssert.assertEquals(actual, expected, message + "-Failed");
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

	
	public void logOut() {
		reporter.log(Status.INFO, "Logging out and closing the window");
		driver.manage().deleteAllCookies();
		driver.close();
	}
	
	/*
				
	Make sure that browser zoom level is set to 100%; IE browser View menu --> Zoom --> 100%
	Make sure that "protection mode" is enabled for all the zones; IE browser Tools menu --> Internet Options --> Security tab --> navigate to Internet, 
	Local intranet, Trusted sites and Restricted site and check Protection Mode checkbox is checked

	}*/
	
}
