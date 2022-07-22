package apps.utils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;

import apps.enums.DriverType;
import apps.reporting.ExtentManager;
import apps.reporting.ExtentTestManager;
import apps.reporting.Reporter;
import apps.reporting.ReportManager;
import io.github.bonigarcia.wdm.WebDriverManager;


public class TestBase {
	public static WebDriver driver;
	public static Reporter reporter;
	//public ExcelUtil reader;
//	protected static WebDriverWait wait;
	public static SoftAssert sAssert;
	private String strURL;
	String time,date;
//	public ExtentTest parent;
	public static ReportManager rm;
	public static ThreadLocal<ExtentTest> parentTest = new ThreadLocal<ExtentTest>();
	public static ThreadLocal<ExtentTest> test = new ThreadLocal<ExtentTest>();

	private static String reportFile = "";

	public TestBase() {
		File folder = new File(getOutputFolder());
		folder.mkdirs();
		SimpleDateFormat formatter = new SimpleDateFormat("HH.mm.ss"); 
		time = formatter.format(Calendar.getInstance().getTime());
		
		formatter = new SimpleDateFormat("dd-MM-yy");
		date = formatter.format(Calendar.getInstance().getTime());
		sAssert = new SoftAssert();
//		wait = new WebDriverWait(driver, 60);
	}

	public static WebDriver getDriver() {
		return driver;
	}

	@BeforeSuite(alwaysRun=true)
	public void beforeSuite(ITestContext itc) {
//		reportFile = getOutputFolder() + itc.getSuite().getName()+"  "+date+" "+time + ".html";
		reportFile = getOutputFolder() + itc.getSuite().getName()+".html"; //suitename will be taken from testng xml
		ExtentManager.createInstance(reportFile);
		ExtentTestManager.setReporter(ExtentManager.getInstance());
	}

	@BeforeTest(alwaysRun=true)
	public void beforeTest(ITestContext iTC) {
//		methodnum =0;
//		lastPriority =0;
		CommonUtil.loadProps();
		ExtentTest parent = ExtentManager.getInstance()
				.createTest(iTC.getCurrentXmlTest().getName());// test name in testng xml will be taken
//		parent = ExtentManager.getInstance()
//				.createTest(iTC.getCurrentXmlTest().getName());
		parentTest.set(parent);
		
	}
	
		
	@BeforeClass(alwaysRun=true)
	public void browserInitilization(ITestContext iTC) throws InterruptedException, IOException {
		rm = new ReportManager(reporter);
		reporter = rm.getReporter();
	}

	@BeforeMethod(alwaysRun=true)
	public void beforeMethod(Method method, ITestContext iTC) {
		
		Test test1 = method.getAnnotation(Test.class);
		
		
		String strTestCaseName = method.getDeclaringClass().getPackage().getName();
		strTestCaseName = strTestCaseName.toLowerCase();
		
		DriverType driverType = DriverType.DEFAULT;
		
		if (strTestCaseName.contains("google")) {
			strURL = ConstUtil.google;
			driverType = ConstUtil.chrome;
		}
//		} else if(strTestCaseName.contains("casa")) {
//			strURL = ConstUtil.CASAURL;
//			driverType = ConstUtil.CASADRIVER;
//		} else if(strTestCaseName.contains("cas")) {
//			strURL = ConstUtil.CASURL;
//			driverType = ConstUtil.CASDRIVER;
//		} else if(strTestCaseName.contains("dfv")) {
//			strURL = ConstUtil.DFVURL;
//			driverType = ConstUtil.DFVDRIVER;
//		} else if(strTestCaseName.contains("xdh")) {
//			strURL = ConstUtil.XDHURL;
//			driverType = ConstUtil.XDHDRIVER;
//		} else if(strTestCaseName.contains("imr")) {
//			strURL = ConstUtil.IMRURL;
//			driverType = ConstUtil.IMRDRIVER;
//		} else if (strTestCaseName.contains("gst")) {
//			strURL = ConstUtil.GSTURL;
//			driverType = ConstUtil.GSTDRIVER;
//		}

		iTC.getCurrentXmlTest().getSuite().getParameters().put("browser", driverType.toString());
		sAssert = new SoftAssert();
						
		switch(driverType) 
		{ 
		case CHROME: 
			ChromeOptions options = new ChromeOptions();
//			options.addArguments("--headless");
			options.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
			options.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS,true);
			WebDriverManager.chromedriver().setup();  //driver download, driver init
			driver = new ChromeDriver(options); //driver object
			
			driver.get(strURL);  // www.google.com   http
			WaitUtil.waittillpageload(driver);
			break; 
			
//		case INTERNETEXPLORER:
//		case DEFAULT: 
//			WebDriverManager.iedriver().browserVersion("11").setup();
//			InternetExplorerOptions ieoptions = new InternetExplorerOptions();
//			ieoptions.disableNativeEvents();
//			ieoptions.setCapability("disable-popup-blocking", true);
//			ieoptions.setCapability("enablePersistentHover", true);
//			ieoptions.requireWindowFocus();
//			ieoptions.withInitialBrowserUrl(strURL);
//			driver = new InternetExplorerDriver(ieoptions);
//			break;
//									
//		case FIREFOX:
//			System.out.println("Firefox");
//			WebDriverManager.firefoxdriver().arch32().setup();
//			driver = new FirefoxDriver();
//			driver.get(strURL);
//			break; 
		default:
			driver = null;
			System.out.println("Browser Not Supported");
		} 

