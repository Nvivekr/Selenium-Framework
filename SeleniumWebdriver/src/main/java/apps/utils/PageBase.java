package apps.utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import apps.reporting.Reporter;

public class PageBase {
	
	protected Reporter reporter;
	public WebDriver driver;
	protected CommonUtil commonUtil;
	protected WebDriverWait wait;	
	
	public PageBase(){
		reporter = TestBase.rm.getReporter();
		driver = getDriver();
		commonUtil = new CommonUtil(driver);
		wait = new WebDriverWait(driver, 50);
		
		}

	private WebDriver getDriver() {
		return driver = TestBase.getDriver();
		}
	}