		if (driver!=null) {
		System.out.println("Driver Loaded for " + driverType.toString());
		driver.manage().window().maximize();
		}
		else {
			System.exit(0);
		}
		
		System.out.println("Test description is " + test1.description());
		System.out.println("==========================================");
		System.out.println("=====================================");
//		System.out.println("Started executing test:" + iTC.getAllTestMethods()[methodnum].getDescription());
		System.out.println("Method Name executing: "+ method.getName());
		System.out.println("=====================================");
		String strMethodName = test1.description();
//		String strMethodName = iTC.getAllTestMethods()[methodnum].getDescription();
		System.out.println("Test Case Name: "+strMethodName);
		ExtentTest child = parentTest.get().createNode(strMethodName);
		test.set(child);
		reporter.initlogger(child);
//		parent = parentTest.get().createNode(strMethodName);
//		test.set(parent);
//		reporter.initlogger(parent);
		System.out.println("=====================================");
//		methodnum=methodnum+1;
//		lastPriority=lastPriority+1;
	}


	@AfterMethod(alwaysRun=true)
	public void afterMethod(ITestResult result) {
		if (result.getStatus() == ITestResult.FAILURE) {
			reporter.log(Status.FAIL, result.getThrowable().getMessage());
			test.get().fail(MarkupHelper.createLabel("TEST CASE FAILED", ExtentColor.PINK));
			}
		else if (result.getStatus() == ITestResult.SKIP)
				test.get().skip(MarkupHelper.createLabel(result.getThrowable().toString()/* getLocalizedMessage() */, ExtentColor.GREY));
		else if (result.getStatus() == ITestResult.SUCCESS)
			test.get().pass(MarkupHelper.createLabel("TEST CASE PASSED SUCCESSFULLY", ExtentColor.TEAL));
		else if (result.getStatus() == ITestResult.CREATED)
			test.get().skip(MarkupHelper.createLabel("TEST CASE SKIPPED", ExtentColor.GREY));
		System.out.println("=====================================");
		System.out.println("Completed executing test:" + result.getMethod().getMethodName());
		System.out.println("==========================================");
		
//		driver.manage().deleteAllCookies();
		driver.close();
		driver.quit();
	
	
	}

	@AfterClass(alwaysRun=true)
	public void afterClass() {
	
	}

	@AfterSuite(alwaysRun=true)
	public void afterSuite() {
		try {
			//		Runtime.getRuntime().exec("taskkill /F /IM IEDriverServer.exe");
					Runtime.getRuntime().exec("taskkill /F /IM chromedriver.exe");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					System.out.println(e.getMessage());
				}
		ExtentManager.getInstance().flush();
		/*CommonUtil cu = new CommonUtil();
		cu.sendEmail(ConstUtil.EMAIL_TO, ConstUtil.EMAIL_CC, cu.zipFile(reportFile, true));*/
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



	public void logOut() {
		reporter.log(Status.INFO, "Logging out and closing the window");
		driver.manage().deleteAllCookies();
		driver.close();
	}
	
	/*
		} if (browser.equalsIgnoreCase("headless")) {
			WebDriverManager.chromedriver().setup();
			ChromeOptions options = new ChromeOptions();
			options.addArguments("--headless");
			options.addArguments("--disable-gpu");
			driver = new ChromeDriver(options);
		} 
		
	Make sure that browser zoom level is set to 100%; IE browser View menu --> Zoom --> 100%
	Make sure that "protection mode" is enabled for all the zones; IE browser Tools menu --> Internet Options --> Security tab --> navigate to Internet, 
	Local intranet, Trusted sites and Restricted site and check Protection Mode checkbox is checked

	}*/
	
	/**
	 * 
	 * @return
	 */
	protected String loadBrowser()
	{
		String urlKey = ConstUtil.CASAURL;
//		driver.manage().deleteAllCookies();
		driver.manage().window().maximize();
		driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
		try {
		driver.get(urlKey);
	} catch (Exception ignored) {
	    System.out.println("ignored: "+ignored);
	}
		if (driver.getCurrentUrl().isEmpty()
				|| !driver.getCurrentUrl().startsWith(urlKey.toLowerCase())) {
			driver.get(urlKey);
		}
		return driver.getTitle();
//		return "all good";
	}
	
}
